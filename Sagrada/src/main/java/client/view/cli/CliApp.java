package client.view.cli;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import client.view.cli.cliphasestate.*;
import common.RemoteMVC.RemoteController;
import common.command.GameCommand;
import common.response.Response;

/*
Mettere restrizioni sulla prima mossa*/

/*Input per annullare la mossa (per esempio toolcard) */

/*lens cutter, quando mi chiede di selezionare un dado dalla roundtrack, al primo tunro come la gestiamo?*/

/*Quando finisco il turno dopo aver mosso un dado e usato una toolcard mi stampa il menù*/

/*Al primo round non ho messo nulla e ora non posso mettere niente*/
public class CliApp {

    private RemoteController remoteController;
    private CliPhaseState currentState;
    private int id;
    private boolean waitingPhase=true;
    private ArrayDeque<GameCommand> commandBuffer = new ArrayDeque<GameCommand>();
    Scanner scanner;

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
        sendCommand();
        setCurrentState(new SelectingWindowFrameCell());
        sendCommand();
        setWaitingPhase(true);
    }

    public void setCurrentState(CliPhaseState currentState){
        this.currentState = currentState;
        setWaitingPhase(false);
    }


    public void mainLoop(){
        scanner = new Scanner(System.in);
        String input = "";
        while(true){
            synchronized (this) {
                while (waitingPhase) {
                    try {
                        wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }

            if(CliState.getCliState().isGameFinished()){
                scanner.close();
                return;
            }


            do {
                input = scanner.nextLine();
            }while(input.equals(""));

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
            CliDisplayer.getDisplayer().displayText("\nYou can't insert anything now\n");
        else
            synchronized (this) {
                notifyAll();
            }
    }

    public void addCommandToBuffer(GameCommand gameCommand){
        commandBuffer.add(gameCommand);
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
        new Thread( () ->
        {
            try {

                remoteController.command(commandBuffer.poll());
            } catch (IOException e) {
                CliDisplayer.getDisplayer().displayText(e.getMessage());
                suspend();
            }
        }).start();

    }

    public void setRemoteController(RemoteController remoteController) {
        this.remoteController = remoteController;
    }

    public void moveFromWindowFrame() {

        setCurrentState(new SelectingWindowFrameCell());
        sendCommand();
        setCurrentState(new SelectingWindowFrameCell());
        sendCommand();
    }

    public void suspend(){ //forse va synchro
        String name = CliState.getCliState().getCliPlayerState(id).getName();
        CliState.getCliState().resetCliState();
        setCurrentState(new Suspended(scanner, name));
        setWaitingPhase(false);
    }


}
