//Sul server

public class GameController{
	private Model model;
	public GameController(Model model){
		this.model=model;
	}
	public String printDraftPool(){
		return model.printDraftPool();
	}
	public String printToolCards(){
		StringBuffer buffer=new StringBuffer();
		ToolCard toolCards=model.getToolCards();
		for(int i=0; i<toolCards.length;i++){
			buffer.append(toolCards[i].toString()+"\n");
		}
		return buffer.toString();
	}
	public void moveDice(int dice, int i, int j, Player player) throws InvalidMoveException{
		Dice dice=model.getDraftPoolDice(dice);
		WindowFrameSpace target=player.getWindowFrame(i,j);
		try{ 
			dice.move(target);
		}
		catch(InvalidMoveException e){
			throw e;
		}
		model.notify(player.toString + " ha spostato " + dice.toString() + " dalla riserva sulla sua vetrata.");
	}
	public void useToolCard(int card,View playerView) throws InvalidMoveException{
		ToolCard card=model.getToolCard(card);
		try{
			card.doAbility(model, playerView);
		}
		catch(InvalidMoveException e){
			throw e;
		}
	}
}