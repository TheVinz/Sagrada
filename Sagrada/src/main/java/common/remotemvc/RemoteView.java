package common.remotemvc;

import common.command.Command;
import common.exceptions.InvalidMoveException;
import common.viewchangement.Changement;

public interface RemoteView{
    void bindRemoteModel(RemoteModel model);

    void sendChangement(Changement change);
    void receiveCommand(Command c) throws InvalidMoveException;
}
