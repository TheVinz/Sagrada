package client.view.gui.util;

import client.view.gui.MainApp;
import com.sun.istack.internal.NotNull;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

/**
 * The <tt>Util</tt> GUI class only contains static methods used to load the pictures that represents the main game elements.
 */
public class Util {

    private Util(){}

    /**
     * Initializes the difficulty choices buttons.
     * @param difficulty the difficulty level the button should represent.
     * @return the button representing the difficulty indicated, <code>null</code> if the difficulty value is less than 1 ore more than 5.
     */
    public static Pane getDifficultyButton(int difficulty){
        if(difficulty < 1 || difficulty > 5 )
            return null;
        Image img = new Image(MainApp.class.getResource("resources/singleplayer/"+difficulty+".png").toString());
        ImageView button = new ImageView(img);
        button.setFitHeight(100);
        button.setFitWidth(100);
        button.setX(2);
        button.setY(2);
        Circle circle = new Circle(50, Color.WHITE);
        circle.setCenterX(52);
        circle.setCenterY(52);
        Pane pane = new Pane();
        pane.getChildren().addAll(circle, button);
        pane.getStyleClass().add("difficultyButton");
        return pane;
    }

    /**
     * @deprecated
     * @param value the dice value.
     * @return a no-colored dice with the indicated value, an empty white image if the value is more than 6 or less than 1.
     */
    @Deprecated
    public static Pane getEmptyDice(int value){
        ImageView result = new ImageView();
        Image image;
        switch(value){
            case 1:
                image=new Image(MainApp.class.getResource("resources/frame/one.png").toString());
                break;
            case 2:
                image=new Image(MainApp.class.getResource("resources/frame/two.png").toString());
                break;
            case 3:
                image=new Image(MainApp.class.getResource("resources/frame/three.png").toString());
                break;
            case 4:
                image=new Image(MainApp.class.getResource("resources/frame/four.png").toString());
                break;
            case 5:
                image=new Image(MainApp.class.getResource("resources/frame/five.png").toString());
                break;
            case 6:
                image=new Image(MainApp.class.getResource("resources/frame/six.png").toString());
                break;
            default:
                image=new Image(MainApp.class.getResource("resources/frame/empty.png").toString());
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

    /**
     * Returns an {@link ImageView} representing a window frame cell with the restriction indicated by the argument.
     * The default cell image, in case the representation char is different from colors' chars (b,g,r,y,p) and values'
     * chars (1,2,3,4,5,6), is the white empty cell: the cell without restrictions for the game logic.
     * @param rep the char indicating the restriction.
     * @return an ImageView representing the window frame cell with requested restriction, or the default one in case the
     * char is not a valid char for window frame string encoding.
     */
    public static ImageView getImage(char rep){
        ImageView result = new ImageView();
        Image image;
        switch(rep){
            case '1':
                image=new Image(MainApp.class.getResource("resources/frame/empty1.png").toString());
                break;
            case '2':
                image=new Image(MainApp.class.getResource("resources/frame/empty2.png").toString());
                break;
            case '3':
                image=new Image(MainApp.class.getResource("resources/frame/empty3.png").toString());
                break;
            case '4':
                image=new Image(MainApp.class.getResource("resources/frame/empty4.png").toString());
                break;
            case '5':
                image=new Image(MainApp.class.getResource("resources/frame/empty5.png").toString());
                break;
            case '6':
                image=new Image(MainApp.class.getResource("resources/frame/empty6.png").toString());
                break;
            case 'b':
                image=new Image(MainApp.class.getResource("resources/frame/emptyblue.png").toString());
                break;
            case 'r':
                image=new Image(MainApp.class.getResource("resources/frame/emptyred.png").toString());
                break;
            case 'y':
                image=new Image(MainApp.class.getResource("resources/frame/emptyyellow.png").toString());
                break;
            case 'p':
                image=new Image(MainApp.class.getResource("resources/frame/emptypurple.png").toString());
                break;
            case 'g':
                image=new Image(MainApp.class.getResource("resources/frame/emptygreen.png").toString());
                break;
            default:
                image=new Image(MainApp.class.getResource("resources/frame/empty.png").toString());
                break;
        }
        result.setImage(image);
        result.setFitHeight(50);
        result.setFitWidth(50);
        result.setY(2);
        result.setX(2);
        return result;
    }

    /**
     * Get method for ImageViews representing the game dices. This method return an image representing the dice with the
     * given value and color. If the color char is different from the color encoding chars (b,y,r,g,p) or the value given
     * is different from the six allowed (1,2,3,4,5,6) <code>null</code> is returned.
     * @param color the dice's color.
     * @param value the dice's value.
     * @return the image view showing the dice's image if the color char is one from b,r,g,p or y and the value int is
     * one from 1,2,3,4,5 or 6, <code>null</code> otherwise.
     */
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
        ImageView result = new ImageView( new Image(MainApp.class.getResource("resources/dices/"+diceColor+dicevalue+".png").toString()));
        result.setFitWidth(50);
        result.setFitHeight(50);
        result.setX(2);
        result.setY(2);
        return result;
    }

    /**
     * Returns a {@link GridPane} representing the empty window frame from the given representation string.
     * @param rep the window frame string representation.
     * @return the GridPane showing the empty window frame represented by the given string.
     */
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
        return pane;
    }

    /**
     * Returns an ImageView with the tool card image with the given index inside a {@link Pane}. The pane is used to show
     * some graphic effects on tool card hover (such as coloring card's border). If the index argument is less than 1 or more
     * than 12, <code>null</code> is returned.
     * @param index the tool card's index.
     * @return the Pane containing the tool card image if the index is a number from 1 to 12
     */
    public static Pane getToolCard(int index){
        String name;
        Image image;
        String path="resources/toolcards/";
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
        image=new Image(MainApp.class.getResource(path).toString());
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

    /**
     * Returns a ImageView with the private objective card image with the given color.
     * @param color the private objective card's color.
     * @return the image view showing the private objective card's image.
     */
    public static ImageView getPrivateObjectiveCard(char color){
        String path = "resources/objectivecards/privateobjectivecards/";
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
        Image image = new Image(MainApp.class.getResource(path).toString());
        ImageView card = new ImageView(image);
        card.setOnMouseEntered((event) -> zoom(card));
        card.setOnMouseExited((event) -> resize(card));
        card.setFitWidth(134);
        card.setFitHeight(198);
        return card;
    }

    public static ImageView getPrivateObjectiveCardEndGame(char color){
        ImageView card = new ImageView(getPrivateObjectiveCard(color).getImage());
        card.setFitWidth(268);
        card.setFitHeight(396);
        card.setX(2);
        card.setY(2);
        return card;
    }

    public static ImageView getPublicObjectiveCard(int card){
        String path = "resources/objectivecards/publicobjectivecards/";
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
        Image image = new Image(MainApp.class.getResource(path).toString());
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
