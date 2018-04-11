//Sul server

public class Model{
	private State state;
	private View view;
	private Player player;

	public Model(){
	}
	public void setView(View view){
		this.view=view;
	}
	public void setPlayer(Player player){
		this.player=player;
	}
	public void init(){
		view.init();
	}
	public void doRound(){
		view.print("Tocca a te!!");
		view.doRound();
	}

	public void notify(State state){
		this.state=state;
		view.print(state.toString());
	}

	public void printDraftPool(){
		String draftPool=gameState.printDraftPool();
		view.print(draftPool);
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
		view.print(buffer.toString());
	}

	public ToolCard getToolCard(int card){
		return state.getToolCard(card);
	}

	public void getWindowFrameChoises(View view){
		WindowFrame[] windowFrameChoises=player.getWindowFrameChoises();
		StringBuffer buffer= new StringBuffer();
		for(int i=0; i<windowFrameChoises.length; i++){
			buffer.append(i + "\n" + windowFrameChoises[i].toString());
		}
		view.print(buffer.toString());
	}

}