package TestGUI.common;

public class Command {
    public final static int DRAFTPOOL_CLICK=1;
    public final static int WINDOW_FRAME_CLICK=2;
    public final static int REFILL_DRAFTPOOL=3;

    private int x;
    private int y;
    private final int type;

    public Command(int row, int column){
        this.x=row;
        this.y=column;
        this.type=WINDOW_FRAME_CLICK;
    }

    public Command(int index){
        this.x=index;
        this.type=DRAFTPOOL_CLICK;
    }

    public Command(){
        this.type=REFILL_DRAFTPOOL;
    }

    public int getType() {
        return type;
    }

    public int getY() {
        return y;
    }

    public int getX() {
        return x;
    }
}
