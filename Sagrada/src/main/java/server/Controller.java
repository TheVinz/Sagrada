package server;
import client.view.View;
import common.dice.Dice;

public class Controller{
	private Model model;
	private View view;
	private ToolCardManager toolCardManager;
	public Controller(Model model){
		this.model=model;
	}

/*	public void useToolCard(int card) {
		toolCardManager = new ToolCardManager(model.getToolCard(card));
	}*/

	public void selectFrameCell(int i, int j)
	{
		/*toolCardManager.setParameter(model.get(int dice));*/
	}

}