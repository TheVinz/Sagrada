package common.RemoteMVC;

import common.command.GameCommand;

import java.io.IOException;
import java.rmi.Remote;

public interface RemoteController extends Remote {

    void command(GameCommand gameCommand) throws IOException;

}
