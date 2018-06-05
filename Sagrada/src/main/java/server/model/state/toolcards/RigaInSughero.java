package server.model.state.toolcards;

//import client.view.gui.guicontroller.gamephase.GamePhase;
import common.exceptions.InvalidMoveException;
import common.response.Response;
import server.model.Model;
import server.model.state.boards.Cell;
import server.model.state.utilities.Color;
import server.model.state.utilities.GameRules;
import server.model.state.boards.draftpool.DraftPoolCell;
import server.model.state.boards.windowframe.WindowFrame;
import server.model.state.boards.windowframe.WindowFrameCell;
import server.model.state.player.Player;

import java.util.ArrayDeque;
import java.util.ArrayList;

import static server.model.state.ModelObject.ModelType.*;

public class RigaInSughero extends ToolCard {
    public RigaInSughero(Model model) {
        super(model);
    }

    @Override
    public int getNumber() {
        return 9;
    }

    @Override
    public Color getColor() {
        return Color.YELLOW;
    }

    @Override
    public void start(Player player) throws InvalidMoveException {
        this.player=player;
        if(!player.isFirstMoveDone())
            throw new InvalidMoveException("You still have to place your first dice");
        if(model.getState().getDraftPool().isEmpty())
            throw new InvalidMoveException("Draft pool is empty");
        if(!playable())
            throw new InvalidMoveException("No available moves");
        if(player.isDiceMoved())
            throw new InvalidMoveException("You can only place a dice once per turn");
        parameters=new ArrayList<>(3);
        expectedParameters=new ArrayDeque<>(3);
        expectedParameters.add(DRAFT_POOL_CELL);
        expectedParameters.add(WINDOW_FRAME);
        expectedParameters.add(WINDOW_FRAME_CELL);
    }

    @Override
    void doAbility() throws InvalidMoveException {
        DraftPoolCell poolCell= (DraftPoolCell) parameters.get(0);
        if(poolCell.isEmpty())
            throw new InvalidMoveException("PoolCell is empty");
        WindowFrame frame= (WindowFrame) parameters.get(1);
        WindowFrameCell cell= (WindowFrameCell) parameters.get(2);
        if(GameRules.validAdjacentDices(frame, cell))
            throw new InvalidMoveException("Cell must have no adjacent dices");
        else if(!GameRules.validAllCellRestriction(poolCell.getDice(), cell))
            throw new InvalidMoveException("Placement must respect cell restrictions");
        else{
            model.move(player, poolCell, cell);
            player.setDiceMoved();
            model.toolCardUsed(player, this);
        }
    }

    @Override
    public Response next() {  //trascinamento da draftpool
        if(expectedParameters.peek().equals(DRAFT_POOL_CELL))
            return Response.DRAFT_POOL_MOVE;
        else
            return null;
    }

    private boolean playable(){
        for(int i=0; i<WindowFrame.ROWS; i++){
            for(int j=0; j<WindowFrame.COLUMNS; j++){
                if(!GameRules.validAdjacentDices(player.getWindowFrame(), player.getWindowFrame().getCell(i,j))){
                    for(Cell cell : model.getState().getDraftPool().getDraftPool()) {
                        if(!cell.isEmpty()) {
                            if (GameRules.validAllCellRestriction(cell.getDice(), player.getWindowFrame().getCell(i, j)))
                                return true;
                        }
                    }
                }
            }
        }
        return false;
    }

}
