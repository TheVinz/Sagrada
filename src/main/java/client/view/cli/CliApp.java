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

/**
 * <tt>CliApp</tt> is a singleton that gets the inputs of the user and contains the method that allow to change the current {@link CliPhaseState}.
 */
/*Al primo round non ho messo nulla e ora non posso mettere niente*/
public class CliApp {

    private RemoteController remoteController;
    private CliPhaseState currentState;
    private int id;
    private boolean waitingPhase=true;
    private ArrayDeque<GameCommand> commandBuffer = new ArrayDeque<GameCommand>();
    Scanner scanner;

    private static CliApp cliApp;

    private CliApp(){ }

    /**
     * Creates the unique instance of this class if it has never been created.
     * @return the unique instance of this class.
     */
    public static CliApp getCliApp(){
        if(cliApp == null)
            cliApp = new CliApp();
        return cliApp;
    }

    /**
     * Retrieves the Id of this user.
     * @return the Id of this user.
     */
    public int getId(){
        return this.id;
    }
    public void setId(int id){
        this.id=id;
    }

    /**
     * Sets the {@link CliPhaseState} needed for the move of a {@link server.model.state.dice.Dice} from the {@link server.model.state.boards.draftpool.DraftPool} to the {@link server.model.state.boards.windowframe.WindowFrame} in the proper order.
     */
    public void moveFromDraftPool()
    {
        setCurrentState(new SelectingDraftPoolCell());
        sendCommand();
        setCurrentState(new SelectingWindowFrameCell());
        sendCommand();
        setWaitingPhase(true);
    }

    /**
     * Sets a new {@link CliPhaseState} that will handle the input from the user.
     * @param currentState
     */
    public void setCurrentState(CliPhaseState currentState){
        this.currentState = currentState;
        setWaitingPhase(false);
    }


    /**
     * Keeps waiting for input from user and then passes it to the current <tt>CliPhaseState</tt>, that will handle it.
     */
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


    /**
     * Lets know the <tt>mainLoop</tt> method if the input inserted from the user needs to be processed.
     * If the user is not allowed to type, this is notified on the display.
     * @param waitingPhase is false only if the user is expected to insert input.
     */
    public void setWaitingPhase(boolean waitingPhase) {
        this.waitingPhase = waitingPhase;
        if(waitingPhase)
            CliDisplayer.getDisplayer().displayText("\nYou can't insert anything now\n");
        else
            synchronized (this) {
                notifyAll();
            }
    }

    /**
     * Adds a {@link GameCommand} to the buffer that contains the <tt>GameCommands</tt> to send to the server.
     * @param gameCommand
     */
    public void addCommandToBuffer(GameCommand gameCommand){
        commandBuffer.add(gameCommand);
    }


    /**
     * Sends all the the <tt>GameCommand</tt>, still not processed, to the {@link RemoteController}.
     */
    public void sendCommand() {
        synchronized (this){
        while(commandBuffer.isEmpty()) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }}
        GameCommand gameCommand = commandBuffer.poll();
        new Thread( () ->
        {
            try {

                remoteController.command(gameCommand);
            } catch (IOException e) {
                CliDisplayer.getDisplayer().displayText(e.getMessage());
                suspend();
            }
        }).start();

    }

    /**
     * Sets the {@link RemoteController} to that the <tt>GameCommand</tt>s will be sent.
     * @param remoteController
     */
    public void setRemoteController(RemoteController remoteController) {
        this.remoteController = remoteController;
    }

    public void moveFromWindowFrame() {

        setCurrentState(new SelectingWindowFrameCell());
        sendCommand();
        setCurrentState(new SelectingWindowFrameCell());
        sendCommand();
    }

    /**
     * Manages all the operations in the cases where the game is finished or this user has been suspended.
     */
    public void suspend(){ //forse va synchro
        if(CliState.getCliState().isGameFinished())
            return;
        String name = CliState.getCliState().getCliPlayerState(id).getName();
        CliState.getCliState().resetCliState();
        setCurrentState(new Suspended(scanner, name));
        setWaitingPhase(false);
    }


}
