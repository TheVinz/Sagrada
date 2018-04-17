package TestGUI.common.viewchangement;

public class RefilledDraftPool extends Changement {
    private final String[] dices;

    public RefilledDraftPool(String[] dices){
        this.dices=dices;
    }

    public String[] getDices() {
        return dices;
    }

    @Override
    public int getType() {
        return ChangementTypes.REFILLED_DRAFT_POOL;
    }
}
