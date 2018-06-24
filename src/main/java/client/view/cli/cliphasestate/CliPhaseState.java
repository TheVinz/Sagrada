package client.view.cli.cliphasestate;


public interface CliPhaseState {
    void handle(String input) throws InvalidInput;

    CliPhaseState reset();
}
