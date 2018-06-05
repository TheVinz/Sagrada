package client.view.cli;

import common.Changer;
import client.view.cli.cliphasestate.*;
import common.Notification;
import common.RemoteMVC.RemoteView;
import common.response.Response;
import common.viewchangement.*;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;


public class CliModel extends UnicastRemoteObject implements RemoteView{

    private final Changer changer;
    private final boolean singlePlayer;
    private Response lastResponse;

    public CliModel(boolean singlePlayer) throws RemoteException {
        super();

        this.singlePlayer = singlePlayer;
        changer = singlePlayer ? new SinglePlayerCliChanger() : new CliChanger();
    }

    @Override
    public void change(Changement changement) {
        changement.change(changer);
    }


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
            case SUSPENDED:
                CliApp.getCliApp().setWaitingPhase(false);
                CliApp.getCliApp().setCurrentState(new Suspended());
                break;
            default:
                return;

        }}).start();
    }


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



}
