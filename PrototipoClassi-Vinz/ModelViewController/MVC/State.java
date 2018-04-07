//Sul server

public class State{
	private Bag bag;
	private DraftPool draftPool;
	private RoundTrack roundTrack;
	private List<Player> players;
	private final ToolCard[] toolCards;
	private final PublicObjectiveCard[] publicObjectiveCards;
	public State(Collection<Player> players){
		this.bag=new Bag();
		this.draftPool= new DraftPool(playersNumber);
		this.roundTrack= new RoundTrack();
		this.players=new ArrayList<Player>(players);
		this.toolCards=ToolCard.getToolCards();
		this.publicObjectiveCards=ObjectiveCard.getPublicObjectiveCards();
	}
	public void startRound(){
		draftPool.drow();
	}
	public String printDraftPool(){
		return draftPool.toString();
	}
	public Dice getDraftPoolDice(int dice){
		return draftPool.get(dice)
	}
	public ToolCard[] getToolCards(){
		return Arrays.clone(this.toolCards);
	}
	public ToolCard getToolCard(int card){
		return ToolCards[card];
	}
}