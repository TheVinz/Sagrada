package server.controller;
import common.exceptions.InvalidMoveException;
import common.exceptions.WrongParameter;
import common.response.Response;
import server.settings.Settings;
import server.model.Model;
import server.model.state.ModelObject.ModelObject;
import server.model.state.boards.windowframe.WindowFrameList;
import server.model.state.player.Player;
import server.model.state.utilities.Timer;
import server.model.state.utilities.TimerObserver;
import server.viewproxy.ViewProxy;

/**
 * The <tt>Controller</tt> class is the controller of our MVC pattern. There is one instance of <tt>Controller</tt> class for each
 * player logged in. This class uses a State pattern to translate the player inputs into moves for the game logic.
 */
public class Controller implements TimerObserver {
	private Model model;
	private Player player;
	private ViewProxy view;

	private PlayerState currentState;

	/**
	 * Creates a new instance of <tt>Controller</tt> with the {@link Model} of his MVC, the {@link Player} that is sending
	 * command to this controller and the {@link ViewProxy} this controller have to send {@link Response}s.
	 * @param model the MVC Model.
	 * @param player the player that in sending inputs to this controller.
	 * @param view the view handling the connection to the client.
	 */
	public Controller(Model model, Player player, ViewProxy view){
		this.player=player;
		this.model=model;
		this.view=view;
		currentState=new WaitingState(player, model);
		player.setTimer(new Timer(this, Settings.getPlayerTimeout()));
	}

	/**
	 * Sends the {@link ModelObject} received by the ViewProxy and send it to the current phase, modifying it if it is necessary.
	 * Then sends back a response to the client to describe the result of the input or to require a new parameter for the next move.
	 * Eventually handles the exceptions thrown by the model sending back to client an error message.
	 * @param o the object represented by the client input.
	 */
	public synchronized void selectObject(ModelObject o) {
		if(model.getState().isGameFinished() && model.isSingleplayer())
			currentState = new SelectingPrivateObjectiveCard(player, model);
		PlayerState temp;
		if (player.isActive() || currentState.getClass().equals(SelectingPrivateObjectiveCard.class)) {
			try {
				temp = currentState;
				currentState = currentState.selectObject(o);
			} catch (InvalidMoveException e) {
				currentState = new WaitingState(player, model);
				view.notifyError(e.getMessage());
				return;
			} catch (WrongParameter e) {
				view.notifyWrongParameter(e.getMessage());
				return;
			}
			if (temp.nextParam() != null) {
				view.notifyNextParameter(temp.nextParam());
			}
			else if(currentState.nextParam() != null)
				view.notifyNextParameter(currentState.nextParam());
			if (player.isDiceMoved() && player.isToolCardUsed()) {
				endTurn();
			}
		}
	}

	/**
	 * If the player associated to this controller is active, ends his turn closing the current phase and setting the current
	 * state to a new {@link WaitingState}.
	 */
	public void endTurn() {
		if(player.isActive()) {
			model.endTurn(player);
			currentState.abort();
			currentState = new WaitingState(player, model);
			player.getTimer().stop();
		}
	}

	/**
	 * Initializes the current phase with a new {@link SelectingWindowFrame} state with the indicated {@link WindowFrameList}s as
	 * window frame choices.
	 * @param windowFrameLists the frames that the client can choose.
	 */
    public void windowFrameChoice(WindowFrameList[] windowFrameLists) {
		currentState=new SelectingWindowFrame(player, model, windowFrameLists);
    }


	/**
	 * If the player has not moved a dice yet, notifies a move from the draft pool to the window frame as next parameter,
	 * sends an error message otherwise.
	 */
	public void isDiceMove(){
		if(player.isDiceMoved())
			view.notifyError("You have already moved a dice!\n");
		else
			view.notifyNextParameter(Response.DRAFT_POOL_MOVE);
	}

	/**
	 * If the {@link Timer} notifying this timeout is this controller's timer's blinker then the player is suspended.
	 */
	public synchronized void notifyTimeout() {
		if(player.getTimer().getBlinker().equals(Thread.currentThread())) {
			view.setPing(false);
			timeFinished();
			endTurn();
		}
	}

	/**
	 * Suspends the player.
	 */
	public synchronized void timeFinished(){
		model.suspendPlayer(player);
	}

}
