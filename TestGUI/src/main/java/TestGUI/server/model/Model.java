package TestGUI.server.model;

import TestGUI.common.Observable;
import TestGUI.common.Observer;
import TestGUI.server.model.bag.Bag;
import TestGUI.server.model.boards.Cell;
import TestGUI.server.model.boards.draftpool.DraftPool;
import TestGUI.server.model.boards.windowframe.WindowFrame;
import TestGUI.server.model.exceptions.InvalidMoveException;
import TestGUI.server.model.toolcards.PennelloPerEglomise;
import TestGUI.server.model.toolcards.PennelloperPastaSalda;
import TestGUI.server.model.toolcards.ToolCard;

import java.util.ArrayList;
import java.util.List;

public class Model implements Observable {
    private WindowFrame windowFrame;
    private DraftPool draftPool;
    private Bag bag;
    private ToolCard[] toolCards;
    private List<Observer> observers;


    public Model(){
        this.observers=new ArrayList<>();
        windowFrame=new WindowFrame();
        draftPool=new DraftPool(5);
        bag=new Bag();
        toolCards=new ToolCard[3];
        toolCards[0]=new PennelloPerEglomise(this);
        toolCards[1]=new PennelloperPastaSalda(this);
    }

    public DraftPool getDraftPool() {
        return draftPool;
    }

    public WindowFrame getWindowFrame() {
        return windowFrame;
    }

    public ToolCard[] getToolCards() {
        return toolCards;
    }

    public void move(Cell source, Cell target) throws InvalidMoveException {
        source.move(target);
        notifyMove(source, target);
    }

    public void refillDraftPool() throws InvalidMoveException {
        draftPool.drow(bag);
        notifyRefillDraftPool();
    }

    public void increaseDice(Cell cell) throws InvalidMoveException {
        cell.getDice().increase();
        updateCell(cell);
    }

    public void decreaseDice(Cell cell) throws InvalidMoveException{
        cell.getDice().decrease();
        updateCell(cell);
        }

    public void useToolCard() {
    }

    @Override
    public void addObserver(Observer o) {
        observers.add(o);
        o.loadToolCards(toolCards);
    }

    @Override
    public void removeObserver(Observer o) {
        observers.remove(o);
    }

    @Override
    public void notifyObservers() {
    }

    @Override
    public void notifyMove(Cell source, Cell target) {
        for(Observer o : observers) o.updateMove(source, target);
    }


    @Override
    public void notifyRefillDraftPool() {
        for(Observer o : observers) o.updateRefillDraftPool();
    }

    @Override
    public void updateCell(Cell cell) {
        for(Observer o: observers) o.updateCell(cell);
    }


}
