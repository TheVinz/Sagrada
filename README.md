

## Sagrada #

This is the repository for the java implementation of the board game *[Sagrada](http://floodgategames.com/Sagrada/)*. This project is part of the final test in Software Engineering 2018 course at Politecnico di Milano.

## General info ##

### Contributors: ###
* Andrea Scotti (846630)
* Vincenzo Santomarco (846442)
* Gabriele Stucchi (847482)

### Repository folders: ###

* **src/main:** contains the java source code of the application.
* **src/test:** application unit tests using junit.
* **lib:** contains the compiled sources of external libraries.
* **configurations:** contains the configurations files.

## Usage info ##

#### Start the server launcher ####

In order to play you first need to set the values of timeouts and ip in the configuration file **/lib/server_settings.json**.
Then start the server, to do that run `java -jar server.jar`, it will start automatically and wait for incoming RMI or Socket connections.

#### Start the client launcher ####

Before playing remember to set the values of the server ip in the configuration file **/lib/client_settings.json**.
* ##### Cli Launcher ##### 

    If you want to play in the CLI environment  run `java -jar cliClient.jar`.

* ##### Gui Launcher #####

    If you want to play in the GUI environment run `java -jar guiClient.jar`.

#### MultiPlayer ####

When the first player connects to the server, a new waiting room will be created, following players will be automatically placed in the same waiting room. When at least two people joined the same waiting room a countdown is set: if the countdown does reach 0 a new game will be created, if someone else join the room during the countdown he is added to the game. When the room reaches the maximum of 4 players the game will start instantly.

#### Concurrent Games  ####

The game manager supports multiple concurrent games. When a game starts new incoming players will be put in another waiting room, and a new game will start with the same rules as before.

#### Single Player ####

When the player starts the game it can choose to play alone with some difference in the rules.

## Implementation details ##

The whole architecture of the application follows the **MVC** pattern:
* the **MODEL** holds all the state and the application logic
* the **VIEW** displays the state to the user and it gets updated when the state changes
* the **CONTROLLER** maps the user input from the view to methods to call on the model in order to change its state

<p align="center"><img src="docs/MVC.png"></p>

## Controller ##

Controller is implemented with a **STATE PATTERN**, where the class *Controller* refers to the *PlayerState* interface, as *currentState*, for performing the *selectObject(ModelObject o)* operation.
In this way *Controller* is independent of how state-specific behavior is implemented. The *currentState* is updated every time a *selectObject(ModelObject o)* finishes, by retrieving is returned value.

<p align="center"><img src="docs/controller.png"></p>

## Model ##

The class *Model* contains:
* a reference to the *State*
* all the methods that can be called by *Controller* to change the *State*
* a list of *Observer*s;

This class inherit from the interface *Observable* all the methods which notify the *Observer*s of this class about a change of the state.

<p align="center"><img src="docs/model.png"></p>

<p align="center"><img src="docs/state.png"></p>

## View ##

### Cli ###

Cli is implemented with a **STATE PATTERN**, where the class *CliApp* refers to the *CliPhaseState* interface, as the *currentState*.
A loop inside *CliApp* keeps waiting for user input and it's handled by the current state. 
<p align="center"><img src="docs/cli.png"></p>

### Gui ###

Our *Sagrada* GUI, made with JavaFX, is made with 5 main FXML file: "RootLayout.fxml" is the root *BorderPane* used to contains all other windows, "Login.fxml" is the login window, "WindowFrameChoice.fxml" is the window frame choice screen, "Game.fxml" for the single-player game window and "SinglePlayer.fxml" is the single-player game window. For handling the player input a state pattern has been implemented so the server responses, about the next move the player is supposed to perform, sets the appropriate gui state. This way the gui can sets game graphics (such as drag and drop and hover effects) depending on the next move the player should perform.

## MVC examples ##

In these examples we shortly describe the MVC communications.
In the first one it's shown what happens when user wants to use a *ToolCard*. The view send a *GameCommand* to the *ViewProxy* which translates it in a real *ToolCard* and pass it to the *Controller*. In this case the current state of the controller is *WaitingPhase*, that, by recognizing the *ToolCard*, will initialize a new *UsingToolCard* state that will store this *ToolCard* to manages the next operations of the user. The *Controller* asks to the *ToolCard* which is the next expected parameter and this will be notify to the user by the *ViewProxy*.

<p align="center"><img src="docs/sequence1.png"></p>

In the second one it's shown what happens when user sends the last parameter expected from the *ToolCard* but this one can't be used. When the *ToolCard* recognizes that this parameter is the last one needed, it calls the method *doAbility* which realizes all the effects of the *ToolCard* on the state. Some information are requested to the *Model* (this passage is not shown on the diagram) and then the *ToolCard* throws an InvalidMoveException that is notify as an error message to the *ViewProxy*.
<p align="center"><img src="docs/sequence2.png"></p>

## Network ##

For the network we decided to hide the net to the *Model* and the *Client* by creating a *ViewProxy* class. This class receives updates and notifications, from *Model* and *Controller* respectively, sending them to the client and receiving *GameCommand*s from the remote player forwarding them to the controller. This way *Model* and *Controller* does not know which kind of connection the client is using, and so even if a disconnected player tries to reconnect with another kind of connection this will be totally transparent for the game.

### RMI ###
The class *RMILoginManager*, that extends the *RemoteLoginManager*, initialize the connection with the client who called remotely the method *connect* of this class.
This method return to the user a *RemoteController* that it will be used to call remote methods on the the server.

### Sockets ###
The class *ServerSocketHandler* initialize the socket connection and calls the method *mainLoop* on the *ViewProxy* which starts a new thread which keeps waiting for user inputs.
<p align="center"><img src="docs/network.png"></p>

## Game Manager ##
When a connection is set, the method *addPlayer* of the class *GameManager* is called and manages the player by the rules explained in the introduction.

### Limitations ###

Unfortunately, with this implementation, if a client connected in RMI gets disconnected from the network, while the server calls a method on a remote object of this client, the game can't manage to continue because it's stuck to wait the return value of this method. 

## Testing ##

### Sonar screens
<p align="center"><img src="docs/sonar1.jpg"></p>
<p align="center"><img src="docs/sonar2.jpg"></p>


