package client.view.cli;

import common.exceptions.InvalidMoveException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import server.state.boards.draftpool.DraftPool;
import server.state.boards.windowframe.WindowFrame;
import server.state.boards.windowframe.WindowFrameList;
import server.state.dice.Dice;
import server.state.objectivecards.privateobjectivecards.PrivateObjectiveCard;
import server.state.player.Player;
import server.state.utilities.Color;
import static org.junit.Assert.*;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class CliDisplayerTest {

    private String[][] stringFrame = new String[4][5];
    private CliPlayerState cliPlayerState;
    private CliDisplayer cliDisplayer;
    private CliState cliState;
    private String privateObjectiveCard;
    private List<String> draftPool = new ArrayList<>();
    private ByteArrayOutputStream outContent;
    private Integer[] toolCard = new Integer[3];
    private Integer[] publicObjectiveCardIds = new Integer[3];



    @Before
    public void initClass() throws Exception {
        cliPlayerState = Mockito.mock(CliPlayerState.class);
        cliState = Mockito.mock(CliState.class);
        cliDisplayer = new CliDisplayer();
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
        toolCard[0]=1;
        toolCard[1]=5;
        toolCard[2]=12;
        publicObjectiveCardIds[0]=1;
        publicObjectiveCardIds[1]=12;
        publicObjectiveCardIds[2]=5;

        draftPool.add("3B");
        draftPool.add("X");
        draftPool.add("5Y");

        when(cliPlayerState.getWindowFrame()).thenReturn(stringFrame);
        when(cliState.getToolCardIds()).thenReturn(toolCard);
        when(cliState.getDraftPool()).thenReturn(draftPool);
        when(cliState.getPublicObjectiveCardIds()).thenReturn(publicObjectiveCardIds);

        privateObjectiveCard = "RED";
        when(cliPlayerState.getPrivateObjectiveCard()).thenReturn(privateObjectiveCard);

    }

    @Test
    public void shouldPrintWindowFrame() {
        outContent = new ByteArrayOutputStream();
        cliDisplayer.printWindowFrame(cliPlayerState);
    }
    @Test
    public void shouldPrintMenu() {
        outContent = new ByteArrayOutputStream();
        cliDisplayer.printMenu();
    }
    @Test
    public void shouldPrintDraftPool(){
        outContent = new ByteArrayOutputStream();
        cliDisplayer.printDraftPool(cliState);
    }
    @Test
    public void shouldPrintToolCard(){
        outContent = new ByteArrayOutputStream();
        cliDisplayer.printToolCard(cliState);
    }
    @Test
    public void shouldPrintPrivateObjeciveCard(){
        outContent = new ByteArrayOutputStream();
        cliDisplayer.printPrivateObjectiveCard(cliPlayerState);
    }
    @Test
    public void shouldPrintPublicObjectiveCard(){
        outContent = new ByteArrayOutputStream();
        cliDisplayer.printPublicObjectiveCards(cliState);
    }

}