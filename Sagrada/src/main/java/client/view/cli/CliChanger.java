package client.view.cli;

import client.view.Changer;
import client.view.cli.cliphasestate.MenuPhase;
import client.view.cli.cliphasestate.ToolCardsChoice;
import client.view.cli.cliphasestate.WindowFrameChoice;
import common.viewchangement.*;

import static common.response.Response.DRAFT_POOL_CELL;
import static common.response.Response.ROUND_TRACK_CELL;
import static common.response.Response.WINDOW_FRAME_CELL;

public class CliChanger implements Changer {

    private ToolCardsEffects toolCardsEffects = new ToolCardsEffects();
    private ObjectiveCardsEffects objectiveCardsEffects = new ObjectiveCardsEffects();

    public void change(LoadId loadId) {
        CliApp.getCliApp().setId(loadId.getId());
    }


    public void change(Move move){
        CliPlayerState cliPlayerState = null;
        cliPlayerState = CliState.getCliState().getCliPlayerState(move.getPlayerId());
        String source, target;
        String dice;
        if(move.getParam4() == -1) {
            if (move.getSourceType() == DRAFT_POOL_CELL) {
                source = "draft pool";
                dice = CliState.getCliState().getDraftPool()[move.getParam1()];
                CliState.getCliState().getDraftPool()[move.getParam1()] = "0";
            } else if (move.getSourceType() == WINDOW_FRAME_CELL) {
                dice = cliPlayerState.getWindowFrame()[move.getParam1()][move.getParam2()];
                cliPlayerState.setEmpty(move.getParam1(), move.getParam2());
                source = "window frame";
            } else {
                source = "round track";
                dice = CliState.getCliState().getRoundTrack()[move.getParam1() - 1][move.getParam2()];
                CliState.getCliState().getRoundTrack()[move.getParam1() - 1][move.getParam2()] = "0";
            }
            if (move.getTargetType() == DRAFT_POOL_CELL) {
                CliState.getCliState().getDraftPool()[move.getParam3()] = dice;
                target = "draft pool";
            } else if (move.getTargetType() == WINDOW_FRAME_CELL) {
                cliPlayerState.getWindowFrame()[move.getParam2()][move.getParam3()] = dice;
                target = "window frame";
            } else {
                target = "round track";
                CliState.getCliState().getRoundTrack()[move.getParam2() - 1][move.getParam3()] = dice;
            }

            CliDisplayer.getDisplayer().displayText(cliPlayerState.getName().concat(" moved dice from ").concat(source).concat(" to ").concat(target) + "\n");
        }
        else {
            if (move.getSourceType() == WINDOW_FRAME_CELL) {
                dice = cliPlayerState.getWindowFrame()[move.getParam1()][move.getParam2()];
                cliPlayerState.setEmpty(move.getParam1(), move.getParam2());
                source = "window frame";
            } else {
                source = "round track";
                dice = CliState.getCliState().getRoundTrack()[move.getParam1() - 1][move.getParam2()];
                CliState.getCliState().getRoundTrack()[move.getParam1() - 1][move.getParam2()] = "0";
            }
            if (move.getTargetType() == WINDOW_FRAME_CELL) {
                cliPlayerState.getWindowFrame()[move.getParam3()][move.getParam4()] = dice;
                target = "window frame";
            } else {
                target = "round track";
                CliState.getCliState().getRoundTrack()[move.getParam3() - 1][move.getParam4()] = dice;
            }

            CliDisplayer.getDisplayer().displayText(cliPlayerState.getName() + (" moved dice from ") + (source) + (" to ") + (target) + "\n");
        }
    }

    public void change(CellUpdate cellUpdate){
        String s= ""+cellUpdate.getValue()+cellUpdate.getColor();
        if(cellUpdate.getColumn()==-1){
            CliState.getCliState().getDraftPool()[cellUpdate.getRow()]=s;
            CliDisplayer.getDisplayer().displayText("Updated Draft Pool Cell "+ cellUpdate.getRow() + " >>> "+s+"\n");
        }
        else{
            CliPlayerState cliPlayerState;
            if(cellUpdate.getCellType()==WINDOW_FRAME_CELL){
                cliPlayerState = CliState.getCliState().getCliPlayerState(cellUpdate.getPlayerId());
                cliPlayerState.getWindowFrame()[cellUpdate.getRow()][cellUpdate.getColumn()]=s;
                CliDisplayer.getDisplayer().displayText("Updated Window Frame Cell "+ cellUpdate.getRow() + " "+cellUpdate.getColumn()+ " >>> "+s+"\n");
            }
            else if(cellUpdate.getCellType() == ROUND_TRACK_CELL){
                CliState.getCliState().getRoundTrack()[cellUpdate.getRow()-1][cellUpdate.getColumn()]=s;
                CliDisplayer.getDisplayer().displayText("Updated Round Track Cell "+ cellUpdate.getRow() + " "+cellUpdate.getColumn()+ " >>> "+s+"\n");
            }
        }
    }


    public void change(LoadToolCards loadToolCards) {
        CliState.getCliState().setToolCardIds(loadToolCards.getToolCards());
        for(int i=0; i<loadToolCards.getToolCards().length;i++){
            CliDisplayer.getDisplayer().displayText("Selected tool card No. " + loadToolCards.getToolCards()[i] + ";\n");
        }
    }


    public void change(RefilledDraftPool refilledDraftPool) {
        String[] s= new String[refilledDraftPool.getValues().length];
        for(int i=0;i<refilledDraftPool.getColors().length;i++){
            s[i]=""+refilledDraftPool.getValues()[i]+refilledDraftPool.getColors()[i];
        }
        CliState.getCliState().setDraftPool(s);
        CliDisplayer.getDisplayer().displayText("The DraftPool is full.\n");
        CliDisplayer.getDisplayer().printDraftPool();
    }

    public void change(LoadPublicObjectiveCards loadPublicObjectiveCards) {
        CliState.getCliState().setPublicObjectiveCardIds(loadPublicObjectiveCards.getPublicObjectiveCards());
        for (int i = 0; i < loadPublicObjectiveCards.getPublicObjectiveCards().length; i++) {
            CliDisplayer.getDisplayer().displayText("Selected public objective card  " + objectiveCardsEffects.returnName(loadPublicObjectiveCards.getPublicObjectiveCards()[i]) + ";\n");
            CliState.getCliState().getPublicObjectiveCardIds()[i] = loadPublicObjectiveCards.getPublicObjectiveCards()[i];
        }
    }

    public void change(WindowFrameChoices windowFrameChoices) { //c'era synchronized, booo
        CliDisplayer.getDisplayer().displayText("Choose a WindowFrame:\n");
        for(int i=0; i<windowFrameChoices.getReps().length; i++){
            CliDisplayer.getDisplayer().displayText(i+")"+LoadWindowFrame.getLoadWindowFrame().returnName(windowFrameChoices.getReps()[i]));
            CliDisplayer.getDisplayer().printRep(windowFrameChoices.getReps()[i],windowFrameChoices.getFavorTokens()[i]);
            if(i==2){CliDisplayer.getDisplayer().displayText("\n");}
        }
        CliApp.getCliApp().setCurrentState(new WindowFrameChoice());
    }

    public void change(LoadPlayers loadPlayers) {
        CliPlayerState[] players =new CliPlayerState[loadPlayers.getNames().length];
        for(int i=0; i<loadPlayers.getNames().length; i++){
            players[i]=new CliPlayerState(loadPlayers.getNames()[i], loadPlayers.getIds()[i], loadPlayers.getWindowFrameReps()[i], loadPlayers.getWindowFrameFavorTokens()[i]);
        }
        CliState.getCliState().setCliPlayerStates(players);
    }

    public void change(ToolCardUsed toolCardUsed) {
        CliPlayerState playerState=CliState.getCliState().getCliPlayerState(toolCardUsed.getId());
        CliDisplayer.getDisplayer().displayText(playerState.getName() + " used tool card  " + toolCardsEffects.returnName(CliState.getCliState().getToolCardIds()[toolCardUsed.getIndex()]) + ";\n -" + toolCardUsed.getTokens() + " favor tokens;\n");
        playerState.removeFavorTokens(toolCardUsed.getTokens());
    }

    public void change(LoadPrivateObjectiveCard loadPrivateObjectiveCard) {
        String card;
        switch(loadPrivateObjectiveCard.getColor()){
            case 'b':
                card="BLU";
                break;
            case 'r':
                card="RED";
                break;
            case 'y':
                card="YELLOW";
                break;
            case 'p':
                card="PURPLE";
                break;
            case 'g':
                card="GREEN";
                break;
            default:
                card="Compiler wants me to add a default case";
                break;
        }
        if(CliState.getCliState().getPrivateObjectiveCard().length!=0)
            return;
        CliState.getCliState().setPrivateObjectiveCard(card);
        CliDisplayer.getDisplayer().displayText("Your private objective:\n");
        CliDisplayer.getDisplayer().printColoredPrvCard(card);
    }

    public void change(NewTurn newTurn) {
        CliPlayerState cliPlayerState=CliState.getCliState().getCliPlayerState(newTurn.getId());
        CliDisplayer.getDisplayer().displayText("Is the turn of " + cliPlayerState.getName() + "!\n");
        CliState.getCliState().setActivePlayer(newTurn.getId());
        if(newTurn.getId() == CliApp.getCliApp().getId()) {
            cliPlayerState.setSecondTurn(!cliPlayerState.isSecondTurn());
            CliApp.getCliApp().setCurrentState(new MenuPhase());
        }
        else
            CliApp.getCliApp().setWaitingPhase(true);
    }

    public void change(DiceDraw diceDraw) {
        CliPlayerState cliPlayerState=CliState.getCliState().getCliPlayerState(diceDraw.getId());
        String diceColor;

        switch(diceDraw.getColor()){
            case 'b':
                diceColor="blue";
                break;
            case 'r':
                diceColor="red";
                break;
            case 'y':
                diceColor="yellow";
                break;
            case 'p':
                diceColor="purple";
                break;
            case 'g':
                diceColor="green";
                break;
            default:
                diceColor="Compiler wants me to add a default case";
                break;
        }
        CliDisplayer.getDisplayer().displayText(cliPlayerState.getName() + " drawn out a " + diceColor + " dice;\n");
    }

    public void change(LoadLastRoundTrack loadLastRoundTrack) {
        String[] roundDices=new String[loadLastRoundTrack.getValues().length];
        for (int i=0; i<roundDices.length; i++){
            roundDices[i]=""+loadLastRoundTrack.getValues()[i]+loadLastRoundTrack.getColors()[i];
        }
        CliDisplayer.getDisplayer().displayText("Round track is updated.\n");
        CliState.getCliState().setRoundDices(loadLastRoundTrack.getRound(), roundDices);
    }

    public void change(MutableData mutableData){
        CliState cliState = CliState.getCliState();

        String[] draftPool = new String[mutableData.getDraftPoolValues().length];
        for(int i=0; i<mutableData.getDraftPoolValues().length; i++){
            if(mutableData.getDraftPoolValues()[i]==0)
                draftPool[i]="0";
            else
                draftPool[i]=""+mutableData.getDraftPoolValues()[i]+mutableData.getDraftPoolColors()[i];
        }
        cliState.setDraftPool(draftPool);

        for (int i=1; i<mutableData.getRoundTrackValues().length; i++){
            String[] roundDices=new String[mutableData.getRoundTrackValues()[i].length];
            for (int j=0; j<roundDices.length; j++){
                roundDices[j]=""+mutableData.getRoundTrackValues()[i][j]+mutableData.getRoundTrackColors()[i][j];
            }
            cliState.setRoundDices(i, roundDices);
        }

        CliPlayerState[] players =new CliPlayerState[mutableData.getNames().length];
        for(int i=0; i<mutableData.getNames().length; i++){
            players[i]=new CliPlayerState(mutableData.getNames()[i], mutableData.getIds()[i], mutableData.getWindowFrameReps()[i], mutableData.getFavorTokens()[i]);
        }
        cliState.setCliPlayerStates(players);

        for(int i=0; i<mutableData.getNames().length; i++){
            CliPlayerState cliPlayerState = cliState.getCliPlayerState(i);
            for(int h=0; h<4; h++)
                for(int k=0; k<5; k++){
                    if(mutableData.getWindowFrameValues()[i][h][k]==0)
                        cliPlayerState.setEmpty(h, k);
                    else{
                        cliPlayerState.getWindowFrame()[h][k]=""+mutableData.getWindowFrameValues()[i][h][k]+mutableData.getWindowFrameColors()[i][h][k];
                    }

                }
        }
    }

    public void change(ReinsertedPlayer reinsertedPlayer)  {
        CliDisplayer.getDisplayer().displayText(CliState.getCliState().getCliPlayerState(reinsertedPlayer.getIdPlayer()).getName()+" has been reinserted in the game!\n");
    }

    public void change(SuspendedPlayer suspendedPlayer)  {
        CliDisplayer.getDisplayer().displayText(CliState.getCliState().getCliPlayerState(suspendedPlayer.getPlayerId()).getName()+" has been suspended from the game!\n");
    }

    public void change(ToolCardsChoices toolCardsChoices)  {
        CliApp.getCliApp().setCurrentState(new ToolCardsChoice());
    }

    public void change(RemovedDice removedDice) {
        CliState.getCliState().getDraftPool()[removedDice.getIndex()]="0";
        CliDisplayer.getDisplayer().displayText("A draftpool dice has been removed in order to use the toolcard!");
    }

    public void change(EndGame endGame){
        CliDisplayer.getDisplayer().printResults(endGame.getCharCards(),endGame.getScoreboardIds(),endGame.getMatrixPoins());
    }
}
