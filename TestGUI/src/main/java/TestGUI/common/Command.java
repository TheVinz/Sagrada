package TestGUI.common;

public class Command {
    public final static int DRAFTPOOL_CLICK=1;
    public final static int WINDOW_FRAME_CLICK=2;
    public final static int REFILL_DRAFTPOOL=3;
    public final static int USE_TOOL_CARD=4;

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

    public int getY() {
        return y;
    }

    public int getX() {
        return x;
    }
}
