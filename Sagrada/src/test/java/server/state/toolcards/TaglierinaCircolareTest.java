package server.state.toolcards;

import common.exceptions.InvalidMoveException;
import org.junit.Before;
import org.junit.Test;
import org.mockito.*;
import server.model.Model;
import server.model.state.boards.draftpool.DraftPool;
import server.model.state.boards.roundtrack.RoundTrack;
import server.model.state.dice.Dice;
import server.model.state.player.Player;
import server.model.state.toolcards.TaglierinaCircolare;
import server.model.state.toolcards.ToolCard;
import server.model.state.utilities.Color;

public class TaglierinaCircolareTest {
    private Model model;
    private ToolCard toolCard;
    private Player player;
    private DraftPool draftPool;
    private RoundTrack roundTrack;
    private Dice dice1;

    @Before
    public void setUp() throws Exception {
        model = Mockito.mock(Model.class);
        player = Mockito.mock(Player.class);
        toolCard = new TaglierinaCircolare(model);
        dice1 = new Dice (Color.BLUE,4);
        draftPool = new DraftPool();
        draftPool.increaseSize();
        draftPool.getCell(0).put(new Dice(Color.RED, 4));
        roundTrack = new RoundTrack();
        roundTrack.endRound(draftPool);
        draftPool.getCell(1).put(new Dice(Color.BLUE,5));
           }
    @Test
    public void start() throws InvalidMoveException {
        toolCard.start(player);
    }

    @Test
    public void shouldDoAbility() throws InvalidMoveException {
        toolCard.start(player);
        toolCard.setParameter(draftPool.getCell(1));
        toolCard.setParameter(roundTrack.getRoundSet(1).get(0));
    }

}