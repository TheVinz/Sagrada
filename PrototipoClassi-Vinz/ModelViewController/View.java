

public class View{
	private Controller playerController;
	private Player player;
	public View(Player player, Controller controller){
		this.player=player;
		this.playerController=controller;
	}
	//Appena la partita può iniziare la view viene inizializzata
	public void init(){
		boolean invalid=true;
		Scanner sc= new Scanner(System.in);
		String text=player.getWindowFrameChoises();
		while(invalid){
			System.out.println(text);
			System.out.println("Quale gradisci?\n>>> ");
			int choice=sc.nextInt();
			if(choice>3 || choice<0){
				System.out.println("Non ci siamo, non ci siamo...");
			}
			else invalid=false;
		}
		player.setFrame(choice);
		System.out.println(player.toString() + " ready!");
	}
	//Stampa a schermo il nuovo stato a seguito di una mossa o altra roba
	public void print(String message){
		System.out.println(message);
		System.out.println("La tua vetrata \n");
		System.out.println(player.getFrameState();
	}
}