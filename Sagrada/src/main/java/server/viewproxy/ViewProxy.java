package server.viewproxy;

import server.model.Model;
import server.controller.Controller;
import server.observer.Observer;
import server.model.state.State;
import server.model.state.player.Player;

public abstract class ViewProxy implements Observer {
    protected Controller controller;
    protected State state;
    protected Player player;

    public ViewProxy(Model model, Player player){
        model.addObserver(this);
        state=model.getState();
        controller=new Controller(model, player);
        this.player=player;
    }
}
