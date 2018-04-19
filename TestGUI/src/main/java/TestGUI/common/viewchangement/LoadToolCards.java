package TestGUI.common.viewchangement;

public class LoadToolCards implements Changement {
    String[] toolCards;

    public LoadToolCards(String[] names) {
        toolCards=names;
    }

    public String[] getToolCards() {
        return toolCards;
    }

    @Override
    public int getType() {
        return ChangementTypes.LOAD_TOOL_CARDS;
    }
}
