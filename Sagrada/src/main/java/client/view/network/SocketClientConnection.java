package client.view.network;

import common.CommandVisitor;

public class SocketClientConnection implements ClientConnection {
    @Override
    public CommandVisitor getCommandVisitor() {
        return null;
    }
}
