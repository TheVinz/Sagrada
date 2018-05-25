package common.response;

import java.io.Serializable;

public enum Response implements Serializable {
    WINDOW_FRAME_CELL, DRAFT_POOL_CELL, ROUND_TRACK_CELL,
    PINZA_SGROSSATRICE_CHOICE, DILUENTE_PER_PASTA_SALDA_CHOICE, CHOICE, TAGLIERINA_MANUALE_CHOICE,
    END_TURN, TOOL_CARD, DRAFT_POOL_MOVE, WINDOW_FRAME_MOVE, SUCCESS, WINDOW_FRAME, SUCCESS_INIT_SIMPLE_MOVE;

    public static final int PINZA_SGROSSATRICE_INCREASE=0;
    public static final int PINZA_SGROSSATRICE_DECREASE=1;
    public static final int TAGLIERINA_MANUALE_ONE_MOVE=2;
    public static final int TAGLIERINA_MANUALE_TWO_MOVES=3;

    private String message=null;

    public String getMessage(){
        return this.message;
    }
    public void setMessage(String message){
        this.message=message;
    }
}
