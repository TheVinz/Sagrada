package server.state.boards.roundtrack;

import org.junit.Before;
import org.junit.Test;
import server.model.state.ModelObject.ModelType;
import server.model.state.boards.draftpool.DraftPool;
import server.model.state.boards.draftpool.DraftPoolCell;
import server.model.state.boards.roundtrack.RoundTrack;
import server.model.state.boards.roundtrack.RoundTrackCell;
import server.model.state.dice.Dice;
import server.model.state.utilities.Color;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static server.model.state.ModelObject.ModelType.ROUND_TRACK_CELL;

public class RoundTrackCellTest {
    private ModelType modelType;
    private RoundTrack roundTrack;
    private DraftPool draftPool;
    private RoundTrackCell roundTrackCell;
    @Before
    public void setUp() throws Exception {
        draftPool = mock(DraftPool.class);
        roundTrack = new RoundTrack();
        roundTrackCell = new RoundTrackCell(1,1);
        roundTrackCell.put(new Dice(Color.BLUE,4));
    }
    @Test
    public void shouldGetRound(){
            assertEquals(1, roundTrackCell.getRound());
    }
    @Test
    public void shouldGetIndex(){
        assertEquals(1,roundTrackCell.getIndex());
    }
    @Test
    public void shouldGetType(){
        assertEquals(modelType.ROUND_TRACK_CELL,roundTrackCell.getType());
    }


}