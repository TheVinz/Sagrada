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

    public static Pane getEmptyDice(int value){
        ImageView result = new ImageView();
        Image image;
        switch(value){
            case 1:
                image=new Image(Util.class.getResource("../resources/frame/one.png").toString());
                break;
            case 2:
                image=new Image(Util.class.getResource("../resources/frame/two.png").toString());
                break;
            case 3:
                image=new Image(Util.class.getResource("../resources/frame/three.png").toString());
                break;
            case 4:
                image=new Image(Util.class.getResource("../resources/frame/four.png").toString());
                break;
            case 5:
                image=new Image(Util.class.getResource("../resources/frame/five.png").toString());
                break;
            case 6:
                image=new Image(Util.class.getResource("../resources/frame/six.png").toString());
                break;
            default:
                image=new Image(Util.class.getResource("../resources/frame/empty.png").toString());
                break;
        }
        result.setImage(image);
        result.setFitWidth(50);
        result.setFitHeight(50);
        result.setX(2);
        result.setY(2);
        Pane pane = new Pane();
        pane.getStyleClass().add("cell");
        pane.getChildren().add(result);
        return pane;
    }

    public static ImageView getImage(char rep){
        ImageView result = new ImageView();
        Image image;
        switch(rep){
            case '1':
                image=new Image(Util.class.getResource("../resources/frame/empty1.png").toString());
                break;
            case '2':
                image=new Image(Util.class.getResource("../resources/frame/empty2.png").toString());
                break;
            case '3':
                image=new Image(Util.class.getResource("../resources/frame/empty3.png").toString());
                break;
            case '4':
                image=new Image(Util.class.getResource("../resources/frame/empty4.png").toString());
                break;
            case '5':
                image=new Image(Util.class.getResource("../resources/frame/empty5.png").toString());
                break;
            case '6':
                image=new Image(Util.class.getResource("../resources/frame/empty6.png").toString());
                break;
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
        result.setY(2);
        result.setX(2);
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
        result.setX(2);
        result.setY(2);
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
            image = getImage(rep.charAt(i));
            Pane cell = new Pane();
            cell.getChildren().add(image);
            pane.add(cell, col, row);
        }
        pane.setAlignment(Pos.CENTER);
        return pane;
    }

    public static Pane getToolCard(int index){
        String name;
        Image image;
        String path="../resources/toolcards/";
        switch(index){
            case 1:
                path=path+"1-pinzasgrossatrice.png";
                name = "Pinza Sgrossatrice";
                break;
            case 2:
                path=path+"2-pennellopereglomise.png";
                name = "Pennello Per Eglomise";
                break;
            case 3:
                path=path+"3-alesatoreperlaminadirame.png";
                name = "Alesatore Per Lamina Di Rame";
                break;
            case 4:
                path=path+"4-lathekin.png";
                name = "Lathekin";
                break;
            case 5:
                path=path+"5-taglierinacircolare.png";
                name = "Taglierina Circolare";
                break;
            case 6:
                path=path+"6-pennelloperpastasalda.png";
                name = "Pennello Per Pasta Salda";
                break;
            case 7:
                path=path+"7-martelletto.png";
                name = "Martelletto";
                break;
            case 8:
                path=path+"8-tenagliaarotelle.png";
                name = "Tenaglia A Rotelle";
                break;
            case 9:
                path=path+"9-rigainsughero.png";
                name = "Riga in Sughero";
                break;
            case 10:
                path=path+"10-tamponediamantato.png";
                name = "Tampone Diamantato";
                break;
            case 11:
                path=path+"11-diluenteperpastasalda.png";
                name = "Diluente Per Pasta Salda";
                break;
            case 12:
                path=path+"12-taglierinamanuale.png";
                name = "Taglierina Manuale";
                break;
            default:
                return null;
        }
        image=new Image(Util.class.getResource(path).toString());
        ImageView toolCard=new ImageView(image);
        toolCard.setFitWidth(134);
        toolCard.setFitHeight(198);
        toolCard.setX(2);
        toolCard.setY(2);
        Pane pane = new Pane();
        pane.getStyleClass().add("toolcard");
        pane.getChildren().add(toolCard);
        pane.setAccessibleText(name);
        pane.setOnMouseEntered((event) -> zoom(toolCard));
        pane.setOnMouseExited((event) -> resize(toolCard));
        return pane;
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
        card.setOnMouseEntered((event) -> zoom(card));
        card.setOnMouseExited((event) -> resize(card));
        card.setFitWidth(134);
        card.setFitHeight(198);
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
        publicObjectiveCard.setFitWidth(134);
        publicObjectiveCard.setFitHeight(198);
        publicObjectiveCard.setOnMouseEntered((event) -> zoom(publicObjectiveCard));
        publicObjectiveCard.setOnMouseExited((event) -> resize(publicObjectiveCard));
        return publicObjectiveCard;
    }

    private static void zoom(ImageView card){
        card.setFitHeight(297);
        card.setFitWidth(201);
    }

    private static void resize(ImageView card){
        card.setFitHeight(198);
        card.setFitWidth(134);
    }
}
