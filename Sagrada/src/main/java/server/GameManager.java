package server;

import server.model.Model;
import server.model.SinglePlayerModel;
import server.model.state.utilities.Timer;
import server.model.state.utilities.TimerObserver;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class GameManager implements TimerObserver {

    private Map<String,Model> gamesMap = new HashMap<>();
    private Model currentModel = null;
    private SinglePlayerModel singlePlayerModel;
    private Timer timer;

    public GameManager(){
        timer = new Timer(this, 30);
    }


    public synchronized Model getModel(String name, boolean singlePlayer) {
        if(gamesMap.containsKey(name)){
            System.out.print(name + " -- reconnecting --> " + gamesMap.get(name) + "\n>>>");
            return gamesMap.get(name);
        }
        else if(singlePlayer)
        {
            singlePlayerModel = new SinglePlayerModel(this);
            gamesMap.put(name, singlePlayerModel);
            printPlayers();
            return singlePlayerModel;
        }else{
            if(currentModel == null)
            {
                currentModel = new Model(this);
                timer.start();
            }
            gamesMap.put(name, currentModel);
            printPlayers();
            return currentModel;
        }
    }

    public synchronized void startGame(Model model){
        if(model.isSingleplayer())
            model.startGame();
        else if(model.equals(currentModel) && model.getState().getPlayers().size()==4) {
            timer.stop();
            currentModel.startGame();
            currentModel = null;
        }
    }

    public synchronized void endGame(Model model, String message){

        model.getState().getPlayers().forEach( player -> gamesMap.remove(player.getName()));

        System.out.println(model.hashCode()+" terminated. --> "+message+"\n>>>");
        printPlayers();
    }

    private void printPlayers(){
        System.out.println("Players playing:\n");
        gamesMap.forEach((key, value) -> System.out.println("\t" + key + " ---> " + value.hashCode()));
        System.out.print("\n>>>");
    }

    @Override
    public synchronized void notifyTimeout() {
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
