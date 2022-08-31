package spaceinvaders.game;

import javafx.scene.image.Image;
import spaceinvaders.Resource;
import spaceinvaders.Utility;

/**
 * Ship sprite. In constructor the sprite is configured.
 * The ship moves only horizontally.
 * There is also a function that allows you to set the direction of movement of the ship.
 */
public class Ship extends Sprite {
    //ship constant parameters
    private static final Image SHIP_IMAGE = Utility.getImage(Resource.MAIN_SHIP);
    private static final double HEIGHT = 0.07;
    private static final double WIDTH = HEIGHT * SHIP_IMAGE.getWidth() / SHIP_IMAGE.getHeight();
    private static final double Y_PADDING = 0.02;
    private static final double SPEED = 0.0004;

    /**
     * Direction of movement of the ship.
     */
    public enum Direction {
        LEFT,
        RIGHT,
        FORWARD
    }

    public Ship() {
        super(SHIP_IMAGE);

        //set size
        setSize(WIDTH, HEIGHT);

        //set position to center x and bottom y
        double shipPosX = 0.5 - WIDTH / 2;
        double shipPosY = 1 - HEIGHT - Y_PADDING;
        setPos(shipPosX, shipPosY);
    }

    public void setDirection(Direction direction) {
        switch (direction) {
            case FORWARD -> setSpeedX(0);
            case LEFT -> setSpeedX(-SPEED);
            case RIGHT -> setSpeedX(SPEED);
        }
    }
}
