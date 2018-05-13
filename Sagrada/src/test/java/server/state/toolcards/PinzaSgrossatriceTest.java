package server.state.toolcards;

import common.exceptions.InvalidMoveException;
import org.junit.Before;
import org.junit.Test;
import org.mockito.*;
import server.model.Model;
import server.model.state.boards.draftpool.DraftPool;
import server.model.state.dice.Dice;
import server.model.state.player.Player;
import server.model.state.toolcards.PinzaSgrossatrice;
import server.model.state.toolcards.ToolCard;
import server.model.state.utilities.Choice;
import server.model.state.utilities.Color;

public class PinzaSgrossatriceTest {
    private Model model;
    private ToolCard toolCard;
    private Player player;
    private DraftPool draftPool;
    private Integer integer;

    @Before
    public void setUp() throws Exception {
        model= Mockito.spy(new Model());
        player=Mockito.mock(Player.class);

        toolCard = new PinzaSgrossatrice(model);
        draftPool = model.getState().getDraftPool();
        draftPool = new DraftPool();
        draftPool.increaseSize();
        draftPool.getCell(0).put(new Dice(Color.RED, 3));
        draftPool.getCell(1).put(new Dice(Color.GREEN, 5));
        draftPool.getCell(2).put(new Dice(Color.YELLOW, 4));

    }

    @Test
    public void shouldStart() throws InvalidMoveException {
        toolCard.start(player);
    }
    @Test
    public void shouldDoAbility() throws InvalidMoveException {
        toolCard.start(player);
        toolCard.setParameter(draftPool.getCell(0));

       toolCard.setParameter(new Choice(0));
        toolCard.start(player);
        toolCard.setParameter(draftPool.getCell(0));

       toolCard.setParameter(new Choice(1));
    }

}