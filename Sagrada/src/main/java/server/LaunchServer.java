package server;

import common.login.RemoteLoginManager;
import server.login.LoginManager;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.util.Scanner;

public class LaunchServer {
    public static void main(String[] args){
        String ip = "127.0.0.1";
        int port = 1099;
        try {
            System.out.print(">>>");
            RemoteLoginManager loginManager = new LoginManager();
            System.out.print("Starting server...\n>>>");
            Naming.rebind("rmi://" + ip + ":" + port + "/LoginManager", loginManager);
            System.out.print("Server on\n>>>");
            Scanner sc = new Scanner(System.in);
            String command = sc.nextLine();
            while(!command.equals("start"))
                command=sc.nextLine();
            ((LoginManager) loginManager).startGame();
        } catch (RemoteException e) {
            e.printStackTrace();
            System.exit(1);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }
}
