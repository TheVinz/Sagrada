package server.viewproxy;

import common.Notification;
import common.command.GameCommand;
import common.response.Response;
import common.viewchangement.Changement;
import common.viewchangement.LoadId;
import server.model.Model;
import server.model.state.player.Player;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.rmi.RemoteException;

public class SocketViewProxy extends ViewProxy {

    private ObjectOutputStream out;
    private Socket socket;
    private ObjectInputStream in;
    private boolean loop = true;

    public SocketViewProxy(ObjectOutputStream out, Socket socket) throws RemoteException {
        super();
        this.out = out;
        this.socket = socket;
    }

    /**
     * Sends a changement of the state to the client.
     * @param changement the changement to send to the client.
     */
    @Override
    void change(Changement changement) {
        sendData(changement);
    }

    /**
     * Notifies the client about the mistake of his last action.
     * @param notification the notification to send to the client.
     */
    @Override
    void notify(Notification notification) {
        sendData(notification);
    }

    /**
     * Informs the client about his next expected action in reaction to his last action.
     * @param response the response to send to the client.
     */
    @Override
    void send(Response response) {
        sendData(response);
    }

    /**
     * Pings the client in order to know if he's still connected, otherwise he will be suspended.
     */
    @Override
    public void ping() {
        Thread.currentThread().setName(player.getName() + " socket ping");
        while(super.isPing()) {
            sendData(null);
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    /**
     * Informs the client that he has been suspended by closing the socket connection.
     */
    @Override
    public synchronized void closeConnection(){
        loop=false;
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Writes in the output stream of the socket connection the data to send to the client.
     * @param data to send to the client.
     */
    private synchronized void sendData(Object data){
        try {
            if(!player.isSuspended()) out.writeObject(data);
        } catch (IOException e) {
            super.suspendPlayer();
        }
    }

    /**
     * This method keeps waiting for incoming {@link GameCommand} from the input stream of the socket connection and calls the method command of the instance of this class with this gameCommand as a parameter.
     * @param in the input stream of the socket connection
     * @throws ClassNotFoundException
     */
    public void mainLoop(ObjectInputStream in) throws ClassNotFoundException {
        this.in = in;
        try{
            while(loop) {
                GameCommand command = (GameCommand) in.readObject();
                command(command);
            }
        } catch (IOException e) {
            synchronized (this) {
                if(player.isSuspended())
                    return;
                super.suspendPlayer();
            }
        }
    }


    /**
     * Useful to send an initial confirmation of the successful connection to the client.
     * @param player the remote player representation on the Model.
     * @throws Exception in case the client is unreachable.
     */
    @Override
    public void setPlayer(Player player) throws Exception{
        try {
            out.writeObject("OK");
        } catch (IOException e) {
            throw new Exception("User unreachable!");
        }
        super.setPlayer(player);
    }

}
