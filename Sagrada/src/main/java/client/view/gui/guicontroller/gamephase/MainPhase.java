package client.view.gui.guicontroller.gamephase;

import client.view.gui.guicontroller.GameController;
import common.RemoteMVC.RemoteController;
import common.command.GameCommand;
import common.exceptions.InvalidMoveException;
import common.response.Response;

import java.io.IOException;
import java.rmi.RemoteException;

public class MainPhase extends GamePhase {

    private int sourceIndex=-1;
    private int destRow;
    private int destCol;

    public MainPhase(RemoteController controller, GameController gameController) {
        super(controller, gameController);
        gameController.mainPhase();
    }

    @Override
    public GamePhase handleDraftPool(int index){
        sourceIndex=index;
        return this;
    }

    @Override
    public GamePhase handleWindowFrame(int row, int col) {
        destRow=row;
        destCol=col;
        if(sourceIndex!=-1){
            new Thread(() -> {
                try {
                    controller.command(new GameCommand(Response.DRAFT_POOL_CELL, sourceIndex));
                    controller.command(new GameCommand(Response.WINDOW_FRAME_CELL, destRow, destCol));
                }catch(IOException e){
                    gameController.suspend();
                }
            }).start();
        }
        return new GamePhase(controller, gameController);
    }

    @Override
    public GamePhase handleToolCard(int index) throws IOException {
        controller.command(new GameCommand(Response.TOOL_CARD, index));
        GamePhase phase = new GamePhase(controller, gameController);
        return phase;
    }

}
