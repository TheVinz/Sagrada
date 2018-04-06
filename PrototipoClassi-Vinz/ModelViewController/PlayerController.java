

public class PlayerController {
	private GameController gameController;
	public PlayerController(){
		super();
	}
	public bindGameController(GameController gc){
		this.GameController=gc;
	}
}