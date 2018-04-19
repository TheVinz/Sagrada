package TestGUI.common.viewchangement;

public class Move implements Changement{

    public static final int FROM_DP_TO_WF=1;
    public static final int FROM_WF_TO_WF=2;

    private final int moveType;
    private final int sourceX;
    private final int sourceY;
    private final int targetX;
    private final int targetY;

    public Move(int sourceX, int sourceY, int targetX, int targetY){
        this.moveType=FROM_WF_TO_WF;
        this.sourceX=sourceX;
        this.sourceY=sourceY;
        this.targetX=targetX;
        this.targetY=targetY;
    }

    public Move(int index, int row, int column) {
        this.sourceX=index;
        this.sourceY=0;
        this.targetX=row;
        this.targetY=column;
        this.moveType=Move.FROM_DP_TO_WF;
    }

    public int getSourceX() {
        return sourceX;
    }

    public int getSourceY() {
        return sourceY;
    }

    public int getTargetX() {
        return targetX;
    }

    public int getTargetY() {
        return targetY;
    }

    public int getType() {
        return ChangementTypes.MOVE;
    }

    public int getMoveType(){
        return this.moveType;
    }
}