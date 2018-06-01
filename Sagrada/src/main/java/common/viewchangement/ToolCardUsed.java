package common.viewchangement;

import client.view.Changer;

public class ToolCardUsed extends Changement {
    private final int id;
    private final int index;
    private final int tokens;
    public ToolCardUsed(int id, int index, int tokens){
        this.id = id;
        this.index = index;
        this.tokens = tokens;
    }

    public int getId() {
        return id;
    }

    public int getIndex() {
        return index;
    }

    public int getTokens() {
        return tokens;
    }

    public void change(Changer changer){
        changer.change(this);
    }
}
