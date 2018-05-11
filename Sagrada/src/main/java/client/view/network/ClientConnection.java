package client.view.network;

import common.CommandVisitor;

public interface ClientConnection {

    CommandVisitor getCommandVisitor();
}
