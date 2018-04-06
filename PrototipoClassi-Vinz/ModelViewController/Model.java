

public class Model{
	private final State gameState;
	private final View[] playerViews;
	public Model(State state, Collection<View> views){
		gameState=state;
		playerViews=views.toArray();
	}
	public void init(){
		for(View v : playerViews){
			v.init();
		}
	}
	public void startRound(){
		state.startRound();
	}
}