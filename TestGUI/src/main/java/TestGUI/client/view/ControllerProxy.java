package TestGUI.client.view;

import TestGUI.common.Command;
import TestGUI.common.remotemvc.RemoteController;
import TestGUI.server.model.exceptions.InvalidMoveException;

import java.util.ArrayDeque;

public class ControllerProxy implements RemoteController {
    RemoteController serverController;
    ArrayDeque<Command> queue=new ArrayDeque<>();

    public ControllerProxy(RemoteController controller){
        this.serverController=controller;
    }

    @Override
    public void sendCommand(Command c){
        queue.add(c);
    }

    public void flush() throws InvalidMoveException {
        while(!queue.isEmpty()){
            try {
                serverController.sendCommand(queue.poll());
            } catch (InvalidMoveException e) {
                queue.clear();
                throw e;
            }
        }
    }
}
