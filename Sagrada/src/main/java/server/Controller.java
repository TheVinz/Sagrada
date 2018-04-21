package server;
import common.exceptions.InvalidMoveException;
import server.state.boards.draftpool.DraftPoolCell;
import server.state.boards.windowframe.WindowFrame;
import server.state.boards.windowframe.WindowFrameCell;
import server.state.player.Player;
import server.state.toolcards.ToolCard;

public class Controller{
	private Model model;
	private Player player;

	private boolean moveDone;
	private boolean toolCardUsed;

	private ToolCard activeToolCard;
	private DraftPoolCell picked;

	public Controller(Model model, Player player){
		this.player=player;
		this.model=model;
	}

	public void startTurn(){
		moveDone=false;
		toolCardUsed=false;
		activeToolCard=null;
		picked=null;
	}

    public void draftPoolClick(DraftPoolCell cell) throws InvalidMoveException {
		if(activeToolCard!=null){
			activeToolCard.setParameter(cell);
			if(!activeToolCard.hasNext()) {
				activeToolCard=null;
				toolCardUsed=true;
			}
		}
		else if(picked==null && !moveDone) picked=cell;
    }

    public void windowFrameClick(WindowFrame windowFrame, WindowFrameCell cell) throws InvalidMoveException {
		if(activeToolCard!=null){
			activeToolCard.setParameter(windowFrame);
			activeToolCard.setParameter(cell);
			if(!activeToolCard.hasNext()) {
				activeToolCard=null;
				toolCardUsed=true;
			}
		}
		else if(picked != null){
			model.move(player, picked, cell);
			moveDone=true;
		}
	}

	public void roundTrackClick(int round, int index){

	}

	public void useToolCard(int index){
		if(!toolCardUsed) {
			this.activeToolCard=model.getState().getToolCard(index);
			activeToolCard.start();
		}
	}
}