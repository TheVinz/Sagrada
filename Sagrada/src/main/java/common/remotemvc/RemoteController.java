package common.remotemvc;

import common.command.Command;

public interface RemoteController {
    void sendCommand(Command c);
}
