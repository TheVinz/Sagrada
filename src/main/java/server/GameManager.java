package server;

import server.model.Model;
import server.model.SinglePlayerModel;
import server.model.state.player.Player;
import server.model.state.utilities.Timer;
import server.model.state.utilities.TimerObserver;
import server.settings.Settings;
import server.viewproxy.ViewProxy;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * The <tt>GameManager</tt> class is the class used to implement the advanced feature of the multi-game server. This class
 * contains a username-model map, mapping each username to the {@link Model} representing the game he is playing. So every time
 * a new player tries to connect to the Server the GameManager class knows if he has to reconnect to an existing game, if his username
 * is still used by an other logged and playing {@link Player} or if he can start a new single-player or join a new multi-player game.
 */
public class GameManager implements TimerObserver {


    private Map<String,Model> gamesMap = new ConcurrentHashMap<>();
    private Model currentModel = new Model(this);
    private Timer timer;


    /**
     * Creates a new <tt>GameManager</tt> setting the timeout {@link Timer} for starting the game by reading it by the settings file.
     */
    public GameManager(){
        timer = new Timer(this, Settings.getStartGameTimeout());
    }

    /**
     * Handle method for the new connections. If the player name is still present into the player returns the {@link Player}
     * instance with this name, if the player is inactive, or throws an Exception. If the player name is not present in the map,
     * returns a new Player instance.
     * @param name the client's username.
     * @param viewProxy the view proxy bound to the remote player.
     * @param singlePlayer the player's choice to play a single-player or a multi-player game.
     * @throws Exception if the player username is still in use by an active player.
     */
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

    /**
     * Starts the game represented by the model.
     * @param model the model representing the starting game.
     */
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

    /**
     * Removes the model's player from the map and prints info about the ended game.
     * @param model the model representing the ended game.
     * @param message a message containing informations about the ended game.
     */
    public void endGame(Model model, String message){

        model.getState().getPlayers().forEach( player -> gamesMap.remove(player.getName()));
        System.out.println(model.hashCode()+" terminated. --> "+message+"\n>>>");
        printPlayers();
        System.gc();
    }

    private void printPlayers(){
        System.out.println("Players playing:\n");
        gamesMap.forEach((key, value) -> System.out.println("\t" + key + " ---> " + value.hashCode()));
        System.out.print("\n>>>");
    }

    /**
     * Removes a player from the game map.
     * @param name the client's username.
     */
    public void removePlayer(String name){
        gamesMap.remove(name);
    }

    /**
     * If the current multi-player model has more than one player then his game starts. This method works only if called
     * by the blinker thread of this class' {@link Timer}
     */
    @Override
    public void notifyTimeout() {
        if(Thread.currentThread().equals(timer.getBlinker())) {
            if (currentModel.getState().getPlayers().size() > 1)
                startGame(currentModel);
            else
                timer.start();
        }
    }
}
