package server.viewproxy;

import common.response.Response;
import server.model.Model;
import server.controller.Controller;
import server.observer.Observer;
import server.model.state.State;
import server.model.state.player.Player;

public interface ViewProxy extends Observer {

    void notifyNextParameter(Response response);
    void notifyError(String message);
    void notifyWrongParameter(String message);
}
