package client.view.cli;

import common.exceptions.InvalidMoveException;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import server.state.boards.windowframe.WindowFrame;
import server.state.boards.windowframe.WindowFrameList;
import server.state.dice.Dice;
import server.state.player.Player;
import server.state.utilities.Color;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

public class CliDisplayerTest {
    private String [][] stringFrame=new String[4][5];

    private String check;
    private CliPlayerState cliPlayerState;
    private Player player;
    private CliDisplayer cliDisplayer;


    @Before
    public void initClass() throws InvalidMoveException {
        cliPlayerState=Mockito.mock(CliPlayerState.class);
        player=Mockito.mock(Player.class);
        cliDisplayer=Mockito.mock(CliDisplayer.class);
        stringFrame[0][0]="X";
        stringFrame[0][1]="3G";
        stringFrame[0][2]="3";
        stringFrame[0][3]="G";
        stringFrame[0][4]="3B";
        stringFrame[1][0]="X";
        stringFrame[1][1]="X";   //X3G3G3BXX4Y6BXR12G1GYBX3RY
        stringFrame[1][2]="4";
        stringFrame[1][3]="Y";
        stringFrame[1][4]="6B";
        stringFrame[2][0]="X";
        stringFrame[2][1]="R";
        stringFrame[2][2]="1";
        stringFrame[2][3]="2G";
        stringFrame[2][4]="1G";
        stringFrame[3][0]="Y";
        stringFrame[3][1]="B";
        stringFrame[3][2]="X";
        stringFrame[3][3]="3R";
        stringFrame[3][4]="Y";
        for(int i=0;i<4;i++)
          for(int j=0;j<5;j++)
             when(cliPlayerState.getWindowFrame()[i][j]).thenReturn(stringFrame[i][j]);



            }
    @Test
    public void shouldPrintWindowFrame(){
        cliDisplayer.printWindowFrame(cliPlayerState);

    }
}