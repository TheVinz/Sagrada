package common.response;

import java.io.Serializable;

/**
 * Possible responses to send to the user in order to inform him about his next action.
 */
public enum Response implements Serializable {
    WINDOW_FRAME_CELL, DRAFT_POOL_CELL, ROUND_TRACK_CELL,
    PINZA_SGROSSATRICE_CHOICE, DILUENTE_PER_PASTA_SALDA_CHOICE,
    TAGLIERINA_MANUALE_CHOICE, CHOICE,
    END_TURN, TOOL_CARD, DRAFT_POOL_MOVE, WINDOW_FRAME_MOVE,
    SUCCESS_MOVE_DONE, SUCCESS_USED_TOOL_CARD, SUCCESS_TOOL_CARD_WITH_MOVE,
    WINDOW_FRAME, SIMPLE_MOVE_REQUEST, ACTIVE_AGAIN;

    public static final int PINZA_SGROSSATRICE_INCREASE=0;
    public static final int PINZA_SGROSSATRICE_DECREASE=1;
    public static final int TAGLIERINA_MANUALE_ONE_MOVE=2;
    public static final int TAGLIERINA_MANUALE_TWO_MOVES=3;

}
