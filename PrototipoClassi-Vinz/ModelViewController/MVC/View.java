//Sul clients

public class View{
	private Controller playerController;
	private Model model;
	public View(Controller controller){
		this.playerController=controller;
	}

	public void bindModel(Model model){
		this.model=model;
	}
	//Appena la partita può iniziare la view viene inizializzata
	public void init(){
		Scanner sc= new Scanner(System.in);
		model.getWindowFrameChoises();
		System.out.println("Quale gradisci?\n>>> ");
		int choice=sc.nextInt();
		while(choice>3 || choice<0){
			choice=invalidInt(sc);
		}
		playerController.setFrame(choice);
		sc.close();
		System.out.println("Ready!!");
	}

	private int invalidInt(Scanner sc){
		System.out.println("Non ci siamo, non ci siamo...\n>>>");
		return sc.nextInt();
	}

	private boolean invalidMoveRoutine(){
		Scanner sc=new Scanner(System.in);
		System.out.println("Riprovare?(s/n)...\n>>>");
		char action=sc.nextChar();
		if(char=='s') return true;
		else return false;
		}

	public void yourTurn(){
		Scanner sc= new Scanner(System.in);
		char answer;;
		System.out.println("\n\nTocca a te!\n");
		model.printDraftPool();
		System.out.println("Piazzare un dado?(s/n)...\n\n");
		System.out.println(board);
		answer=sc.nextChar();
		if(answer=='s') moveDice(board);
		System.out.println("Usare una carta strumento?");
		model.printToolCards();
		answer=sc.nextChar();
		if(answer=='s') useToolCard(board);
		System.out.println("FineTurno");
	}

	private void moveDice(){
		Scanner sc=new Scanner(System.in);
		boolean loop=true;
		int input,i,j;
		while(loop){
			System.out.println("Scegli un dado\n>>>");
			input=sc.nextInt();
			System.out.println("Scegli una posizione\nCoordinata x >>>");
			i=sc.nextInt();
			System.out.println("Coordinata y >>>");
			j=sc.nextInt();
			try{
				playerController.moveDice(input,i,j,player);
			}
			catch(InvalidMoveException e){
				e.printMessage();
				loop=invalidMoveRoutine();
			}
		}
	}

	private void useToolCard(){
		Scanner sc=new Scanner(System.in);
		int choice=sc.nextInt();
		playerController.useToolCard(choice);
	}

	//Stampa a schermo il nuovo stato a seguito di una mossa o altra roba
	public void print(String message){
		System.out.println(message);
	}
}