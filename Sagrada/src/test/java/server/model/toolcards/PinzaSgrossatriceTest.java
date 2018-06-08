package server.model.toolcards;

import common.exceptions.InvalidMoveException;
import common.exceptions.WrongParameter;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import server.model.Model;
import server.model.state.ModelObject.ModelType;
import server.model.state.boards.draftpool.DraftPool;
import server.model.state.dice.Dice;
import server.model.state.player.Player;
import server.model.state.toolcards.PinzaSgrossatrice;
import server.model.state.toolcards.ToolCard;
import server.model.state.utilities.Choice;
import server.model.state.utilities.Color;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

public class PinzaSgrossatriceTest {

    private ToolCard toolCard;
    private Player player;
    private DraftPool draftPool = new DraftPool();
    private int choice;
    private Model model;

    @Before
    public void setUp() throws Exception {
        model = Mockito.spy(new Model());
        toolCard = new PinzaSgrossatrice(model);
        draftPool = model.getState().getDraftPool();
        player = mock(Player.class);
        draftPool.increaseSize();

    }

    @Test
    public void shouldStart() throws InvalidMoveException {
        try{toolCard.start(player);}
        catch(InvalidMoveException invalidMoveException){
            invalidMoveException.printStackTrace();
        }
    }

    @Test
    public void doAbility() throws InvalidMoveException {
        draftPool.getCell(0).put(new Dice(Color.RED, 3));
        draftPool.getCell(2).put(new Dice(Color.YELLOW, 1));
        toolCard.start(player);

        try {
            toolCard.setParameter(draftPool.getCell(0));
        } catch (WrongParameter wrongParameter) {
            wrongParameter.printStackTrace();
        }
        try {
            toolCard.setParameter(new Choice(0));
        } catch (WrongParameter wrongParameter) {
            wrongParameter.printStackTrace();
        }
        assertEquals(4, draftPool.getCell(0).getDice().getValue());

        toolCard.start(player);

        try {
            toolCard.setParameter(draftPool.getCell(1));
        } catch (WrongParameter wrongParameter) {
            wrongParameter.printStackTrace();
        }

        try{try {
            toolCard.setParameter(new Choice(1));
        } catch (WrongParameter wrongParameter) {
            wrongParameter.printStackTrace();
        }}
        catch (InvalidMoveException invalidMoveException){
            invalidMoveException.printStackTrace();
        }
        toolCard.start(player);
        try {
            toolCard.setParameter(draftPool.getCell(0));
        } catch (WrongParameter wrongParameter) {
            wrongParameter.printStackTrace();
        }

        try{try {
            toolCard.setParameter(new Choice(1));
        } catch (WrongParameter wrongParameter) {
            wrongParameter.printStackTrace();
        }}
        catch (InvalidMoveException invalidMoveException){
            invalidMoveException.printStackTrace();
        }
    }

    @Test
    public void shouldGetNumber(){
        assertEquals(1,toolCard.getNumber());
    }
    @Test
    public void shouldGetColor(){
        assertEquals(Color.PURPLE,toolCard.getColor());
    }
    @Test
    public void shouldGetType(){
        assertEquals(ModelType.TOOL_CARD,toolCard.getType());
    }

}