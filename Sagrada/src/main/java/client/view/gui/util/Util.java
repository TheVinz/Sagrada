package client.view.gui.util;

import javafx.geometry.Pos;
import javafx.scene.effect.Reflection;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.RowConstraints;

public class Util {

    public static ImageView getImage(int value){
        ImageView result = new ImageView();
        Image image;
        switch(value){
            case 1:
                image=new Image(Util.class.getResource("../resources/frame/empty1.png").toString());
                break;
            case 2:
                image=new Image(Util.class.getResource("../resources/frame/empty2.png").toString());
                break;
            case 3:
                image=new Image(Util.class.getResource("../resources/frame/empty3.png").toString());
                break;
            case 4:
                image=new Image(Util.class.getResource("../resources/frame/empty4.png").toString());
                break;
            case 5:
                image=new Image(Util.class.getResource("../resources/frame/empty5.png").toString());
                break;
            case 6:
                image=new Image(Util.class.getResource("../resources/frame/empty6.png").toString());
                break;
            default:
                image=new Image(Util.class.getResource("../resources/frame/empty.png").toString());
                break;
        }
        result.setImage(image);
        return result;
    }

    public static ImageView getImage(char color){
        ImageView result = new ImageView();
        Image image;
        switch(color){
            case 'b':
                image=new Image(Util.class.getResource("../resources/frame/emptyblue.png").toString());
                break;
            case 'r':
                image=new Image(Util.class.getResource("../resources/frame/emptyred.png").toString());
                break;
            case 'y':
                image=new Image(Util.class.getResource("../resources/frame/emptyyellow.png").toString());
                break;
            case 'p':
                image=new Image(Util.class.getResource("../resources/frame/emptypurple.png").toString());
                break;
            case 'g':
                image=new Image(Util.class.getResource("../resources/frame/emptygreen.png").toString());
                break;
            default:
                image=new Image(Util.class.getResource("../resources/frame/empty.png").toString());
                break;
        }
        result.setImage(image);
        result.setFitHeight(50);
        result.setFitWidth(50);
        return result;
    }

    public static ImageView getImage(char color, int value){
        String diceColor;
        String dicevalue;
        switch(color){
            case 'b':
                diceColor="blue";
                break;
            case 'r':
                diceColor="red";
                break;
            case 'y':
                diceColor="yellow";
                break;
            case 'p':
                diceColor="purple";
                break;
            case 'g':
                diceColor="green";
                break;
            default:
                return null;
        }
        switch(value){
            case 1:
                dicevalue="one";
                break;
            case 2:
                dicevalue="two";
                break;
            case 3:
                dicevalue="three";
                break;
            case 4:
                dicevalue="four";
                break;
            case 5:
                dicevalue="five";
                break;
            case 6:
                dicevalue="six";
                break;
            default:
                return null;
        }
        ImageView result = new ImageView( new Image(Util.class.getResource("../resources/dices/"+diceColor+dicevalue+".png").toString()));
        result.setFitWidth(50);
        result.setFitHeight(50);
        return result;
    }

    public static GridPane getWindowFrame(String rep){
        ImageView image;
        GridPane pane = new GridPane();

        for(int i=0; i<5; i++){
            ColumnConstraints column = new ColumnConstraints(54);
            pane.getColumnConstraints().add(column);
        }
        for(int i=0; i<4; i++){
            RowConstraints row = new RowConstraints(54);
            pane.getRowConstraints().add(row);
        }

        for(int i=0; i<rep.length(); i++){
            int row = i/5;
            int col= i%5;
            switch(rep.charAt(i)){
                case '0':
                    image = getImage(0);
                    break;
                case '1':
                    image = getImage(1);
                    break;
                case '2':
                    image = getImage(2);
                    break;
                case '3':
                    image = getImage(3);
                    break;
                case '4':
                    image = getImage(4);
                    break;
                case '5':
                    image = getImage(5);
                    break;
                case '6':
                    image = getImage(6);
                    break;
                case 'b':
                    image = getImage('b');
                    break;
                case 'r':
                    image = getImage('r');
                    break;
                case 'p':
                    image = getImage('p');
                    break;
                case 'y':
                    image = getImage('y');
                    break;
                case 'g':
                    image = getImage('g');
                    break;
                default:
                    return null;
            }
            image.setX(2);
            image.setY(2);
            Pane cell = new Pane();
            cell.getChildren().add(image);
            pane.add(cell, col, row);
        }
        pane.setAlignment(Pos.CENTER);
        Reflection reflection = new Reflection();
        reflection.setFraction(0.7f);
        pane.setEffect(reflection);
        return pane;
    }

    public static ImageView getToolCard(int index){
        Image image;
        String path="../resources/toolcards/";
        switch(index){
            case 1:
                path=path+"1-pinzasgrossatrice.png";
                break;
            case 2:
                path=path+"2-pennellopereglomise.png";
                break;
            case 3:
                path=path+"3-alesatoreperlaminadirame.png";
                break;
            case 4:
                path=path+"4-lathekin.png";
                break;
            case 5:
                path=path+"5-taglierinacircolare.png";
                break;
            case 6:
                path=path+"6-pennelloperpastasalda.png";
                break;
            case 7:
                path=path+"7-martelletto.png";
                break;
            case 8:
                path=path+"8-tenagliaarotelle.png";
                break;
            case 9:
                path=path+"9-rigainsughero.png";
                break;
            case 10:
                path=path+"10-tamponediamantato.png";
                break;
            case 11:
                path=path+"11-diluenteperpastasalda.png";
                break;
            case 12:
                path=path+"12-taglierinamanuale.png";
                break;
            default:
                return null;
        }
        image=new Image(Util.class.getResource(path).toString());
        ImageView toolCard=new ImageView(image);
        toolCard.setFitHeight(297);
        toolCard.setFitWidth(201);
        return toolCard;
    }

    public static ImageView getPrivateObjectiveCard(char color){
        String path = "../resources/objectivecards/privateobjectivecards/";
        switch(color){
            case 'b':
                path=path+"blueprivateobjective.png";
                break;
            case 'r':
                path=path+"redprivateobjective.png";
                break;
            case 'y':
                path=path+"yellowprivateobjective.png";
                break;
            case 'g':
                path=path+"greenprivateobjective.png";
                break;
            case 'p':
                path=path+"purpleprivateobjective.png";
                break;
            default:
                return null;
        }
        Image image = new Image(Util.class.getResource(path).toString());
        ImageView card = new ImageView(image);
        card.setFitWidth(201);
        card.setFitHeight(297);
        return card;
    }

    public static ImageView getPublicObjectiveCard(int card){
        String path = "../resources/objectivecards/publicobjectivecards/";
        switch(card){
            case 0:
                path=path+"coloreddiagonal.png";
                break;
            case 1:
                path=path+"darkshades.png";
                break;
            case 2:
                path=path+"differentcolors.png";
                break;
            case 3:
                path=path+"differentcolorscolumn.png";
                break;
            case 4:
                path=path+"differentcolorsrow.png";
                break;
            case 5:
                path=path+"differentshades.png";
                break;
            case 6:
                path=path+"differentshadescolumn.png";
                break;
            case 7:
                path=path+"differentshadesrow.png";
                break;
            case 8:
                path=path+"mediumshades.png";
                break;
            case 9:
                path=path+"paleshades.png";
                break;
            default:
                return null;
        }
        Image image = new Image(Util.class.getResource(path).toString());
        ImageView publicObjectiveCard = new ImageView(image);
        publicObjectiveCard.setFitHeight(297);
        publicObjectiveCard.setFitWidth(201);
        return publicObjectiveCard;
    }
}
