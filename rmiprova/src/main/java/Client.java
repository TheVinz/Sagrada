import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Client
{
    static public void main(String[] args) throws Exception
    {

        Registry registry = LocateRegistry.getRegistry("https://rmiregistry.herokuapp.com");
        Prova prova = new ProvaImpl();
        registry.rebind("prova", prova);
        Prova prova1 = (ProvaImpl) registry.lookup("prova");
        prova1.prova();

    }
}