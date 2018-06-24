package common;

import java.io.Serializable;

/**
 * The <tt>Notification</tt> class contains data to send to the client to inform him about the mistake of his last action.
 */
public class Notification implements Serializable {
    public static final int ERROR = 0;
    public static final int WRONG_PARAMETER = 1;
    private final int type;
    private final String message;

    /**
     * Creates a new notification.
     * @param type the type of the notification (0 for error, 1 for wrong parameter).
     * @param message the message of the notification.
     */
    public Notification(int type, String message){

        this.type = type;
        this.message = message;
    }

    /**
     * Get the type of the notification (0 for error, 1 for wrong parameter).
     * @return the type of the notification.
     */
    public int getType() {
        return type;
    }

    /**
     * Get the message of the notification.
     * @return the message of the notification.
     */
    public String getMessage() {
        return message;
    }
}
