package common.command;

import common.response.Response;

import java.io.Serializable;

public class GameCommand implements Serializable {
	/*
	I diversi tipi di changement sono rappresentati mediante int, dandogli un nome simbolico mediante public static final 
	rendiamo il codice più leggibile.
	Le classi StateController (lato server) e View (lato client) non devono sapere della struttura interna di questa classe, il lavoro di
	traduzione viene fatto dal RemoteController che invia il GameCommand alla RemoteView, questa traduce in elementi dello state e chiama metodi
	del StateController.
	*/

    private int x;
    private int y;
    private final Response type;

    public GameCommand(Response type, int row, int column){
        this.x=row;
        this.y=column;
        this.type=type;
    }

    public GameCommand(Response type, int index){
        this.x=index;
        this.type=type;
    }

    public GameCommand(Response type){
        this.type=type;
    }

    public Response getType() {
        return type;
    }

    public int getY() { return y; }

    public int getX() {return x; }
}