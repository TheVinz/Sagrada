package TestGUI.client.view;

import TestGUI.common.Command;
import TestGUI.common.remotemvc.RemoteController;
import TestGUI.server.model.exceptions.InvalidMoveException;

public class ControllerProxy implements RemoteController {
    RemoteController serverController;

    public ControllerProxy(RemoteController controller){
        this.serverController=controller;
    }

    @Override
    public void sendCommand(Command c) throws InvalidMoveException {
        serverController.sendCommand(c);
    }

    public void bindServerController(RemoteController controller){
        this.serverController=controller;
    }
}
