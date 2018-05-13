package common.RemoteMVC;

import java.rmi.Remote;

public interface RemoteController extends Remote {
    void command(int type);
    void command(int type, int index);
    void command(int type, int x, int y);
}
