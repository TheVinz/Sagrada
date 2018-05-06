package server.state.toolcards;

import static org.junit.Assert.*;



import common.exceptions.InvalidMoveException;
import org.junit.Before;
import org.junit.Test;
import org.mockito.*;
import server.Model;
import server.state.boards.draftpool.DraftPool;
import server.state.boards.draftpool.DraftPoolCell;
import server.state.boards.windowframe.WindowFrame;
import server.state.boards.windowframe.WindowFrameCell;
import server.state.dice.Dice;
import server.state.player.Player;
import server.state.utilities.Color;


import static org.junit.Assert.*;


    public class TamponeDiamantatoTest {


        private Model model;
        private ToolCard test;
        private Player player;
        private DraftPool draftPool;

        @Before
        public void setUp() throws Exception {
            model = Mockito.mock(Model.class);
            player = Mockito.mock(Player.class);
            test = new TamponeDiamantato(model);
            draftPool = new DraftPool();
            draftPool.increaseSize();

            draftPool.getCell(0).put(new Dice(Color.RED, 4));
        }

        @Test
        public void start() throws InvalidMoveException {
            test.start(player);
        }

        @Test
        public void doAbility() throws InvalidMoveException {
            test.start(player);
            test.setParameter(draftPool.getCell(0));


        }

        @Test
        public void setParameter() throws InvalidMoveException {
            test.start(player);

            try {
                test.setParameter(draftPool.getCell(0));
            } catch (InvalidMoveException e) {
                assertEquals("Wrong parameter", e.getMessage());
            }


        }
    }