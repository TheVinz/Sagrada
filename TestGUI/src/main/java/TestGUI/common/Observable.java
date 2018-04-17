package TestGUI.common;



import TestGUI.common.viewchangement.Changement;

import java.util.ArrayList;
import java.util.List;

public abstract class Observable {
    private List<Observer> observers;
    public Observable(){ observers=new ArrayList<>(); }
    public void addObserver(Observer o){ observers.add(o); }
    public void removeObserver(Observer o){ this.observers.remove(o); }
    public abstract void notifyObservers();
    public void notifyObservers(Changement c){
        for(Observer o : observers){
            o.update(c);
        }
    }
}
