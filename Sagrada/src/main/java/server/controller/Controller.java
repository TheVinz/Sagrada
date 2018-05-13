package server.controller;
import common.exceptions.InvalidMoveException;
import server.model.Model;
import server.model.state.ModelObject;
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

	public void selectObject(ModelObject o) throws InvalidMoveException {
		if(player.isActive()) {
			try {
				currentState = currentState.selectObject(o);
			} catch(InvalidMoveException e){
				currentState=new WaitingState(player, model);
				throw e;
			}
			if(player.isDiceMoved() && player.isToolCardUsed()) endTurn();
		}
	}

	public PlayerState getCurrentState(){
		return this.currentState;
	}

	public void endTurn(){
		currentState=new WaitingState(player, model);
		model.endTurn(player);
	}
}
