package server;

import server.model.Model;
import server.model.SinglePlayerModel;
import server.model.state.utilities.Timer;
import server.model.state.utilities.TimerObserver;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class GameManager implements TimerObserver {

    private Map<String,Model> gamesMap = new HashMap<>();
    private Model currentModel = null;
    private SinglePlayerModel singlePlayerModel;
    private Timer timer;

    public GameManager(){
        timer = new Timer(this, 30);
    }


    synchronized public Model setModel(String name, boolean singlePlayer) {
        if(singlePlayer)
        {
            singlePlayerModel = new SinglePlayerModel();
            gamesMap.put(name, singlePlayerModel);
            return singlePlayerModel;
        }else{
            if(currentModel == null)
            {
                currentModel = new Model();
                timer.start();
            }
            gamesMap.put(name, currentModel);
            return currentModel;
        }
    }

    synchronized public void startGame(boolean singlePlayer){
        if(singlePlayer)
            singlePlayerModel.startGame();
        else if(currentModel.getState().getPlayers().size()==4) {
            timer.stop();
            currentModel.startGame();
            currentModel = null;
        }

    }




    @Override
    synchronized public void notifyTimeout() {
        if(currentModel.getState().getPlayers().size()==1)
        {
            //da gestire
        }
        else
        {
            currentModel.startGame();
        }
        timer.stop();
        currentModel = null;
    }
}
