//Sul clients

public class View{
	private Controller controller;
	private Model model;
	public View(Controller controller){
		this.controller=controller;
	}

	public void bindModel(Model model){
		this.model=model;
	}
	//Appena la partita può iniziare la view viene inizializzata
	public void init(){
		Scanner sc= new Scanner(System.in);
		model.getWindowFrameChoises();
		int choice=sc.nextInt();
		controller.setFrame(choice);
		sc.close();
	}

	public void yourTurn(){
		Scanner sc= new Scanner(System.in);
		char answer;;
		model.printDraftPool();
		System.out.println("Piazzare un dado?(s/n)...\n\n");
		answer=sc.nextChar();
		if(answer=='s') moveDice();
		System.out.println("Usare una carta strumento?");
		model.printToolCards();
		answer=sc.nextChar();
		if(answer=='s') useToolCard();
		System.out.println("FineTurno");
	}

	private void moveDice(){
		Scanner sc=new Scanner(System.in);
		int input,i,j;
		System.out.println("Scegli un dado\n>>>");
		input=sc.nextInt();
		System.out.println("Scegli una posizione\nCoordinata x >>>");
		i=sc.nextInt();
		System.out.println("Coordinata y >>>");
		j=sc.nextInt();
		try{
			controller.moveDice(input,i,j,player);
		}
		catch(InvalidMoveException e){
			e.printMessage();
		}
	}

	private void useToolCard(){
		Scanner sc=new Scanner(System.in);
		int choice=sc.nextInt();
		controller.useToolCard(choice);
	}

	//Stampa a schermo il nuovo stato a seguito di una mossa o altra roba
	public void print(String message){
		System.out.println(message);
	}
}