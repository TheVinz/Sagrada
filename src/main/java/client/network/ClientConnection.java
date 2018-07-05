package client.network;

import common.RemoteMVC.RemoteController;
import common.RemoteMVC.RemoteView;
import common.login.RemoteLoginManager;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.MalformedURLException;
import java.net.Socket;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public abstract class ClientConnection {

    protected boolean ping = true;
    private static final int SOCKET_PORT = 8080;
    private static final int RMI_PORT = 1099;


    protected void connectRmi(String ip, String name, RemoteView remoteView, boolean singleplayer) throws RemoteException, MalformedURLException, NotBoundException {
        RemoteLoginManager login =(RemoteLoginManager) Naming.lookup("rmi://"+ip+":"+RMI_PORT+"/RMILoginManager");
        RemoteController remoteController=login.connect(name, remoteView, singleplayer);
        if(remoteController == null){
            connectionError();
            return;
        }
        setRemoteController(remoteController);
        new Thread(() -> {
            while(ping) {
                Thread.currentThread().setName("RMI ping");
                try {
                    remoteController.command(null);
                    Thread.sleep(1000);
                } catch (IOException e) {
                    if(ping)
                        notifyDisconnection();
                    return;
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    return;
                }
            }
        }).start();
    }

    protected void connectSocket(String ip, RemoteView remoteView, String name, boolean singleplayer){
        new Thread(() -> {
            Thread.currentThread().setName("Socket connection handler");
            try(Socket connection = new Socket(ip, SOCKET_PORT)){
                ObjectOutputStream out = new ObjectOutputStream(connection.getOutputStream());
                ObjectInputStream in = new ObjectInputStream(connection.getInputStream());
                out.writeObject(name);
                out.writeObject(singleplayer);
                String response = (String) in.readObject();
                if(response.equals("ERROR")){
                    connectionError();
                    return;
                }
                ClientSocketHandler clientSocketHandler = new ClientSocketHandler(in, out, remoteView);
                setRemoteController(clientSocketHandler);
                clientSocketHandler.mainLoop();
            }catch (IOException e) {
                notifyDisconnection();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }).start();
    }

    public abstract void setRemoteController(RemoteController remoteController);
    public abstract void notifyDisconnection();
    public abstract void connectionError();
}
