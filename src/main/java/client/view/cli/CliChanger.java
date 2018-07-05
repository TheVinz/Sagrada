package client.view.cli;

import client.view.cli.cliphasestate.PrivateObjectiveCardChoice;
import client.view.gui.guicontroller.ViewController;
import common.Changer;
import client.view.cli.cliphasestate.MenuPhase;
import client.view.cli.cliphasestate.ToolCardsChoice;
import client.view.cli.cliphasestate.WindowFrameChoice;
import common.viewchangement.*;

import static common.response.Response.DRAFT_POOL_CELL;
import static common.response.Response.ROUND_TRACK_CELL;
import static common.response.Response.WINDOW_FRAME_CELL;

/**
 * The class <tt>CliChanger</tt> translates the {@link Changement}s received from the server in operations which change the {@link CliState}, in order to keep the current graphical representation of the game updated.
 * Each different change receives as parameter the {@link Changement} containing info about how the game
 * state changed and the player who performed this change.
 */
public class CliChanger implements Changer {

    private ToolCardsEffects toolCardsEffects = new ToolCardsEffects();
    private ObjectiveCardsEffects objectiveCardsEffects = new ObjectiveCardsEffects();

    private static CliChanger cliChanger;

    protected CliChanger(){}


    /**
     * Creates the unique instance of this class if this one has never been initialized.
     * @return the unique instance of this class
     */
    public static CliChanger getCliChanger() {
        if(cliChanger == null)
            cliChanger = new CliChanger();
        return cliChanger;
    }

    /**
     * Set the id of the current player in {@link CliApp}.
     * @param loadId the id of the current player
     */
    public void change(LoadId loadId) {
        CliApp.getCliApp().setId(loadId.getId());
    }

    /**
     * Informs {@link CliState} and {@link CliDisplayer} about a dice move represented by the {@link Move} Changement.
     * @param move the Changement representing the move.
     */
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

    /**
     * Informs CliState and CliDisplayer about dice re-draws and re-rolls, the {@link CellUpdate} Changement
     * contains the cell coordinates and dice value and color. This method is called only for window frame cell's
     * changes or for DraftPool's changes.
     * @param cellUpdate the Changement containing cell coordinates and dice value and color.
     */
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

    /**
     * Load the tool cards drawn at the beginning of the game.
     * @param loadToolCards the Changement containing the tool cards identifiers.
     */
    public void change(LoadToolCards loadToolCards) {
        CliState.getCliState().setToolCardIds(loadToolCards.getToolCards());
        for(int i=0; i<loadToolCards.getToolCards().length;i++){
            CliDisplayer.getDisplayer().displayText("Selected tool card No. " + toolCardsEffects.returnName(loadToolCards.getToolCards()[i]) + ";\n");
        }
    }

    /**
     * Informs CliState and CliDisplayer about the dices drawn at the beginning of the new Round.
     * @param refilledDraftPool the Changement containing the dices colors and values.
     */
    public void change(RefilledDraftPool refilledDraftPool) {
        String[] s= new String[refilledDraftPool.getValues().length];
        for(int i=0;i<refilledDraftPool.getColors().length;i++){
            s[i]=""+refilledDraftPool.getValues()[i]+refilledDraftPool.getColors()[i];
        }
        CliState.getCliState().setDraftPool(s);
        CliDisplayer.getDisplayer().displayText("The DraftPool is full.\n");
    }

    /**
     * Load the {@link server.model.state.objectivecards.publicobjectivecards.PublicObjectiveCard}s at the beginning of the game.
     * @param loadPublicObjectiveCards the Changement containing the PublicObjectiveCards.
     */
    public void change(LoadPublicObjectiveCards loadPublicObjectiveCards) {
        CliState.getCliState().setPublicObjectiveCardIds(loadPublicObjectiveCards.getPublicObjectiveCards());
        for (int i = 0; i < loadPublicObjectiveCards.getPublicObjectiveCards().length; i++) {
            CliDisplayer.getDisplayer().displayText("Selected public objective card  " + objectiveCardsEffects.returnName(loadPublicObjectiveCards.getPublicObjectiveCards()[i]) + ";\n");
            CliState.getCliState().getPublicObjectiveCardIds()[i] = loadPublicObjectiveCards.getPublicObjectiveCards()[i];
        }
    }

    /**
     * Loads the player's window frame scheme choice before the game starts.
     * @param windowFrameChoices the Changement containing the window frame scheme's.
     */
    public void change(WindowFrameChoices windowFrameChoices) { //c'era synchronized, booo
        CliDisplayer.getDisplayer().displayText("Choose a WindowFrame:\n");
        for(int i=0; i<windowFrameChoices.getReps().length; i++){
            CliDisplayer.getDisplayer().displayText(i+")"+LoadWindowFrame.getLoadWindowFrame().returnName(windowFrameChoices.getReps()[i]));
            CliDisplayer.getDisplayer().printRep(windowFrameChoices.getReps()[i],windowFrameChoices.getFavorTokens()[i]);
            if(i==2){CliDisplayer.getDisplayer().displayText("\n");}
        }
        CliApp.getCliApp().setCurrentState(new WindowFrameChoice());
    }


    /**
     * Loads the player's PrivateObjectiveCards choice at the end of the game.
     * @param privateObjectiveCardsChoice the Changement containing the PrivateObjectiveCards.
     */
    @Override
    public void change(PrivateObjectiveCardsChoice privateObjectiveCardsChoice) {
        CliApp.getCliApp().setCurrentState(new PrivateObjectiveCardChoice());
    }

    /**
     * Informs the CliState that the game is finished and informs the CliDisplayer on the final scoreboard.
     * @param singlePlayerEndGame the Changement containing information about the final point in SinglePlayer mode.
     */
    @Override
    public void change(SinglePlayerEndGame singlePlayerEndGame) {
        CliDisplayer.getDisplayer().printSinglePlayerPoints(singlePlayerEndGame);
      //  CliDisplayer.getDisplayer().displayText(singlePlayerEndGame.getFinalScore()+" - "+singlePlayerEndGame.getVectorPoints()[4]);
        CliState.getCliState().setGameFinished(true);
        CliApp.getCliApp().setWaitingPhase(false);
    }

    /**
     * Informs the CliState about the other players playing the game, also including the id the server
     * associated to the local client. In case of singleplayer games, this Changement only contains
     * informations about the single player.
     * @param loadPlayers the Changement containing information about the players.
     */
    public void change(LoadPlayers loadPlayers) {
        CliPlayerState[] players =new CliPlayerState[loadPlayers.getNames().length];
        for(int i=0; i<loadPlayers.getNames().length; i++){
            players[i]=new CliPlayerState(loadPlayers.getNames()[i], loadPlayers.getIds()[i], loadPlayers.getWindowFrameReps()[i], loadPlayers.getWindowFrameFavorTokens()[i]);
        }
        CliState.getCliState().setCliPlayerStates(players);
    }

    /**
     * Informs the Players that he used a {@link server.model.state.toolcards.ToolCard}.
     * @param toolCardUsed the Changement containing information about the used ToolCard.
     */
    public void change(ToolCardUsed toolCardUsed) {
        CliPlayerState playerState=CliState.getCliState().getCliPlayerState(toolCardUsed.getId());
        CliDisplayer.getDisplayer().displayText(playerState.getName() + " used tool card  " + toolCardsEffects.returnName(CliState.getCliState().getToolCardIds()[toolCardUsed.getIndex()]) + ";\n -" + toolCardUsed.getTokens() + " favor tokens;\n");
        playerState.removeFavorTokens(toolCardUsed.getTokens());
    }

    /**
     * Loads the PrivateObjectiveCard of a player at the beginning of the game.
     * @param loadPrivateObjectiveCard the Changement containing information about the PrivateObjectiveCard.
     */
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
        CliState.getCliState().setPrivateObjectiveCard(card);
        CliDisplayer.getDisplayer().displayText("Your private objective:\n");
        CliDisplayer.getDisplayer().printColoredPrvCard(card);
    }

    /**
     * Loads the information about the NewTurn.
     * @param newTurn the Changement containing information about the NewTurn.
     */
    public void change(NewTurn newTurn) {
        CliPlayerState cliPlayerState=CliState.getCliState().getCliPlayerState(newTurn.getId());

        CliDisplayer.getDisplayer().displayText("Is the turn of " + cliPlayerState.getName() + "!\n");
        CliState.getCliState().setActivePlayer(newTurn.getId());
        if(newTurn.getId()!=CliApp.getCliApp().getId())
            CliDisplayer.getDisplayer().printState(CliState.getCliState().getCliPlayerState(newTurn.getId()).getName());

        CliDisplayer.getDisplayer().printState();
        CliDisplayer.getDisplayer().printDraftPool();

        if(newTurn.getId() == CliApp.getCliApp().getId()) {
            cliPlayerState.setSecondTurn(!cliPlayerState.isSecondTurn());
            CliApp.getCliApp().setCurrentState(new MenuPhase());
        }
        else
            CliApp.getCliApp().setWaitingPhase(true);
    }

    /**
     * Informs the player about the drafted Dice.
     * @param diceDraw the Changement containing the information about the drafted Dice.
     */
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

    /**
     * Loads in CliState the information about the Dice to insert in the {@link server.model.state.boards.roundtrack.RoundTrack}.
     * @param loadLastRoundTrack the Changement containing the information about the Dice to put in the RoundTrack.
     */
    public void change(LoadLastRoundTrack loadLastRoundTrack) {
        String[] roundDices=new String[loadLastRoundTrack.getValues().length];
        for (int i=0; i<roundDices.length; i++){
            roundDices[i]=""+loadLastRoundTrack.getValues()[i]+loadLastRoundTrack.getColors()[i];
        }
        CliDisplayer.getDisplayer().displayText("Round track for round "+loadLastRoundTrack.getRound()+" is updated.\n");
        CliState.getCliState().setRoundDices(loadLastRoundTrack.getRound(), roundDices);
    }

    /**
     * This method is called when the client reconnects to a game still in progress, so it set the whole
     * view game state from the {@link MutableData} Changement and then re-initializes the game window.
     * @param mutableData the Changement representing the game state.
     */
    public void change(MutableData mutableData){

        CliApp.getCliApp().setId(mutableData.getId());
        CliState cliState = CliState.getCliState();
        cliState.resetPrivate();

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

        CliDisplayer.getDisplayer().printWindowFrame();

    }

    /**
     * Prints the name of the reinserted player.
     * @param reinsertedPlayer the Changement containing the information on the reinserted player.
     */
    public void change(ReinsertedPlayer reinsertedPlayer)  {
        CliDisplayer.getDisplayer().displayText(CliState.getCliState().getCliPlayerState(reinsertedPlayer.getIdPlayer()).getName()+" has been reinserted in the game!\n");
    }

    /**
     * Prints the name of the suspended player.
     * @param suspendedPlayer the Changement containing the information on the suspended player.
     */
    public void change(SuspendedPlayer suspendedPlayer)  {
        CliDisplayer.getDisplayer().displayText(CliState.getCliState().getCliPlayerState(suspendedPlayer.getPlayerId()).getName()+" has been suspended from the game!\n");
    }

    /**
     * Puts the CliApp in the ToolCardsChoice state.
     * @param toolCardsChoices the Changement containing the information on the ToolCardChoice.
     */
    public void change(ToolCardsChoices toolCardsChoices)  {
        CliApp.getCliApp().setCurrentState(new ToolCardsChoice());
    }

    /**
     * Removes a Dice in the DraftPool.
     * @param removedDice the Changement containing the information about the removed Dice.
     */
    public void change(RemovedDice removedDice) {
        CliState.getCliState().getDraftPool()[removedDice.getIndex()]="0";
        CliDisplayer.getDisplayer().displayText("A draftpool dice has been removed in order to use the toolcard!\n");
    }

    /**
     * Informs the CliState that the game is finished and informs the CliDisplayer on the final scoreboard.
     * @param endGame the Changement containing information about the final point.
     */
    public void change(EndGame endGame){
        CliDisplayer.getDisplayer().printResults(endGame.getCharCards(),endGame.getScoreboardIds(),endGame.getMatrixPoins());
        CliState.getCliState().setGameFinished(true);
        CliApp.getCliApp().setWaitingPhase(false);
    }
}
