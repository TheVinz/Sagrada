package common.viewchangement;


import client.view.Changer;

public class LoadToolCards extends Changement {

    private final int[] toolCards;

    public LoadToolCards(int[] toolCards){
        this.toolCards=toolCards;
    }


    public int[] getToolCards() {
        return toolCards;
    }

    public void change(Changer changer){
        changer.change(this);
    }
}
