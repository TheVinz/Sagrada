package server.model.toolcards;

import common.exceptions.InvalidMoveException;
import common.exceptions.WrongParameter;
import org.junit.Before;
import org.junit.Test;
import org.mockito.*;
import server.GameManager;
import server.model.Model;
import server.model.state.boards.draftpool.DraftPool;
import server.model.state.boards.roundtrack.RoundTrack;
import server.model.state.dice.Dice;
import server.model.state.player.Player;
import server.model.state.toolcards.TaglierinaCircolare;
import server.model.state.toolcards.ToolCard;
import server.model.state.utilities.Color;

import java.rmi.RemoteException;

import static junit.framework.TestCase.assertEquals;

public class TaglierinaCircolareTest {
    private Model model;
    private ToolCard toolCard;
    private Player player;
    private DraftPool draftPool = new DraftPool();
    private RoundTrack roundTrack = new RoundTrack();
    private GameManager gameManager;

    @Before
    public void setUp() throws Exception {
        gameManager = new GameManager();
        model = Mockito.spy(new Model(gameManager));
        player = Mockito.mock(Player.class);
        toolCard = new TaglierinaCircolare(model);
        draftPool = model.getState().getDraftPool();
        draftPool.increaseSize();
        roundTrack = model.getState().getRoundTrack();

        }
        @Test
        public void shouldStart() throws InvalidMoveException {
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
        draftPool.getCell(0).put(new Dice(Color.RED, 4));
        roundTrack.endRound(draftPool);
        draftPool.getCell(0).put(new Dice(Color.GREEN,3));
        try {
            toolCard.start(player);
        } catch (InvalidMoveException e) {
            e.printStackTrace();
        }
        try {
            toolCard.setParameter(draftPool.getCell(0));
        } catch (InvalidMoveException e) {
            e.printStackTrace();
        } catch (WrongParameter wrongParameter) {
            wrongParameter.printStackTrace();
        }
        try {
            toolCard.setParameter(roundTrack.getRoundSet(1).get(0));
        } catch (InvalidMoveException e) {
            e.printStackTrace();
        } catch (WrongParameter wrongParameter) {
            wrongParameter.printStackTrace();
        }
        toolCard.start(player);
        try{
            try{
                toolCard.setParameter(draftPool.getCell(2));
            }catch (WrongParameter wrongParameter){
                wrongParameter.printStackTrace();
            }
        }catch (InvalidMoveException e){
           e.printStackTrace();
        }
        try{
            try{
                toolCard.setParameter(roundTrack.getRoundSet(1).get(0));
            }catch (WrongParameter wrongParameter){
                wrongParameter.printStackTrace();
            }
        }catch (InvalidMoveException e){
            assertEquals("PoolCell is empty",e.getMessage());
        }
    }
    @Test
    public void shouldGetNumber(){
        assertEquals(5,toolCard.getNumber());
    }
    @Test
    public void shouldGetColor(){
        assertEquals(Color.GREEN,toolCard.getColor());
    }

}