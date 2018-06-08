package server.state.toolcards2;

import static org.junit.Assert.*;



import common.exceptions.InvalidMoveException;
import common.exceptions.WrongParameter;
import org.junit.Before;
import org.junit.Test;
import org.mockito.*;
import server.model.Model;
import server.model.state.ModelObject.ModelType;
import server.model.state.boards.draftpool.DraftPool;
import server.model.state.dice.Dice;
import server.model.state.player.Player;
import server.model.state.toolcards.TamponeDiamantato;
import server.model.state.toolcards.ToolCard;
import server.model.state.utilities.Color;


public class TamponeDiamantatoTest {
        private Model model;
        private ToolCard toolCard;
        private Player player;
        private DraftPool draftPool = new DraftPool();
        @Before
        public void setUp() throws Exception {
            model = Mockito.spy(new Model());
            player = Mockito.mock(Player.class);
            toolCard = new TamponeDiamantato(model);
            draftPool = model.getState().getDraftPool();
            draftPool.increaseSize();
        }
        @Test
        public void start() {
            try {
                toolCard.start(player);
            } catch (InvalidMoveException e) {
                e.printStackTrace();
            }
        }
        @Test
        public void setParameter() throws InvalidMoveException {
            draftPool.getCell(1).put(new Dice(Color.RED,3));
            toolCard.start(player);
            try {
                try {
                    toolCard.setParameter(draftPool.getCell(0));
                } catch (WrongParameter wrongParameter) {
                    wrongParameter.printStackTrace();
                }
            } catch (InvalidMoveException e) {
                assertEquals("PoolCell is empty", e.getMessage());
            }
            draftPool.getCell(0).put(new Dice(Color.RED, 4));
            toolCard.start(player);
            try {
                try {
                    toolCard.setParameter(draftPool.getCell(0));
                } catch (WrongParameter wrongParameter) {
                    wrongParameter.printStackTrace();
                }
            } catch (InvalidMoveException e) {
                e.printStackTrace();
            }
            assertEquals(3,draftPool.getCell(0).getDice().getValue());
        }
        @Test
        public void shouldGetNumber(){
            assertEquals(10,toolCard.getNumber());
        }
        @Test
        public void shouldGetType(){
            assertEquals(ModelType.TOOL_CARD,toolCard.getType());
        }

    }