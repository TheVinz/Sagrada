package client.network;

import common.Notification;
import common.RemoteMVC.RemoteController;
import common.RemoteMVC.RemoteView;
import common.command.GameCommand;
import common.response.Response;
import common.viewchangement.Changement;
import common.viewchangement.EndGame;
import common.viewchangement.SinglePlayerEndGame;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * the <tt>ClientSocketHandler</tt> is the class that handles socket connection to the server.
 */
public class ClientSocketHandler implements RemoteController {

    private final ObjectInputStream in;
    private final ObjectOutputStream out;
    private final RemoteView viewModel;

    /**
     * Creates a new <tt>ClientSocketHandler</tt> whit the given input and output stream and viewModel.
     * @param in the socket input stream.
     * @param out the socket output stream.
     * @param viewModel the client Model.
     */
    ClientSocketHandler(ObjectInputStream in, ObjectOutputStream out, RemoteView viewModel){

        this.in = in;
        this.out = out;
        this.viewModel = viewModel;
    }

    /**
     * Serializes the command to the server.
     * @param gameCommand the command to serialize.
     * @throws IOException if the connection drops down.
     */
    @Override
    public void command(GameCommand gameCommand) throws IOException {
        out.writeObject(gameCommand);
    }

    /**
     * Starts main loop for reading the input buffer.
     * @throws IOException if the connection drops down.
     */
    void mainLoop() throws IOException {
        try{
            do {
                Object o = in.readObject();
                if(o instanceof Notification)
                    viewModel.notify((Notification) o);
                else if(o instanceof Response)
                    viewModel.send((Response) o);
                else if(o instanceof Changement) {
                    viewModel.change((Changement) o);
                    if(o instanceof EndGame || o instanceof SinglePlayerEndGame)
                        return;
                }
            }while (true);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }
}
