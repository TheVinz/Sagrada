package server.model.state.toolcards;

import common.exceptions.InvalidMoveException;
import common.response.Response;
import server.model.Model;
import common.ModelObject;
import server.model.state.player.Player;

import java.rmi.RemoteException;
import java.util.List;
import java.util.Queue;

public abstract class ToolCard implements ModelObject {
	protected final Model model;
	protected List<ModelObject> parameters;
	protected Queue<Integer> expectedParameters;
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
	public void setParameter(ModelObject o) throws InvalidMoveException {
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

	public int next(){
		try {
			return expectedParameters.peek();
		}
		catch(NullPointerException e){
			return Response.END;
		}
	}

	@Override
	public int getType() {
		return TOOL_CARD;
	}
}