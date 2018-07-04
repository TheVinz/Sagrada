package client.view.cli;

import common.Changer;
import client.view.cli.cliphasestate.*;
import common.Notification;
import common.RemoteMVC.RemoteView;
import common.response.Response;
import common.viewchangement.*;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 * The <tt>CliModel</tt> is a singleton that represents the model for the CLI client MVC. This class receives
 * {@link Changement}s, {@link Response}s and {@link Notification}s from the server and then
 * notifies the {@link CliApp}. This class can either be used with both RMI and Socket connections
 * so it does not know which of them is being used by the player.
 */
public class CliModel extends UnicastRemoteObject implements RemoteView{

    private final Changer changer;
    private final boolean singlePlayer;
    private Response lastResponse;
    private static CliModel cliModel;


    private CliModel(boolean singlePlayer) throws RemoteException {
        super();

        this.singlePlayer = singlePlayer;
        changer = singlePlayer ? SinglePlayerCliChanger.getSinglePlayerCliChanger() : CliChanger.getCliChanger();
    }

    /**
     * Initialize the <tt>CliModel</tt> creating a new {@link CliChanger}.
     * @param singlePlayer is true if tha game is in single player mode, in this case a {@link SinglePlayerCliChanger} is created.
     * @throws RemoteException from the {@link UnicastRemoteObject} superclass constructor.
     */
    public static CliModel getCliModel(boolean singlePlayer) throws RemoteException{
        if(cliModel == null)
            cliModel = new CliModel(singlePlayer);
        return cliModel;
    }

    /**
     * Calls method change from the {@link Changer} passing the received {@link Changement}
     * as argument, so the Changer can process it and update the CLI game representation.
     * @param changement the Changement received from the server.
     */
    @Override
    public void change(Changement changement) {
        changement.change(changer);
    }

    /**
     * Informs the {@link CliApp} about the {@link Response} received from the server.
     * @param response the Response received from the server.
     */
    @Override
    public void send(Response response) {
        lastResponse = response;
        new Thread( () -> {
        switch (response){
            case WINDOW_FRAME:
                CliApp.getCliApp().setCurrentState(new SelectingWindowFrameCell());
                CliApp.getCliApp().sendCommand();
                CliApp.getCliApp().setWaitingPhase(true);
                break;
            case DRAFT_POOL_CELL:
                CliApp.getCliApp().setCurrentState(new SelectingDraftPoolCell());
                CliApp.getCliApp().sendCommand();
                CliApp.getCliApp().setWaitingPhase(true);
                break;
            case ROUND_TRACK_CELL:
                CliApp.getCliApp().setCurrentState(new SelectingRoundTrackCell());
                CliApp.getCliApp().sendCommand();
                CliApp.getCliApp().setWaitingPhase(true);
                break;
            case END_TURN:
                CliDisplayer.getDisplayer().displayText("Your turn is finished!\n");
                CliApp.getCliApp().setWaitingPhase(true);
                break;
            case TOOL_CARD:
                CliApp.getCliApp().setCurrentState(new SelectingSendingToolCard());
                break;
            case WINDOW_FRAME_MOVE:
                CliApp.getCliApp().moveFromWindowFrame();
                break;
            case DRAFT_POOL_MOVE:
                CliApp.getCliApp().moveFromDraftPool();
                break;
            case SUCCESS_USED_TOOL_CARD:
                CliApp.getCliApp().setWaitingPhase(false);
                CliApp.getCliApp().setCurrentState(new MenuPhase());
                break;
            case SUCCESS_MOVE_DONE:
                CliApp.getCliApp().setWaitingPhase(false);
                CliApp.getCliApp().setCurrentState(new MenuPhase());
                break;
            case PINZA_SGROSSATRICE_CHOICE:
                CliApp.getCliApp().setCurrentState(new PinzaSgrossatriceChoice());
                CliApp.getCliApp().sendCommand();
                CliApp.getCliApp().setWaitingPhase(true);
                break;
            case TAGLIERINA_MANUALE_CHOICE:
                CliApp.getCliApp().setCurrentState(new TaglierinaManualeChoice());
                CliApp.getCliApp().sendCommand();
                CliApp.getCliApp().setWaitingPhase(true);
                break;
            case DILUENTE_PER_PASTA_SALDA_CHOICE:
                CliApp.getCliApp().setCurrentState(new DiluentePerPastaSaldaChoice());
                CliApp.getCliApp().sendCommand();
                CliApp.getCliApp().setWaitingPhase(true);
                break;
            default:
                return;

        }}).start();
    }

    /**
     * Informs the CliApp about the {@link Notification} received from the server.
     * If the Notification is a wrong parameter notification the method send of this class is called with the last
     * response received from the server as parameter.
     * @param notification the Notification received from the server.
     */
    public void notify(Notification notification){
        new Thread(() -> {
            switch (notification.getType()) {
                case Notification.WRONG_PARAMETER:
                    CliDisplayer.getDisplayer().displayText(notification.getMessage());
                    send(lastResponse);
                    break;
                case Notification.ERROR:
                    CliDisplayer.getDisplayer().displayText(notification.getMessage());
                    CliApp.getCliApp().setWaitingPhase(false);
                    CliApp.getCliApp().setCurrentState(new MenuPhase());
                    break;
                default:
                    return;
            }
        }).start();

    }

    /**
     * This method is called from the server to check if the client is still connected to the game.
     * @throws RemoteException in case of connection drops.
     */
    @Override
    public void ping() throws RemoteException {}


}
