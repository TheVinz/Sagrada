package server.state.toolcards;

import common.exceptions.InvalidMoveException;
import server.Model;
import server.state.ModelObject;
import server.state.player.Player;

import java.util.List;
import java.util.Queue;

public abstract class ToolCard implements ModelObject {
	protected final Model model;
	List<ModelObject> parameters;
	Queue<String> expectedParameters;
	protected Player player;

	public ToolCard(Model model){
		this.model=model;
	}

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
	public String getType() {
		return "ToolCard";
	}
}