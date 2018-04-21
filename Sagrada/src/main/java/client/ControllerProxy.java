package client;

import common.command.Command;
import common.remotemvc.RemoteController;
import common.remotemvc.RemoteView;

public class ControllerProxy implements RemoteController {
    RemoteView server;

    @Override
    public void sendCommand(Command c) {
    }
}
