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

    @Override
    void change(Changement changement) {
        sendData(changement);
    }

    @Override
    void notify(Notification notification) {
        sendData(notification);
    }

    @Override
    void send(Response response) {
        sendData(response);
    }

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

    @Override
    public synchronized void closeConnection(){
        loop=false;
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private synchronized void sendData(Object data){
        try {
            if(!player.isSuspended()) out.writeObject(data);
        } catch (IOException e) {
            super.suspendPlayer();
        }
    }

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
