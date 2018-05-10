package client.view.cli;

import client.view.ChangementVisitor;
import common.viewchangement.*;
import server.state.dice.Dice;

public class CliChangementVisitor implements ChangementVisitor {
    CliDisplayer cliDisplayer;
    CliState cliState;

    public CliChangementVisitor(CliDisplayer cliDisplayer, CliState cliState){
        this.cliDisplayer = cliDisplayer;
        this.cliState = cliState;
    }
    @Override
    public void change(CellUpdate cellUpdate) throws Exception {
        CliPlayerState cliPlayerState = null;
        int row,column;
        String s= ""+cellUpdate.getValue()+cellUpdate.getColor();
       if(cellUpdate.getCellType()==2){    //caso draftpool
           column = cellUpdate.getColumn();

           cliState.getDraftPool().add(column,s);      //s voglio che sia la stringa con numero e colore dado
       }
       else if(cellUpdate.getCellType()==1){   //caso windowframe
           if(cellUpdate.getPlayer()!=null)
               cliPlayerState = cliState.getCliPlayerState(cellUpdate.getPlayer());   //assegno il giocatore
           row=cellUpdate.getRow();
           column=cellUpdate.getColumn();
           cliPlayerState.getWindowFrame()[row][column]=s;    //cella presa da cellupdate
       }
       else{  //caso roundtrack

       }

    }

    @Override
    public void change(LoadToolCards loadToolCards) {
            for(int i=0; i<3;i++){
                cliState.getToolCardIds()[i]=loadToolCards.getToolCards()[i];
            }
    }

    @Override
    public void change(Move move) throws Exception{
        CliPlayerState cliPlayerState = null;
        if(move.getPlayer() != null)
            cliPlayerState = cliState.getCliPlayerState(move.getPlayer());
        String source, target;
        String dice = null;
        if(move.getSourceType() == 0) {
            source = "draft pool";
           dice = cliState.getDraftPool().get(move.getSourceX());
           cliState.getDraftPool().add(move.getSourceX(), null);
        }
        else if(move.getSourceType() == 1) {
            dice = cliPlayerState.getWindowFrame()[move.getSourceX()][move.getSourceY()];
            source = "window frame";
        }
        else {
            source = "round track";
           //da implementare
        }
        if(move.getTargetType() == 0) {
            cliState.getDraftPool().add(move.getTargetX(), dice);
            target = "draft pool";
        }
        else if(move.getTargetType() == 1){
            cliPlayerState.getWindowFrame()[move.getTargetX()][move.getTargetY()] = dice;
            target = "window frame";
        }
        else{
            target = "round track";
            //da implementare
        }

        cliDisplayer.displayText(move.getPlayer().concat(" moves dice from ").concat(source).concat(" to ").concat(target)+"\n");

    }

    @Override
    public void change(RefilledDraftPool refilledDraftPool) {
        String s;
        for(int i=0;i<refilledDraftPool.getColors().length;i++){
            s=""+refilledDraftPool.getValues()[i]+refilledDraftPool.getColors()[i];
        cliState.getDraftPool().add(i,s);
        }
        cliDisplayer.displayText("La DraftPool è stata riempita:\n");
    }

    @Override
    public void change(StartTurn startTurn) {


    }
}
