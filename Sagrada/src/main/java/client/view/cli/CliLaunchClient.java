package client.view.cli;

import client.view.cli.cliphasestate.CliPhaseSate;
import client.view.cli.cliphasestate.MenuPhase;
import client.view.cli.cliphasestate.MovingDicePhase;
import client.view.cli.cliphasestate.UsingToolCardPhase;
import client.view.network.ClientConnection;
import client.view.network.ClientConnectionFactory;

import java.rmi.RemoteException;
import java.util.Scanner;

public class CliLaunchClient {
    public static void main(String[] args){

        int connectionChoice = 0;
        ClientConnection clientConnection;
        Scanner scanner = new Scanner(System.in);
        CliDisplayer cliDisplayer = new CliDisplayer();
        CliState cliState = new CliState();

        do{
            connectionChoice = scanner.nextInt();
        }while(connectionChoice!=0 || connectionChoice!=1);

        clientConnection = new ClientConnectionFactory().getClientConnection(0, new CliChangementVisitor(cliDisplayer, cliState));

        CliPhaseSate currentPhase = new MenuPhase(cliDisplayer, clientConnection.getCommandVisitor());
        cliDisplayer.printMenu();

        while(true){
            cliDisplayer.displayText("(insert M to undo the operation and see the menu)");
            String input = scanner.nextLine();
            try{
                currentPhase = currentPhase.handle(input);
            }catch (RemoteException e){
                cliDisplayer.displayText("Network problem, try again\n");
            }

        }


    }
}
