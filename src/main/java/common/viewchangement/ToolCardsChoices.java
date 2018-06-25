package common.viewchangement;

import common.Changer;

/**
 * The <tt>ToolCardChoices</tt> class contains the data to send to the client
 */
public class ToolCardsChoices extends Changement {
    /**
     * Delegate the handling of this <tt>ToolCardsChoices</tt> Changement to a specific {@link common.Changer}.
     * @param changer will handle this <tt>ToolCardsChoices</tt> Changement.
     */
    public void change(Changer changer){
        changer.change(this);
    }

}
