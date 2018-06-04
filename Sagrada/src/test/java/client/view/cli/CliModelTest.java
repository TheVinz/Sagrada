package client.view.cli;

import common.response.Response;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.io.ByteArrayOutputStream;
import java.rmi.RemoteException;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

public class CliModelTest {
    private CliModel cliModel;
    private CliState cliState;
    private CliPlayerState[] cliPlayerState = new CliPlayerState[2];
    private CliPlayerState cliPlayerState1;
    private CliPlayerState cliPlayerState2;
    private CliDisplayer cliDisplayer;
    private ByteArrayOutputStream outContent;
    private char color;
    private String[][] stringFrame = new String[4][5];
    private String[] draftPool = new String[3];
    private char[] draftPoolc = new char[3];
    private int[] draftPoolv = new int[3];
    private int[] toolCard = new int[3];
    private int[] publicObjectiveCardIds = new int[3];
    private String[] reps = new String[4];
    private int[] favor = new int[4];
    private String[] names = new String[4];
    private int[] ids = new int[4];

    @Before
    public void initClass() throws RemoteException {
        cliDisplayer = Mockito.spy(new CliDisplayer());
        //  cliState=Mockito.spy(new CliState());
        //    cliModel= new CliModel();
        color = 'r';
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
        draftPool[0] = "3B";
        draftPool[1] = "2P";
        draftPool[2] = "3N";
        toolCard[0] = 1;
        toolCard[1] = 4;
        toolCard[2] = 12;
        publicObjectiveCardIds[0] = 1;
        publicObjectiveCardIds[1] = 9;
        publicObjectiveCardIds[2] = 5;
        draftPoolv[0] = 1;
        draftPoolv[1] = 3;
        draftPoolv[2] = 4;
        draftPoolc[0] = 'b';
        draftPoolc[1] = 'y';
        draftPoolc[2] = 'p';
        names[0] = "gabriele";
        names[1] = "andrea";
        names[2] = "vinz";
        names[3] = "wella";
        ids[0] = 1;
        ids[1] = 2;
        ids[2] = 3;
        ids[3] = 4;

        reps[0] = "X3G3G3BXX4Y6BXR12G1GYBX3RY";
        reps[1] = "X3G3G3BXX4Y6BXR12G1GYBX3RY";
        reps[2] = "X3G3G3BXX4Y6BXR12G1GYBX3RY";
        reps[3] = "X3G3G3BXX4Y6BXR12G1GYBX3RY";
        favor[0] = 5;
        favor[1] = 3;
        favor[2] = 2;
        favor[3] = 1;
        cliPlayerState1 = new CliPlayerState("gabriele", 1, "X3G3G3BXX4Y6BXR12G1GYBX3RY", 5);
        cliPlayerState2 = new CliPlayerState("andrea", 2, "X3G3Y3BX24Y6BXR12G1GYBX3RY", 4);
        cliPlayerState[0] = cliPlayerState1;
        cliPlayerState[1] = cliPlayerState2;
        cliState.setCliPlayerStates(cliPlayerState);
        cliState.getToolCardIds()[0] = toolCard[0];
        cliState.getToolCardIds()[1] = 5;
        cliState.getToolCardIds()[2] = toolCard[2];
        cliState.setDraftPool(draftPool);
        when(cliState.getCliPlayerState(1)).thenReturn(cliPlayerState1);
        //cliModel.setCliState(cliState);
        //  cliDisplayer.setCliState(cliState);
    }

}