package server.model.toolcards;

import common.exceptions.InvalidMoveException;
import common.exceptions.WrongParameter;
import org.junit.Before;
import org.junit.Test;
import org.mockito.*;
import org.omg.CORBA.CODESET_INCOMPATIBLE;
import server.model.Model;
import server.model.state.boards.draftpool.DraftPool;
import server.model.state.boards.roundtrack.RoundTrack;
import server.model.state.boards.windowframe.WindowFrame;
import server.model.state.boards.windowframe.WindowFrameList;
import server.model.state.dice.Dice;
import server.model.state.player.Player;
import server.model.state.toolcards.TaglierinaManuale;
import server.model.state.toolcards.ToolCard;
import server.model.state.utilities.Choice;
import server.model.state.utilities.Color;

import static junit.framework.TestCase.assertEquals;
import static org.mockito.Mockito.when;

public class TaglierinaManualeTest {
    private Model model;
    private ToolCard toolCard;
    private Player player;
    private DraftPool draftPool;
    private RoundTrack roundTrack;
    private WindowFrame windowFrame;
    private Integer integer;

    @Before
    public void setUp() throws Exception {
        model = Mockito.spy(new Model());
        player = Mockito.mock(Player.class);
        toolCard = new TaglierinaManuale(model);
        when(player.getWindowFrame()).thenReturn(new WindowFrame(WindowFrameList.AURORA_SAGRADIS));
        windowFrame = player.getWindowFrame();
        windowFrame.getCell(0, 0).put(new Dice(Color.RED, 4));
        windowFrame.getCell(0, 1).put(new Dice(Color.YELLOW, 3));
        windowFrame.getCell(0, 2).put(new Dice(Color.BLUE, 5));
        windowFrame.getCell(1, 0).put(new Dice(Color.YELLOW, 3));
        draftPool = model.getState().getDraftPool();
        draftPool.increaseSize();
        roundTrack = model.getState().getRoundTrack();
    }
    @Test
    public void start() throws InvalidMoveException {
        try {
            toolCard.start(player);
        } catch (InvalidMoveException e) {
            assertEquals("Draft pool is empty",e.getMessage());
        }
        draftPool.getCell(0).put(new Dice(Color.BLUE,3));
        try {
            toolCard.start(player);
        } catch (InvalidMoveException e) {
            assertEquals("Empty round track",e.getMessage());
        }
    }
    @Test
    public void shouldDoAbility() throws Exception {
        draftPool.getCell(0).put(new Dice(Color.YELLOW, 4));
        roundTrack.endRound(draftPool);
        draftPool.getCell(0).put(new Dice(Color.BLUE,3));
        toolCard.start(player);
        try {
            toolCard.setParameter(roundTrack.getRoundSet(1).get(0));
        } catch (WrongParameter wrongParameter) {
            wrongParameter.printStackTrace();
        }
        try {
            toolCard.setParameter(windowFrame);
        } catch (WrongParameter wrongParameter) {
            wrongParameter.printStackTrace();
        }
        try {
            toolCard.setParameter(windowFrame.getCell(0,1));    //primo dado
        } catch (WrongParameter wrongParameter) {
            wrongParameter.printStackTrace();
        }
        try {
            toolCard.setParameter(windowFrame);
        } catch (WrongParameter wrongParameter) {
            wrongParameter.printStackTrace();
        }
        try {
            toolCard.setParameter(windowFrame.getCell(0,3));    //dove sposto il primo dado
        } catch (WrongParameter wrongParameter) {
            wrongParameter.printStackTrace();
        }
        try {
            toolCard.setParameter(new Choice(TaglierinaManuale.TWO_MOVES));
        } catch (WrongParameter wrongParameter) {
            wrongParameter.printStackTrace();
        }
        toolCard.hasNext();
        try {
            try {
                toolCard.setParameter(windowFrame);
            } catch (WrongParameter wrongParameter) {
                wrongParameter.printStackTrace();
            }
        }catch (InvalidMoveException e){
            e.printStackTrace();
        }
        try{
            try {
                toolCard.setParameter(windowFrame.getCell(1,0));   //secondo dado
            } catch (WrongParameter wrongParameter) {
                wrongParameter.printStackTrace();
            }
        }catch (InvalidMoveException e){
            assertEquals("Wrong parameter",e.getMessage());
        }
        try {
            toolCard.setParameter(windowFrame);
        } catch (WrongParameter wrongParameter) {
            wrongParameter.printStackTrace();
        }
        try {
            toolCard.setParameter(windowFrame.getCell(1,2));
        } catch (WrongParameter wrongParameter) {
            wrongParameter.printStackTrace();
        }


    }

}