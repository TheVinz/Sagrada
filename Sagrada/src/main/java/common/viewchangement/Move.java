package common.viewchangement;


public class Move implements Changement{

    public static final int DRAFT_POOL=0;
    public static final int WINDOW_FRAME=1;
    public static final int ROUND_TRACK=2;

    private final String player;
    private final int sourceType;
    private final int sourceX;
    private final int sourceY;
    private final int targetType;
    private final int targetX;
    private final int targetY;

    public Move(String player, int sourceType, int sourceX, int sourceY,int targetType, int targetX, int targetY){
        this.player = player;
        this.sourceType=sourceType;
        this.sourceX=sourceX;
        this.sourceY=sourceY;
        this.targetType=targetType;
        this.targetX=targetX;
        this.targetY=targetY;
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

    public int getSourceType(){
        return this.sourceType;
    }

    public int getTargetType(){
        return this.targetType;
    }

    public String getPlayer() {
        return player;
    }



}