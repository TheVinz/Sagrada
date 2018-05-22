package common.response;

import java.io.Serializable;

public enum Response implements Serializable {
    WINDOW_FRAME_CELL, DRAFT_POOL_CELL, ROUND_TRACK_CELL,
    PINZA_SGROSSATRICE_CHOICE, DILUENTE_PER_PASTA_SALDA_CHOICE, CHOICE,
    END_TURN, TOOL_CARD, DRAFT_POOL_MOVE, WINDOW_FRAME_MOVE, SUCCESS, ERROR;

    private String message=null;

    public String getMessage(){
        return this.message;
    }
    public void setMessage(String message){
        this.message=message;
    }
}
