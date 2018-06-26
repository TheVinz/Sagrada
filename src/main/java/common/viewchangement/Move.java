package common.viewchangement;


import common.Changer;
import common.response.Response;

/**
 * The <tt>Move</tt> class contains the data to send to the client about a {@link server.model.state.dice.Dice} moved.
 */
public class Move extends Changement{



    private final int playerId;
    private final Response sourceType;
    private final int param1;
    private final int param2;
    private final Response targetType;
    private final int param3;
    private final int param4;

    /**
     * Creates a new <tt>Move</tt> changement relative to a Dice moved by a {@link server.model.state.player.Player} from
     * {@link server.model.state.boards.windowframe.WindowFrame} to WindowFrame.
     * @param id the if of the player which made the move.
     * @param sourceType the type of {@link server.model.state.boards.Cell} where was the Dice, a {@link server.model.state.boards.windowframe.WindowFrameCell }.
     * @param targetType the type of Cell where will be the Dice, a WindowFrameCell.
     * @param param1 an int which indicates the row of the source Cell.
     * @param param2 an int which indicates the column of the source Cell.
     * @param param3 an int which indicates the row of the target Cell.
     * @param param4 an int which indicates the column of the target Cell.
     */
    public Move(int id, Response sourceType, Response targetType, int param1, int param2, int param3, int param4){
        this.sourceType=sourceType;
        this.param1=param1;
        this.param2=param2;
        this.targetType=targetType;
        this.param3=param3;
        this.param4=param4;
        this.playerId = id;
    }

    /**
     * Creates a new <tt>Move</tt> changement relative to a Dice moved by a Player from {@link server.model.state.boards.draftpool.DraftPool}
     * to WindowFrame or from DraftPool to {@link server.model.state.boards.roundtrack.RoundTrack} or from RoundTrack to WindowFrame.
     * @param id the if of the player which made the move.
     * @param sourceType the type of the source Cell.
     * @param targetType the type of the target Cell.
     * @param param1 an int to indicates the position of the Cell.
     * @param param2 an int to indicates the position of the Cell.
     * @param param3 an int to indicates the position of the Cell.
     */
    public Move(int id, Response sourceType, Response targetType, int param1,  int param2, int param3){
        this.sourceType=sourceType;
        this.param1=param1;
        this.param2=param2;
        this.targetType=targetType;
        this.param3=param3;
        this.param4=-1;
        this.playerId = id;
    }


    /**
     * Gets the if of the player which made the move.
     * @return the if of the player which made the move.
     */
    public int getPlayerId() {
        return playerId;
    }

    /**
     * Gets the type of the source Cell.
     * @return the type of the source Cell.
     */
    public Response getSourceType() {
        return sourceType;
    }

    /**
     * Gets an int to indicates the position of the Cell.
     * @return an int to indicates the position of the Cell.
     */
    public int getParam1() {
        return param1;
    }

    /**
     * Gets an int to indicates the position of the Cell.
     * @return an int to indicates the position of the Cell.
     */
    public int getParam2() {
        return param2;
    }

    /**
     * Gets the type of the target Cell.
     * @return the type of the target Cell.
     */
    public Response getTargetType() {
        return targetType;
    }

    /**
     * Gets an int to indicates the position of the Cell.
     * @return an int to indicates the position of the Cell.
     */
    public int getParam3() {
        return param3;
    }

    /**
     * Gets an int to indicates the position of the Cell.
     * @return an int to indicates the position of the Cell.
     */
    public int getParam4() {
        return param4;
    }

    /**
     * Delegate the handling of this <tt>Move</tt> Changement to a specific {@link common.Changer}.
     * @param changer will handle this <tt>Move</tt> Changement.
     */
    public void change(Changer changer){
        changer.change(this);
    }
}