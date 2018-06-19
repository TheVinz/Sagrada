package server.controller;
import common.exceptions.InvalidMoveException;
import common.exceptions.WrongParameter;
import common.response.Response;
import server.model.Model;
import server.model.SinglePlayerModel;
import server.model.state.ModelObject.ModelObject;
import server.model.state.boards.windowframe.WindowFrameList;
import server.model.state.player.Player;
import server.model.state.utilities.Timer;
import server.model.state.utilities.TimerObserver;
import server.viewproxy.ViewProxy;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Controller implements TimerObserver {
	private Model model;
	private Player player;
	private ViewProxy view;

	private PlayerState currentState;


	public Controller(Model model, Player player, ViewProxy view){
		this.player=player;
		this.model=model;
		this.view=view;
		currentState=new WaitingState(player, model);
		player.setTimer(new Timer(this, 10));
	}

	public synchronized void selectObject(ModelObject o) { //synchronized? e si eh
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

	public PlayerState getCurrentState(){
		return this.currentState;
	}

	public void endTurn() {
		if(player.isActive()) {
			model.endTurn(player);
			currentState.abort();
			currentState = new WaitingState(player, model);
			player.getTimer().stop();
		}
	}

    public void windowFrameChoice(WindowFrameList[] windowFrameLists) {
		currentState=new SelectingWindowFrame(player, model, windowFrameLists);
    }


    public void isDiceMove(){
		if(player.isDiceMoved())
			view.notifyError("You have already moved a dice!\n");
		else
			view.notifyNextParameter(Response.DRAFT_POOL_MOVE);
	}

	public synchronized void notifyTimeout() {
		if(player.getTimer().getBlinker().equals(Thread.currentThread())) {
			timeFinished();
			endTurn();
		}
	}

	public synchronized void timeFinished(){
		//view.notifyNextParameter(Response.SUSPENDED);
		model.suspendPlayer(player);
	}

	@Deprecated
	public void reinsertPlayer() {
		model.reinsertPlayer(player);
		new Thread(() -> view.ping()).start();
	}
}
