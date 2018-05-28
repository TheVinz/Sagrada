package client.view.cli;

public class LoadWindowFrame {
    private static LoadWindowFrame singleton;
    public static LoadWindowFrame getLoadWindowFrame() {
        if (singleton == null) singleton = new LoadWindowFrame();
        return singleton;
    }
    public String returnName(String rep){
        switch(rep){
            case "yb001g050430r0g200by":
                return "KALEIDOSCOPIC DREAM";
            case "4025g006g203g405g100":
                return "VIRTUS";
            case "5gbp2p000yy060p100g4":
                    return "AURORAE MAGNIFICUS";
            case "y0600015023yrp00043r":
                return "VIA LUX";
            case "0b20y040r0005y0g300p":
                return "SUN CATCHER";
            case "b600y03b00056200401g":
                return "BELLESGUARD";
            case "p60035p30002p10015p4":
                return "FIRMITAS";
            case "20501y6p2r0b5g003050":
                return "SYMPHONY OF LIGHT";
            case "r0b0y4p3g20105000600":
                return "AURORA SAGRADIS";
            case "1r30654r20005r10003r":
                return "INDUSTRIA";
            case "6p00550p00r60p0yr543":
                return "SHADOW THIEF";
            case "0060005b403gyp214r53":
                return "BATTLO";
            case "103b002b006b040b5201":
                return "GRAVITAS";
            case "040y6r020000rp1by000":
                return "FRACTAL DROPS";
            case "01rp46p25g1g53p00000":
                return "LUX ASTRAL";  //nome lux astram
            case "00g002y5b10r3p010604":
                return "CHROMATIC SPLENDOR";
            case "341500620y000yr50yr6":
                return "FIRELIGHT";
            case "00r50p40g3600b00y200":
                    return "LUZ CELESTIAL";
            case "6b00105b004r2b0g6y3p":
                return "WATER OF LIFE";
            case "000r500p4b0b3y6y2g1r":
                return "RIPPLES OF LIGHT";
            case "001001g3b2g546g0b5g0":  //tra 2 e 5 b e non g
                return "LUX MUNDI";
            case "y02060405y000y512y30":
                return "COMITAS";
            case "1py04py006y005305421":
                return "SUNS_GLORY";
            case "0br000450bb20r56r310":
                    return "FULGOR_DEL_CIELO";
            default:
                return "Error Load window frame";
        }

    }
}
