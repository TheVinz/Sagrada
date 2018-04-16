package TestGUI.view;

import TestGUI.MainApp;
import TestGUI.model.Model;
import TestGUI.model.exceptions.InvalidMoveException;
import TestGUI.model.utilities.CellObserver;
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


public class PlayerViewController implements CellObserver {

    private MainApp mainApp;
    private Model model;

    private ImageView[][] grid;
    private ImageView[] pool;

    private Image picked;

    @FXML
    private GridPane gridPane;
    @FXML
    private GridPane poolPane;
    @FXML
    private void initialize(){
        grid=new ImageView[4][5];
        pool=new ImageView[5];
        gridPane.setCursor(gridPane.getParent().getCursor());
        poolPane.setCursor(gridPane.getCursor());
    }

    public void setMainApp(MainApp mainApp){
        this.mainApp=mainApp;
        model=mainApp.getModel();
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
        pic.setImage(new Image(model.getDraftPool().getDraftPool()[i].asImage()));
        GridPane.setHalignment(pic, HPos.CENTER);
        poolPane.add(pic, i, 0);
        pic.setCursor(gridPane.getCursor());
        pic.setOnMouseClicked(e -> {
            if(e.getButton()==MouseButton.PRIMARY) draftPoolCellMoved(e);
            else cancelMove(); });
        pic.setOnMouseDragEntered(e-> System.out.println("drag entered"));
        pic.setOnMouseDragReleased(e -> System.out.println("drag released"));
        model.getDraftPool().getDraftPool()[i].addObserver(this);
    }

    private void addPic(int i, int j) {
        grid[i][j]=new ImageView();
        ImageView pic=grid[i][j];
        pic.setFitWidth(50);
        pic.setFitHeight(50);
        pic.setImage(new Image(model.getWindowFrame().getCell(i,j).asImage()));
        gridPane.add(pic, j, i);
        GridPane.setHalignment(pic, HPos.CENTER);
        pic.setCursor(gridPane.getCursor());
        pic.setOnMouseClicked(e->{
            if(e.getButton()==MouseButton.PRIMARY) windowFrameCellMoved(e);
            else cancelMove();
        });
        model.getWindowFrame().getCell(i,j).addObserver(this);
    }

    private void windowFrameCellMoved(MouseEvent e) {
        ImageView pic = (ImageView) e.getSource();
        if(picked==null){
            pickFromWindow(pic);
        }
        else{
            try {
                model.moveToWindow(GridPane.getRowIndex(pic), GridPane.getColumnIndex(pic));
            } catch (InvalidMoveException e1) {
                handleExceprion(e1);
            }
            finally{
                mainApp.setCursor(Cursor.DEFAULT);
                picked=null;
            }
        }
    }

    private void pickFromWindow(ImageView pic) {
        picked=pic.getImage();
        mainApp.setCursor(new ImageCursor(picked));
        model.pickFromWindow(GridPane.getRowIndex(pic),GridPane.getColumnIndex(pic));
    }

    private void draftPoolCellMoved(MouseEvent e){
        ImageView pic = (ImageView) e.getSource();
        if(picked==null){
            pickFromDraftPool(pic);
        }
        else try {
            model.moveToDraftPool(GridPane.getColumnIndex(pic));
        } catch (InvalidMoveException e1) {
            handleExceprion(e1);
        }
        finally{
            mainApp.setCursor(Cursor.DEFAULT);
            picked=null;
        }
    }


    private void cancelMove(){
        picked=null;
        model.cancelMove();
        mainApp.setCursor(Cursor.DEFAULT);
    }

    private void pickFromDraftPool(ImageView pic) {
        picked=pic.getImage();
        mainApp.setCursor(new ImageCursor(picked));
        model.pickFromDraftPool(GridPane.getColumnIndex(pic));
    }

    private void handleExceprion(InvalidMoveException e1) {
        Alert alert=new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Invalid move");
        alert.setHeaderText(e1.getMessage());
        alert.showAndWait();
    }

    @Override
    public void updateWindowFrameCell(int row, int column) {
        grid[row][column].setImage(new Image(model.getWindowFrame().getCell(row, column).asImage()));
    }

    @Override
    public void updateDraftPoolCell(int index) {
        pool[index].setImage(new Image(model.getDraftPool().getDraftPool()[index].asImage()));
    }

    @FXML
    private void drawDraftPool(){
        new Thread(() -> model.drawDraftPool()).start();
    }

    @FXML
    private void randomClicks(MouseEvent e){
        if(e.getButton()==MouseButton.SECONDARY) cancelMove();
    }

}
