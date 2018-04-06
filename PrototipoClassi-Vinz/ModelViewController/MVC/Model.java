//Sul server

public class Model{
	private final State gameState;
	private final Queue<View> playerViews;
	public Model(State state, Collection<View> views){
		gameState=state;
		playerViews=new ArrayDequeue(views);
	}
	public void init(){
		for(View v : playerViews){
			v.init();
		}
	}
	public void startRound(){
		gameState.startRound();
		Iterator<View> viewIter = playerViews.iterator();
		while(viewIter.hasNext()){
			viewIter.next().yourTurn();
		}
	}

	public void notify(String message){
		String stateRep=gameState.toString();
		for(View v: playerViews.toArray()){
			v.print(message + "\n\n" + stateRep);
		}
	}

	public String printDraftPool(){
		gameState.printDraftPool();
	}

	public Dice getDraftPoolDice(int dice){
		return state.getDraftPoolDice(int dice);
	}

	public ToolCard[] getToolCards(){
		return state.getToolCards();
	}

	public ToolCard getToolCard(int card){
		return state.getToolCard(int card);
	}
}