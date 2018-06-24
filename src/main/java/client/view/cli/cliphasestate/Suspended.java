package client.view.cli.cliphasestate;

import client.view.cli.CliApp;
import client.view.cli.CliDisplayer;
import client.view.cli.CliLaunchClient;
import common.command.GameCommand;
import common.response.Response;
import server.LaunchServer;

import java.util.Scanner;

public class Suspended implements CliPhaseState {
    private final Scanner sc;
    private final String name;

    public Suspended(Scanner sc, String name){
        this.sc = sc;
        this.name = name;
        CliDisplayer.getDisplayer().displayText("You are suspended, press anything:\n>>>");
    }
    @Override
    public void handle(String input) throws InvalidInput {
        CliLaunchClient.reconnect(sc, name);
        CliDisplayer.getDisplayer().displayText("Please wait your next turn!");
        CliApp.getCliApp().setWaitingPhase(true);
    }

    @Override
    public CliPhaseState reset() {
        return new Suspended(sc, name);
    }
}
