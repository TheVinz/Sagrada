package TestGUI.client.view;

import TestGUI.MainApp;
import TestGUI.client.toolcards.ToolCard;
import TestGUI.common.Command;
import TestGUI.common.remotemvc.RemoteController;
import TestGUI.server.model.exceptions.InvalidMoveException;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.scene.Cursor;
import javafx.scene.ImageCursor;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;


public class PlayerViewController{

    private MainApp mainApp;
    private ControllerProxy controller;

    private ImageView[][] grid;
    private ImageView[] pool;

    private ImageView picked;

    private ToolCard[] toolCards;
    private int toolCardParameters;
    private ToolCard activeToolCard;

    @FXML
    private Label text;
    @FXML
    private Button toolCard1;
    @FXML
    private Button toolCard2;
    @FXML
    private Button toolCard3;
    @FXML
    private GridPane gridPane;
    @FXML
    private GridPane poolPane;
    @FXML
    private void initialize(){
        activeToolCard=null;
        grid=new ImageView[4][5];
        pool=new ImageView[5];
        gridPane.setCursor(gridPane.getParent().getCursor());
        poolPane.setCursor(gridPane.getCursor());
    }


    public void setToolCards(ToolCard[] toolCards) {
        this.toolCards=toolCards;
        toolCard1.setText(toolCards[0].getName());
    }

    public void setMainApp(MainApp mainApp){
        this.mainApp=mainApp;
    }

    public void bindController(RemoteController controller){
        this.controller=new ControllerProxy(controller);
        for(int i=0; i<4; i++){
            for(int j=0; j<5; j++){
                addPic(i,j);
            }
        }
        for(int i=0; i<5; i++){
            addPic(i);
        }
    }

    private void addPic(int i) {
        pool[i]=new ImageView();
        ImageView pic=pool[i];
        pic.setFitHeight(50);
        pic.setFitWidth(50);
        pic.setImage(new Image("File:/resources/icons/void.png"));
        GridPane.setHalignment(pic, HPos.CENTER);
        poolPane.add(pic, i, 0);
        pic.setCursor(gridPane.getCursor());
        pic.setOnMouseClicked(e -> {
            if(e.getButton()==MouseButton.PRIMARY) draftPoolCellClicked(e);
            else endMove(); });
    }

    private void addPic(int i, int j) {
        grid[i][j]=new ImageView();
        ImageView pic=grid[i][j];
        pic.setFitWidth(50);
        pic.setFitHeight(50);
        pic.setImage(new Image("File:resources/icons/void.png"));
        gridPane.add(pic, j, i);
        GridPane.setHalignment(pic, HPos.CENTER);
        pic.setCursor(gridPane.getCursor());
        pic.setOnMouseClicked(e->{
            if(e.getButton()==MouseButton.PRIMARY) windowFrameCellClicked(e);
            else endMove();
        });
    }

    private void windowFrameCellClicked(MouseEvent e) {
        ImageView pic = (ImageView) e.getSource();
        if (activeToolCard!=null) {
            if(picked==null) {
                controller.sendCommand(new Command(Command.WINDOW_FRAME_CLICK, GridPane.getRowIndex(pic), GridPane.getColumnIndex(pic)));
                toolCardParameters--;
                text.setText(activeToolCard.nextMessage());
                if(toolCardParameters==0) flush();
                pickFromWindow(pic);
            }
            else{
                controller.sendCommand(new Command(Command.WINDOW_FRAME_CLICK, GridPane.getRowIndex(pic), GridPane.getColumnIndex(pic)));
                toolCardParameters--;
                text.setText(activeToolCard.nextMessage());
                if(toolCardParameters==0) flush();
            }
        }
        else if (picked != null) {
            controller.sendCommand(new Command(Command.WINDOW_FRAME_CLICK, GridPane.getRowIndex(pic), GridPane.getColumnIndex(pic)));
            flush();
        }
    }

    private void draftPoolCellClicked(MouseEvent e){
        ImageView pic = (ImageView) e.getSource();
        if (activeToolCard!=null) {
            if( picked==null){
                controller.sendCommand(new Command(Command.DRAFTPOOL_CLICK, GridPane.getColumnIndex(pic)));
                toolCardParameters--;
                text.setText(activeToolCard.nextMessage());
                if(toolCardParameters==0) flush();
                pickFromDraftPool(pic);
            }
            else{
                controller.sendCommand(new Command(Command.DRAFTPOOL_CLICK, GridPane.getColumnIndex(pic)));
                toolCardParameters--;
                text.setText(activeToolCard.nextMessage());
                if(toolCardParameters==0) flush();
                endMove();
                flush();
            }
        } else if (picked == null) {
            controller.sendCommand(new Command(Command.DRAFTPOOL_CLICK, GridPane.getColumnIndex(pic)));
            pickFromDraftPool(pic);
        }
    }

    private void pickFromWindow(ImageView pic) {
        picked=pic;
        mainApp.setCursor(new ImageCursor(picked.getImage()));
    }

    private void pickFromDraftPool(ImageView pic) {
        picked=pic;
        mainApp.setCursor(new ImageCursor(picked.getImage()));
    }

    private void endMove(){
        picked=null;
        mainApp.setCursor(Cursor.DEFAULT);
        activeToolCard=null;
    }

    public void refillDraftPool(String[] dices) {
        for(int i=0; i<dices.length; i++){
            pool[i].setImage(new Image(dices[i]));
        }
    }

    public void moveFromDPtoWF(int index, int row, int column){
        Image pic=pool[index].getImage();
        pool[index].setImage(null);
        grid[row][column].setImage(pic);
        endMove();
    }


    public void moveFromWFtoWF(int sourceRow, int sourceColumn, int targetRow, int targetColumn) {
        Image pic=grid[sourceRow][sourceColumn].getImage();
        grid[sourceRow][sourceColumn].setImage(new Image("File:resources/icons/void.png"));
        grid[targetRow][targetColumn].setImage(pic);
        endMove();
    }

    public void updateWindowFrameCell(int row, int column, String image) {
        grid[row][column].setImage(new Image(image));
        endMove();
    }

    public void updateDraftPoolCell(int index, String image){
        pool[index].setImage(new Image(image));
        endMove();
    }

    private void handleExceprion(InvalidMoveException e) {
        text.setText(e.getMessage());
        endMove();
    }

    private void flush() {
        try {
            controller.flush();
        } catch (InvalidMoveException e) {
            handleExceprion(e);
        }
    }


    @FXML
    private void drawDraftPool(){
        try {
            controller.sendCommand(new Command(Command.REFILL_DRAFTPOOL));
            controller.flush();
        } catch (InvalidMoveException e) {
            handleExceprion(e);
        }
    }

    @FXML
    private void randomClicks(MouseEvent e){
        if(e.getButton()==MouseButton.SECONDARY) endMove();
    }

    @FXML
    private void useToolCard1(){
        activeToolCard=toolCards[0];
        activeToolCard.start();
        toolCardParameters=activeToolCard.getNumParameters();
        text.setText(activeToolCard.nextMessage());
        controller.sendCommand(new Command(Command.USE_TOOL_CARD, 0));
    }
    @FXML
    private void useToolCard2(){
        activeToolCard=toolCards[1];
        controller.sendCommand(new Command(Command.USE_TOOL_CARD, 1));
    }

}
