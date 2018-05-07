package server.state.toolcards;

import common.exceptions.InvalidMoveException;
import org.junit.Before;
import org.junit.Test;
import org.mockito.*;
import server.Model;
import server.state.boards.draftpool.DraftPool;
import server.state.boards.roundtrack.RoundTrack;
import server.state.boards.windowframe.WindowFrame;
import server.state.boards.windowframe.WindowFrameCell;
import server.state.boards.windowframe.WindowFrameList;
import server.state.dice.Dice;
import server.state.player.Player;
import server.state.utilities.Color;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

public class TaglierinaManualeTest {
    private Model model;
    private ToolCard toolCard;
    private Player player;
    private DraftPool draftPool;
    private RoundTrack roundTrack;
    private WindowFrame windowFrame;
    private Integer integer;

    @Test
    public void setUp() throws Exception {
        model = Mockito.spy(new Model());
        player = Mockito.mock(Player.class);
        toolCard = new TaglierinaManuale(model);
        Mockito.when(player.getWindowFrame()).thenReturn(new WindowFrame(WindowFrameList.AURORA_SAGRADIS));
        windowFrame = player.getWindowFrame();
        windowFrame.getCell(0, 0).put(new Dice(Color.RED, 4));
        windowFrame.getCell(0, 1).put(new Dice(Color.YELLOW, 3));
        windowFrame.getCell(0, 2).put(new Dice(Color.BLUE, 5));
        windowFrame.getCell(1, 0).put(new Dice(Color.YELLOW, 1));
        draftPool = new DraftPool();
        draftPool.increaseSize();
        draftPool.getCell(0).put(new Dice(Color.YELLOW, 4));
        roundTrack = new RoundTrack();
        roundTrack.endRound(draftPool);
    }
    @Test
    public void start() throws InvalidMoveException {
        toolCard.start(player);
    }
    @Test
    public void shouldDoAbility() throws InvalidMoveException {
        toolCard.start(player);
        toolCard.setParameter(roundTrack.getRoundSet(0).get(0));
        toolCard.setParameter(windowFrame);
        toolCard.setParameter(windowFrame.getCell(0,1));    //primo dado
        toolCard.setParameter(windowFrame);
        toolCard.setParameter(windowFrame.getCell(0,3));    //dove sposto il primo dado
        integer = 1;
        toolCard.setParameter(windowFrame);
        toolCard.setParameter(windowFrame.getCell(1,0));   //secondo dado
        toolCard.setParameter(windowFrame);
        toolCard.setParameter(windowFrame.getCell(1,2));



    }

}