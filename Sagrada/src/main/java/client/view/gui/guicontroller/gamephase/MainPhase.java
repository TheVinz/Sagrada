package client.view.gui.guicontroller.gamephase;

import client.view.gui.guicontroller.GameController;
import common.RemoteMVC.RemoteController;
import common.command.GameCommand;
import common.exceptions.InvalidMoveException;
import common.response.Response;
import javafx.application.Platform;

import java.io.IOException;
import java.rmi.RemoteException;

public class MainPhase extends GamePhase {

    private boolean send = false;
    private int sourceIndex;

    public MainPhase(RemoteController controller, GameController gameController) {
        super(controller, gameController);
        gameController.mainPhase();
    }

    @Override
    public GamePhase handleDraftPool(int index){
        sourceIndex=index;
        send = true;
        return this;
    }

    @Override
    public GamePhase handleWindowFrame(int row, int col) {
        if(send){
            new Thread(() -> {
                try {
                    send(sourceIndex, row, col);
                }catch(IOException e){
                    Platform.runLater(() -> gameController.suspend());
                }
            }).start();
        }
        return new GamePhase(controller, gameController);
    }

    private void send(int sourceIndex, int destRow, int destCol) throws IOException {
        controller.command(new GameCommand(Response.DRAFT_POOL_CELL, sourceIndex));
        controller.command(new GameCommand(Response.WINDOW_FRAME_CELL, destRow, destCol));
    }

    @Override
    public GamePhase handleToolCard(int index) throws IOException {
        controller.command(new GameCommand(Response.TOOL_CARD, index));
        GamePhase phase = new GamePhase(controller, gameController);
        return phase;
    }

}
