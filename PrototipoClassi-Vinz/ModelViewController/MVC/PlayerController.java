//Sul client

public class PlayerController {
	private GameController gameController;
	public PlayerController(){}

	public bindGameController(GameController gc){
		this.GameController=gc;
	}

	public void setFrame(int choice){
		player.setFrame(int choice);
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