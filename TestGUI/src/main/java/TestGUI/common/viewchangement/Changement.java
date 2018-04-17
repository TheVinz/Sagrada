package TestGUI.common.viewchangement;

import java.util.List;

public class Changement {
    public final static int FROM_DP_TO_WF=0;
    public final static int REFILLED_DP=1;

    private int type;
    private List<Changeble> parameters;

    public Changement(int type){
        this.type=type;
    }

    public void addParameter(Object obj);
}
