package common.RemoteMVC;

import common.exceptions.InvalidMoveException;

import java.rmi.Remote;

public interface RemoteController extends Remote {
    void command(int type); //command generici
    void command(int type, int index) throws InvalidMoveException; //draftpool/toolCard
    void command(int type, int x, int y) throws InvalidMoveException; //windowframecell/roundtrackcell
}
