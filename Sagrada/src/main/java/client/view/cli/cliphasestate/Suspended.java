package client.view.cli.cliphasestate;

import client.view.cli.CliApp;
import client.view.cli.CliDisplayer;
import common.command.GameCommand;
import common.response.Response;

public class Suspended implements CliPhaseState {
    public Suspended(){
        CliDisplayer.getDisplayer().displayText("You are suspended, press anything to come back to play:\n>>>");
    }
    @Override
    public void handle(String input) throws InvalidInput {
        CliDisplayer.getDisplayer().displayText("Please wait your next turn!");
        CliApp.getCliApp().addCommandToBuffer(new GameCommand(Response.ACTIVE_AGAIN));
        CliApp.getCliApp().sendCommand();
        CliApp.getCliApp().setWaitingPhase(true);
    }

    @Override
    public CliPhaseState reset() {
        return new Suspended();
    }
}
