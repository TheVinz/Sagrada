package TestGUI.server.model.exceptions;

public class InvalidMoveException extends Exception{
    public InvalidMoveException(String message){
        super(message);
    }
}