package server.model.state.boards.roundtrack;

//import com.sun.org.apache.xpath.internal.operations.Mod;
import common.ModelObject;
import server.model.state.boards.Cell;

public class RoundTrackCell extends Cell implements ModelObject {

    private int round;
    private int index;

    public RoundTrackCell(int round, int index){
        this.round=round;
        this.index=index;
    }

    public int getRound() {
        return round;
    }

    public int getIndex() {
        return index;
    }

    @Override
    public int getType() {
        return ROUND_TRACK_CELL;
    }
}
