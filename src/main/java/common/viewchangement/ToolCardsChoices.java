package common.viewchangement;

import common.Changer;

/**
 * The <tt>ToolCardChoices</tt> class informs the client that he have to choose the number of ToolCards.
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
