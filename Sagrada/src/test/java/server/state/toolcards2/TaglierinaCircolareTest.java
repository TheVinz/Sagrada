package server.state.toolcards2;

import common.exceptions.InvalidMoveException;
import common.exceptions.WrongParameter;
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

import java.rmi.RemoteException;

public class TaglierinaCircolareTest {
    private Model model;
    private ToolCard toolCard;
    private Player player;
    private DraftPool draftPool = new DraftPool();
    private RoundTrack roundTrack = new RoundTrack();
    private Dice dice1;

    @Before
    public void setUp() throws Exception {
        model = Mockito.spy(new Model());
        player = Mockito.mock(Player.class);
        toolCard = new TaglierinaCircolare(model);
        draftPool = model.getState().getDraftPool();
        //dice1 = new Dice (Color.BLUE,4);
      //  draftPool = new DraftPool();
        draftPool.getCell(0).put(new Dice(Color.RED, 4));
        roundTrack = model.getState().getRoundTrack();
       // roundTrack = new RoundTrack();
        roundTrack.endRound(draftPool);
        System.out.println(roundTrack.getRoundSet(1).get(0).getDice().getColor());
        // System.out.println(draftPool.getCell(0).getDice().getColor());
       // draftPool = model.getState().getDraftPool();
        draftPool.getCell(0).put(new Dice(Color.GREEN,3));
       // draftPool.getCell(1).put(new Dice(Color.BLUE,5));
           }
    @Test
    public void shouldDoAbility() throws InvalidMoveException {
        try {
            toolCard.start(player);
        } catch (InvalidMoveException e) {
            e.printStackTrace();
        }
        try {
            toolCard.setParameter(draftPool.getCell(0));
            System.out.println(draftPool.getCell(0).getDice().getColor());
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
        draftPool.getCell(0).removeDice();
        try {
            toolCard.setParameter(roundTrack.getRoundSet(1).get(0));
        } catch (InvalidMoveException e) {
            e.printStackTrace();
        } catch (WrongParameter wrongParameter) {
              wrongParameter.printStackTrace();
        }
        try {
            toolCard.setParameter(draftPool.getCell(0));
        } catch (InvalidMoveException e) {
            e.printStackTrace();
        } catch (WrongParameter wrongParameter) {
            wrongParameter.printStackTrace();
        }


    }

}