package server;
import common.exceptions.InvalidMoveException;
import server.state.boards.draftpool.DraftPoolCell;
import server.state.boards.roundtrack.RoundTrackCell;
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
			setToolCardParameter(cell);
			if(!activeToolCard.hasNext()) {
				activeToolCard=null;
				toolCardUsed=true;
			}
		}
		else if(picked==null && !moveDone) picked=cell;
    }

    /*
    NB quando alla tool card serve una cella questa deve essere sempre accompagnata dalla window frame
    alla quale appartiene per poter valutare l' effettiva validità della mossa mediante la classe
    GameRules
    */

    public void windowFrameClick(WindowFrame windowFrame, WindowFrameCell cell) throws InvalidMoveException {
		if(activeToolCard!=null){
			setToolCardParameter(windowFrame);
			setToolCardParameter(cell);
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

	public void roundTrackClick(RoundTrackCell cell) throws InvalidMoveException {
    	if(activeToolCard!=null)
    		setToolCardParameter(cell);

	}


	private void setToolCardParameter(Object o) throws InvalidMoveException {
		try {
			activeToolCard.setParameter(o);
		} catch (InvalidMoveException e) {
			activeToolCard=null;
			throw e;
		}
		if(!activeToolCard.hasNext()) activeToolCard=null;
	}

	//TODO il controller deve poter verificare se il Player dispone di abbastanza segnalini favore per poter utilizzare la carta
	public void useToolCard(int index) throws InvalidMoveException {
		if(!toolCardUsed) {
			this.activeToolCard=model.getState().getToolCard(index);
			activeToolCard.start(this.player);
			if(!activeToolCard.hasNext()) this.activeToolCard=null;
		}
		else throw new InvalidMoveException("Only one tool card per turn");
	}
}