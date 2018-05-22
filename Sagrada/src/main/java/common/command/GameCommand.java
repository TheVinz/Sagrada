package common.command;

import java.io.Serializable;

public class GameCommand implements Serializable {
	/*
	I diversi tipi di changement sono rappresentati mediante int, dandogli un nome simbolico mediante public static final 
	rendiamo il codice più leggibile.
	Le classi StateController (lato server) e View (lato client) non devono sapere della struttura interna di questa classe, il lavoro di
	traduzione viene fatto dal RemoteController che invia il GameCommand alla RemoteView, questa traduce in elementi dello state e chiama metodi
	del StateController.
	*/
    public static final int DRAFT_POOL_CELL=0;
    public static final int WINDOW_FRAME_CELL=1;
    public static final int ROUND_TRACK_CELL=2;
    public static final int CHOICE=5;
    public static final int WINDOW_FRAME=3;
    public static final int TOOL_CARD = 4;
    public static final int END_TURN = 6;

    private int x;
    private int y;
    private final int type;

    public GameCommand(int type, int row, int column){
        this.x=row;
        this.y=column;
        this.type=type;
    }

    public GameCommand(int type, int index){
        this.x=index;
        this.type=type;
    }

    public GameCommand(int type){
        this.type=type;
    }

    public int getType() {
        return type;
    }

    public int getY() { return y; }

    public int getX() {return x; }
}
