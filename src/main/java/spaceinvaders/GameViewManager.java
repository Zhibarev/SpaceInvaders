package spaceinvaders;

import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import spaceinvaders.background.SpaceBackground;
import spaceinvaders.game.Game;
import spaceinvaders.game.Ship;
import spaceinvaders.hud.HUD;
import spaceinvaders.menu.Menu;

/**
 * The class that controls all elements of the game
 */
public class GameViewManager {
    /**
     * Game size
     * The game was created based on the ratio of height to width 1 to 1
     * If change the ratio, the game will look compressed or expanded
     */
    private static final double GAME_WIDTH = 600;
    private static final double GAME_HEIGHT = 600;

    private final Stage stage;
    private final Scene scene;
    private final StackPane pane;

    private final Menu menu;
    private final Game game;
    private final HUD hud;

    private boolean isLeftPressed;
    private boolean isRightPressed;

    public GameViewManager() {
        pane = new StackPane();
        pane.setPrefSize(GAME_WIDTH, GAME_HEIGHT);

        scene = new Scene(pane);

        stage = new Stage();
        stage.setScene(scene);
        stage.setResizable(false);
        stage.setTitle("Space Invaders");
        stage.getIcons().add(Utility.getImage(Resource.LOGO));

        //create background node
        SpaceBackground background = new SpaceBackground(GAME_WIDTH, GAME_HEIGHT);
        pane.getChildren().add(background.getNode());

        //create game node
        game = new Game(GAME_WIDTH, GAME_HEIGHT);
        game.setOnTickAction(this::updateHud);
        game.setOnEndGameAction(this::onEndGameAction);

        //create hud
        hud = new HUD();

        //create menu
        menu = new Menu();
        menu.setOnStartAction(this::startGame);
        menu.setOnExitAction(stage::close);
        pane.getChildren().add(menu.getNode());
    }

    public Stage getStage() {
        return stage;
    }

    /**
     * A function that is called at the start of the game.
     */
    public void startGame() {
        pane.getChildren().remove(menu.getNode());
        pane.getChildren().add(game.getNode());
        pane.getChildren().add(hud.getNode());

        createControllers();

        game.startGame();
    }

    /**
     * A function that is called at the end of the game.
     */
    private void onEndGameAction() {
        pane.getChildren().remove(hud.getNode());
        pane.getChildren().remove(game.getNode());
        menu.setLastGameScore(game.getScore());
        pane.getChildren().add(menu.getNode());
    }

    /**
     * Update HUD, this function is called every tick
     */
    private void updateHud() {
        hud.setNumLives(game.getNumLives());
        hud.setScore(game.getScore());
    }

    /**
     * Set events on keyboard keystrokes
     * Keys:
     *  A - move left
     *  D - move right
     *  Space - shoot
     */
    private void createControllers() {
        isLeftPressed = false;
        isRightPressed = false;

        scene.setOnKeyPressed(keyEvent -> {
            switch (keyEvent.getCode()) {
                case A -> {
                    isLeftPressed = true;
                    setMovingDirection();
                }
                case D -> {
                    isRightPressed = true;
                    setMovingDirection();
                }
                case SPACE -> game.setFireMode(true);
            }
        });

        scene.setOnKeyReleased(keyEvent -> {
            switch (keyEvent.getCode()) {
                case A -> {
                    isLeftPressed = false;
                    setMovingDirection();
                }
                case D -> {
                    isRightPressed = false;
                    setMovingDirection();
                }
                case SPACE -> game.setFireMode(false);
            }
        });
    }

    /**
     * Set the direction of movement of the ship
     * This function is called after each press / release of A and D
     */
    private void setMovingDirection() {
        if (!isLeftPressed && isRightPressed)
            game.setPlayerShipDirection(Ship.Direction.RIGHT);
        else if (isLeftPressed && !isRightPressed)
            game.setPlayerShipDirection(Ship.Direction.LEFT);
        else
            game.setPlayerShipDirection(Ship.Direction.FORWARD);
    }
}
