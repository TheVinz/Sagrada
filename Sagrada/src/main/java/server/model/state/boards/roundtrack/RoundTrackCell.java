package server.model.state.boards.roundtrack;

//import com.sun.org.apache.xpath.internal.operations.Mod;
import server.model.state.ModelObject.ModelObject;
import server.model.state.ModelObject.ModelType;
import server.model.state.boards.Cell;

import static server.model.state.ModelObject.ModelType.ROUND_TRACK_CELL;

/**
 * The RoundTrackCell class extends the {@link server.model.state.boards.Cell} class.
 * Represents the cell in the {@link server.model.state.boards.roundtrack.RoundTrack}.
 */
public class RoundTrackCell extends Cell implements ModelObject {

    private int round;
    private int index;

    /**
     * Initialize a RoundTrackCell in the RoundTrack at given round and index.
     * @param round indicates the ArrayList where is going this RoundTrackCell.
     * @param index indicates the position in the ArrayList of this RoundTrackCell.
     */
    public RoundTrackCell(int round, int index){
        this.round=round;
        this.index=index;
    }

    /**
     * Gets the round of the ArrayList where is located this RoundTrackCell.
     * @return the round.
     */
    public int getRound() {
        return round;
    }

    /**
     * Gets the index in the ArrayList where is located this RoundTrackCell.
     * @return
     */
    public int getIndex() {
        return index;
    }

    /**
     * Gets the type of this object.
     * @return ROUND_TRACK_CELL type.
     */
    @Override
    public ModelType getType() {
        return ROUND_TRACK_CELL;
    }
}
