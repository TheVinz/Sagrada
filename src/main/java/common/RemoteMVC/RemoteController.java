package common.RemoteMVC;

import common.command.GameCommand;

import java.io.IOException;
import java.rmi.Remote;

/**
 * The <tt>RemoteController</tt> interface is implemented by all the classes that are used to send data to the server, via RMI and Socket.
 * The <tt>RemoteController</tt> interface is implemented as {@link server.viewproxy.ViewProxy} by server side, for RMI connections, because this represents
 * the controller for the client, the client sends {@link common.command.GameCommand}s to the bound ViewProxy that translates this commands
 * into {@link server.model.state.ModelObject.ModelObject}s, and {@link server.viewproxy.SocketViewProxy} by client side, in case of Socket
 * connection, that serializes each player input to the server Socket.
 */
public interface RemoteController extends Remote {

    /**
     * Sends the given Command to the server.
     * @param gameCommand the client's command.
     * @throws IOException if a connection error occurs.
     */
    void command(GameCommand gameCommand) throws IOException;

}
