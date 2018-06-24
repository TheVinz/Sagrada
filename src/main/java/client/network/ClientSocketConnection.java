package client.network;

import client.view.cli.CliApp;
import common.RemoteMVC.RemoteView;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ClientSocketConnection implements ClientConnection{

    private final int port=8010;
    private Socket connection;
    private ObjectInputStream in;
    private ObjectOutputStream out;
    private ClientSocketHandler clientSocketHandler;

    public ClientSocketConnection(String ip, String name, RemoteView viewModel, boolean singlePlayer){
        try{
            connection = new Socket(ip, port);
            out = new ObjectOutputStream(connection.getOutputStream());
            in = new ObjectInputStream(connection.getInputStream());
        }catch (IOException e){
            e.printStackTrace();
        }
        try {
            System.out.println((String) in.readObject());
            out.writeObject(name);
            out.writeObject(new Boolean(singlePlayer));
            clientSocketHandler = new ClientSocketHandler(in, out, viewModel);
            CliApp.getCliApp().setRemoteController(clientSocketHandler);
            new Thread(() -> {
                try {
                    clientSocketHandler.mainLoop();
                } catch (IOException e) {
                    CliApp.getCliApp().suspend();
                }
            }).start();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }


    @Override
    public void close() {
        try {
            in.close();
            out.close();
            connection.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
