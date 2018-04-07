//Sul server

public class Model{
	private State gameState;
	private Queue<View> playerViews;
	private View activePlayer;
	private Map<View,Player> viewPlayerMap;

	public Model(Map<View,Player> map){
		this.gameState=new state(map.values());
		this.viewPlayerMap=map;
		this.playerViews=new ArrayDequeue<View>(map.keySet());
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
			activePlayer=viewIter.next();
			activePlayer.yourTurn();
		}
	}

	public void notify(String message){
		String stateRep=gameState.toString();
		for(View v: playerViews.toArray()){
			v.print(message + "\n\n" + stateRep);
		}
	}

	public void printDraftPool(){
		String draftPool=gameState.printDraftPool();
		activePlayer.print(draftPool);
	}

	public Dice getDraftPoolDice(int dice){
		return state.getDraftPoolDice(int dice);
	}

	public void printToolCards(){
		ToolCard[] toolCards=state.getToolCards();
		StringBuffer buffer=new StringBuffer();
		for(int i=0; i<toolCards.length;i++){
			buffer.append(toolCards[i].toString()+"\n");
		}
		activePlayer.print(buffer.toString());
	}

	public ToolCard useToolCard(int card){
		ToolCard card=state.getToolCard(card);
		card.doAbility(this, activePlayer);
	}

	public void getWindowFrameChoises(View view){
		Player player = viewPlayerMap.get(view);
		view.print(player.getWindowFrameChoises());
	}

}