

public class GameController{
	private Queue<PlayerController> playerControllers;
	private Model model;
	public GameController(Collection<PlayerController> pc, Model model){
		this.playerControllers=new ArrayDequeue<PlayerController>(pc);
		this.model=model;
	}
	public void round(){
		Iterator<PlayerController> controllersIter=playerControllers.iterator();
		model.startRound();
		while(controllersIter.hasNext()){
			controllersIter.next().play();
		}
		controllersIter=playerControllers.descendingIterator();
		while(controllersIter.hasNext()){
			controllersIter.next().play();
		}
		endRound();
	}
}