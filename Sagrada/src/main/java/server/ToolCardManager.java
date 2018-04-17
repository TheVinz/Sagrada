package server;

import common.exceptions.InvalidMoveException;
import common.toolcards.ToolCard;
import server.Controller;

public class ToolCardManager {
    private ToolCard toolCard;
    public ToolCardManager(ToolCard toolCard)
    {
        this.toolCard = toolCard;
    }

    public void setParameter(Object o) throws InvalidMoveException {
        toolCard.setParameter(o);
        if(toolCard.nextParameter() == null)
            toolCard.doAbility();
    }
}
