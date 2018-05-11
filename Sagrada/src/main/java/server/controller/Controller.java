package server.controller;
import common.exceptions.InvalidMoveException;
import server.Model;
import server.state.ModelObject;
import server.state.boards.draftpool.DraftPoolCell;
import server.state.boards.roundtrack.RoundTrackCell;
import server.state.boards.windowframe.WindowFrame;
import server.state.boards.windowframe.WindowFrameCell;
import server.state.player.Player;
import server.state.toolcards.ToolCard;

public class Controller{
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
		}
	}

}
