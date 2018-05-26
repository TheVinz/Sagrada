import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.server.RemoteObject;

public interface Prova extends Remote {
    void prova() throws RemoteException;
}
