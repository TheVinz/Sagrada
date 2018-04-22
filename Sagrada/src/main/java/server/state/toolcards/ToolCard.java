package server.state.toolcards;

import common.exceptions.InvalidMoveException;
import server.Model;
import server.state.player.Player;

import java.util.List;
import java.util.Queue;

public abstract class ToolCard {
	protected final Model model;
	List<Object> parameters;
	Queue<Class> expectedParameters;
	protected Player player;

	public ToolCard(Model model){
		this.model=model;
	}

	public abstract void start(Player player) throws InvalidMoveException;
	public void setParameter(Object o) throws InvalidMoveException{
		if(o.getClass()!=expectedParameters.poll()) throw new InvalidMoveException("Wrong parameter");
		else {
			parameters.add(o);
			if(!hasNext()) doAbility();
		}
	}

	public boolean hasNext(){
		return !expectedParameters.isEmpty();
	}

	abstract void doAbility() throws InvalidMoveException;
}