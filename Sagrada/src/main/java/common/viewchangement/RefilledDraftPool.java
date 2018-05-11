package common.viewchangement;

import common.ChangementVisitor;

import java.rmi.RemoteException;

public class RefilledDraftPool implements Changement {
    private final int[] values;
    private final char[] colors;

    public RefilledDraftPool(int[] values, char[] colors){
        this.values=values;
        this.colors=colors;
    }

    public int[] getValues() {
        return values;
    }

    public char[] getColors() {
        return colors;
    }

    @Override
    public void change(ChangementVisitor changementVisitor) {
        try{
            changementVisitor.change(this);
        }catch(RemoteException e){

        }
    }
}
