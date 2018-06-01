package server.network;

import common.command.GameCommand;
import server.viewproxy.ViewProxy;
import java.io.ObjectInputStream;

public class ServerSocketHandler implements Runnable {

    private final ObjectInputStream in;
    private final ViewProxy viewProxy;

    public ServerSocketHandler(ObjectInputStream in, ViewProxy viewProxy)  {
        this.in = in;
        this.viewProxy=viewProxy;
    }


    @Override
    public void run() {
        try {
            do {
                viewProxy.command((GameCommand) in.readObject());
            } while (true);
        } catch (Exception e) {
           e.printStackTrace();
        }

    }
}
