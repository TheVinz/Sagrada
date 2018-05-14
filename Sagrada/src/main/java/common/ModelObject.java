package common;

public interface ModelObject {
    public static final int DRAFT_POOL_CELL=0;
    public static final int WINDOW_FRAME_CELL=1;
    public static final int ROUND_TRACK_CELL=3;
    public static final int CHOICE=5;
    public static final int WINDOW_FRAME=7;
    public static final int TOOL_CARD = 4;


    int getType();
}
