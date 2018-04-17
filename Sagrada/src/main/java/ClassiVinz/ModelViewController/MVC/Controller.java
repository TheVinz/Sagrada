//Sul server

public class Controller{
	private Model model;
	public Controller(Model model){
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
		ToolCard toolCard=model.getToolCard(card);
		toolCard.doAbility(model);
	}
}