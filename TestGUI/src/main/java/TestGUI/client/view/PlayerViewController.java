package TestGUI.client.view;

import TestGUI.MainApp;
import TestGUI.common.Command;
import TestGUI.common.Observer;
import TestGUI.common.viewchangement.Changement;
import TestGUI.common.viewchangement.ChangementTypes;
import TestGUI.common.viewchangement.Move;
import TestGUI.common.viewchangement.RefilledDraftPool;
import TestGUI.server.Controller;
import TestGUI.server.model.exceptions.InvalidMoveException;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.scene.Cursor;
import javafx.scene.ImageCursor;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;


public class PlayerViewController implements Observer {

    private MainApp mainApp;
    private Controller controller;

    private ImageView[][] grid;
    private ImageView[] pool;

    private ImageView picked;

    private boolean activeToolCard;

    @FXML
    private GridPane gridPane;
    @FXML
    private GridPane poolPane;
    @FXML
    private void initialize(){
        activeToolCard=false;
        grid=new ImageView[4][5];
        pool=new ImageView[5];
        gridPane.setCursor(gridPane.getParent().getCursor());
        poolPane.setCursor(gridPane.getCursor());
    }

    public void set(MainApp mainApp, Controller controller){
        this.setController(controller);
        this.mainApp=mainApp;
        for(int i=0; i<4; i++){
            for(int j=0; j<5; j++){
                addPic(i,j);
            }
        }
        for(int i=0; i<5; i++){ ;
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
        if(picked!=null){
            try {
                controller.sendCommand(new Command(GridPane.getRowIndex(pic), GridPane.getColumnIndex(pic)));
            } catch (InvalidMoveException e1) {
                handleExceprion(e1);
            }
            endMove();
        }
    }

    private void draftPoolCellClicked(MouseEvent e){
        ImageView pic = (ImageView) e.getSource();
        if(picked==null){
            pickFromDraftPool(pic);
            try {
                controller.sendCommand(new Command(GridPane.getColumnIndex(pic)));
            } catch (InvalidMoveException e1) {
                handleExceprion(e1);
            }
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
    }


    private void handleExceprion(InvalidMoveException e1) {
        Alert alert=new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Invalid move");
        alert.setHeaderText(e1.getMessage());
        alert.showAndWait();
    }

    private void handleMoveChangement(Move move){
        switch(move.getMoveType()){
            case Move.FROM_DP_TO_WF:
                Image pic = pool[move.getSourceX()].getImage();
                grid[move.getTargetX()][move.getTargetY()].setImage(pic);
                pool[move.getSourceX()].setImage(null);
        }
    }

    private void handleRefilledDraftPool(RefilledDraftPool refil){
        String[] dices=refil.getDices();
        for(int i=0; i<dices.length; i++){
            pool[i].setImage(new Image(dices[i]));
        }
    }

    @FXML
    private void drawDraftPool(){
        try {
            controller.sendCommand(new Command());
        } catch (InvalidMoveException e) {
            handleExceprion(e);
        }
    }

    @FXML
    private void randomClicks(MouseEvent e){
        if(e.getButton()==MouseButton.SECONDARY) endMove();
    }

    @FXML
    private void useToolCard(){
        activeToolCard=true;
    }

    public void setController(Controller controller) {
        this.controller=controller;
    }

    @Override
    public void update() {

    }

    @Override
    public void update(Changement change) {
        switch(change.getType()){
            case ChangementTypes.MOVE:
                handleMoveChangement((Move) change);
                break;
            case ChangementTypes.REFILLED_DRAFT_POOL:
                handleRefilledDraftPool((RefilledDraftPool) change);
                break;
            default:
                break;
        }
    }

}
