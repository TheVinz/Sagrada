package server.controller;
import common.exceptions.InvalidMoveException;
import common.exceptions.WrongParameter;
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

	public int selectObject(ModelObject o) throws InvalidMoveException, WrongParameter {
		PlayerState temp = null;
		if(player.isActive()) {
			try {
				temp = currentState;
				currentState = currentState.selectObject(o);
			} catch(InvalidMoveException e){
				currentState=new WaitingState(player, model);
				view.notifyError(e.getMessage());
				throw e;
			} catch(WrongParameter e){
				view.notifyError(e.getMessage());
				view.notifyNextParameter(temp.nextParam());
				throw e;
			}
			if(temp.nextParam() != null) {
				view.notifyNextParameter(temp.nextParam());
			}
			if(player.isDiceMoved() && player.isToolCardUsed())
			{
				endTurn();
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

    public void isDiceMove(){
		if(player.isDiceMoved())
			view.notifyError("You have already moved a dice!\n");
		else
			view.notifyNextParameter(Response.DRAFT_POOL_MOVE);
	}

}
