package client.network;

import client.view.cli.CliApp;
import common.Notification;
import common.RemoteMVC.RemoteController;
import common.RemoteMVC.RemoteView;
import common.command.GameCommand;
import common.response.Response;
import common.viewchangement.Changement;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class ClientSocketHandler implements RemoteController {

    private final ObjectInputStream in;
    private final ObjectOutputStream out;
    private final RemoteView viewModel;

    public ClientSocketHandler(ObjectInputStream in, ObjectOutputStream out, RemoteView viewModel){

        this.in = in;
        this.out = out;
        this.viewModel = viewModel;
    }

    @Override
    public void command(GameCommand gameCommand) throws IOException {
        out.writeObject(gameCommand);
    }

    public void mainLoop() throws IOException {
        try{
            do {
                Object o = in.readObject();
                if(o instanceof Notification)
                    viewModel.notify((Notification) o);
                else if(o instanceof Response)
                    viewModel.send((Response) o);
                else if(o instanceof Changement)
                    viewModel.change((Changement) o);
            }while (true);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }
}
