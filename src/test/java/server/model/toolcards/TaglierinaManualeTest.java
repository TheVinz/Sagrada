package server.model.toolcards;

import common.exceptions.InvalidMoveException;
import common.exceptions.WrongParameter;
import common.response.Response;
import org.junit.Before;
import org.junit.Test;
import org.mockito.*;
import org.omg.CORBA.CODESET_INCOMPATIBLE;
import server.GameManager;
import server.model.Model;
import server.model.state.ModelObject.ModelType;
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
    private WindowFrame secondFrame;
    private GameManager gameManager;

    @Before
    public void setUp() throws Exception {
        gameManager = Mockito.mock(GameManager.class);
        model = Mockito.spy(new Model(gameManager));
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
        secondFrame = new WindowFrame(WindowFrameList.AURORAE_MAGNIFICUS);
    }
    @Test
    public void start() throws InvalidMoveException {
    /*    try {
            toolCard.start(player);
        } catch (InvalidMoveException e) {
            assertEquals("Draft pool is empty",e.getMessage());
        }*/
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
        try{
            toolCard.setParameter(windowFrame.getCell(0,1));
        }catch (InvalidMoveException e){
            assertEquals("Wrong parameter",e.getMessage());
        }

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
            toolCard.setParameter(new Choice(TaglierinaManuale.ONE_MOVE));
        } catch (WrongParameter wrongParameter) {
            wrongParameter.printStackTrace();
        }
        when(player.getWindowFrame()).thenReturn(new WindowFrame(WindowFrameList.AURORA_SAGRADIS));
        windowFrame = player.getWindowFrame();
        windowFrame.getCell(0, 0).put(new Dice(Color.RED, 4));
        windowFrame.getCell(0, 1).put(new Dice(Color.YELLOW, 3));
        windowFrame.getCell(0, 2).put(new Dice(Color.BLUE, 5));
        windowFrame.getCell(1, 0).put(new Dice(Color.YELLOW, 3));

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
            toolCard.setParameter(windowFrame.getCell(0,3));    //primo dado
        } catch (WrongParameter wrongParameter) {
            wrongParameter.printStackTrace();
        }
        try {
            toolCard.setParameter(windowFrame);
        } catch (WrongParameter wrongParameter) {
            wrongParameter.printStackTrace();
        }
        try{
            try {
                toolCard.setParameter(windowFrame.getCell(2,1));    //dove sposto il primo dado
            } catch (WrongParameter wrongParameter) {
                wrongParameter.printStackTrace();
            }
        }catch (InvalidMoveException e){
        assertEquals("Cell restriction must be respected",e.getMessage());
            }

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
       try{
            try {
               toolCard.setParameter(windowFrame.getCell(0,3));    //dove sposto il primo dado
           } catch (WrongParameter wrongParameter) {
               wrongParameter.printStackTrace();
           }
       }catch (InvalidMoveException e){
            assertEquals("Empty cell",e.getMessage());
       }
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
            toolCard.setParameter(windowFrame.getCell(0,0));    //primo dado
        } catch (WrongParameter wrongParameter) {
            wrongParameter.printStackTrace();
        }
        try {
            toolCard.setParameter(windowFrame);
        } catch (WrongParameter wrongParameter) {
            wrongParameter.printStackTrace();
        }
        try{
            try {
                toolCard.setParameter(windowFrame.getCell(0,1));    //dove sposto il primo dado
            } catch (WrongParameter wrongParameter) {
                wrongParameter.printStackTrace();
            }
        }catch (InvalidMoveException e){
            assertEquals("Selected color and dice color must be equals",e.getMessage());
        }



        //casi sulla seconda mossa

        secondFrame.getCell(0,0).put(new Dice(Color.RED,5));
        secondFrame.getCell(0,1).put(new Dice(Color.GREEN,1));
        secondFrame.getCell(0,2).put(new Dice(Color.BLUE,5));
        secondFrame.getCell(0,3).put(new Dice(Color.PURPLE,1));
        secondFrame.getCell(0,4).put(new Dice(Color.RED,2));
        secondFrame.getCell(1,0).put(new Dice(Color.PURPLE,1));
        secondFrame.getCell(1,1).put(new Dice(Color.RED,5));
        secondFrame.getCell(1,2).put(new Dice(Color.YELLOW,1));
        secondFrame.getCell(1,3).put(new Dice(Color.RED,5));
        secondFrame.getCell(1,4).put(new Dice(Color.YELLOW,4));
        secondFrame.getCell(2,0).put(new Dice(Color.YELLOW,5));
        secondFrame.getCell(2,1).put(new Dice(Color.GREEN,2));
        secondFrame.getCell(2,2).put(new Dice(Color.RED,6));
        secondFrame.getCell(2,3).put(new Dice(Color.BLUE,3));
        secondFrame.getCell(2,4).put(new Dice(Color.PURPLE,5));
        secondFrame.getCell(3,0).put(new Dice(Color.RED,1));
        secondFrame.getCell(3,1).put(new Dice(Color.BLUE,4));
        secondFrame.getCell(3,2).put(new Dice(Color.PURPLE,5));
        secondFrame.getCell(3,3).put(new Dice(Color.GREEN,1));
        // secondFrame.getCell(3,4).put(new Dice(Color.BLUE,4));

        toolCard.start(player);
        when(player.getWindowFrame()).thenReturn(secondFrame);
        assertEquals(Response.ROUND_TRACK_CELL,toolCard.next());
        try{
            toolCard.setParameter(roundTrack.getRoundSet(1).get(0));
        }catch (WrongParameter wrongParameter){
            wrongParameter.printStackTrace();
        }
        assertEquals(Response.WINDOW_FRAME_MOVE,toolCard.next());
            try {
                toolCard.setParameter(secondFrame);
            }catch (WrongParameter wrongParameter){
               wrongParameter.printStackTrace();
            }
            try {
            toolCard.setParameter(secondFrame.getCell(1,4));
            }catch (WrongParameter wrongParameter){
                wrongParameter.printStackTrace();
            }
            try {
            toolCard.setParameter(secondFrame);
            }catch (WrongParameter wrongParameter){
            wrongParameter.printStackTrace();
            }
            try {
            toolCard.setParameter(secondFrame.getCell(3,4));
            }catch (WrongParameter wrongParameter){
            wrongParameter.printStackTrace();
            }
            assertEquals(Response.TAGLIERINA_MANUALE_CHOICE,toolCard.next());
            toolCard.setParameter(new Choice(TaglierinaManuale.TWO_MOVES));
            toolCard.hasNext();
            assertEquals(Response.WINDOW_FRAME_MOVE,toolCard.next());
           try {
               toolCard.setParameter(secondFrame);
           }catch (WrongParameter wrongParameter){
               wrongParameter.printStackTrace();
           }
           assertEquals(null,toolCard.next());
            try {
               toolCard.setParameter(secondFrame.getCell(3,3));
            }catch (WrongParameter wrongParameter){
               assertEquals("Selected color and dice color must be equals",wrongParameter.getMessage());
            }
         try {
            toolCard.setParameter(secondFrame);
        }catch (WrongParameter wrongParameter){
            wrongParameter.printStackTrace();
        }
        try {
            toolCard.setParameter(secondFrame.getCell(3,3));
        }catch (WrongParameter wrongParameter){
            assertEquals("Selected color and dice color must be equals",wrongParameter.getMessage());
        }

        toolCard.start(player);
        when(player.getWindowFrame()).thenReturn(secondFrame);
        try{
            toolCard.setParameter(roundTrack.getRoundSet(1).get(0));
        }catch (WrongParameter wrongParameter){
            wrongParameter.printStackTrace();
        }
        try {
            toolCard.setParameter(secondFrame);
        }catch (WrongParameter wrongParameter){
            wrongParameter.printStackTrace();
        }
        try {
            toolCard.setParameter(secondFrame.getCell(3,4));
        }catch (WrongParameter wrongParameter){
            wrongParameter.printStackTrace();
        }
        try {
            toolCard.setParameter(secondFrame);
        }catch (WrongParameter wrongParameter){
            wrongParameter.printStackTrace();
        }
        try {
            toolCard.setParameter(secondFrame.getCell(1,4));
        }catch (WrongParameter wrongParameter){
            wrongParameter.printStackTrace();
        }
        toolCard.setParameter(new Choice(TaglierinaManuale.TWO_MOVES));
        toolCard.hasNext();
        try {
            toolCard.setParameter(secondFrame);
        }catch (WrongParameter wrongParameter){
            wrongParameter.printStackTrace();
        }
        try {
            toolCard.setParameter(secondFrame.getCell(2,0));
        }catch (WrongParameter wrongParameter){
            wrongParameter.printStackTrace();
        }
        try {
            toolCard.setParameter(secondFrame);
        }catch (WrongParameter wrongParameter){
            wrongParameter.printStackTrace();
        }
        try {
            toolCard.setParameter(secondFrame.getCell(3,4));
        }catch (WrongParameter wrongParameter){
            assertEquals("Cell restriction must be respected",wrongParameter.getMessage());
        }

        secondFrame.getCell(3,0).removeDice();
        secondFrame.getCell(3,0).put(new Dice(Color.YELLOW,1));
        secondFrame.getCell(2,0).removeDice();
        toolCard.start(player);
        when(player.getWindowFrame()).thenReturn(secondFrame);
        try{
            toolCard.setParameter(roundTrack.getRoundSet(1).get(0));
        }catch (WrongParameter wrongParameter){
            wrongParameter.printStackTrace();
        }
        try {
            toolCard.setParameter(secondFrame);
        }catch (WrongParameter wrongParameter){
            wrongParameter.printStackTrace();
        }
        try {
            toolCard.setParameter(secondFrame.getCell(1,4));
        }catch (WrongParameter wrongParameter){
            wrongParameter.printStackTrace();
        }
        try {
            toolCard.setParameter(secondFrame);
        }catch (WrongParameter wrongParameter){
            wrongParameter.printStackTrace();
        }
        try {
            toolCard.setParameter(secondFrame.getCell(3,4));
        }catch (WrongParameter wrongParameter){
            wrongParameter.printStackTrace();
        }
        toolCard.setParameter(new Choice(TaglierinaManuale.TWO_MOVES));
        toolCard.hasNext();
        try {
            toolCard.setParameter(secondFrame);
        }catch (WrongParameter wrongParameter){
            wrongParameter.printStackTrace();
        }
        try {
            toolCard.setParameter(secondFrame.getCell(3,0));
        }catch (WrongParameter wrongParameter){
           wrongParameter.printStackTrace();
            // assertEquals("Dice restrictions must be respected",wrongParameter.getMessage());
        }
        try {
            toolCard.setParameter(secondFrame);
        }catch (WrongParameter wrongParameter){
            wrongParameter.printStackTrace();
        }
        try {
            toolCard.setParameter(secondFrame.getCell(2,0));
        }catch (WrongParameter wrongParameter){
            assertEquals("Dice restrictions must be respected",wrongParameter.getMessage());
        }


        toolCard.start(player);
        when(player.getWindowFrame()).thenReturn(secondFrame);
        try{
            toolCard.setParameter(roundTrack.getRoundSet(1).get(0));
        }catch (WrongParameter wrongParameter){
            wrongParameter.printStackTrace();
        }
        try {
            toolCard.setParameter(secondFrame);
        }catch (WrongParameter wrongParameter){
            wrongParameter.printStackTrace();
        }
        try {
            toolCard.setParameter(secondFrame.getCell(3,0));
        }catch (WrongParameter wrongParameter){
            wrongParameter.printStackTrace();
        }
        try {
            toolCard.setParameter(secondFrame);
        }catch (WrongParameter wrongParameter){
            wrongParameter.printStackTrace();
        }
        try {
            toolCard.setParameter(secondFrame.getCell(2,0));
        }catch (InvalidMoveException e){
            assertEquals("Dice restrictions must be respected",e.getMessage());
        }
    }
    @Test
    public void shouldGetNumber(){
        assertEquals(12,toolCard.getNumber());
    }
    @Test
    public void shouldGetColor(){
        assertEquals(Color.BLUE,toolCard.getColor());
    }

}