package client.view.cli;

import java.rmi.RemoteException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import client.view.cli.cliphasestate.*;
import common.RemoteMVC.RemoteController;
import common.command.GameCommand;
import common.response.Response;

public class CliApp {

    private RemoteController remoteController;
    private CliPhaseState currentState;
    private CliState cliState;
    private int id;
    private boolean waitingPhase=true;
    private boolean movingDice=false;
    private ArrayDeque<GameCommand> commandBuffer = new ArrayDeque<GameCommand>();
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

    public void moveFromDraftPool()
    {
        setCurrentState(new SelectingDraftPoolCell());
        synchronized (this){
            while(getCommandBufferSize() == 0) {
                try {
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        sendCommand();
        if(!movingDice){
            addCommandToBuffer(new GameCommand(Response.WINDOW_FRAME, CliState.getCliState().getActivePlayer().getId()));
            sendCommand();
        }
        setCurrentState(new SelectingWindowFrameCell());
        sendCommand();
        setWaitingPhase(true);
        movingDice =false;
    }

    public void setCurrentState(CliPhaseState currentState){
        this.currentState = currentState;
        setWaitingPhase(false);
    }


    public void mainLoop(){
        String input = "";
        while(!input.equals("quit")){
           // System.out.println("error0\n");
            synchronized (this) {
                while (waitingPhase) {
                    try {
                        wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
            //System.out.println("error1\n");
            input = scanner.nextLine();
            //System.out.println("error2\n");
            try {
                currentState.handle(input);
            } catch (InvalidInput e) {
                CliDisplayer.getDisplayer().displayText(e.getMessage());
                currentState = currentState.reset();
            }
            synchronized (this){
                notifyAll();
            }


        }
    }


    public void setWaitingPhase(boolean waitingPhase) {
        this.waitingPhase = waitingPhase;
        if(waitingPhase)
            CliDisplayer.getDisplayer().displayText("You can't insert anything now\n");
        else
            synchronized (this) {
                notifyAll();
            }
    }

    public void addCommandToBuffer(GameCommand gameCommand){
        commandBuffer.add(gameCommand);
    }

    public int getCommandBufferSize(){
        return commandBuffer.size();
    }

    public void sendCommand() {
        synchronized (this){
        while(commandBuffer.isEmpty()) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }}
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

    public void moveFromWindowFrame() {

        addCommandToBuffer(new GameCommand(Response.WINDOW_FRAME, CliState.getCliState().getActivePlayer().getId()));
        sendCommand();
        setCurrentState(new SelectingWindowFrameCell());
        synchronized (this){
            while(getCommandBufferSize() == 0) {
                try {
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        sendCommand();
        addCommandToBuffer(new GameCommand(Response.WINDOW_FRAME, CliState.getCliState().getActivePlayer().getId()));
        sendCommand();
        setCurrentState(new SelectingWindowFrameCell());
        sendCommand();

    }


    public void setMovingDice(boolean movingDice) {
        this.movingDice = movingDice;
    }
}
