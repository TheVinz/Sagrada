package client.view.cli;

import client.view.cli.cliphasestate.*;
import common.RemoteMVC.RemoteView;
import common.command.GameCommand;
import common.response.Response;
import server.model.state.boards.windowframe.WindowFrame;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import static common.response.Response.*;


public class CliModel extends UnicastRemoteObject implements RemoteView{


    public CliModel() throws RemoteException {
        super();
    }



    @Override
    public void setId(int id) {
        CliApp.getCliApp().setId(id);
    }

    @Override @SuppressWarnings("Duplicates")
    public void move(int player, Response sourceType, Response destType, int param1, int param2, int param3) {
        CliPlayerState cliPlayerState = null;
        cliPlayerState = CliState.getCliState().getCliPlayerState(player);
        String source, target;
        String dice;
        if(sourceType == DRAFT_POOL_CELL) {
            source = "draft pool";
            dice = CliState.getCliState().getDraftPool()[param1];
            CliState.getCliState().getDraftPool()[param1] = "0";
        }
        else if(sourceType == WINDOW_FRAME_CELL) {
            dice = cliPlayerState.getWindowFrame()[param1][param2];
            cliPlayerState.setEmpty(param1, param2);
            source = "window frame";
        }
        else {
            source = "round track";
            dice=CliState.getCliState().getRoundTrack()[param1-1][param2];
            CliState.getCliState().getRoundTrack()[param1-1][param2]="0";
        }
        if(destType == DRAFT_POOL_CELL) {
            CliState.getCliState().getDraftPool()[param3]=dice;
            target = "draft pool";
        }
        else if(destType == WINDOW_FRAME_CELL){
            cliPlayerState.getWindowFrame()[param2][param3] = dice;
            target = "window frame";
        }
        else{
            target = "round track";
            CliState.getCliState().getRoundTrack()[param2-1][param3]=dice;
        }

        CliDisplayer.getDisplayer().displayText(cliPlayerState.getName().concat(" moved dice from ").concat(source).concat(" to ").concat(target)+"\n");
    }

    @Override @SuppressWarnings("Duplicates")
    public void move(int player, Response sourceType, Response destType, int param1, int param2, int param3, int param4) {
        CliPlayerState cliPlayerState = null;
        cliPlayerState = CliState.getCliState().getCliPlayerState(player);
        String source, target;
        String dice;
        if(sourceType == WINDOW_FRAME_CELL) {
            dice = cliPlayerState.getWindowFrame()[param1][param2];
            cliPlayerState.setEmpty(param1, param2);
            source = "window frame";
        }
        else {
            source = "round track";
            dice=CliState.getCliState().getRoundTrack()[param1-1][param2];
            CliState.getCliState().getRoundTrack()[param1-1][param2]="0";
        }
        if(destType == WINDOW_FRAME_CELL){
            cliPlayerState.getWindowFrame()[param3][param4] = dice;
            target = "window frame";
        }
        else{
            target = "round track";
            CliState.getCliState().getRoundTrack()[param3-1][param4]=dice;
        }

        CliDisplayer.getDisplayer().displayText(cliPlayerState.getName() + (" moved dice from ") + (source) + (" to ") + (target)+"\n");
    }

    @Override
    public void updateCell(int player, Response type, int index, int value, char color) {
        String s= ""+value+color;
        CliState.getCliState().getDraftPool()[index]=s;
        CliDisplayer.getDisplayer().displayText("Updated Draft Pool Cell "+ index + " >>> "+s);
    }

    @Override
    public void updateCell(int player, Response type, int param1, int param2, int value, char color) {
        CliPlayerState cliPlayerState;
        String s= ""+value+color;
        if(type==WINDOW_FRAME_CELL){
            cliPlayerState = CliState.getCliState().getCliPlayerState(player);
            cliPlayerState.getWindowFrame()[param1][param2]=s;
            CliDisplayer.getDisplayer().displayText("Updated Window Frame Cell "+ param1 + " "+param2+ " >>> "+s);
        }
        else if(type == ROUND_TRACK_CELL){
            CliState.getCliState().getRoundTrack()[param1-1][param2]=s;
            CliDisplayer.getDisplayer().displayText("Updated Round Track Cell "+ param1 + " "+param2+ " >>> "+s);
        }
    }

    @Override
    public void loadToolCards(int[] toolCards) {
        CliState.getCliState().setToolCardIds(toolCards);
        for(int i=0; i<toolCards.length;i++){
            CliDisplayer.getDisplayer().displayText("Selected tool card No. " + toolCards[i] + ";\n");
        }
    }

    @Override
    public void refilledDraftPool(int[] values, char[] colors) {
        String[] s= new String[values.length];
        for(int i=0;i<colors.length;i++){
            s[i]=""+values[i]+colors[i];
        }
        CliState.getCliState().setDraftPool(s);
        CliDisplayer.getDisplayer().displayText("The DraftPool is full.\n");
        CliDisplayer.getDisplayer().printDraftPool();
    }

    @Override
    public void loadPublicObjectiveCards(int[] cards) {
        CliState.getCliState().setPublicObjectiveCardIds(cards);
        for(int i=0; i<cards.length; i++){
            CliDisplayer.getDisplayer().displayText("Selected public objective card No. "+cards[i]+";\n");
        }
    }

    @Override
    synchronized public void loadWindowFrameChoice(String[] reps, int[] favorTokens) {
        CliDisplayer.getDisplayer().displayText("Choose a WindowFrame:\n");
        for(int i=0; i<reps.length; i++){
            CliDisplayer.getDisplayer().displayText(i+")"+LoadWindowFrame.getLoadWindowFrame().returnName(reps[i]));
            CliDisplayer.getDisplayer().printRep(reps[i],favorTokens[i]);
            if(i==2){CliDisplayer.getDisplayer().displayText("\n");}
        }
        CliApp.getCliApp().setCurrentState(new WindowFrameChoice());
    }

    @Override
    public void loadPlayers(String[] names, int[] ids, String[] windowFrameReps, int[] windowFrameFavorTokens) {
        CliPlayerState[] players =new CliPlayerState[names.length];
        for(int i=0; i<names.length; i++){
            players[i]=new CliPlayerState(names[i], ids[i], windowFrameReps[i], windowFrameFavorTokens[i]);
        }
        CliState.getCliState().setCliPlayerStates(players);
    }

    @Override
    public void toolCardUsed(int player, int index, int tokens) {
        CliPlayerState playerState=CliState.getCliState().getCliPlayerState(player);
        CliDisplayer.getDisplayer().displayText(playerState.getName() + " used tool card No. " + CliState.getCliState().getToolCardIds()[index] + ";\n -" + tokens + " favor tokens;\n");
        playerState.removeFavorTokens(tokens);
    }

    @Override
    public void loadPrivateObjectiveCard(char color) {
        String card;
        switch(color){
            case 'b':
                card="Sum the values of every"+(char)27+"[1;36m"+" BLU "+(char)27+"[0m" +"dice on your WindowFrame\n";
                break;
            case 'r':
                card="Sum the values of every"+(char)27+"[1;31m"+" RED "+(char)27+"[0m"+"dice on your WindowFrame\n";
                break;
            case 'y':
                card="Sum the values of every"+(char)27+"[1;33m"+" YELLOW "+(char)27+"[0m"+"dice on your WindowFrame\n";
                break;
            case 'p':
                card="Sum the values of every"+(char)27+"[1;35m"+" PURPLE "+(char)27+"[0m"+"dice on your WindowFrame\n";
                break;
            case 'g':
                card="Sum the values of every"+(char)27+"[1;32m"+" GREEN "+(char)27+"[0m"+"dice on your WindowFrame\n";
                break;
            default:
                card="Compiler wants me to add a default case";
                break;
        }
        if(CliState.getCliState().getPrivateObjectiveCard().length!=0)
            return;
        CliState.getCliState().setPrivateObjectiveCard(card);
        CliDisplayer.getDisplayer().displayText("Your private objective:\n " + card + ";\n");
    }

    @Override
    public void newTurn(int player) {
        CliPlayerState cliPlayerState=CliState.getCliState().getCliPlayerState(player);
        CliDisplayer.getDisplayer().displayText("Is the turn of " + cliPlayerState.getName() + "!\n");
        CliState.getCliState().setActivePlayer(player);
        if(player == CliApp.getCliApp().getId()) {
            cliPlayerState.setSecondTurn(!cliPlayerState.isSecondTurn());
            CliApp.getCliApp().setCurrentState(new MenuPhase());
        }
        else
            CliApp.getCliApp().setWaitingPhase(true);
    }

    @Override
    public void notifyDiceDraw(int player, char color) {
        CliPlayerState cliPlayerState=CliState.getCliState().getCliPlayerState(player);
        String diceColor;

        switch(color){
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

    @Override
    public void updateRoundTrack(int round, int[] values, char[] colors) {
        String[] roundDices=new String[values.length];
        for (int i=0; i<roundDices.length; i++){
            roundDices[i]=""+values[i]+colors[i];
        }
        CliDisplayer.getDisplayer().displayText("Round track is updated.");
        CliState.getCliState().setRoundDices(round, roundDices);
    }

    @Override
    public void nextParameter(Response response) {
        new Thread( () -> {
        switch (response){
            case WINDOW_FRAME:
                CliApp.getCliApp().setCurrentState(new SelectingWindowFrameCell());
                CliApp.getCliApp().sendCommand();
                CliApp.getCliApp().setWaitingPhase(true);
                break;
            case DRAFT_POOL_CELL:
                CliApp.getCliApp().setCurrentState(new SelectingDraftPoolCell());
                CliApp.getCliApp().sendCommand();
                CliApp.getCliApp().setWaitingPhase(true);
                break;
            case ROUND_TRACK_CELL:
                CliApp.getCliApp().setCurrentState(new SelectingRoundTrackCell());
                CliApp.getCliApp().sendCommand();
                CliApp.getCliApp().setWaitingPhase(true);
                break;
            case END_TURN:
                CliDisplayer.getDisplayer().displayText("Your turn is finished!");
                CliApp.getCliApp().setWaitingPhase(true);
                break;
            case TOOL_CARD:
                CliApp.getCliApp().setCurrentState(new SelectingSendingToolCard());
                break;
            case WINDOW_FRAME_MOVE:
                CliApp.getCliApp().moveFromWindowFrame();
                break;
            case DRAFT_POOL_MOVE:
                CliApp.getCliApp().moveFromDraftPool();
                break;
            case SUCCESS_USED_TOOL_CARD:
                CliApp.getCliApp().setWaitingPhase(false);
                CliApp.getCliApp().setCurrentState(new MenuPhase());
                break;
            case SUCCESS_MOVE_DONE:
                CliApp.getCliApp().setWaitingPhase(false);
                CliApp.getCliApp().setCurrentState(new MenuPhase());
                break;
            case PINZA_SGROSSATRICE_CHOICE:
                CliApp.getCliApp().setCurrentState(new PinzaSgrossatriceChoice());
                CliApp.getCliApp().sendCommand();
                CliApp.getCliApp().setWaitingPhase(true);
                break;
            case TAGLIERINA_MANUALE_CHOICE:
                CliApp.getCliApp().setCurrentState(new TaglierinaManualeChoice());
                CliApp.getCliApp().sendCommand();
                CliApp.getCliApp().setWaitingPhase(true);
                break;
            case DILUENTE_PER_PASTA_SALDA_CHOICE:
                CliApp.getCliApp().setCurrentState(new DiluentePerPastaSaldaChoice());
                CliApp.getCliApp().sendCommand();
                CliApp.getCliApp().setWaitingPhase(true);
                break;
            case SUSPENDED:
                CliApp.getCliApp().setWaitingPhase(false);
                CliApp.getCliApp().setCurrentState(new Suspended());
            default:
                return;

        }}).start();
    }

    @Override
    public void error(String message){
        new Thread(() -> {
            CliDisplayer.getDisplayer().displayText(message);
            CliApp.getCliApp().setWaitingPhase(false);
            CliApp.getCliApp().setCurrentState(new MenuPhase());
        }).start();

    }
    @Override
    public void wrongParameter(String message){
        new Thread(() -> {
            CliDisplayer.getDisplayer().displayText(message);
        }).start();

    }


    @Override
    public void mutableData(int[] draftPoolValues, char[] draftPoolColors, int[][] roundTrackValues, char[][] roundTrackColors, String[] names, int[] ids, int[] favorTokens, String[] windowFrameReps, int[][][] windowFrameValues, char[][][] windowFrameColors) {
        CliState cliState = CliState.getCliState();

        String[] draftPool = new String[draftPoolValues.length];
        for(int i=0; i<draftPoolValues.length; i++){
            if(draftPoolValues[i]==0)
                draftPool[i]="0";
            else
                draftPool[i]=""+draftPoolValues[i]+draftPoolColors[i];
        }
        cliState.setDraftPool(draftPool);

        for (int i=1; i<roundTrackValues.length; i++){
            String[] roundDices=new String[roundTrackValues[i].length];
            for (int j=0; j<roundDices.length; j++){
                roundDices[j]=""+roundTrackValues[i][j]+roundTrackColors[i][j];
            }
            cliState.setRoundDices(i, roundDices);
        }

        CliPlayerState[] players =new CliPlayerState[names.length];
        for(int i=0; i<names.length; i++){
            players[i]=new CliPlayerState(names[i], ids[i], windowFrameReps[i], favorTokens[i]);
        }
        cliState.setCliPlayerStates(players);

        for(int i=0; i<names.length; i++){
            CliPlayerState cliPlayerState = cliState.getCliPlayerState(i);
            for(int h=0; h<4; h++)
                for(int k=0; k<5; k++){
                    if(windowFrameValues[i][h][k]==0)
                        cliPlayerState.setEmpty(h, k);
                    else{
                        cliPlayerState.getWindowFrame()[h][k]=""+windowFrameValues[i][h][k]+windowFrameColors[i][h][k];
                    }

                }
        }
    }

    @Override
    public void reinsertPlayer(int id)  {
        CliDisplayer.getDisplayer().displayText(CliState.getCliState().getCliPlayerState(id).getName()+" has been reinserted in the game!\n");
    }

    @Override
    public void suspendPlayer(int id)  {
        CliDisplayer.getDisplayer().displayText(CliState.getCliState().getCliPlayerState(id).getName()+" has been suspended from the game!\n");
    }

    @Override
    public void toolCardsChoice()  {
        CliApp.getCliApp().setCurrentState(new ToolCardsChoice());
    }

    @Override
    public void removeDice(int id, Response draftPoolCell, int index) throws RemoteException {
        CliState.getCliState().getDraftPool()[index]="0";
        CliDisplayer.getDisplayer().displayText("A draftpool dice has been removed in order to use the toolcard!");
    }

    public void endGame(char[] cards, int[] scoreboard, int[][] points) {
        CliDisplayer.getDisplayer().printResults(cards,scoreboard,points);
    }
}
