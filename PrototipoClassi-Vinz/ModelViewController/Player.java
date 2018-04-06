

public class Player{
	private String name;
	private int favorTokens;
	private WindowFrame windowFrame;
	private PrivateObjectiveCard privateObjectiveCard;
	private WindowFrame[] windowFrameChoises;
	public Player(WindowFrame[] wfc, PrivateObjectiveCard poc){
		this.windowFrameChoises=wfc;
		this.privateObjectiveCard=poc;
	}
	//Selezione della vetrata tra quelle proposte
	public String getWindowFrameChoises(){
		StringBuffer buffer= new StringBuffer();
		buffer.append("Scegli una griglia vetrata: \n");
		for(int i=0; i<windowFrameChoises.length; i++){
			buffer.append("Scapolottina numero " + i + "\n" windowFrameChoises.toString()+"\n\n");
		}
		return buffer.toString();
	}
	public void setFrame(int i){
		if(windowFrame==null) windowFrame=windowFrameChoises[i];
	}

	public String getFrameState(){
		return windowFrame.getState();
	}

	@Override
	public String toString(){
		return this.name;
	}
}