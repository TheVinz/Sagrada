package TestGUI.client.toolcards;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class PennelloPerEglomise implements ToolCard {
    private List<String> messages;
    private Iterator<String> messageIterator;
    private final int numParameters=2;

    public PennelloPerEglomise(){
        messages=new ArrayList<>();
        messages.add("Pick a dice from your window frame");
        messages.add("Select an empty cell");
    }

    @Override
    public void start(){
        messageIterator= messages.iterator();
    }

    @Override
    public String nextMessage() {
        if(messageIterator.hasNext())
            return messageIterator.next();
        else return "";
    }

    @Override
    public int getNumParameters() {
        return numParameters;
    }

    @Override
    public String getName(){return "Pennello per\nEglomise";}
}
