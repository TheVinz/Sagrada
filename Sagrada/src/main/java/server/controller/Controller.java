package server.controller;
import common.exceptions.InvalidMoveException;
import common.exceptions.WrongParameter;
import common.response.Response;
import server.model.Model;
import server.model.state.ModelObject.ModelObject;
import server.model.state.boards.windowframe.WindowFrameList;
import server.model.state.player.Player;
import server.model.state.utilities.Timer;
import server.viewproxy.ViewProxy;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Controller {
	private Model model;
	private Player player;
	private ViewProxy view;

	private PlayerState currentState;

	private Lock lock;

	public Controller(Model model, Player player, ViewProxy view){
		this.player=player;
		this.model=model;
		this.view=view;
		currentState=new WaitingState(player, model);
		lock = new ReentrantLock();
		player.setTimer(new Timer(this));
	}

	public void selectObject(ModelObject o) throws InvalidMoveException, WrongParameter {
		if(lock.tryLock()) {
			try {
				PlayerState temp = null;
				if (player.isActive()) {
					try {
						temp = currentState;
						currentState = currentState.selectObject(o);
					} catch (InvalidMoveException e) {
						currentState = new WaitingState(player, model);
						view.notifyError(e.getMessage());
						if(player.getTimer().timeFinished())
							timeFinished();
						throw e;
					} catch (WrongParameter e) {
						view.notifyError(e.getMessage());
						if(temp.nextParam()==null)
							view.notifyNextParameter(Response.SUCCESS_USED_TOOL_CARD);
						else
							view.notifyNextParameter(temp.nextParam());
						throw e;
					}
					player.getTimer().start();
					if (temp.nextParam() != null) {
						view.notifyNextParameter(temp.nextParam());
					}
					if (player.isDiceMoved() && player.isToolCardUsed()) {
						endTurn();
					}
				}
			}finally {
				lock.unlock();
			}
		}
	}

	public PlayerState getCurrentState(){
		return this.currentState;
	}

	public void endTurn() {
		if(player.isActive()) {
			currentState = new WaitingState(player, model);
			model.endTurn(player);
		}
	}

    public void windowFrameChoice(WindowFrameList[] windowFrameLists) {
		currentState=new SelectingWindowFrame(player, model, windowFrameLists);
    }

    public void isDiceMove(){
		if(player.isDiceMoved())
			view.notifyError("You have already moved a dice!\n");
		else
			view.notifyNextParameter(Response.DRAFT_POOL_MOVE);
	}

	public void notifyTimeout() {
		if(lock.tryLock()){
			try{
				if(Thread.currentThread()==player.getTimer().getBlinker())
					timeFinished();
			}finally {
				lock.unlock();
			}
		}
	}

	public void timeFinished(){
	    currentState.abort();
		endTurn();
	}
}
