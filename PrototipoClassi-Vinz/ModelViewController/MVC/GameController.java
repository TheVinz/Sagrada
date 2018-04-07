//Sul server

public class GameController{
	private Model model;
	public GameController(Model model){
		this.model=model;
	}
	public void printDraftPool(){
		model.printDraftPool();
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
	public void useToolCard(int card) throws InvalidMoveException{
		try{
			model.useToolCard(card);
		}
		catch(InvalidMoveException e){
			throw e;
		}
	}
}