package client.view.gui.util;

import javafx.geometry.Pos;
import javafx.scene.Node;
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
            cell.setStyle("-fx-background-color: black;");
            cell.getChildren().add(image);
            pane.add(cell, col, row);
        }
        pane.setAlignment(Pos.CENTER);
        Reflection reflection = new Reflection();
        reflection.setFraction(0.7f);
        pane.setEffect(reflection);
        return pane;
    }

}
