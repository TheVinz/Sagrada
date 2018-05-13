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
    private List<CliPlayerState> cliPlayerStates;
    private CliDisplayer cliDisplayer;
    private CliState cliState;
    private String privateObjectiveCard;
    private List<String> draftPool = new ArrayList<>();
    private ByteArrayOutputStream outContent;
    private Integer[] toolCard = new Integer[3];
    private Integer[] publicObjectiveCardIds = new Integer[3];
    private Integer favorTokens;
    private List<String> roundTrack = new ArrayList<String>();



    @Before
    public void initClass() throws Exception {


        //cliState = Mockito.mock(CliState.class);
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

        draftPool.add(0,"3B");
        draftPool.add("X");
        draftPool.add("5Y");
        roundTrack.add(0,"3G6B");
        roundTrack.add(1,"3B");
        roundTrack.add(2,"5R2G4B");
        privateObjectiveCard = "RED";
       // when(cliPlayerState.getPrivateObjectiveCard()).thenReturn(privateObjectiveCard);
        favorTokens = 3;
        //when(cliPlayerState.getFavorTokens()).thenReturn(favorTokens);
        //when(cliPlayerState.getWindowFrame()).thenReturn(stringFrame);
        cliPlayerState = new CliPlayerState("Gabriele",stringFrame,privateObjectiveCard,favorTokens);
        cliPlayerStates.add(0,cliPlayerState);
        cliState=new CliState(draftPool,roundTrack,toolCard,publicObjectiveCardIds,cliPlayerStates);

        //when(cliState.getToolCardIds()).thenReturn(toolCard);
        //when(cliState.getDraftPool()).thenReturn(draftPool);
        //when(cliState.getPublicObjectiveCardIds()).thenReturn(publicObjectiveCardIds);
        //when(cliState.getRoundTrack()).thenReturn(roundTrack);



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
        cliDisplayer.printDraftPool();
    }
    @Test
    public void shouldPrintToolCard(){
        outContent = new ByteArrayOutputStream();
        cliDisplayer.printToolCard();
    }
    @Test
    public void shouldPrintPrivateObjeciveCard(){
        outContent = new ByteArrayOutputStream();
        cliDisplayer.printPrivateObjectiveCard(cliPlayerState);
    }
    @Test
    public void shouldPrintPublicObjectiveCard(){
        outContent = new ByteArrayOutputStream();
        cliDisplayer.printPublicObjectiveCards();
    }
    @Test
    public void shouldPrintFavorTokens(){
        outContent = new ByteArrayOutputStream();
        cliDisplayer.printFavorTokens(cliPlayerState);
    }
    @Test
    public void shouldPrintRoundTrack(){
        outContent = new ByteArrayOutputStream();
        cliDisplayer.printRoundTrack();
    }



}