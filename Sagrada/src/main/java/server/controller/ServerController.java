package server.controller;

import common.CommandVisitor;
import common.command.GameCommand;
import common.command.LoginCommand;
import common.exceptions.InvalidMoveException;
import common.response.Response;
import server.Model;
import server.state.player.Player;

import java.rmi.RemoteException;

import static common.command.GameCommand.*;

public class ServerController implements CommandVisitor {
    Model model;
    StateController controller;
    Player player;
    public ServerController(Model model, StateController controller, Player player){
        this.model = model;
        this.controller = controller;
        this.player = player;
    }
    @Override
    public Response handle(GameCommand gameCommand) throws RemoteException {
        switch(gameCommand.getType()){
            case DRAFTPOOL_CLICK:
                try{
                    controller.selectObject(model.getState().getDraftPool().getCell(gameCommand.getX()));
                }catch (InvalidMoveException e){
                    return new Response(e.getMessage());
                }

                break;
            case WINDOW_FRAME_CLICK:
                try {
                    controller.selectObject(model.getState().getWindowFrame(player.getId()).getCell(gameCommand.getX(), gameCommand.getY()));
                }catch(InvalidMoveException e){
                    return new Response(e.getMessage());
                }
                break;
            //mancano altri casi
            default:
                break;
        }
        return new Response("ok");
    }

    @Override
    public Response handle(LoginCommand loginCommand) throws RemoteException {
        return null;
    }
}
