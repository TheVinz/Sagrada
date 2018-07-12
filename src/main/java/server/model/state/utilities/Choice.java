package server.model.state.utilities;

import server.model.state.ModelObject.ModelObject;
import server.model.state.ModelObject.ModelType;

/**
 * The <tt>Choice</tt> class is a particular {@link ModelObject} for choices inputs by the client. This choices are represented by
 * integers.
 */
public class Choice implements ModelObject {
    private Integer choiceInt;

    /**
     * Initializes the <tt>Choice</tt> with the indicated integer.
     * @param choice the Choice's integer.
     */
    public Choice(Integer choice){
        this.choiceInt = choice;
    }

    /**
     * Returns this <tt>Choice</tt>'s choice integer.
     * @return this <tt>Choice</tt>'s choice integer.
     */
    public Integer getChoice() {
        return choiceInt;
    }

    /**
     * Returns this class' {@link ModelType}.
     * @return <code>ModelType.CHOICE</code>.
     */
    @Override
    public ModelType getType() {
        return ModelType.CHOICE;
    }
}
