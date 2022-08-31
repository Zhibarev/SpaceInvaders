package spaceinvaders.game;

import javafx.animation.Animation;
import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.util.Duration;
import spaceinvaders.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Class responsible for all game logic.
 * <p>
 * General logic:
 *  1) There is a canvas (gameCanvas) on which the whole game is drawn
 *  2) Timer (gameTimer), on each operation of which the tick function is called
 *  3) The tick function represents one frame, it moves all sprites, handling collisions and drawing sprites on the canvas
 */
public class Game implements NodeManager {
    private static final int NUM_METEORS = 20;
    private static final int NUM_LIVES = 3;
    private static final int SCORE_UP_BONUS = 50;
    private static final double SHOT_DELAY = 0.2; //in seconds
    private static final double LEVEL_DURATION = 10; //in seconds
    private static final double MULTIPLIER_BY_LEVEL = 1.1; //how many times the speed increases per level

    private final Canvas gameCanvas; //canvas on which the whole game is drawn

    /**
     * Main game loop
     */
    private final AnimationTimer gameTimer;
    private long lastFrameTime = 0;

    /**
     * Every LEVEL_DURATION seconds the difficulty level of the game increases.
     * The speed of meteorites increases and one power-up is created (the levelUp function).
     * LevelUpTimeLine is responsible for this.
     */
    private final Timeline levelUpTimeLine;

    /**
     * If the weapon is active (setFireMode), the timer is responsible for the delay (SHOT_DELAY) between shots.
     */
    private final Timeline shotTimeLine;

    //sprites and sprites generators
    private Ship playerShip;
    private List<Sprite> meteors;
    private List<Sprite> projectiles;
    private List<PowerUp> powerUps;
    private Weapon weapon;

    private int score;
    private int numLives;

    /**
     * An external function that is called every tick. (used to update the state of the HUD)
     */
    private Runnable onTickAction;

    /**
     * An external function that is called when the game ends. (used to close the game and exit to the menu)
     */
    private Runnable onEndGameAction;

    private boolean isGameStarted = false; //current state of the game

    @Override
    public Node getNode() {
        return gameCanvas;
    }

    public Game(double width, double height) {
        //create canvas
        gameCanvas = new Canvas();
        gameCanvas.setHeight(height);
        gameCanvas.setWidth(width);

        //create main game timer
        gameTimer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (Double.compare(lastFrameTime, 0) != 0) {
                    //get duration of last frame in milliseconds
                    double deltaTime = Utility.nanoToMilliseconds(now - lastFrameTime);
                    //render the new frame
                    tick(deltaTime);
                }
                //update time of the last frame
                lastFrameTime = now;
            }
        };

        //create a timeline that fires a shot every SHOT_DELAY seconds
        shotTimeLine = new Timeline();
        shotTimeLine.setCycleCount(Timeline.INDEFINITE);
        KeyFrame shotFrame = new KeyFrame(
                Duration.seconds(SHOT_DELAY),
                actionEvent -> projectiles.addAll(weapon.fire(playerShip))
        );
        shotTimeLine.getKeyFrames().add(shotFrame);

        //create levelUp timeline that increases the difficulty of the game every LEVEL_DURATION seconds
        levelUpTimeLine = new Timeline();
        levelUpTimeLine.setCycleCount(Timeline.INDEFINITE);
        KeyFrame keyFrame = new KeyFrame(Duration.seconds(LEVEL_DURATION), actionEvent -> levelUp());
        levelUpTimeLine.getKeyFrames().add(keyFrame);
    }

    public int getScore() {
        return score;
    }

    public int getNumLives() {
        return numLives;
    }

    public void startGame() {
        score = 0;
        numLives = NUM_LIVES;

        //create sprites
        createSprites();

        //create weapon
        weapon = new Weapon();

        //start timers
        gameTimer.start();
        levelUpTimeLine.play();

        //set the game state
        isGameStarted = true;
    }

    private void createSprites() {
        //create ship
        playerShip = new Ship();

        //create meteors
        meteors = new ArrayList<>();
        for (int i = 0; i < NUM_METEORS; i++)
            meteors.add(Meteor.spawnMeteor());

        //create the list of shots
        projectiles = new ArrayList<>();

        //create the list of powerUps
        powerUps = new ArrayList<>();
    }

    public void endGame() {
        if (isGameStarted) {
            //stop all timers
            gameTimer.stop();
            levelUpTimeLine.stop();
            shotTimeLine.stop();

            //change the game state
            isGameStarted = false;

            //remove all sprites
            meteors.clear();
            projectiles.clear();
            powerUps.clear();
            playerShip = null;

            //call onEndGameAction
            if (onEndGameAction != null)
                onEndGameAction.run();
        }
    }

    /**
     * Start or end shooting
     */
    public void setFireMode(boolean isFireOn) {
        if (isGameStarted) {
            if (isFireOn) {
                if (shotTimeLine.getStatus() == Animation.Status.STOPPED) {
                    projectiles.addAll(weapon.fire(playerShip));
                    shotTimeLine.play();
                }
            }
            else {
                shotTimeLine.stop();
            }
        }
    }


    public void setPlayerShipDirection(Ship.Direction direction) {
        if (isGameStarted)
            playerShip.setDirection(direction);
    }

    public void setOnTickAction(Runnable runnable) {
        onTickAction = runnable;
    }

    public void setOnEndGameAction(Runnable runnable) {
        onEndGameAction = runnable;
    }

    private void tick(double deltaTime) {
        updateItems(deltaTime);

        handleCollisions();

        //after collisions, the state of the game may change, so need to check it before rendering
        if (isGameStarted) {
            drawItems();
            if (onTickAction != null)
                onTickAction.run();
        }
    }

    private void handleCollisions() {
        //meteor collisions
        for (Sprite meteor: meteors) {
            //collisions with playerShip
            if (meteor.checkCollision(playerShip)) {
                numLives--;
                if (numLives < 0) {
                    endGame();
                    return;
                }
                Utility.setPositionAboveCanvas(meteor);
            }
            else {
                //collisions with projectiles
                Iterator<Sprite> projectileIt = projectiles.iterator();
                while (projectileIt.hasNext()) {
                    Sprite shot = projectileIt.next();
                    if (shot.checkCollision(meteor)) {
                        score++;
                        Utility.setPositionAboveCanvas(meteor);
                        projectileIt.remove();
                    }
                }
            }
        }

        //power up collisions
        Iterator<PowerUp> powerUpIt = powerUps.iterator();
        while (powerUpIt.hasNext()) {
            PowerUp powerUp = powerUpIt.next();
            if (powerUp.checkCollision(playerShip)) {
                switch (powerUp.getType()) {
                    case HEALTH_UP -> numLives++;
                    case WEAPON_UP -> weapon.upgrade();
                    case SCORE_UP -> score += SCORE_UP_BONUS;
                }
                powerUpIt.remove();
            }
        }
    }

    private void updateItems(double deltaTime) {
        //update playerShip, if the ship went beyond the canvas, roll back the update
        double playerPosX = playerShip.getPosX();
        playerShip.update(deltaTime);
        if (playerShip.getPosX() < 0 || playerShip.getPosX() > 1 - playerShip.getWidth())
            playerShip.setPosX(playerPosX);

        //update meteors, if the meteor went beyond the canvas, move it to a random position above the canvas
        for (Sprite meteor: meteors) {
            meteor.update(deltaTime);
            if (meteor.getPosY() > 1)
                Utility.setPositionAboveCanvas(meteor);
        }

        //update projectiles, if the projectile went beyond the canvas, remove it
        Iterator<Sprite> projectileIt = projectiles.iterator();
        while (projectileIt.hasNext()) {
            Sprite shot = projectileIt.next();
            shot.update(deltaTime);
            if (shot.getPosY() < -shot.getHeight())
                projectileIt.remove();
        }

        //update power-ups, if the power-up went beyond the canvas, remove it
        Iterator<PowerUp> powerUpIt = powerUps.iterator();
        while (powerUpIt.hasNext()) {
            PowerUp powerUp = powerUpIt.next();
            powerUp.update(deltaTime);
            //if powerUp crosses the border, remove it
            if (powerUp.getPosY() > 1) {
                powerUpIt.remove();
            }
        }
    }

    private void drawItems() {
        GraphicsContext graphicsContext = gameCanvas.getGraphicsContext2D();

        //clear canvas
        graphicsContext.clearRect(0, 0, gameCanvas.getWidth(), gameCanvas.getHeight());

        //draw sprites
        playerShip.draw(gameCanvas);
        for (Sprite meteor: meteors)
            meteor.draw(gameCanvas);
        for (Sprite shot: projectiles)
            shot.draw(gameCanvas);
        for (PowerUp powerUp: powerUps)
            powerUp.draw(gameCanvas);
    }

    /**
     * Every LEVEL_DURATION seconds the difficulty level increases.
     * Meteorites are accelerated in MULTIPLIER_BY_LEVEL times and one power up is created
     */
    private void levelUp() {
        //scale meteor speed
        for (Sprite meteor: meteors)
            meteor.setSpeedY(meteor.getSpeedY() * MULTIPLIER_BY_LEVEL);

        //create power up
        PowerUp powerUp = PowerUp.getRandomPowerUp();
        Utility.setPositionAboveCanvas(powerUp);
        powerUp.setSpeedY(meteors.get(0).getSpeedY());
        powerUps.add(powerUp);
    }
}
