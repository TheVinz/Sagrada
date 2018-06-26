package server;

import server.model.Model;
import server.model.SinglePlayerModel;
import server.model.state.player.Player;
import server.model.state.utilities.Timer;
import server.model.state.utilities.TimerObserver;
import server.viewproxy.ViewProxy;

import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class GameManager implements TimerObserver {

    private Map<String,Model> gamesMap = new ConcurrentHashMap<>();
    private Model currentModel = new Model(this);
    private Timer timer;

    public GameManager(){
        timer = new Timer(this, 15);
    }

    //gestire quando uno si vuole connettere ma la partita è finita
    public synchronized void addPlayer(String name, ViewProxy viewProxy, boolean singlePlayer) throws Exception{
        Model model;
        Player player;
        if(gamesMap.containsKey(name)){
            if(singlePlayer)
                throw new Exception("Single player exception.");
            model = gamesMap.get(name);
            player = model.getState().getPlayers()
                                        .stream()
                                        .filter( p -> p.getName().equals(name))
                                        .findFirst()
                                        .orElse(null);
            if(player.isSuspended())
                System.out.print(name + " -- reconnecting --> " + gamesMap.get(name) + "\n>>>");
            else{
                throw new Exception("Invalid username.");
            }
        }
        else if(singlePlayer)
        {
            model = new SinglePlayerModel(this);
            player = model.addPlayer(name);
            gamesMap.put(name, model);
        }else{
            model = currentModel;
            player = model.addPlayer(name);
            gamesMap.put(name, currentModel);
            if(currentModel.getState().getPlayers().size()>1)
                timer.start();
        }
        viewProxy.setModel(model);
        viewProxy.setPlayer(player);
        model.addViewProxyPlayer(viewProxy, player);
        printPlayers();
        if(singlePlayer) startGame(model);
    }

    public synchronized void startGame(Model model){
        if(model.isSingleplayer())
            new Thread( model::startGame ).start();
        else if(model.equals(currentModel)) {
            timer.stop();
            new Thread( currentModel::startGame ).start();
            System.out.print(currentModel.hashCode() + " starting...\n>>>");
            currentModel = new Model(this);
        }
    }

    public void endGame(Model model, String message){

        model.getState().getPlayers().forEach( player -> gamesMap.remove(player.getName()));
        //ma la partita viene eliminata?
        System.out.println(model.hashCode()+" terminated. --> "+message+"\n>>>");
        printPlayers();
        System.gc();
    }

    private void printPlayers(){
        System.out.println("Players playing:\n");
        gamesMap.forEach((key, value) -> System.out.println("\t" + key + " ---> " + value.hashCode()));
        System.out.print("\n>>>");
    }

    public void removePlayer(String name){
        gamesMap.remove(name);
    }

    public Model getCurrentModel() {
        return this.currentModel;
    }

    public Map<String, Model> getGamesMap(){
        return new HashMap<>(gamesMap);
    }

    @Override
    public void notifyTimeout() {
        if(currentModel.getState().getPlayers().size()>1)
            startGame(currentModel);
        else
            timer.start();
    }
}
