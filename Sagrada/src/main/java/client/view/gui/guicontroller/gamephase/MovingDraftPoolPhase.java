package client.view.gui.guicontroller.gamephase;

import client.view.gui.guicontroller.GameController;
import common.RemoteMVC.RemoteController;
import common.command.GameCommand;
import common.response.Response;

import java.io.IOException;

public class MovingDraftPoolPhase extends GamePhase{

    private boolean send = false;
    private int sourceIndex;

    public MovingDraftPoolPhase(RemoteController controller, GameController gameController) {
        super(controller, gameController);
        gameController.movingDraftPoolPhase();
    }

    @Override
    public GamePhase handleDraftPool(int index){
        sourceIndex=index;
        send = true;
        return this;
    }
    @Override
    public GamePhase handleWindowFrame(int row, int col) throws IOException{
        if(send) {
            new Thread(() -> {
                try {
                    controller.command(new GameCommand(Response.DRAFT_POOL_CELL, sourceIndex));
                    controller.command(new GameCommand(Response.WINDOW_FRAME_CELL, row, col));
                } catch (IOException e) {
                    gameController.suspend();
                }
            }).start();
            gameController.unableAll();
            return new GamePhase(controller, gameController);
        }
        else return this;
    }
}
