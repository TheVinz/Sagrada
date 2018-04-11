package client.control;

import common.dice.Dice;
import common.toolCards.ToolCard;
import client.view.View;
public class ToolCardManager {
    private ToolCard toolCard;
    private Controller controller;
    public ToolCardManager(ToolCard toolCard)
    {
        this.toolCard = toolCard;
        controller.analyzeObject(toolCard.nextParameter());
    }

    public void setParameter(Object o){
        toolCard.setParameter(o);
        if(toolCard.nextParameter() == null)
            toolCard.doAbility();
        controller.analyzeObject(toolCard.nextParameter());
    }
}
