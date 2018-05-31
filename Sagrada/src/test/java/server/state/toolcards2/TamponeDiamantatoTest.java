package server.state.toolcards2;

import static org.junit.Assert.*;



import common.exceptions.InvalidMoveException;
import common.exceptions.WrongParameter;
import org.junit.Before;
import org.junit.Test;
import org.mockito.*;
import server.model.Model;
import server.model.state.boards.draftpool.DraftPool;
import server.model.state.dice.Dice;
import server.model.state.player.Player;
import server.model.state.toolcards.TamponeDiamantato;
import server.model.state.toolcards.ToolCard;
import server.model.state.utilities.Color;


public class TamponeDiamantatoTest {


        private Model model;
        private ToolCard test;
        private Player player;
        private DraftPool draftPool = new DraftPool();

        @Before
        public void setUp() throws Exception {
            model = Mockito.spy(new Model());
            player = Mockito.mock(Player.class);
            test = new TamponeDiamantato(model);
            draftPool = model.getState().getDraftPool();
            //draftPool = new DraftPool();    vorrei farlo qua ma mi dice che è empty
            draftPool.increaseSize();
            draftPool.getCell(0).put(new Dice(Color.RED, 4));
        }

        @Test
        public void start() throws InvalidMoveException {
            test.start(player);
        }



        @Test
        public void setParameter() throws InvalidMoveException {
            test.start(player);

            try {
                try {
                    test.setParameter(draftPool.getCell(0));
                } catch (WrongParameter wrongParameter) {
                    wrongParameter.printStackTrace();
                }
            } catch (InvalidMoveException e) {
                assertEquals("Wrong parameter", e.getMessage());
            }
            assertEquals(3,draftPool.getCell(0).getDice().getValue());


        }
    }