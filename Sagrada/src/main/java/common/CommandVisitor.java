package common;

import common.command.GameCommand;
import common.response.Response;

import java.rmi.Remote;

public interface CommandVisitor extends Remote {
    public Response handle(GameCommand gameCommand);
}
