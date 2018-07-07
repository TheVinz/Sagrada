package client.view.gui.guicontroller;

import client.view.gui.guicontroller.gamephase.DiluentePerPastaSaldaChoicePhase;
import client.view.gui.MainApp;
import client.view.gui.guicontroller.gamephase.*;
import client.view.gui.util.Util;
import common.command.GameCommand;
import common.response.Response;
import common.RemoteMVC.RemoteController;
import javafx.application.Platform;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

/**
 * The <tt>ViewController</tt> class represents the main GUI controller of the root layout,
 * a {@link BorderPane} described in the RootLayout.fxml file in the fxml resource directory. This class also contains
 * all the methods called by the {@link client.view.gui.guimodel.GuiModel}.
 */
public class ViewController {

    private RemoteController remoteController;
    private GameController gameController;
    private LoginController loginController;


    private AnchorPane gamePane;
    private AnchorPane windowFrameChoicesPane;
    private WindowFrameChoiceController windowFrameChoiceController;
    private int id;
    private GamePhase currentPhase;
    private Stage dialog;

    @FXML
    private BorderPane rootLayout;

    /**
     * Initializes the {@link WindowFrameChoiceController} and the {@link LoginController}, setting it as center of the root layout.
     */
    public void init() {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(MainApp.class.getResource("resources/fxml/Login.fxml"));
        try {
            AnchorPane pane= loader.load();
            rootLayout.setCenter(pane);
            loginController = loader.getController();
            Image background = new Image(MainApp.class.getResource("resources/style/background.jpg").toString());
            BackgroundImage backgroundImage = new BackgroundImage(background, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, new BackgroundSize(0,0,false,false,false,true));
            rootLayout.setBackground(new Background(backgroundImage));
            loginController.addListener(this);
            loader=new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("resources/fxml/WindowFrameChoice.fxml"));
            windowFrameChoicesPane = loader.load();
            windowFrameChoiceController = loader.getController();
            windowFrameChoiceController.addListener(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Shows the difficulty choice popup for single-player games.
     */
    public synchronized void choseDifficulty(){
        dialog = new Stage();
        dialog.setTitle("Difficulty Choice");
        dialog.initStyle(StageStyle.UNDECORATED);
        dialog.setAlwaysOnTop(true);
        Platform.setImplicitExit(false);
        dialog.setOnCloseRequest(Event::consume);
        dialog.initModality(Modality.APPLICATION_MODAL);
        VBox dialogBox = new VBox(10);
        dialogBox.setStyle("-fx-background-color: rgba(255,255,255,0.4)");
        dialogBox.setAlignment(Pos.CENTER);
        Label title = new Label("Difficulty: ");
        title.setStyle("-fx-font: 22 bold;");
        dialogBox.getChildren().add(title);
        HBox buttonsBox = new HBox(10);
        buttonsBox.setAlignment(Pos.CENTER);
        for(int i=5; i>0; i--){
            int difficulty = i;
            Pane button = Util.getDifficultyButton(difficulty);
            button.setOnMouseClicked((event) -> {
                try {
                    remoteController.command(new GameCommand(Response.CHOICE, difficulty));
                } catch (IOException e) {
                    handleIOException();
                }
                dialog.close();
            });
            buttonsBox.getChildren().add(button);
        }
        dialogBox.getChildren().add(buttonsBox);
        Scene scene = new Scene(dialogBox,700, 150);
        scene.getStylesheets().add(MainApp.class.getResource("resources/style/main.css").toString());
        dialog.setScene(scene);
        dialog.showAndWait();
    }

    /**
     * Binds the {@link RemoteController} for the server communication and initializes the {@link GameController} loading
     * the single-player game screen (SinglePlayerGame.fxml) if singleplayer is <code>true</code>, or the single-player game
     * (Game.fxml) otherwise.
     * @param remoteController the RemoteController for sending {@link GameCommand}s to the server.
     * @param singleplayer the user's game choice, <code>true</code> for single-player games, <code>false</code> for multi-player.
     */
    void notifyLogin(RemoteController remoteController, boolean singleplayer){
        this.remoteController=remoteController;
        FXMLLoader loader=new FXMLLoader();
        if(!singleplayer)
            loader.setLocation(MainApp.class.getResource("resources/fxml/Game.fxml"));
        else
            loader.setLocation(MainApp.class.getResource("resources/fxml/SinglePlayer.fxml"));
        try {
            gamePane=loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        gameController=loader.getController();
        gameController.addListener(this);
    }

    /**
     * Shows the disconnected player info {@link Alert}.
     * @param id the disconnected player's id.
     */
    public void notifyPlayerDisconnected(int id){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Disconnected");
        alert.setHeaderText(gameController.getPlayerName(id) + " disconnected.");
        alert.showAndWait();
    }

    /**
     * Shows the reconnected player info Alert.
     * @param id the reconnected player's id.
     */
    public void notifyPlayerReconnected(int id){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Reconnected");
        alert.setHeaderText(gameController.getPlayerName(id) + " reconnected.");
        alert.showAndWait();
    }

    /**
     * Shows the window frame choice screen.
     * @param reps the schemes string representations.
     * @param tokens the schemes tokens.
     *
     * @see server.model.state.boards.windowframe.WindowFrame
     */
    public void loadWindowFrameChoice(String[] reps, int[] tokens){
        setGameBackground();
        rootLayout.setCenter(windowFrameChoicesPane);
        windowFrameChoiceController.setChoice(reps, tokens);
    }

    void show(Node node){
        rootLayout.setCenter(node);
    }

    /**
     * Sets the main game background.
     */
    public void setGameBackground(){
        Image background = new Image(MainApp.class.getResource("resources/style/gamebackground.jpg").toString());
        BackgroundImage backgroundImage = new BackgroundImage(background, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, new BackgroundSize(0,0,false,false,false,true));
        rootLayout.setBackground(new Background(backgroundImage));
    }

    /**
     * Shows and set active the main game window loaded by the NotifyLogin method.
     */
    public void startGame(){
        rootLayout.setCenter(gamePane);
    }

    /**
     * Loads all the players' names, identifiers, window frames and tokens and save the id representing this user in the server.
     * @param names the players' names.
     * @param ids the players' identifiers.
     * @param windowFrameReps the players' frames string representations.
     * @param windowFrameFavorTokens the player's favor tokens.
     * @param id the identifier representing the user in the server's {@link server.model.Model}.
     *
     * @see server.model.state.player.Player
     */
    public void loadPlayers(String[] names, int[] ids, String[] windowFrameReps, int[] windowFrameFavorTokens, int id) {
        this.id=id;
        for(int i=0; i<names.length; i++){
            if(ids[i]==id)
                gameController.setActiveFrame(names[i], ids[i], windowFrameReps[i], windowFrameFavorTokens[i]);
            else {
                gameController.loadPlayer(names[i], ids[i], windowFrameReps[i], windowFrameFavorTokens[i]);
            }
        }
    }

    void notifyChoice(int index) {
        try {
            remoteController.command(new GameCommand(Response.CHOICE, index));
        } catch (IOException e) {
            handleIOException();
        }
    }

    /**
     * Loads the tool cards drawn at the beginning of the game.
     * @param toolCards the tool cards' identifiers array.
     *
     * @see server.model.state.toolcards.ToolCard
     */
    public void loadToolCard(int[] toolCards) {
        for(int i=0; i<toolCards.length; i++){
            gameController.loadToolCard(toolCards[i], i);
        }
    }

    /**
     * Loads a private objective card drawn for the user at the beginning of the game.
     * @param color the color representing the private objective card.
     *
     * @see server.model.state.objectivecards.privateobjectivecards.PrivateObjectiveCard
     */
    public void loadPrivateObjectiveCard(char color) {
        windowFrameChoiceController.setPrivateObjectiveCard(color);
        gameController.setPrivateObjectiveCard(color);
    }

    /**
     * Loads a new set of dices representing the draft pool.
     * @param values the dices' values array.
     * @param colors the dices' colors array.
     *
     * @see server.model.state.boards.draftpool.DraftPool
     * @see server.model.state.dice.Dice
     */
    public void setDraftPool(int[] values, char[] colors) {
        gameController.cleanDraftPool();
        for(int i=0; i<values.length; i++)
            gameController.setDraftPoolDice(i, values[i], colors[i]);
    }

    /**
     * Loads all the dices placed in a player's window frame.
     * @param id the player's identifier.
     * @param values the dices' values matrix. If a dice value is equals to <code>0</code> then an empty cell image is
     *               loaded from the player's window frame string representation.
     * @param colors the dice's colors matrix.
     *
     * @see server.model.state.boards.windowframe.WindowFrame
     */
    public void setWindowFrameDices(int id, int[][] values, char[][] colors){
        for(int i=0; i<values.length; i++){
            for(int j=0; j<values[i].length; j++){
                if(values[i][j] >= 1 && values[i][j]<=6) {
                    ImageView dice = Util.getImage(colors[i][j], values[i][j]);
                    gameController.setFromWindowFrame(id, i, j, dice);
                }
            }
        }
    }

    /**
     * Loads the public objective cards drawn at the beginning of the game.
     * @param cards the pubic objective cards' identifiers array.
     */
    public void setPublicObjectiveCards(int[] cards) {
        gameController.setPublicObjectiveCards(cards);
    }

    /**
     * Notifies the GameController of a dice move. The parameters sourceType and destType defines the king of move performed
     * by the player, and depending to them the three ints (param1, param2 and param3) may represent different coordinates :
     * <ul>
     *     <li>draft pool to window frame, param1 is the pool dice's index and param2 and param3 are the destination cell's
     *     row and column;</li>
     *     <li>draft pool to round track, param1 is the pool dice's index and param2 and param3 are round track dice's round
     *     and position in the round dices array.</li>
     * </ul>
     * The player is also supposed to be the owner of a possible window frame target. In case of move made without any tool
     * card's ability, a message is shown in the bottom GameController's {@link Label}.
     * @param player the player who performed the moved.
     * @param sourceType the type of the move's source cell (only draft pool type is allowed here).
     * @param destType the type of the move's target cell (only window frame and round track are allowed here).
     * @param param1 the first coordinate for identify a cell.
     * @param param2 the second coordinate for identify a cell.
     * @param param3 the third coordinate for identify a cell.
     */
    public void move(int player, Response sourceType, Response destType, int param1, int param2, int param3) {
        ImageView source;
        String message = gameController.getPlayerName(player) + " moved a dice from ";
        if(sourceType==Response.DRAFT_POOL_CELL) {
            source = gameController.getFromDraftPool(param1);
            message = message + "draft pool to";
        }
        else return;
        if(destType == Response.WINDOW_FRAME_CELL) {
            gameController.setFromWindowFrame(player, param2, param3, source);
            message = message + "window frame.\n";
        }
        else if(destType == Response.ROUND_TRACK_CELL){
            gameController.setFromRoundTrack(param2, param3, source);
            message = null;
        }
        else return;
        if(message!= null) gameController.log(message);
    }

    /**
     * Notifies the GameController of a dice move. The parameters sourceType and destType defines the king of move performed
     * by the player, and depending to them the four ints (param1, param2, param3 and param 4) may represent different coordinates.
     * In this method only window frame cell's are allowed as source and target because only the window frame to window
     * frame move requires four parameters to be identified so the four parameters can only identify source and target raw and
     * column. The player is also supposed to be the owner of a the window frame.
     * @param player the player who performed the move.
     * @param sourceType the type of the source cell (only window frame allowed here).
     * @param destType the type of the target cell (only window frame allowed here).
     * @param param1 the first coordinate for identify a cell.
     * @param param2 the second coordinate for identify a cell.
     * @param param3 the third coordinate for identify a cell.
     * @param param4 the fourth coordinate for identify a cell.
     */
    public void move(int player, Response sourceType, Response destType, int param1, int param2, int param3, int param4) {
        ImageView source;
        if(sourceType == Response.WINDOW_FRAME_CELL){
            source = gameController.getFromWindowFrame(player, param1, param2);
        }
        else return;
        if(destType == Response.WINDOW_FRAME_CELL){
            gameController.setFromWindowFrame(player, param3, param4, source);
        }
    }

    /**
     * Remove the indexed dice from the draft pool.
     * @param index the draft pool dice's index.
     */
    public void removeDraftPoolDice(int index){
        gameController.getFromDraftPool(index);
    }

    /**
     * Notifies the user of a successfully tool card's ability performed by a player, logging the action in the bottom
     * GameController's label. Also the player's tokens are decreased of the indicated amount (multi-player games only).
     * @param player the player who used the tool card's ability.
     * @param index the tool card's index.
     * @param tokens the number of tokens the player consumed in order to perform the ability.
     */
    public void toolCardUsed(int player, int index, int tokens) {
        String message;
        if(player == id) {
            message = "You ";
        }
        else
            message = gameController.getPlayerName(player) + " ";
        message = message + "used "+gameController.getToolCardName(index) +  "\n";
        gameController.log(message);
        if(loginController.isSingleplayer())
            gameController.removeToolCard(index);
        else
            gameController.decreaseFavorTokens(player, tokens);
    }

    /**
     * Notifies a dice draw performed by a player thanks to the "Diluente Per Pasta Salda" card's ability. This notification
     * only indicates the color of the dice because the player can then set the dice value himself. In case this player
     * corresponds to the user a new {@link DiluentePerPastaSaldaChoicePhase} is set as current phase and his handle choice
     * method is called. A message is logged in the bottom GameController's Label.
     * @param player the player who performed the draw.
     * @param color the drawn dice's color.
     */
    public void notifyDiceDraw(int player, char color) {
        if(this.id==player)
            DiluentePerPastaSaldaChoicePhase.color=color;
        else{
            String message = gameController.getPlayerName(player) + " drawed a ";
            switch(color){
                case 'b':
                    message = message + "blue ";
                    break;
                case 'r':
                    message = message + "red ";
                    break;
                case 'y':
                    message = message + "yellow ";
                    break;
                case 'p':
                    message = message + "purple ";
                    break;
                case 'g':
                    message = message + "green ";
                    break;
                default:
                    return;
            }
            message = message + "dice\n";
            gameController.log(message);
        }
    }

    /**
     * Adds an array of dices to the indicated round track cell.
     * @param round the round of the target round track cell.
     * @param values the dices' values array.
     * @param colors the dices' colors array.
     */
    public void updateRoundTrack(int round, int[] values, char[] colors) {
        gameController.addRoundTrackBox(round, values, colors);
        gameController.setRound(round);
    }

    /**
     * Notifies a draft pool dice change.
     * @param player the player who performed the change.
     * @param type the type of cell (only draft pool allowed here).
     * @param index the dice's index in the draft pool dices array.
     * @param value the new dice's value.
     * @param color the new dice's color.
     */
    public void updateCell(int player, Response type, int index, int value, char color) {
        if(type == Response.DRAFT_POOL_CELL){
            gameController.updateDraftPool(index, value, color);
        }
    }

    /**
     * Notifies the beginning of a new turn and the player that will be the active player from now on. In case the active player
     * corresponds to the user, then a new {@link MainPhase} is set as current phase and the player is allowed to move dices
     * from draft pool to window frame or use tool cards' ability. Also a message is logged in the bottom GameContoller's Label.
     * @param id the new active player's id.
     */
    public synchronized void startTurn(int id) {
        if(id==this.id) {
            gameController.log("Is your turn!!\n");
            GamePhase.unsetDiceMoved();
            GamePhase.unsetToolCardUsed();
            this.currentPhase=new MainPhase(remoteController, gameController);
        }
        else {
            gameController.log("Is " + gameController.getPlayerName(id) + " turn!!\n");
            currentPhase= new GamePhase(remoteController, gameController);
        }
    }

    /**
     * Handles the server response caused by a player input. Depending to the response this method can:
     * <ul>
     *     <li>require the user to move a dice from the draft pool to his window frame;</li>
     *     <li>require the user to move a dice inside hi window frame;</li>
     *     <li>require the user to select a dice from the draft pool;</li>
     *     <li>require the user to select a cell from his window frame;</li>
     *     <li>require the user to select a dice from the round track;</li>
     *     <li>require the user to select which "Pinza Sgrossatrice" card effect perform;</li>
     *     <li>require the user to select which "Taglierina Manuale" card effect perform;</li>
     *     <li>require the user to select which value set to a drawn dice;</li>
     *     <li>set the player state in the {@link GamePhase} class to "dice moved";</li>
     *     <li>set the player state in the GamePhase class to "tool card used";</li>
     *     <li>allow the user to decide if move a dice from the draft pool to his window frame, use a tool card's ability
     *     or end his turn.</li>
     * </ul>
     * The management of all of this different cases is realized by the GamePhases of the gamephase package.
     * @param response the server response.
     *
     */
    public synchronized void handleResponse(Response response) {
        switch(response){
            case DRAFT_POOL_MOVE:
                currentPhase=new MovingDraftPoolPhase(remoteController, gameController);
                break;
            case WINDOW_FRAME_MOVE:
                currentPhase=new MovingWindowFramePhase(remoteController, gameController);
                break;
            case DRAFT_POOL_CELL:
                currentPhase=new DraftPoolPhase(remoteController, gameController);
                break;
            case WINDOW_FRAME:
                currentPhase=new WindowFramePhase(remoteController, gameController);
                break;
            case ROUND_TRACK_CELL:
                currentPhase=new RoundTrackPhase(remoteController, gameController);
                break;
            case PINZA_SGROSSATRICE_CHOICE:
                currentPhase = new PinzaSgrossatriceChoicePhase(remoteController, gameController);
                try {
                    currentPhase = currentPhase.handleChoice();
                } catch (IOException e) {
                    handleIOException();
                }
                break;
            case TAGLIERINA_MANUALE_CHOICE:
                currentPhase = new TaglierinaManualeChoicePhase(remoteController, gameController);
                try {
                    currentPhase = currentPhase.handleChoice();
                } catch (IOException e) {
                    handleIOException();
                }
                break;
            case DILUENTE_PER_PASTA_SALDA_CHOICE:
                currentPhase = new DiluentePerPastaSaldaChoicePhase(remoteController, gameController);
                try {
                    currentPhase = currentPhase.handleChoice();
                } catch (IOException e) {
                    handleIOException();
                }
                break;
            case SUCCESS_MOVE_DONE:
                GamePhase.setDiceMoved();
                currentPhase = new MainPhase(remoteController, gameController);
                break;
            case SUCCESS_USED_TOOL_CARD:
                GamePhase.setToolCardUsed();
                currentPhase = new MainPhase(remoteController, gameController);
                break;
            case SUCCESS_TOOL_CARD_WITH_MOVE:
                GamePhase.setToolCardUsed();
                GamePhase.setDiceMoved();
                currentPhase = new MainPhase(remoteController, gameController);
                break;


            default:
                currentPhase=new MainPhase(remoteController, gameController);
                break;
        }
    }

    synchronized void handleIOException() {

        Platform.runLater(() ->{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("You have been disconnected");
            alert.showAndWait();

            if(currentPhase != null)
                currentPhase.close();
            if(dialog != null)
                dialog.close();
            init();
        });

    }

    synchronized void roundTrackClick(int round, int index) {
        try {
            currentPhase=currentPhase.handleRoundTrack(round, index);
        } catch (IOException e) {
            handleIOException();
        }
    }

    synchronized void toolCardClick(int index) {
        try {
            currentPhase=currentPhase.handleToolCard(index);
        } catch (IOException e) {
            handleIOException();
        }
    }

    synchronized void draftPoolClick(int index){
        try {
            currentPhase=currentPhase.handleDraftPool(index);
        } catch (IOException e) {
            handleIOException();
        }
    }

    synchronized void windowFrameClick(int row, int col){
        try {
            currentPhase=currentPhase.handleWindowFrame(row,col);
        } catch (IOException e) {
            handleIOException();
        }
    }


    synchronized void endTurn() {
        try {
            remoteController.command(new GameCommand(Response.END_TURN));
        } catch (IOException e) {
            handleIOException();
        }
        currentPhase=new GamePhase(remoteController, gameController);
    }

    /**
     * Requires the user to choose which private objective card should be used to calculate his points at the end of a single-player game.
     * @param card1 the first private card's color.
     * @param card2 the second private objective card's color.
     */
    public void notifyPrivateObjectiveCardChoice(char card1, char card2){
        HBox box = new HBox();
        box.setAlignment(Pos.CENTER);
        Pane first = new Pane();
        first.getStyleClass().add("end_game_card");
        first.getChildren().add(Util.getPrivateObjectiveCardEndGame(card1));
        first.setOnMouseClicked((mouseEvent -> {
            try {
                remoteController.command(new GameCommand(Response.CHOICE,0));
            } catch (IOException e) {
                handleIOException();
            }
        }));
        Pane second = new Pane();
        second.setPrefHeight(400);
        second.setPrefWidth(272);
        second.getStyleClass().add("end_game_card");
        second.getChildren().add(Util.getPrivateObjectiveCardEndGame(card2));
        second.setOnMouseClicked((mouseEvent -> {
            try {
                remoteController.command(new GameCommand(Response.CHOICE,1));
            } catch (IOException e) {
                handleIOException();
            }
        }));
        box.getChildren().addAll(first, second);
        rootLayout.setCenter(box);
    }

    /**
     * Initializes and shows the single-player end game screen.
     * @param card the chosen private objective card for calculating points.
     * @param points the array representing the points the user scored.
     * @param targetPoints the points the user needs for win the game.
     */
    public synchronized void endSinglePlayerGame(char card, int[] points, int targetPoints){

        String style = "-fx-background-color: rgba(0,0,0,0.7); -fx-text-fill: white; -fx-font-size: 26";

        HBox box = new HBox(20);
        box.setAlignment(Pos.CENTER);

        VBox frameBox = new VBox(10);
        frameBox.setAlignment(Pos.CENTER);
        Label targetPointsLabel = new Label("Target points: " + targetPoints);
        targetPointsLabel.setStyle(style);
        ImageView privateCard = Util.getPrivateObjectiveCardEndGame(card);
        frameBox.getChildren().addAll(privateCard, gameController.getPlayerFrame(this.id), targetPointsLabel);

        VBox pointsBox = new VBox(10);
        pointsBox.setAlignment(Pos.CENTER);

        int publicPoints = points[0]+points[1];
        Label publicCardPoints = new Label("Public objective cards points: " + publicPoints);
        Label privateCardPoints = new Label("Private objective card points: " + points[2]);
        Label emptyCellMalus = new Label("Empty cells malus: " + points[3]);
        Label total = new Label("Total: " + points[4]);

        publicCardPoints.setStyle(style);
        privateCardPoints.setStyle(style);
        emptyCellMalus.setStyle(style);
        total.setStyle(style);

        String result;

        if(points[4]>targetPoints)
            result = "You win!";
        else
            result="You lose";

        Label resultLabel = new Label(result);
        resultLabel.setStyle("-fx-background-color: rgba(255, 165, 0, 0.9); -fx-text-fill: black; -fx-font-size: 28");

        pointsBox.getChildren().addAll(publicCardPoints, privateCardPoints, emptyCellMalus, total, resultLabel);

        box.getChildren().addAll(frameBox, pointsBox);

        Button rematchButton = new Button("Play again");
        rematchButton.setOnMouseClicked((mouseEvent -> {
            loginController.closeConnection();
            init();
            rootLayout.setTop(null);
        }));

        Button exitButton = new Button("Exit");
        exitButton.setOnMouseClicked((mouseEvent -> {
            loginController.closeConnection();
            System.exit(0);
        }));

        style = "-fx-background-color: orange; -fx-font-size: 24";
        exitButton.setStyle(style);
        rematchButton.setStyle(style);

        VBox buttonsBox = new VBox(40);
        buttonsBox.setAlignment(Pos.CENTER);
        buttonsBox.getChildren().addAll(rematchButton, exitButton);

        box.getChildren().add(buttonsBox);

        rootLayout.setCenter(box);
    }

    /**
     * Initializes and shows the multi-player end game screen.
     * @param privateObjectiveCards the players' private objective cards.
     * @param ids the players' ids sorted by ranking.
     * @param points the points matrix representing the points each player scored.
     */
    public synchronized void endGame(char[] privateObjectiveCards, int[] ids, int[][] points){

        VBox mainBox = new VBox(10);
        mainBox.setAlignment(Pos.TOP_CENTER);

        Button rematchButton = new Button("Play again");
        rematchButton.setOnMouseClicked((mouseEvent -> {
            loginController.closeConnection();
            init();
            rootLayout.setTop(null);
        }));

        Button exitButton = new Button("Exit");
        exitButton.setOnMouseClicked((mouseEvent -> {
            loginController.closeConnection();
            System.exit(0);
        }));

        String buttonStyle = "-fx-background-color: orange; -fx-font-size: 22";
        exitButton.setStyle(buttonStyle);
        rematchButton.setStyle(buttonStyle);

        HBox buttons = new HBox(50);
        buttons.setAlignment(Pos.CENTER);
        buttons.getChildren().addAll(exitButton, rematchButton);

        HBox endGameBox = new HBox(20);
        endGameBox.setAlignment(Pos.CENTER);
        for(int i=0; i<points.length; i++) {
            VBox playerBox = new VBox(20);
            playerBox.setPadding(new Insets(10,0,0,0));
            playerBox.setAlignment(Pos.CENTER);
            Label nameLabel=new Label(gameController.getPlayerName(ids[i]));
            nameLabel.setStyle("-fx-background-color: rgba(0,0,0,0.7); -fx-text-fill: white; -fx-font-size: 26");
            int publicSum=points[ids[i]][0]+points[ids[i]][1]+points[ids[i]][2];
            Label publicPoints = new Label("Public objective cards: "+publicSum);
            Label privatePoints = new Label("Private objective card: " + points[ids[i]][3]);
            Label favorPoints = new Label("Favor tokens bonus: " + points[ids[i]][4]);
            Label emptyMalus = new Label("Empty cells malus: " + points[ids[i]][5]);
            Label total = new Label("Total: " + points[ids[i]][6]);
            String style = "-fx-background-color: rgba(0,0,0,0.7); -fx-text-fill: white; -fx-font-size: 22";
            publicPoints.setStyle(style);
            privatePoints.setStyle(style);
            favorPoints.setStyle(style);
            emptyMalus.setStyle(style);
            total.setStyle(style);
            GridPane playerFrame = gameController.getPlayerFrame(ids[i]);
            ImageView card = Util.getPrivateObjectiveCard(privateObjectiveCards[ids[i]]);
            VBox labels= new VBox(5);
            labels.getChildren().addAll(publicPoints, privatePoints, favorPoints, emptyMalus, total);
            playerBox.getChildren().addAll(nameLabel, card, playerFrame, labels);
            if(ids[i] == this.id){
                playerBox.setStyle("-fx-background-color: radial-gradient(center 50% 0%, radius 70%, rgba(252,176,19,0.3), rgba(0,0,0,0))");
            }
            endGameBox.getChildren().add(playerBox);
        }
        mainBox.getChildren().addAll(endGameBox, buttons);
        rootLayout.setCenter(mainBox);
    }

    /**
     * Shows an error popup saying the user his move was invalid and aborts the current user's action.
     * Details are included in the message parameter.
     * @param message the string containing details on the error cause.
     */
    public synchronized void error(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(message);
        alert.setTitle("Error");
        alert.showAndWait();
        currentPhase = new MainPhase(remoteController, gameController);
    }

    /**
     * Shows a popup message saying the user his move was not valid, detailing the causes in the message.
     * The user is required to perform a new valid move.
     * @param message the string containing details on the error cause.
     */
    public synchronized void wrongParameter(String message){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(message);
        alert.setTitle("Wrong parameter");
        currentPhase = new GamePhase(remoteController, gameController);
        alert.showAndWait();
    }

    void setFullScreen() {
        ((Stage) rootLayout.getScene().getWindow()).setFullScreen(true);
    }
}
