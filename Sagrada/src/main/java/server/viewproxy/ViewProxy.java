package server.viewproxy;

import common.RemoteMVC.RemoteView;
import server.Model;
import server.controller.Controller;
import server.observer.Observer;
import server.state.State;

public abstract class ViewProxy implements Observer {
    private Controller controller;
    private State state;
    private int id;

    public ViewProxy(Model model, int id){
        model.addObserver(this);
        state=model.getState();
        controller=new Controller(model, model.getState().getPlayer(id));
        this.id=id;
    }
}
