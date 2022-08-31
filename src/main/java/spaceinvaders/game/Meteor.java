package spaceinvaders.game;

import javafx.scene.image.Image;
import spaceinvaders.Resource;
import spaceinvaders.Utility;

import java.util.List;
import java.util.Random;

/**
 * Meteor sprite.
 * Class is used to configure and spawn meteors.
 */
public class Meteor extends Sprite {
    //meteor constant parameters
    private static final Random RANDOMIZER = new Random();
    private static final double HEIGHT = 0.05;
    private static final double SPEED = 0.0003;
    private static final List<Image> METEOR_IMAGES = List.of(
            Utility.getImage(Resource.METEOR_ONE),
            Utility.getImage(Resource.METEOR_TWO),
            Utility.getImage(Resource.METEOR_THREE)
    ); //list of all possible images of meteors

    /**
     * Create meteor at a random position above the canvas.
     * The meteorite moves vertically down.
     * @param angle rotation angle of the meteorite image.
     */
    public Meteor(Image meteorImage, double angle)
    {
        super(meteorImage);

        double width = meteorImage.getWidth() * (HEIGHT / meteorImage.getHeight());
        setSize(width, HEIGHT);
        setSpeedY(SPEED);
        setRotationAngle(angle);
        Utility.setPositionAboveCanvas(this);
    }

    /**
     * Create a meteorite with a random image in a random position above the canvas with a random rotation angle.
     * Used to create all meteorites in the game.
     */
    public static Meteor spawnMeteor() {
        int randomIndex = RANDOMIZER.nextInt(METEOR_IMAGES.size());
        Image randomImage = METEOR_IMAGES.get(randomIndex);
        double randomAngle = RANDOMIZER.nextInt(360);

        return new Meteor(randomImage, randomAngle);
    }
}
