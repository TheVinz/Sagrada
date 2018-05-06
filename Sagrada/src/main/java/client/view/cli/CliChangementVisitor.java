package client.view.cli;

import client.view.ChangementVisitor;
import common.viewchangement.*;

public class CliChangementVisitor implements ChangementVisitor {
    CliDisplayer cliDisplayer;
    CliState cliState;

    public CliChangementVisitor(CliDisplayer cliDisplayer, CliState cliState){
        this.cliDisplayer = cliDisplayer;
        this.cliState = cliState;
    }
    @Override
    public void change(CellUpdate cellUpdate) {

    }

    @Override
    public void change(LoadToolCards loadToolCards) {

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

        cliDisplayer.displayText(move.getPlayer().concat(" moves dice from ").concat(source).concat(" to ").concat(target));

    }

    @Override
    public void change(RefilledDraftPool refilledDraftPool) {

    }

    @Override
    public void change(StartTurn startTurn) {

    }
}
