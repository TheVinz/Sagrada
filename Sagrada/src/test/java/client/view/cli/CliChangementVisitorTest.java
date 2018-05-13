package client.view.cli;

import common.viewchangement.CellUpdate;
import common.viewchangement.LoadToolCards;
import common.viewchangement.RefilledDraftPool;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.io.ByteArrayOutputStream;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

public class CliChangementVisitorTest {
   private CliDisplayer cliDisplayer;
   private CliState cliState;
   private CliPlayerState cliPlayerState;
   private CliChangementVisitor cliChangementVisitor;
    private ByteArrayOutputStream outContent;
   private LoadToolCards loadToolCards;
   private int[] toolcards = new int[3];
   private RefilledDraftPool refilledDraftPool;
   private int[] values={1,2};
   private char[] colors={'B','G'};
   private CellUpdate cellUpdate;
   private String player;
   private int cellType;
   private int row;
   private int column;
   private int value;
   private char color;
   private String[][] stringFrame= new String[4][5];



   @Before
   public void initClass(){
       cliPlayerState = Mockito.mock(CliPlayerState.class);
       cliState = Mockito.mock(CliState.class);
       cliDisplayer = Mockito.mock(CliDisplayer.class);
       loadToolCards=Mockito.mock(LoadToolCards.class);
       toolcards[0]=2;
       toolcards[1]=3;
       toolcards[2]=12;
       cliChangementVisitor = new CliChangementVisitor(cliDisplayer,cliState);
       when(loadToolCards.getToolCards()).thenReturn(toolcards);
       refilledDraftPool=Mockito.mock(RefilledDraftPool.class);
       when(refilledDraftPool.getColors()).thenReturn(colors);
       when(refilledDraftPool.getValues()).thenReturn(values);
       cellUpdate=Mockito.mock(CellUpdate.class);
       player = "Gabriele";
       cellType = 1;
       row =1;
       column =2;
       value= 3;
       color = 'G';
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
      // cliPlayerState=new CliPlayerState(player,stringFrame,"RED",3);
       when(cliPlayerState.getWindowFrame()).thenReturn(stringFrame);
       when(cellUpdate.getPlayer()).thenReturn(player);
       when(cellUpdate.getCellType()).thenReturn(cellType);
       when(cellUpdate.getRow()).thenReturn(row);
       when(cellUpdate.getColumn()).thenReturn(column);
       when(cellUpdate.getValue()).thenReturn(value);
       when(cellUpdate.getColor()).thenReturn(color);
       //when(cliPlayerState.getName()).thenReturn(player);
       //when(cliPlayerState.getName().equals(player)).thenReturn(true);

       when(cliState.getCliPlayerState(cellUpdate.getPlayer())).thenReturn(cliPlayerState);



   }


    @Test
    public void shouldCellUpdate() {

      // cliChangementVisitor.change(cellUpdate);
       //for (int i=0;i<cliState.getDraftPool().size();i++)
         //  System.out.println(cliState.getDraftPool().get(i));
       cellType=1;
       cliChangementVisitor.change(cellUpdate);
       //assertEquals("3G");


    }

    @Test
    public void shouldLoadToolCards() {
       cliChangementVisitor.change(loadToolCards);
       Integer check=2;
       assertEquals(check,cliState.getToolCardIds()[0]);
    }

    @Test
    public void shouldMove() {
    }

    @Test
    public void shouldRefilledDraftPool() {
       cliChangementVisitor.change(refilledDraftPool);
       assertEquals("1B",cliState.getDraftPool().get(0));
    }

    @Test
    public void shouldStartTurn() {
    }
}