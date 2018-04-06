//Sul client

public class PlayerController {
	private GameController gameController;
	private Player player;
	public PlayerController(Player player){
		this.player=player;
	}
	public bindGameController(GameController gc){
		this.GameController=gc;
	}
	public String getWindowFrameChoises(){
		return player.getWindowFrameChoises();
	}
	public void setFrame(int choice){
		player.setFrame(int choice);
	}
	public String getPlayer(){
		return player.toString();
	}
	public String printDraftPool(){
		return gameController.printDraftPool();
	}

	public String printToolCards(){
		return gameController.printToolCards();
	}

	public void moveDice(int dice, int i, int j,Player player) throws InvalidMoveException{
		try{
			gameController.moveDice(dice,i,j,player);
		}
		catch(InvalidMoveException e){
			throw e;
		}
	}

	public void useToolCard(int card) throws InvalidMoveException{
		try {
			gameController.useToolCard(card);
		}
		catch(InvalidMoveException e){
			throw e;
		}
	}
}