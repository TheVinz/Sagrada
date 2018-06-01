package server;

import server.model.Model;
import server.model.SinglePlayerModel;

import java.util.Scanner;

public class GameManager {
    private Model model = null;

    synchronized public void setModel(boolean singlePlayer) {
        if(model == null) { //perchè manca il multipartita
            model = singlePlayer ? new SinglePlayerModel() : new Model();
        }
    }

    synchronized public Model getModel(){
        return model;
    }

    public void init(){
        Scanner sc = new Scanner(System.in);
        String command = sc.nextLine();
        while(!command.equals("start"))
            command=sc.nextLine();
        model.startGame();
    }
}
