package server.model.state.toolcards;

import common.exceptions.InvalidMoveException;
import server.model.Model;
import server.model.state.ModelObject;
import server.model.state.player.Player;

import java.util.List;
import java.util.Queue;

public abstract class ToolCard implements ModelObject {
	protected final Model model;
	List<ModelObject> parameters;
	Queue<Integer> expectedParameters;
	protected Player player;

	private boolean used;

	public ToolCard(Model model){
		this.used=false;
		this.model=model;
	}

	public abstract int getNumber();
	public boolean isUsed(){ return used;}
	public void setUsed(){ used=false; }

	public abstract void start(Player player) throws InvalidMoveException;
	public void setParameter(ModelObject o) throws InvalidMoveException{
		if(o.getType()!=expectedParameters.poll()) throw new InvalidMoveException("Wrong parameter");
		else {
			parameters.add(o);
			if(!hasNext()) doAbility();
		}
	}

	public boolean hasNext(){
		return !expectedParameters.isEmpty();
	}

	abstract void doAbility() throws InvalidMoveException;

	@Override
	public int getType() {
		return TOOL_CARD;
	}
}