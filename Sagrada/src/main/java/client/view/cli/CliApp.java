package client.view.cli;

import java.rmi.RemoteException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import client.view.cli.cliphasestate.CliPhaseState;
import client.view.cli.cliphasestate.InvalidInput;
import client.view.cli.cliphasestate.MenuPhase;
import common.RemoteMVC.RemoteController;
import common.command.GameCommand;


public class CliApp {

    private RemoteController remoteController;
    private CliPhaseState currentState;
    private int id;
    private boolean waitingPhase;
    private ArrayDeque<GameCommand> commandBuffer = new ArrayDeque<GameCommand>();
    private SynchronizedObserver synchronizedObserver;

    Scanner scanner = new Scanner(System.in);

    private static CliApp cliApp;

    public static CliApp getCliApp(){
        if(cliApp == null)
            cliApp = new CliApp();
        return cliApp;
    }

    public int getId(){
        return this.id;
    }
    public void setId(int id){
        this.id=id;
    }

    public void setCurrentState(CliPhaseState currentState){
        this.currentState = currentState;
        CliApp.getCliApp().setWaitingPhase(false);

    }

    public void setSynchronizedObserver(SynchronizedObserver synchronizedObserver){
        this.synchronizedObserver = synchronizedObserver;
    }

    synchronized public void mainLoop(){
        String input = "";
        while(!input.equals("quit")){
            System.out.println("error0\n");
            while(waitingPhase){
                try {
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("error1\n");
            input = scanner.nextLine();
            System.out.println("error2\n");
            try {
                currentState.handle(input);
            } catch (InvalidInput e) {
                CliDisplayer.getDisplayer().displayText(e.getMessage());
                currentState = currentState.reset();
            }
            if(synchronizedObserver!=null)
                synchronizedObserver.notifyThis();


        }
    }


    synchronized public void setWaitingPhase(boolean waitingPhase) {
        this.waitingPhase = waitingPhase;
        if(waitingPhase)
            CliDisplayer.getDisplayer().displayText("You can't insert anything now\n");
        else
            notifyAll();
    }

    public void addCommandToBuffer(GameCommand gameCommand){
        commandBuffer.add(gameCommand);
    }

    public int getCommandBufferSize(){
        return commandBuffer.size();
    }

    public void sendCommand() {
        try {
            remoteController.command(commandBuffer.poll());
        }catch (RemoteException e){
            CliDisplayer.getDisplayer().displayText(e.getMessage());
            currentState = new MenuPhase();
        }

    }

    public void setRemoteController(RemoteController remoteController) {
        this.remoteController = remoteController;
    }
}
