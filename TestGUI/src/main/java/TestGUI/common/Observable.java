package TestGUI.common;



import TestGUI.common.viewchangement.Changement;
import TestGUI.server.model.boards.draftpool.DraftPoolCell;
import TestGUI.server.model.boards.windowframe.WindowFrameCell;

import java.util.ArrayList;
import java.util.List;

public abstract class Observable {
    private List<Observer> observers;
    public Observable(){ observers=new ArrayList<>(); }
    public void addObserver(Observer o){ observers.add(o); }
    public void removeObserver(Observer o){ this.observers.remove(o); }
    public abstract void notifyObservers();
    public void notifySimpleMove(DraftPoolCell source, WindowFrameCell target){
        for(Observer o : observers) o.updateSimpleMove(source, target);
    }
    public void notifyRefillDraftPool(){
        for(Observer o : observers) o.updateRefillDraftPool();
    }
}
