package common.command;

import common.response.Response;

import java.io.Serializable;

/**
 * The <tt>GameCommand</tt> class is the class used to encode the user's inputs into a set of ints to be sent to the server.
 * Basically the command represents a {@link server.model.state.ModelObject.ModelObject} with a given {@link server.model.state.ModelObject.ModelType}
 * described by a {@link Response} and a maximum of two integer coordinates, a <tt>GameCommand</tt> can also represent an end turn command.
 */
public class GameCommand implements Serializable {


    private int x;
    private int y;
    private final Response type;

    /**
     * Initializes a new <tt>GameCommand</tt> with the user's input, described by given type and coordinates.
     * @param type the input type.
     * @param row the first input coordinate.
     * @param column the second input coordinate.
     */
    public GameCommand(Response type, int row, int column){
        this.x=row;
        this.y=column;
        this.type=type;
    }

    /**
     * Initializes a new <tt>GameCommand</tt> with the user's input, described by given type and coordinate.
     * @param type the input type.
     * @param index the input coordinate.
     */
    public GameCommand(Response type, int index){
        this.x=index;
        this.type=type;
    }

    /**
     * Initializes a new <tt>GameCommand</tt> with the given type.
     * @param type the input type.
     */
    public GameCommand(Response type){
        this.type=type;
    }

    /**
     * Returns the command's type.
     * @return the command's type.
     */
    public Response getType() {
        return type;
    }

    /**
     * Returns the second command's coordinate.
     * @return the second command's coordinate.
     */
    public int getY() { return y; }

    /**
     * Returns the first command's coordinate.
     * @return the first command's coordinate.
     */
    public int getX() {return x; }
}
