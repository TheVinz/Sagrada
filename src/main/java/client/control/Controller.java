package client.control;
import client.model.Model;
import client.view.View;
import common.dice.Dice;

public class Controller{
	private Model model;
	private View view;
	private ToolCardManager toolCardManager;
	public Controller(Model model){
		this.model=model;
	}

	public void useToolCard(int card) {
		toolCardManager = new ToolCardManager(model.getToolCard(card));
	}

	public void selectDice(int dice)
	{
		toolCardManager.setParameter(model.getDice(int dice));
	}

	public void analyzeObject(Object o){
		if(o.getClass().equals(Dice.class))
			view.display("Scegli un dado");
	}
}