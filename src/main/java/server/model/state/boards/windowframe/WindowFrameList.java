package server.model.state.boards.windowframe;

/**
 * The <tt>WindowFrameList</tt> is an enum of WindowFrame that can be chose by the player at the begin of the game.
 * Every WindowFrame as a rep. A string of twenty characters, one for cell. Characters that indicates if the
 * cell has a value restriction (a number), a {@link server.model.state.utilities.Color} restriction (a letter) or nothing (zero).
 * Every WindowFrame as also an assigned int that indicates the difficulty, called FavorTokens.
 */
public enum WindowFrameList {
    KALEIDOSCOPIC_DREAM(4, "yb001g050430r0g200by"), VIRTUS(5, "4025g006g203g405g100"),
    AURORAE_MAGNIFICUS(5, "5gbp2p000yy060p100g4"), VIA_LUX(4, "y0600015023yrp00043r"),
    SUN_CATCHER(3, "0b20y040r0005y0g300p"), BELLESGUARD(3, "b600y03b00056200401g"),
    FIRMITAS(5, "p60035p30002p10015p4"), SYMPHONY_OF_LIGHT(6, "20501y6p2r0b4g003050"),
    AURORA_SAGRADIS(4, "r0b0y4p3g20105000600"), INDUSTRIA(5, "1r30654r20005r10003r"),
    SHADOW_THIEF(5, "6p00550p00r60p0yr543"), BATLLO(5, "0060005b403gyp214r53"),
    GRAVITAS(5, "103b002b006b040b5201"), FRACTAL_DROPS(3, "040y6r020000rp1by000"),
    LUX_ASTRAM(5, "01gp46p25g1g53p00000"), CHROMATIC_SPLENDOR(4, "00g002y5b10r3p010604"),
    FIRELIGHT(5, "341500620y000yr50yr6"), LUZ_CELESTIAL(3, "00r50p40g3600b00y200"),
    WATER_OF_LIFE(6, "6b00105b004r2b0g6y3p"), RIPPLES_OF_LIGHT(5, "000r500p4b0b3y6y2g1r"),
    LUX_MUNDI(6, "001001g3b2b546g0b5g0"), COMITAS(5, "y02060405y000y512y30"),
    SUNS_GLORY(6, "1py04py006y005305421"), FULGOR_DEL_CIELO(5, "0br000450bb20r56r310");
    private final int favorToken;
    private final String rep;

    /**
     * Initialize a WindowFrame with given tokens and reps.
     * @param tokens difficulty of the WindowFrame.
     * @param rep String that represents the WindowFrame without dice.
     */
    WindowFrameList(int tokens, String rep){
        this.favorToken = tokens;
        this.rep = rep;
    }

    /**
     * Gets the rep of this WindowFrame.
     * @return a String that correspond to the rep.
     */
    public String getRep(){
        return rep;
    }

    /**
     * Gets the FavorTokens of this WindowFrame.
     * @return an int that correspond to the FavorTokens.
     */
    public int getFavorToken(){
        return favorToken;
    }
}
