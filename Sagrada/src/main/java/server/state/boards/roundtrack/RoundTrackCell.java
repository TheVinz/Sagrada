package server.state.boards.roundtrack;

//import com.sun.org.apache.xpath.internal.operations.Mod;
import server.state.ModelObject;
import server.state.dice.Dice;
import server.state.boards.Cell;
import common.exceptions.InvalidMoveException;

public class RoundTrackCell extends Cell implements ModelObject {

    @Override
    public String getType() {
        return "RoundTrackCell";
    }
}
