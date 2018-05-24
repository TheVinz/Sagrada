package server.controller;
import common.exceptions.InvalidMoveException;
import common.response.Response;
import server.model.Model;
import server.model.state.ModelObject.ModelObject;
import server.model.state.boards.windowframe.WindowFrameList;
import server.model.state.player.Player;
import server.viewproxy.ViewProxy;

public class Controller {
	private Model model;
	private Player player;
	private ViewProxy view;

	private PlayerState currentState;

	public Controller(Model model, Player player, ViewProxy view){
		this.player=player;
		this.model=model;
		this.view=view;
		currentState=new WaitingState(player, model);
	}

	public int selectObject(ModelObject o) throws InvalidMoveException {
		PlayerState temp = null;
		if(player.isActive()) {
			try {
				temp = currentState;
				currentState = currentState.selectObject(o);
			} catch(InvalidMoveException e){
				currentState=new WaitingState(player, model);
				Response.ERROR.setMessage(e.getMessage());
				view.notifyNextParameter(Response.ERROR);
				throw e;
			}
			if(player.isDiceMoved() && player.isToolCardUsed())
			{
				endTurn();
			}
			if(temp.nextParam() != null) {
				view.notifyNextParameter(temp.nextParam());
			}
		}
		return 0;
	}

	public PlayerState getCurrentState(){
		return this.currentState;
	}

	public void endTurn() {
		if(player.isActive()) {
			currentState = new WaitingState(player, model);
			model.endTurn(player);
		}
	}

    public void windowFrameChoice(WindowFrameList[] windowFrameLists) {
		currentState=new SelectingWindowFrame(player, model, windowFrameLists);
    }

}
