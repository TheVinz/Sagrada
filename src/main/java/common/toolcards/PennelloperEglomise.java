package common.toolcards;



import common.boards.windowframe.WindowFrameCell;
import common.exceptions.InvalidMoveException;

import java.util.*;

public class PennelloperEglomise implements ToolCard {

    private List<Object> parameters;
    private Queue<Class> expectedParameters;

    public PennelloperEglomise(){
        expectedParameters = new ArrayDeque<Class>();
        parameters = new ArrayList<Object>();
        expectedParameters.add(WindowFrameCell.class);
        expectedParameters.add(WindowFrameCell.class);
    }


    public Class nextParameter(){
        return expectedParameters.peek();
    }

    public void setParameter(Object o)
    {
        parameters.add(o);
        expectedParameters.remove();

    }

    public void doAbility() throws InvalidMoveException {
        WindowFrameCell windowframecell1 = (WindowFrameCell) parameters.get(0);
        WindowFrameCell windowframecell2 = (WindowFrameCell) parameters.get(1);
        windowframecell1.move(windowframecell2);    //in move secondo me target.put(this.getDice());

    }
}