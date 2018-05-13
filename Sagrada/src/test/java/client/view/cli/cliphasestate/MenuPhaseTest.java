package client.view.cli.cliphasestate;

import client.view.cli.CliDisplayer;
import common.CommandVisitor;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.rmi.RemoteException;

import static org.junit.Assert.*;

public class MenuPhaseTest {
    private CliDisplayer cliDisplayer;
    private CommandVisitor commandVisitor;
    private MenuPhase menuPhase;
    private ByteArrayOutputStream outContent;

    @Before
    public void initClass(){
        cliDisplayer=Mockito.mock(CliDisplayer.class);
        commandVisitor=Mockito.mock(CommandVisitor.class);
        menuPhase = new MenuPhase(cliDisplayer,commandVisitor);


    }
    @Test
    public void shouldHandle() throws RemoteException {
        menuPhase.handle("M");
        menuPhase.handle("D");


    }

}