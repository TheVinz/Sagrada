import java.rmi.RemoteException;

public class ProvaImpl implements Prova {
    public void prova() throws RemoteException{
        System.out.println("ciao");
    }
}
