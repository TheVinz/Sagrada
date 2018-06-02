package common;

import java.io.Serializable;

public class Notification implements Serializable {
    public static final int ERROR = 0;
    public static final int WRONG_PARAMETER = 1;
    private final int type;
    private final String message;

    public Notification(int type, String message){

        this.type = type;
        this.message = message;
    }

    public int getType() {
        return type;
    }

    public String getMessage() {
        return message;
    }
}
