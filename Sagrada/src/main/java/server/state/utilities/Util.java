package server.state.utilities;

import server.Model;
import server.state.boards.windowframe.WindowFrame;
import server.state.objectivecards.publicobjectivecards.PublicObjectiveCard;
import server.state.toolcards.*;
import server.state.objectivecards.publicobjectivecards.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import static server.state.boards.windowframe.WindowFrame.*;

public class Util {
    private static List<Integer> avalaiblePatterns= Arrays.asList(new Integer[]{0,1,2,3,4,5,6,7,8,9,10,11});

    public static WindowFrame[] getWindowFrameChoiche(){
        int choice[]=new int[2];
        WindowFrame[] result=new WindowFrame[4];
        Collections.shuffle(avalaiblePatterns);
        for(int i=0; i<choice.length; i++) {
            choice[i]=avalaiblePatterns.get(0);
            switch (choice[i]) {
                case 1:
                    result[2*i]=KALEIDOSCOPIC_DREAM;
                    result[2*i+1]=VIRTUS ;
                    break;
                case 2:
                    result[2*i]=AURORAE_MAGNIFICUS;
                    result[2*i+1]=VIA_LUX ;
                    break;
                case 3:
                    result[2*i]=SUN_CATCHER;
                    result[2*i+1]=BELLESGUARD ;
                    break;
                case 4:
                    result[2*i]=FIRMITAS;
                    result[2*i+1]=SYMPHONY_OF_LIGHT ;
                    break;
                case 5:
                    result[2*i]=AURORA_SAGRADIS;
                    result[2*i+1]=INDUSTRIA ;
                    break;
                case 6:
                    result[2*i]=SHADOW_THIEF;
                    result[2*i+1]=BATLLO ;
                    break;
                case 7:
                    result[2*i]=GRAVITAS;
                    result[2*i+1]=FRACTAL_DROPS ;
                    break;
                case 8:
                    result[2*i]=LUX_ASTRAL;
                    result[2*i+1]= CHROMATIC_SPLENDOR;
                    break;
                case 9:
                    result[2*i]=FIRELIGHT;
                    result[2*i+1]=LUZ_CELESTIAL ;
                    break;
                case 10:
                    result[2*i]=WATER_OF_LIFE;
                    result[2*i+1]=RIPPLES_OF_LIGHT ;
                    break;
                case 11:
                    result[2*i]=LUX_MUNDI;
                    result[2*i+1]=COMITAS ;
                    break;
                default:
                    break;
            }
        }
        return result;
    }

    public static ToolCard[] getToolCards(Model model) {
        //con StripCutter 13
        boolean[] cards=new boolean[12];
        ToolCard[] result=new ToolCard[3];
        Random rnd=new Random();
        for(boolean b:cards) b=true;
        for(int i=0; i<3; i++){
            int card=rnd.nextInt(12);
            while(!cards[card]){
                card=rnd.nextInt();
            }
            switch (card){
                case 0:
                    result[i]=new PinzaSgrossatrice(model);
                    break;
                case 1:
                    result[i]=new PennelloPerEglomise(model);
                    break;
                case 2:
                    result[i]=new AlesatorePerLaminaDiRame(model);
                    break;
                case 3:
                    result[i]=new Lathekin(model);
                    break;
                case 4:
                    result[i]=new TaglierinaCircolare(model);
                    break;
                case 5:
                    result[i]=new PennelloperPastaSalda(model);
                    break;
                case 6:
                    result[i]=new Martelletto(model);
                    break;
                case 7:
                    result[i]=new TenagliaARotelle(model);
                    break;
                case 8:
                    result[i]=new RigaInSughero(model);
                    break;
                case 9:
                    result[i]=new TamponeDiamantato(model);
                    break;
                case 10:
                    result[i]=new DiluentePerPastaSalda(model);
                    break;
                case 11:
                    result[i]=new TaglierinaManuale(model);
                    break;
                default:
                    break;
            }
        }
        return result;
    }

    public static PublicObjectiveCard[] getPublicObjectiveCards() {
        PublicObjectiveCard[] result=new PublicObjectiveCard[3];
        Random rnd= new Random();
        boolean[] cards=new boolean[10];
        for(boolean b:cards) b=true;
        for(int i=0; i<result.length; i++){
            int card=rnd.nextInt(10);
            while(!cards[card]) card=rnd.nextInt(10);
            switch(card){
                case 0:
                    result[i]=new DifferentColorsRow();
                    break;
                case 1:
                    result[i]=new DifferentColorsColumn();
                    break;
                case 2:
                    result[i]=new DifferentShadesRow();
                    break;
                case 3:
                    result[i]=new DifferentShadesColumn();
                    break;
                case 4:
                    result[i]=new PaleShades();
                    break;
                case 5:
                    result[i]=new MediumShades();
                    break;
                case 6:
                    result[i]=new DarkShades();
                    break;
                case 7:
                    result[i]=new DifferentShades();
                    break;
                case 8:
                    result[i]=new ColoredDiagonal();
                    break;
                case 9:
                    result[i]=new DifferentColors();
                    break;
                default:
                    break;
            }
        }
        return result;
    }
}
