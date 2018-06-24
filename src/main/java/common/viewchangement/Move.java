package common.viewchangement;


import common.Changer;
import common.response.Response;

public class Move extends Changement{



    private final int playerId;
    private final Response sourceType;
    private final int param1;
    private final int param2;
    private final Response targetType;
    private final int param3;
    private final int param4;

    public Move(int id, Response sourceType, Response targetType, int param1, int param2, int param3, int param4){
        this.sourceType=sourceType;
        this.param1=param1;
        this.param2=param2;
        this.targetType=targetType;
        this.param3=param3;
        this.param4=param4;
        this.playerId = id;
    }

    public Move(int id, Response sourceType, Response targetType, int param1,  int param2, int param3){
        this.sourceType=sourceType;
        this.param1=param1;
        this.param2=param2;
        this.targetType=targetType;
        this.param3=param3;
        this.param4=-1;
        this.playerId = id;
    }


    public int getPlayerId() {
        return playerId;
    }

    public Response getSourceType() {
        return sourceType;
    }

    public int getParam1() {
        return param1;
    }

    public int getParam2() {
        return param2;
    }

    public Response getTargetType() {
        return targetType;
    }

    public int getParam3() {
        return param3;
    }

    public int getParam4() {
        return param4;
    }

    public void change(Changer changer){
        changer.change(this);
    }
}