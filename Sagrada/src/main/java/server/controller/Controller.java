package server.controller;
import common.exceptions.InvalidMoveException;
import common.exceptions.WrongParameter;
import common.response.Response;
import server.model.Model;
import server.model.SinglePlayerModel;
import server.model.state.ModelObject.ModelObject;
import server.model.state.boards.windowframe.WindowFrameList;
import server.model.state.player.Player;
import server.model.state.utilities.Timer;
import server.model.state.utilities.TimerObserver;
import server.viewproxy.ViewProxy;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Controller implements TimerObserver {
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
		player.setTimer(new Timer(this, 60));
	}

	public synchronized void selectObject(ModelObject o) { //synchronized? e si eh
		if(lock.tryLock()) {
			try {
				if(model.getState().isGameFinished() && model.getClass().equals(SinglePlayerModel.class))
					currentState = new SelectingPrivateObjectiveCard(player, model);
				PlayerState temp = null;
				if (player.isActive() || currentState.getClass().equals(SelectingPrivateObjectiveCard.class)) {
					try {
						temp = currentState;
						currentState = currentState.selectObject(o);
					} catch (InvalidMoveException e) {
						currentState = new WaitingState(player, model);
						view.notifyError(e.getMessage());
						if(player.getTimer().timeFinished())
							timeFinished();
						return;
					} catch (WrongParameter e) {
						view.notifyWrongParameter(e.getMessage());
						return;
					}
					if (temp.nextParam() != null) {
						view.notifyNextParameter(temp.nextParam());
					}
					else if(currentState.nextParam() != null)
						view.notifyNextParameter(currentState.nextParam());
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
			currentState.abort();
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
		if(player.isActive()){ // forse questo controllo è meglio sostituirlo con un timer.stop() quando finisce il turno altrimenti continua a contare anche dopo il turno
		if(lock.tryLock()){
			try{
				if(Thread.currentThread()==player.getTimer().getBlinker()){
					timeFinished();
					endTurn();
				}

			}finally {
				lock.unlock();
			}
		}}
	}

	public void timeFinished(){
		model.suspendPlayer(player);
		view.notifyNextParameter(Response.SUSPENDED);
	}

	public void reinsertPlayer() {
		model.reinsertPlayer(player);
		new Thread(() -> view.ping()).start();
	}
}
