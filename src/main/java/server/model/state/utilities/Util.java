package server.model.state.utilities;

import server.model.Model;
import server.model.state.objectivecards.publicobjectivecards.*;
import server.model.state.boards.windowframe.WindowFrameList;
import server.model.state.objectivecards.privateobjectivecards.PrivateObjectiveCard;
import server.model.state.objectivecards.publicobjectivecards.PublicObjectiveCard;
import server.model.state.toolcards.*;


import java.util.*;

import static server.model.state.boards.windowframe.WindowFrameList.*;
import static server.model.state.objectivecards.privateobjectivecards.PrivateObjectiveCard.*;

/**
 * In the <tt>Util</tt> class there are methods useful to instantiate the start of a game.
 */
public class Util {
    private List<Integer> avalaiblePatterns=new LinkedList<>(Arrays.asList(new Integer[]{1,2,3,4,5,6,7,8,9,10,11,12}));
    private List<PrivateObjectiveCard> availableCards=new LinkedList<>( Arrays.asList(new PrivateObjectiveCard[]{YELLOW_SHAPES, PURPLE_SHAPES, BLUE_SHAPES, GREEN_SHAPES, RED_SHAPES}));

    /**
     * Randomly returns four {@link server.model.state.boards.windowframe.WindowFrame} described
     * by the enum class {@link server.model.state.boards.windowframe.WindowFrameList}.
     * @return an array with four WindowFrame.
     */
    public WindowFrameList[] getWindowFrameChoice(){
        int choice[]=new int[2];
        WindowFrameList[] result=new WindowFrameList[4];
        Collections.shuffle(avalaiblePatterns);
        for(int i=0; i<choice.length; i++) {
            choice[i]=avalaiblePatterns.get(0);
            avalaiblePatterns.remove(Integer.valueOf(choice[i]));
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
                    result[2*i]=LUX_ASTRAM;
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
                case 12:
                    result[2*i]=SUNS_GLORY;
                    result[2*i+1]=FULGOR_DEL_CIELO;
                    break;
                default:
                    break;
            }
        }
        return result;
    }

    /**
     * Randomly chose {@link server.model.state.toolcards.ToolCard}s to add to the game.
     * @param model contains the twelve possibly ToolCards.
     * @param number of ToolCards to return.
     * @return an array with ToolCards chose randomly.
     */
    public ToolCard[] getToolCards(Model model, int number) {
        //con StripCutter 13
        boolean[] cards={true,true,true,true,true,true,true,true,true,true,true,true};
        ToolCard[] result=new ToolCard[number];
        Random rnd=new Random();
        for(int i=0; i<number; i++){
            int card=rnd.nextInt(12);
            while(!cards[card]){
                card=rnd.nextInt(12);
            }
            cards[card]=false;
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

    /**
     * Randomly chose two or three PublicObjectiveCards depending on if the game is SinglePlayer of MultiPlayer.
     * @param singlePlayer indicates if SinglePlayer game or MultiPlayer game.
     * @return an array with PublicObjectiveCard chose randomly.
     */
    public PublicObjectiveCard[] getPublicObjectiveCards(boolean singlePlayer) {
        PublicObjectiveCard[] result=new PublicObjectiveCard[singlePlayer ? 2 : 3];
        Random rnd= new Random();
        boolean[] cards={true,true,true,true,true,true,true,true,true, true};
        for(int i=0; i<result.length; i++){
            int card=rnd.nextInt(10);
            while(!cards[card]) card=rnd.nextInt(10);
            cards[card]=false;
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

    /**
     * Gets a {@link server.model.state.objectivecards.privateobjectivecards.PrivateObjectiveCard} chose randomly.
     * @return a PrivateObjectiveCard.
     */
    public PrivateObjectiveCard getCard() {
        Collections.shuffle(availableCards);
        PrivateObjectiveCard result=availableCards.get(0);
        availableCards.remove(result);
        return result;
    }
}
