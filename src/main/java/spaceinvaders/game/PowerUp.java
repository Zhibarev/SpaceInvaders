package spaceinvaders.game;

import javafx.scene.image.Image;
import spaceinvaders.Resource;
import spaceinvaders.Utility;

import java.util.Random;

/**
 * Power-up sprite.
 * Class is used to configure and spawn power-ups.
 * <p>
 * There are currently 3 types of power-ups:
 *  Health power-up - increase health
 *  Weapon power-up - upgrade weapon
 *  Score power-up - increase score
 */
public class PowerUp extends Sprite {
    /**
     * All types of power-ups with the corresponding image
     */
    public enum Type {
        HEALTH_UP(Resource.HEALTH_UP),
        WEAPON_UP(Resource.WEAPON_UP),
        SCORE_UP(Resource.SCORE_UP);

        private final Image IMAGE;

        Type(Resource imageResource) {
            IMAGE = Utility.getImage(imageResource);
        }

        public Image getImage() {
            return IMAGE;
        }
    }

    private static final Random RANDOMIZER = new Random();
    private static final double HEIGHT = 0.04;

    private final Type type;

    /**
     * Create a power-up of the given type at a random position above the canvas
     */
    public PowerUp(Type type) {
        super(type.getImage());

        this.type = type;

        //set size
        Image image = getImage();
        double width = HEIGHT * image.getWidth() / image.getHeight();
        setSize(width, HEIGHT);

        //set position
        Utility.setPositionAboveCanvas(this);
    }

    public Type getType() {
        return type;
    }

    /**
     * Create a random power-up at a random position above the canvas.
     * Used to create all power-ups in the game
     */
    public static PowerUp getRandomPowerUp() {
        Type[] types = Type.values();

        int randomIndex = RANDOMIZER.nextInt(types.length);
        Type randomType = types[randomIndex];

        return new PowerUp(randomType);
    }
}
