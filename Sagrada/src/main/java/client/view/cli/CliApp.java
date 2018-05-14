package client.view.cli;

import java.rmi.RemoteException;
import java.util.Scanner;
import client.view.cli.cliphasestate.CliPhaseState;
import client.view.cli.cliphasestate.MenuPhase;
import client.view.cli.cliphasestate.WaitingPhase;
import client.view.cli.cliphasestate.WindowFrameChoiceState;
import common.RemoteMVC.RemoteController;


public class CliApp {

    private RemoteController remoteController;
    private CliPhaseState currentState;
    private int id;
    Scanner scanner = new Scanner(System.in);


    public CliApp(RemoteController remoteController){
        this.remoteController=remoteController;
        this.currentState=new WaitingPhase();
    }

    public int getId(){
        return this.id;
    }
    public void setId(int id){
        this.id=id;
    }

    public void windowFrameChoice(){
        System.out.print("\nSelect a window frame\n>>>");
        this.currentState=new WindowFrameChoiceState(remoteController);
    }
    public void startTurn(){
        this.currentState= new MenuPhase(remoteController);
        CliDisplayer.getDisplayer().displayText("(insert M to undo the operation and see the menu)\n");
    }
    public void waitTurn() {
        this.currentState=new WaitingPhase();
    }

    public void mainLoop(){
        String input = "";
        while(!input.equals("quit")){
            input = scanner.nextLine();
            try{
                currentState.handle(input);
            }catch (RemoteException e){
                CliDisplayer.getDisplayer().displayText("Network problem\n");
                System.exit(1);
            }
        }
    }
}
