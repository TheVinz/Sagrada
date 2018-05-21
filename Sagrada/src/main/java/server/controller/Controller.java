package server.controller;
import common.exceptions.InvalidMoveException;
import common.response.Response;
import server.model.Model;
import common.ModelObject;
import server.model.state.boards.windowframe.WindowFrameList;
import server.model.state.player.Player;

public class Controller {
	private Model model;
	private Player player;

	private PlayerState currentState;

	public Controller(Model model, Player player){
		this.player=player;
		this.model=model;
		currentState=new WaitingState(player, model);
	}

	public int selectObject(ModelObject o) throws InvalidMoveException {
		if(player.isActive()) {
			try {
				currentState = currentState.selectObject(o);
			} catch(InvalidMoveException e){
				currentState=new WaitingState(player, model);
				throw e;
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

	public int endTurn() {
		if(player.isActive()) {
			currentState = new WaitingState(player, model);
			model.endTurn(player);
		}
		return 0;
	}

    public void windowFrameChoice(WindowFrameList[] windowFrameLists) {
		currentState=new SelectingWindowFrame(player, model, windowFrameLists);
    }

}
