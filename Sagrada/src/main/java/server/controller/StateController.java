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

public class StateController {
	private Model model;
	private Player player;

	private PlayerState currentState;

<<<<<<< HEAD:Sagrada/src/main/java/server/controller/StateController.java
/*	private boolean moveDone;
	private boolean toolCardUsed;

	private ToolCard activeToolCard;
	private DraftPoolCell picked;*/

	public StateController(Model model, Player player){
=======
	public Controller(Model model, Player player){
>>>>>>> 1fec6cf48718e7abd567f081ad211c86594442c1:Sagrada/src/main/java/server/controller/Controller.java
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
