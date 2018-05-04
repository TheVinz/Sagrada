package common.command;

public class Command {
	/*
	I diversi tipi di changement sono rappresentati mediante int, dandogli un nome simbolico mediante public static final 
	rendiamo il codice più leggibile.
	Le classi Controller (lato server) e View (lato client) non devono sapere della struttura interna di questa classe, il lavoro di
	traduzione viene fatto dal RemoteController che invia il Command alla RemoteView, questa traduce in elementi dello state e chiama metodi
	del Controller. 
	*/
    public static final int DRAFTPOOL_CLICK=1;
    public static final int WINDOW_FRAME_CLICK=2;
    public static final int ROUND_TRACK_CLICK=3;
    public static final int USE_TOOL_CARD=4;
    public static final int PINZA_SGROSSATRICE_INCREASE=5;
    public static final int PINZA_SGROSSATRICE_DECREASE=6;
    public static final int END_TURN=7;

    private int x;
    private int y;
    private final int type;

    public Command(int type,int row, int column){
        this.x=row;
        this.y=column;
        this.type=type;
    }

    public Command(int type, int index){
        this.x=index;
        this.type=type;
    }

    public Command(int type){
        this.type=type;
    }

    public int getType() {
        return type;
    }

    public int getY() { return y; }

    public int getX() {return x; }
}
