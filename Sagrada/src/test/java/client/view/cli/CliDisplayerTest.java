package client.view.cli;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static org.mockito.Mockito.mockingDetails;
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
    private String[] draftPool = new String[3];
    private Integer[] toolCard = new Integer[3];
    private Integer[] publicObjectiveCardIds = new Integer[3];
    private Integer favorTokens;
    private String[] roundTrack = new String[3];
    private String[] roundTrack1 = new String[1];
    private CliPlayerState[] cliPlayerStates=new CliPlayerState[1];

    @Before
    public void initClass() throws Exception {
        cliPlayerState=Mockito.mock(CliPlayerState.class);
        cliState=CliState.getCliState();
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
        publicObjectiveCardIds[1]=9;
        publicObjectiveCardIds[2]=5;
        draftPool[0]="3B";
        draftPool[1]="X";
        draftPool[2]="5Y";
        privateObjectiveCard = "RED";
        favorTokens=3;
        when(cliPlayerState.getId()).thenReturn(1);
        when(cliPlayerState.getName()).thenReturn("Gabriele");
        when(cliPlayerState.getWindowFrame()).thenReturn(stringFrame);
        when(cliPlayerState.getFavorTokens()).thenReturn(favorTokens);
        cliState.setPrivateObjectiveCard(privateObjectiveCard);
        cliPlayerStates[0]=cliPlayerState;
        cliState.setCliPlayerStates(cliPlayerStates);
        cliState.setActivePlayer(cliPlayerStates[0].getId());
        cliState.setDraftPool(draftPool);
        roundTrack[0]="3B";
        roundTrack[1]="4G";
        roundTrack[2]="2P";
        roundTrack1[0]="4Y";
        cliState.setRoundDices(0,roundTrack);
        cliState.getToolCardIds()[0]=toolCard[0];
        cliState.getToolCardIds()[1]=toolCard[1];
        cliState.getToolCardIds()[2]=toolCard[2];
        cliState.getPublicObjectiveCardIds()[0]=publicObjectiveCardIds[0];
        cliState.getPublicObjectiveCardIds()[1]=publicObjectiveCardIds[1];
        cliState.getPublicObjectiveCardIds()[2]=publicObjectiveCardIds[2];
        cliState.setRoundDices(0,roundTrack);
        cliState.setRoundDices(1,roundTrack1);
    }
    @Test
    public void shouldPrintWindowFrame() {
        cliDisplayer.printWindowFrame(cliPlayerState);
    }
    @Test
    public void shouldPrintMenu() {
        cliDisplayer.printMenu();
    }
    @Test
    public void shouldPrintDraftPool(){
        cliDisplayer.printDraftPool();
    }
    @Test
    public void shouldPrintToolCard(){
        cliDisplayer.printToolCard();
    }
    @Test
    public void shouldPrintPrivateObjeciveCard(){
        cliDisplayer.printPrivateObjectiveCard();
    }
    @Test
    public void shouldPrintPublicObjectiveCard(){
        cliDisplayer.printPublicObjectiveCards();
    }
    @Test
    public void shouldPrintFavorTokens(){
        cliDisplayer.printFavorTokens(cliPlayerState);
    }
    @Test
    public void shouldPrintRoundTrack(){
        cliDisplayer.printRoundTrack();
    }
    @Test
    public void shouldPrintState(){
        cliDisplayer.printState();
    }
    @Test
    public void shouldPrintOtherState(){
        cliDisplayer.printState("Gabriele");
    }



}
