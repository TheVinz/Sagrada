package server.model.state.boards.roundtrack;

//import com.sun.org.apache.xpath.internal.operations.Mod;
import server.model.state.ModelObject.ModelObject;
import server.model.state.ModelObject.ModelType;
import server.model.state.boards.Cell;

import static server.model.state.ModelObject.ModelType.ROUND_TRACK_CELL;

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
    public ModelType getType() {
        return ROUND_TRACK_CELL;
    }
}
