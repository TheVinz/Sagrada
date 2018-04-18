package TestGUI.common.remotemvc;

import TestGUI.common.Command;
import TestGUI.server.model.exceptions.InvalidMoveException;

public interface RemoteController {
    void sendCommand(Command c) throws InvalidMoveException;
}
