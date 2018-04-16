package TestGUI.view;

import TestGUI.MainApp;
import TestGUI.model.Model;
import TestGUI.model.boards.Cell;
import TestGUI.model.boards.windowframe.WindowFrameCell;
import TestGUI.model.exceptions.InvalidMoveException;
import TestGUI.model.utilities.CellObserver;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.scene.Cursor;
import javafx.scene.ImageCursor;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;


public class PlayerViewController implements CellObserver {

    private MainApp mainApp;
    private Model model;

    private ImageView[][] grid;
    private ImageView[] pool;

    private Cell picked;

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

    private void updateWindowFrame(WindowFrameCell cell){
        ImageView pic=grid[cell.getX()][cell.getY()];
        pic.setImage(new Image(cell.asImage()));
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
        pic.setOnMouseClicked(e -> draftPoolCellMoved(e));
        pic.setOnMouseDragEntered(e-> System.out.println("drag entered"));
        pic.setOnMouseDragReleased(e -> System.out.println("drag released"));
        /*pic.setOnMouseDragged(e -> System.out.println("draggered"));*/
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
        pic.setOnMouseClicked(e->windowFrameCellMoved(e));
        model.getWindowFrame().getCell(i,j).addObserver(this);
    }

    private void windowFrameCellMoved(MouseEvent e) {
        ImageView pic = (ImageView) e.getSource();
        if(picked==null){
            picked=model.getWindowFrame().getCell(GridPane.getRowIndex(pic), GridPane.getColumnIndex(pic));
            mainApp.setCursor(new ImageCursor(pic.getImage()));
        }
        else{
            Cell target=model.getWindowFrame().getCell(GridPane.getRowIndex(pic), GridPane.getColumnIndex(pic));
            try {
                picked.move(target);
            } catch (InvalidMoveException e1) {
                handleExceprion(e1);
            }
            mainApp.setCursor(Cursor.DEFAULT);
            picked=null;
        }
    }

    private void draftPoolCellMoved(MouseEvent e){
        ImageView pic = (ImageView) e.getSource();
        if(picked==null){
            picked=model.getDraftPool().getDraftPool()[GridPane.getColumnIndex(pic)];
            mainApp.setCursor(new ImageCursor(pic.getImage()));
        }
        else{
            Cell target=model.getDraftPool().getDraftPool()[GridPane.getColumnIndex(pic)];
            try {
                picked.move(target);
            } catch (InvalidMoveException e1) {
                handleExceprion(e1);
            }
            mainApp.setCursor(Cursor.DEFAULT);
            picked=null;
        }
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
}
