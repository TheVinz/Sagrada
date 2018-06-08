package server.model.toolcards;

import common.exceptions.InvalidMoveException;
import common.exceptions.WrongParameter;
import org.junit.Before;
import org.junit.Test;
import server.model.state.ModelObject.ModelObject;
import server.model.state.ModelObject.ModelType;
import server.model.state.boards.windowframe.WindowFrame;
import server.model.state.boards.windowframe.WindowFrameCell;
import server.model.state.boards.windowframe.WindowFrameList;
import server.model.state.dice.Dice;
import server.model.state.player.Player;
import server.model.Model;
import server.model.state.toolcards.Lathekin;
import server.model.state.toolcards.ToolCard;
import server.model.state.utilities.Color;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

public class LathekinTest {

    private Player player;
    private ToolCard toolCard;
    private boolean firstMoveDone;
    private List<ModelObject> parameters;
    private WindowFrame frame;
    private WindowFrame secondFrame;


    @Before
    public void setUp() throws Exception {

        Model model = spy(new Model());
        toolCard = new Lathekin(model);
        frame = new WindowFrame(WindowFrameList.AURORA_SAGRADIS);
        secondFrame = new WindowFrame(WindowFrameList.AURORAE_MAGNIFICUS);
        player = mock(Player.class);
        when(player.getWindowFrame()).thenReturn(frame);
        frame.getCell(0,0).put(new Dice(Color.RED,6));
        frame.getCell(0,2).put(new Dice(Color.BLUE,4)); //non vorrei aggiungerlo ma devo

        parameters=new ArrayList<>(8);
    }

       public void doAbility() throws InvalidMoveException {
        toolCard.start(player);
        Dice dice1 = new Dice(Color.RED, 5);
        Dice dice2 = new Dice(Color.PURPLE, 2);
       // player.getWindowFrame().getCell(0, 0).put(dice);
      //  player.getWindowFrame().getCell(1, 0).put(dice1);
       // player.getWindowFrame().getCell(2, 2).put(dice2);
                toolCard.start(player);
    }
    @Test
    public void shouldSetParameter() throws InvalidMoveException {
        toolCard.start(player);
        try {
            toolCard.setParameter(frame.getCell(0, 0));    //passo una cella invece che la vetrata
        } catch (InvalidMoveException e) {
            e.printStackTrace();
        } catch (WrongParameter wrongParameter) {
            wrongParameter.printStackTrace();
        }
        try {
            toolCard.setParameter(secondFrame);     //passo la vetrata sbagliata
        } catch (InvalidMoveException e) {
            e.printStackTrace();
        } catch (WrongParameter wrongParameter) {
            wrongParameter.printStackTrace();
        }
        try {
            toolCard.setParameter(frame.getCell(0, 0));    //passso la cella giusta
        } catch (InvalidMoveException e) {
            e.printStackTrace();
        } catch (WrongParameter wrongParameter) {
            wrongParameter.printStackTrace();
        }
        try {
            toolCard.setParameter(frame.getCell(0, 1));    //lancia eccezione perchè non è una windowframe
        } catch (WrongParameter wrongParameter) {
            assertEquals("Wrong parameter", wrongParameter.getMessage());
        }
        try {
            toolCard.setParameter(frame);    //giusta windowframe
        } catch (InvalidMoveException e) {
            e.printStackTrace();
        } catch (WrongParameter wrongParameter) {
            wrongParameter.printStackTrace();
        }
        try {
            try {
                toolCard.setParameter(frame.getCell(0, 1));      //caso windowframe sbagliata
            } catch (WrongParameter wrongParameter) {
                wrongParameter.printStackTrace();
            }
        } catch (InvalidMoveException invalidMoveException) {
            invalidMoveException.printStackTrace();
        }

        toolCard.start(player);   //firstSource=firstTarget
        try {
            toolCard.setParameter(frame);
        } catch (InvalidMoveException e) {
            e.printStackTrace();
        } catch (WrongParameter wrongParameter) {
            wrongParameter.printStackTrace();
        }
        try {
            toolCard.setParameter(frame.getCell(0, 0));    //passso la cella giusta
        } catch (InvalidMoveException e) {
            e.printStackTrace();
        } catch (WrongParameter wrongParameter) {
            wrongParameter.printStackTrace();
        }
        try {
            toolCard.setParameter(frame);
        } catch (InvalidMoveException e) {
            e.printStackTrace();
        } catch (WrongParameter wrongParameter) {
            wrongParameter.printStackTrace();
        }
        try {
            toolCard.setParameter(frame.getCell(0, 0));   //stessa cella
        } catch (InvalidMoveException e) {
            e.printStackTrace();
        } catch (WrongParameter wrongParameter) {
            wrongParameter.printStackTrace();
        }

        toolCard.start(player);
        try {
            toolCard.setParameter(frame);
        } catch (InvalidMoveException e) {
            e.printStackTrace();
        } catch (WrongParameter wrongParameter) {
            wrongParameter.printStackTrace();
        }
        try {
            toolCard.setParameter(frame.getCell(0, 0));    //passso la cella giusta
        } catch (InvalidMoveException e) {
            e.printStackTrace();
        } catch (WrongParameter wrongParameter) {
            wrongParameter.printStackTrace();
        }
        try {
            toolCard.setParameter(frame);
        } catch (InvalidMoveException e) {
            e.printStackTrace();
        } catch (WrongParameter wrongParameter) {
            wrongParameter.printStackTrace();
        }
        try {
            toolCard.setParameter(frame.getCell(1, 3));    //passso la cellasbagliata
        } catch (InvalidMoveException e) {
            e.printStackTrace();
        } catch (WrongParameter wrongParameter) {
            wrongParameter.printStackTrace();
        }

        toolCard.start(player);
        try {
            toolCard.setParameter(frame);
        } catch (InvalidMoveException e) {
            e.printStackTrace();
        } catch (WrongParameter wrongParameter) {
            wrongParameter.printStackTrace();
        }
        try {
            toolCard.setParameter(frame.getCell(0, 0));    //passso la cella giusta
        } catch (InvalidMoveException e) {
            e.printStackTrace();
        } catch (WrongParameter wrongParameter) {
            wrongParameter.printStackTrace();
        }
        try {
            toolCard.setParameter(frame);
        } catch (InvalidMoveException e) {
            e.printStackTrace();
        } catch (WrongParameter wrongParameter) {
            wrongParameter.printStackTrace();
        }
        frame.getCell(0, 4).put(new Dice(Color.BLUE, 3));
        try {
            toolCard.setParameter(frame.getCell(0, 4));    //passso una cella
        } catch (InvalidMoveException e) {
            e.printStackTrace();
        } catch (WrongParameter wrongParameter) {
            wrongParameter.printStackTrace();
        }


        toolCard.start(player);
        try {
            toolCard.setParameter(frame);
        } catch (InvalidMoveException e) {
            e.printStackTrace();
        } catch (WrongParameter wrongParameter) {
            wrongParameter.printStackTrace();
        }
        try {
            toolCard.setParameter(frame.getCell(0, 0));   //giusta
        } catch (InvalidMoveException e) {
            e.printStackTrace();
        } catch (WrongParameter wrongParameter) {
            wrongParameter.printStackTrace();
        }
        System.out.println(frame.getCell(0,0).getDice().getColor());
        try {
            toolCard.setParameter(frame);
        } catch (InvalidMoveException e) {
            e.printStackTrace();
        } catch (WrongParameter wrongParameter) {
            wrongParameter.printStackTrace();
        }
        try {
            try {
                toolCard.setParameter(frame.getCell(0, 1));
            } catch (WrongParameter wrongParameter) {
                wrongParameter.printStackTrace();
            }
        } catch (InvalidMoveException invalidMoveException) {
            invalidMoveException.printStackTrace();
        }

        //primo mosso correttamente
        try {
            toolCard.setParameter(secondFrame);  //passo una windowframe sbagliata
        } catch (InvalidMoveException e) {
            e.printStackTrace();
        } catch (WrongParameter wrongParameter) {
            wrongParameter.printStackTrace();
        }
        try {
            toolCard.setParameter(frame.getCell(0, 0));   //sbagliata ma si ferma prima
        } catch (InvalidMoveException e) {
            e.printStackTrace();
        } catch (WrongParameter wrongParameter) {
            wrongParameter.printStackTrace();
        }
        try {
            toolCard.setParameter(frame);
        } catch (InvalidMoveException e) {
            e.printStackTrace();
        } catch (WrongParameter wrongParameter) {
            wrongParameter.printStackTrace();
        }
        try {
            toolCard.setParameter(frame.getCell(0, 1));
        } catch (InvalidMoveException e) {
            e.printStackTrace();
        } catch (WrongParameter wrongParameter) {
            wrongParameter.printStackTrace();
        }
        //secondo con windowframe sbagliata


        toolCard.start(player);
        try {
            toolCard.setParameter(frame);
        } catch (InvalidMoveException e) {
            e.printStackTrace();
        } catch (WrongParameter wrongParameter) {
            wrongParameter.printStackTrace();
        }
        frame.getCell(0,0).put(new Dice(Color.RED,6));
        try {
            toolCard.setParameter(frame.getCell(0, 0));   //giusta
        } catch (InvalidMoveException e) {
            e.printStackTrace();
        } catch (WrongParameter wrongParameter) {
            wrongParameter.printStackTrace();
        }
        try {
            toolCard.setParameter(frame);
        } catch (InvalidMoveException e) {
            e.printStackTrace();
        } catch (WrongParameter wrongParameter) {
            wrongParameter.printStackTrace();
        }
        frame.getCell(0,1).removeDice();
      //  System.out.println(frame.getCell(0,1).getDice().getColor());
        try {
            try {
                toolCard.setParameter(frame.getCell(0, 1));    //first target
            } catch (WrongParameter wrongParameter) {
                wrongParameter.printStackTrace();
            }
        } catch (InvalidMoveException invalidMoveException) {
            invalidMoveException.printStackTrace();
        }
        //primo mosso correttamente
        try {
            toolCard.setParameter(frame);
        } catch (InvalidMoveException e) {
            e.printStackTrace();
        } catch (WrongParameter wrongParameter) {
            wrongParameter.printStackTrace();
        }
        try {
            toolCard.setParameter(frame.getCell(0, 1));   //cell uguale al target di prima, non dovrebbe andare
        } catch (InvalidMoveException e) {
            e.printStackTrace();
        } catch (WrongParameter wrongParameter) {
            wrongParameter.printStackTrace();
        }
        try {
            toolCard.setParameter(frame);
        } catch (InvalidMoveException e) {
            e.printStackTrace();
        } catch (WrongParameter wrongParameter) {
            wrongParameter.printStackTrace();
        }
        try {
            toolCard.setParameter(frame.getCell(0, 4));   //giusta
        } catch (InvalidMoveException e) {
            e.printStackTrace();
        } catch (WrongParameter wrongParameter) {
            wrongParameter.printStackTrace();
        }
        //secondsource uguale al firstTarget, non mi entra in questo caso


        toolCard.start(player);
        try {
            toolCard.setParameter(frame);
        } catch (InvalidMoveException e) {
            e.printStackTrace();
        } catch (WrongParameter wrongParameter) {
            wrongParameter.printStackTrace();
        }
        frame.getCell(0,0).put(new Dice(Color.RED,6));
        try {
            toolCard.setParameter(frame.getCell(0, 0));   //giusta
        } catch (InvalidMoveException e) {
            e.printStackTrace();
        } catch (WrongParameter wrongParameter) {
            wrongParameter.printStackTrace();
        }
        try {
            toolCard.setParameter(frame);
        } catch (InvalidMoveException e) {
            e.printStackTrace();
        } catch (WrongParameter wrongParameter) {
            wrongParameter.printStackTrace();
        }
        frame.getCell(0,1).removeDice();
        try {
            try {
                toolCard.setParameter(frame.getCell(0, 1));    //first target
            } catch (WrongParameter wrongParameter) {
                wrongParameter.printStackTrace();
            }
        } catch (InvalidMoveException invalidMoveException) {
            invalidMoveException.printStackTrace();
        }
        //primo mosso correttamente
        try {
            toolCard.setParameter(frame);
        } catch (InvalidMoveException e) {
            e.printStackTrace();
        } catch (WrongParameter wrongParameter) {
            wrongParameter.printStackTrace();
        }
        try {
            toolCard.setParameter(frame.getCell(2, 3));   //cella vuota
        } catch (InvalidMoveException e) {
            e.printStackTrace();
        } catch (WrongParameter wrongParameter) {
            wrongParameter.printStackTrace();
        }
        try {
            toolCard.setParameter(frame);
        } catch (InvalidMoveException e) {
            e.printStackTrace();
        } catch (WrongParameter wrongParameter) {
            wrongParameter.printStackTrace();
        }
        try {
            toolCard.setParameter(frame.getCell(0, 1));
        } catch (InvalidMoveException e) {
            e.printStackTrace();
        } catch (WrongParameter wrongParameter) {
            wrongParameter.printStackTrace();
        }
        //second source vuota


        toolCard.start(player);
        try {
            toolCard.setParameter(frame);
        } catch (InvalidMoveException e) {
            e.printStackTrace();
        } catch (WrongParameter wrongParameter) {
            wrongParameter.printStackTrace();
        }
        frame.getCell(0,0).put(new Dice(Color.RED,6));
        try {
            toolCard.setParameter(frame.getCell(0, 0));   //giusta
        } catch (InvalidMoveException e) {
            e.printStackTrace();
        } catch (WrongParameter wrongParameter) {
            wrongParameter.printStackTrace();
        }
        try {
            toolCard.setParameter(frame);
        } catch (InvalidMoveException e) {
            e.printStackTrace();
        } catch (WrongParameter wrongParameter) {
            wrongParameter.printStackTrace();
        }
        frame.getCell(0,1).removeDice();
        try {
            try {
                toolCard.setParameter(frame.getCell(0, 1));    //first target
            } catch (WrongParameter wrongParameter) {
                wrongParameter.printStackTrace();
            }
        } catch (InvalidMoveException invalidMoveException) {
            invalidMoveException.printStackTrace();
        }
        //primo mosso correttamente
        try {
            toolCard.setParameter(frame);
        } catch (InvalidMoveException e) {
            e.printStackTrace();
        } catch (WrongParameter wrongParameter) {
            wrongParameter.printStackTrace();
        }
        try {
            toolCard.setParameter(frame.getCell(0, 2));   //giusta
        } catch (InvalidMoveException e) {
            e.printStackTrace();
        } catch (WrongParameter wrongParameter) {
            wrongParameter.printStackTrace();
        }
        try {
            toolCard.setParameter(frame);
        } catch (InvalidMoveException e) {
            e.printStackTrace();
        } catch (WrongParameter wrongParameter) {
            wrongParameter.printStackTrace();
        }
        try {
            toolCard.setParameter(frame.getCell(0, 2));   //uguale alla second source
        } catch (InvalidMoveException e) {
            e.printStackTrace();
        } catch (WrongParameter wrongParameter) {
            wrongParameter.printStackTrace();
        }
        //caso secondSource=secondTarget

        toolCard.start(player);
        try {
            toolCard.setParameter(frame);
        } catch (InvalidMoveException e) {
            e.printStackTrace();
        } catch (WrongParameter wrongParameter) {
            wrongParameter.printStackTrace();
        }
        frame.getCell(0,0).put(new Dice(Color.RED,6));
        try {
            toolCard.setParameter(frame.getCell(0, 0));   //giusta
        } catch (InvalidMoveException e) {
            e.printStackTrace();
        } catch (WrongParameter wrongParameter) {
            wrongParameter.printStackTrace();
        }
        try {
            toolCard.setParameter(frame);
        } catch (InvalidMoveException e) {
            e.printStackTrace();
        } catch (WrongParameter wrongParameter) {
            wrongParameter.printStackTrace();
        }
        frame.getCell(0,1).removeDice();
        try {
            try {
                toolCard.setParameter(frame.getCell(0, 1));    //first target
            } catch (WrongParameter wrongParameter) {
                wrongParameter.printStackTrace();
            }
        } catch (InvalidMoveException invalidMoveException) {
            invalidMoveException.printStackTrace();
        }
        //primo mosso correttamente
        try {
            toolCard.setParameter(frame);
        } catch (InvalidMoveException e) {
            e.printStackTrace();
        } catch (WrongParameter wrongParameter) {
            wrongParameter.printStackTrace();
        }
        try {
            toolCard.setParameter(frame.getCell(0, 2));   //giusta
        } catch (InvalidMoveException e) {
            e.printStackTrace();
        } catch (WrongParameter wrongParameter) {
            wrongParameter.printStackTrace();
        }
        try {
            toolCard.setParameter(frame);
        } catch (InvalidMoveException e) {
            e.printStackTrace();
        } catch (WrongParameter wrongParameter) {
            wrongParameter.printStackTrace();
        }
        try {
            toolCard.setParameter(frame.getCell(1, 0));   //giusta
        } catch (InvalidMoveException e) {
            e.printStackTrace();
        } catch (WrongParameter wrongParameter) {
            wrongParameter.printStackTrace();
        }
        //secondo dado mosso correttamente




        secondFrame.getCell(0,0).put(new Dice(Color.RED,5));
        secondFrame.getCell(0,1).put(new Dice(Color.GREEN,1));
        secondFrame.getCell(0,2).put(new Dice(Color.BLUE,5));
        secondFrame.getCell(0,3).put(new Dice(Color.PURPLE,1));
        secondFrame.getCell(0,4).put(new Dice(Color.RED,2));
        secondFrame.getCell(1,0).put(new Dice(Color.PURPLE,1));
        secondFrame.getCell(1,1).put(new Dice(Color.RED,5));
        secondFrame.getCell(1,2).put(new Dice(Color.YELLOW,1));
        secondFrame.getCell(1,3).put(new Dice(Color.RED,5));
        secondFrame.getCell(1,4).put(new Dice(Color.YELLOW,1));
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
        try {
            toolCard.setParameter(secondFrame);
        } catch (InvalidMoveException e) {
            e.printStackTrace();
        } catch (WrongParameter wrongParameter) {
            wrongParameter.printStackTrace();
        }
        try {
            toolCard.setParameter(secondFrame.getCell(3, 1));   //giusta
        } catch (InvalidMoveException e) {
            e.printStackTrace();
        } catch (WrongParameter wrongParameter) {
            wrongParameter.printStackTrace();
        }
        try {
            toolCard.setParameter(secondFrame);
        } catch (InvalidMoveException e) {
            e.printStackTrace();
        } catch (WrongParameter wrongParameter) {
            wrongParameter.printStackTrace();
        }
        try {
            toolCard.setParameter(secondFrame.getCell(3, 4));   //giusta
        } catch (InvalidMoveException e) {
            e.printStackTrace();
        } catch (WrongParameter wrongParameter) {
            wrongParameter.printStackTrace();
        }
        try {
            toolCard.setParameter(secondFrame);
        } catch (InvalidMoveException e) {
            e.printStackTrace();
        } catch (WrongParameter wrongParameter) {
            wrongParameter.printStackTrace();
        }
        try {
            toolCard.setParameter(secondFrame.getCell(0, 1));   //giusta
        } catch (InvalidMoveException e) {
            e.printStackTrace();
        } catch (WrongParameter wrongParameter) {
            wrongParameter.printStackTrace();
        }
        try {
            toolCard.setParameter(secondFrame);
        } catch (InvalidMoveException e) {
            e.printStackTrace();
        } catch (WrongParameter wrongParameter) {
            wrongParameter.printStackTrace();
        }
        try {
            toolCard.setParameter(secondFrame.getCell(3, 1));   //giusta
        } catch (InvalidMoveException e) {
            e.printStackTrace();
        } catch (WrongParameter wrongParameter) {
            wrongParameter.printStackTrace();
        }



    }
}