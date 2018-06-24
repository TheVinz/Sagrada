package common.viewchangement;

import common.Changer;

import java.io.Serializable;

/**
 * The <tt>Changement</tt> class contains data to send to the client to inform him about a change in the state.
 */
public abstract class Changement implements Serializable {


    /**
     * Delegate the handling of this <tt>Changement</tt> to a specific {@link common.Changer}.
     * @param changer will handle this <tt>Changement</tt>.
     */
    public abstract void change(Changer changer);
}
