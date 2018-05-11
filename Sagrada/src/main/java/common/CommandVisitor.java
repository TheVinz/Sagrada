package common;

import common.command.GameCommand;
import common.command.LoginCommand;
import common.response.Response;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface CommandVisitor extends Remote {
    Response handle(GameCommand gameCommand) throws RemoteException;
    Response handle(LoginCommand loginCommand) throws RemoteException;
}
