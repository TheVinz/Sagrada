package common.viewchangement;

import client.view.ChangementVisitor;

public class LoadToolCards implements Changement {
    public static final int PINZA_SGROSSATRICE=1;
    public static final int PENNELLO_PER_EGLOMISE=2;
    public static final int ALESATORE_PER_LAMINA_DI_RAME=3;
    public static final int LATHEKIN=4;
    public static final int TAGLIERINA_CIRCOLARE=5;
    public static final int PENNELLO_PER_PASTA_SALDA=6;
    public static final int MARTELLETTO=7;
    public static final int TENAGLIA_A_ROTELLE=8;
    public static final int RIGA_IN_SUGHERO=9;
    public static final int TAMPONE_DIAMANTATO=10;
    public static final int DILUENTE_PER_PASTA_SALDA=11;
    public static final int TAGLIERINA_MANUALE=12;
    public static final int STRIP_CUTTER=13;

    private final int[] toolCards;

    public LoadToolCards(int[] toolCards){
        this.toolCards=toolCards;
    }

    public int[] getToolCards() {
        return toolCards;
    }

    @Override
    public void change(ChangementVisitor changementVisitor) {
        changementVisitor.change(this);
    }
}
