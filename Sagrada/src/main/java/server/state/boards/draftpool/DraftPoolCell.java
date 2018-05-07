package server.state.boards.draftpool;

import server.state.ModelObject;
import server.state.dice.Dice;
import server.state.boards.Cell;
import common.exceptions.InvalidMoveException;

public class DraftPoolCell extends Cell implements ModelObject {

    @Override
    public String getType() {
        return "DraftPoolCell";
    }
}