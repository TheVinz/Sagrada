package client.view.cli;

import common.exceptions.InvalidMoveException;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import server.state.boards.windowframe.WindowFrame;
import server.state.boards.windowframe.WindowFrameList;
import server.state.dice.Dice;
import server.state.objectivecards.privateobjectivecards.PrivateObjectiveCard;
import server.state.player.Player;
import server.state.utilities.Color;




import static org.junit.Assert.*;
import static org.mockito.Mockito.when;
import java.io.*;
public class CliDisplayerTest {

    private String[][] stringFrame = new String[4][5];
    private CliPlayerState cliPlayerState;
    private CliDisplayer cliDisplayer;
    private String privateObjectiveCard;


    @Before
    public void initClass() throws InvalidMoveException {
        cliPlayerState = Mockito.mock(CliPlayerState.class);
        cliDisplayer = Mockito.mock(CliDisplayer.class);
        stringFrame[0][0] = "X";
        stringFrame[0][1] = "3G";
        stringFrame[0][2] = "3";
        stringFrame[0][3] = "G";
        stringFrame[0][4] = "3B";
        stringFrame[1][0] = "X";
        stringFrame[1][1] = "X";   //X3G3G3BXX4Y6BXR12G1GYBX3RY
        stringFrame[1][2] = "4";
        stringFrame[1][3] = "Y";
        stringFrame[1][4] = "6B";
        stringFrame[2][0] = "X";
        stringFrame[2][1] = "R";
        stringFrame[2][2] = "1";
        stringFrame[2][3] = "2G";
        stringFrame[2][4] = "1G";
        stringFrame[3][0] = "Y";
        stringFrame[3][1] = "B";
        stringFrame[3][2] = "X";
        stringFrame[3][3] = "3R";
        stringFrame[3][4] = "Y";

        for (int i = 0; i < 4; i++)
            for (int j = 0; j < 5; j++) {
                when(cliPlayerState.getWindowFrame()).thenReturn(stringFrame);
            }
            privateObjectiveCard = "RED";
            when(cliPlayerState.getPrivateObjectiveCard()).thenReturn(privateObjectiveCard);

    }

    @Test
    public void shouldPrintWindowFrame() {
        cliDisplayer.printWindowFrame(cliPlayerState);
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 5; j++) {
                cliDisplayer.displayText(cliPlayerState.getWindowFrame()[i][j]);
                System.out.print(cliPlayerState.getWindowFrame()[i][j]+" ");
                if(cliPlayerState.getWindowFrame()[i][j].length()==1)
                System.out.print(" ");
            }
            cliDisplayer.displayText("/n");
           System.out.println();
        }
    }

    @Test
    public void shouldDisplayText() {
        cliDisplayer.displayText("/n");

    }

    @Test
    public void shouldPrintPrivateObjeciveCard(){
        System.out.print("La tua carta privata: ");
        System.out.println(cliPlayerState.getPrivateObjectiveCard());

    }
}