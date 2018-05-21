package client.view.cli;

import java.rmi.RemoteException;
import java.util.Scanner;
import client.view.cli.cliphasestate.CliPhaseState;
import client.view.cli.cliphasestate.MenuPhase;
import client.view.cli.cliphasestate.WaitingPhase;
import client.view.cli.cliphasestate.WindowFrameChoiceState;
import common.RemoteMVC.RemoteController;
import static  common.command.GameCommand.*;


public class CliApp {

    private RemoteController remoteController;
    private CliPhaseState currentState;
    private CliState cliState;
    private int id;
    private int nextParam;
    Scanner scanner = new Scanner(System.in);


    public CliApp(RemoteController remoteController) {
        this.remoteController = remoteController;
        this.currentState = new WaitingPhase();
        this.cliState = new CliState();
        CliDisplayer.getDisplayer().setCliState(cliState);
    }

    public int getId(){
        return this.id;
    }
    public void setId(int id){
        this.id=id;
    }

    public void windowFrameChoice(){
        System.out.print("\nSelect a window frame\n>>>");
        synchronized (this) {
            this.currentState = new WindowFrameChoiceState(remoteController);
        }
    }
    public void startTurn(){
        synchronized (this) {
            this.currentState = new MenuPhase(remoteController, this);
        }
        CliDisplayer.getDisplayer().displayText("(insert M to undo the operation and see the menu)\n");
    }
    public void waitTurn() {
        synchronized (this) {
            this.currentState = new WaitingPhase();
        }
    }

    public void mainLoop(){
        String input = "";
        while(!input.equals("quit")){
            input = scanner.nextLine();
            try{
                synchronized (this) {
                    currentState = currentState.handle(input);
                }
            }catch (RemoteException e){
                CliDisplayer.getDisplayer().displayText("Network problem\n");
                System.exit(1);
            }
        }
    }

    public int getNextParam() {
        return nextParam;
    }

    public void setNextParam(int nextParam) {
        this.nextParam = nextParam;
        printNextParam();
    }

    private void printNextParam(){
        switch (nextParam){
            case WINDOW_FRAME_CELL:
                CliDisplayer.getDisplayer().displayText("Select the row of a Window Frame Cell: ");
                break;
            case DRAFT_POOL_CELL:
                CliDisplayer.getDisplayer().displayText("Select a Draft Pool Cell: ");
                break;
            case ROUND_TRACK_CELL:
                CliDisplayer.getDisplayer().displayText("Selec a Round Track Cell: ");
                break;
            default:
                return;
        }
    }

}
