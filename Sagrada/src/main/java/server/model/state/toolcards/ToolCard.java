package server.model.state.toolcards;

import common.exceptions.InvalidMoveException;
import common.exceptions.WrongParameter;
import common.response.Response;
import server.model.Model;
import server.model.state.ModelObject.ModelObject;
import server.model.state.ModelObject.ModelType;
import server.model.state.player.Player;

import java.util.List;
import java.util.Queue;

import static server.model.state.ModelObject.ModelType.TOOL_CARD;

public abstract class ToolCard implements ModelObject {
	protected final Model model;
	protected List<ModelObject> parameters;
	protected Queue<ModelType> expectedParameters;
	protected Player player;

	private boolean used;

	public ToolCard(Model model){
		this.used=false;
		this.model=model;
	}

	public abstract int getNumber();
	public boolean isUsed(){ return used;}
	public void setUsed(){ used=true; }

	public abstract void start(Player player) throws InvalidMoveException;
	public void setParameter(ModelObject o) throws InvalidMoveException, WrongParameter {
		if(o.getType()!=expectedParameters.poll()) throw new WrongParameter("Wrong parameter");
		else {
			parameters.add(o);
			if(!hasNext()) doAbility();
		}
	}

	public boolean hasNext(){
		return !expectedParameters.isEmpty();
	}

	abstract void doAbility() throws InvalidMoveException, WrongParameter;

	public Response next(){
		 switch(expectedParameters.peek()){
			 case WINDOW_FRAME:
			 	return Response.WINDOW_FRAME;
			 case ROUND_TRACK_CELL:
			 	return Response.ROUND_TRACK_CELL;
			 case DRAFT_POOL_CELL:
			 	return Response.DRAFT_POOL_CELL;
			default:
				return null;
		 }
	}

	public Response getSuccess(){
		return Response.SUCCESS_USED_TOOL_CARD;
	}


	@Override
	public ModelType getType() {
		return TOOL_CARD;
	}
}