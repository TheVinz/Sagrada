package common.response;

import java.io.Serializable;

public class Response implements Serializable {
    public static final int DRAFT_POOL_CLICK=1;
    public static final int WINDOW_FRAME_CLICK=2;
    public static final int ROUND_TRACK_CLICK=3;
    public static final int WRONG_PARAMETER=4;
    public static final int SUCCESS=5;

    private int type;

    private String message;
    public Response(int type){
        this.type = type;
    }
    public Response(String message){
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
    public int getType(){
        return type;
    }
}
