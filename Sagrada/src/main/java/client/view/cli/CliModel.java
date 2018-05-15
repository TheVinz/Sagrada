package client.view.cli;

import common.RemoteMVC.RemoteView;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import static common.ModelObject.*;

public class CliModel extends UnicastRemoteObject implements RemoteView {

    private CliState cliState;
    private CliApp cliApp;

    public CliModel() throws RemoteException {
        super();
        this.cliState=new CliState();
        CliDisplayer.getDisplayer().setCliState(cliState);
    }

    public void bindApp(CliApp app){
        this.cliApp=app;
    }

    @Override @SuppressWarnings("Duplicates")
    public void move(int player, int sourceType, int destType, int param1, int param2, int param3) {
        CliPlayerState cliPlayerState = null;
        cliPlayerState = cliState.getCliPlayerState(player);
        String source, target;
        String dice;
        if(sourceType == DRAFT_POOL_CELL) {
            source = "draft pool";
            dice = cliState.getDraftPool().get(param1);
            cliState.getDraftPool().set(param1, null);
        }
        else if(sourceType == WINDOW_FRAME_CELL) {
            dice = cliPlayerState.getWindowFrame()[param1][param2];
            cliPlayerState.setEmpty(param1, param2);
            source = "window frame";
        }
        else {
            source = "round track";
            dice=cliState.getRoundTrack().get(param1).get(param2);
            cliState.getRoundTrack().get(param1).set(param2, null);
        }
        if(destType == DRAFT_POOL_CELL) {
            cliState.getDraftPool().add(param3, dice);
            target = "draft pool";
        }
        else if(destType == WINDOW_FRAME_CELL){
            cliPlayerState.getWindowFrame()[param2][param3] = dice;
            target = "window frame";
        }
        else{
            target = "round track";
            cliState.getRoundTrack().get(param2).set(param3, dice);
        }

        CliDisplayer.getDisplayer().displayText(cliPlayerState.getName().concat(" moved dice from ").concat(source).concat(" to ").concat(target)+"\n");
    }

    @Override @SuppressWarnings("Duplicates")
    public void move(int player, int sourceType, int destType, int param1, int param2, int param3, int param4) {
        CliPlayerState cliPlayerState = null;
        cliPlayerState = cliState.getCliPlayerState(player);
        String source, target;
        String dice;
        if(sourceType == WINDOW_FRAME_CELL) {
            dice = cliPlayerState.getWindowFrame()[param1][param2];
            cliPlayerState.getWindowFrame()[param1][param2] = null;
            source = "window frame";
        }
        else {
            source = "round track";
            dice=cliState.getRoundTrack().get(param1).get(param2);
            cliState.getRoundTrack().get(param1).set(param2, null);
        }
        if(destType == WINDOW_FRAME_CELL){
            cliPlayerState.getWindowFrame()[param3][param4] = dice;
            target = "window frame";
        }
        else{
            target = "round track";
            cliState.getRoundTrack().get(param3).set(param4, dice);
        }

        CliDisplayer.getDisplayer().displayText(cliPlayerState.getName() + (" moved dice from ") + (source) + (" to ") + (target)+"\n");
    }

    @Override
    public void updateCell(int player, int type, int index, int value, char color) {
        String s= ""+value+color;
        cliState.getDraftPool().set(index,s);
        CliDisplayer.getDisplayer().displayText("Updated Draft Pool Cell "+ index + ">>> "+s);
    }

    @Override
    public void updateCell(int player, int type, int param1, int param2, int value, char color) {
        CliPlayerState cliPlayerState;
        String s= ""+value+color;
        if(type==WINDOW_FRAME_CELL){
            cliPlayerState = cliState.getCliPlayerState(player);
            cliPlayerState.getWindowFrame()[param1][param2]=s;
            CliDisplayer.getDisplayer().displayText("Updated Window Frame Cell "+ param1 + " "+param2+ ">>> "+s);
        }
        else if(type == ROUND_TRACK_CELL){
            cliState.getRoundTrack().get(param1).set(param2, s);
            CliDisplayer.getDisplayer().displayText("Updated Round Track Cell "+ param1 + " "+param2+ ">>> "+s);
        }
    }

    @Override
    public void loadToolCards(int[] toolCards) {
        for(int i=0; i<3;i++){
            cliState.getToolCardIds()[i]=toolCards[i];
            CliDisplayer.getDisplayer().displayText("Selected tool card No. " + toolCards[i] + ";\n");
        }
    }

    @Override
    public void refilledDraftPool(int[] values, char[] colors) {
        String[] s= new String[values.length];
        for(int i=0;i<colors.length;i++){
            s[i]=""+values[i]+colors[i];
        }
        cliState.setDraftPool(s);
        CliDisplayer.getDisplayer().displayText("La DraftPool è stata riempita\n");
        CliDisplayer.getDisplayer().printDraftPool();
    }

    @Override
    public void loadPublicObjectiveCards(int[] cards) {
        for(int i=0; i<cards.length; i++){
            CliDisplayer.getDisplayer().displayText("Selected public objective card No. "+cards[i]+";\n");
            cliState.getPublicObjectiveCardIds()[i]=cards[i];
        }
    }

    @Override
    public void loadWindowFrameChoice(String[] reps, int[] favorTokens) {
        for(int i=0; i<reps.length; i++){
            CliDisplayer.getDisplayer().displayText(i + ">>> rep: " + reps[i] + " | tokens: "+ favorTokens[i] + ";\n");
        }
        new Thread( () -> cliApp.windowFrameChoice()).start();
    }

    @Override
    public void loadPlayers(String[] names, int[] ids, String[] windowFrameReps, int[] windowFrameFavorTokens) {
        CliPlayerState player;
        for(int i=0; i<names.length; i++){
            player = new CliPlayerState(names[i],ids[i], windowFrameReps[i], windowFrameFavorTokens[i]);
            cliState.addPlayer(player);
        }
    }

    @Override
    public void toolCardUsed(int player, int index, int tokens) {
        CliPlayerState playerState=cliState.getCliPlayerState(player);
        CliDisplayer.getDisplayer().displayText(playerState.getName() + " used tool card No. " + cliState.getToolCardIds()[index] + ";\n -" + tokens + " favor tokens;\n");
        playerState.removeFavorTokens(tokens);
    }

    @Override
    public void loadPrivateObjectiveCard(char color) {
        String card;
        switch(color){
            case 'b':
                card="One point for each blue dice on your frame";
                break;
            case 'r':
                card="One point for each red dice on your frame";
                break;
            case 'y':
                card="One point for each yellow dice on your frame";
                break;
            case 'p':
                card="One point for each purple dice on your frame";
                break;
            case 'g':
                card="One point for each green dice on your frame";
                break;
            default:
                card="Compiler wants me to add a default case";
                break;
        }
        cliState.setPrivateObjectiveCard(card);
        CliDisplayer.getDisplayer().displayText("Your private objective :\n>>> " + card + ";\n");
    }

    @Override
    public void newTurn(int player) {
        CliPlayerState cliPlayerState=cliState.getCliPlayerState(player);
        CliDisplayer.getDisplayer().displayText("Is your turn " + cliPlayerState.getName() + "!\n");
        cliState.setActivePlayer(player);
        if(player == cliApp.getId()) new Thread( () -> cliApp.startTurn()).start();
        else new Thread( () -> cliApp.waitTurn()).start();
    }

    @Override
    public void notifyDiceDraw(int player, char color) {
        CliPlayerState cliPlayerState=cliState.getCliPlayerState(player);
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
        CliDisplayer.getDisplayer().displayText(cliPlayerState.getName() + " drawn out a " + diceColor + "dice;\n");
    }
}
