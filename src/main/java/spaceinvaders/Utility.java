package spaceinvaders;

import javafx.scene.image.Image;
import javafx.scene.text.Font;
import spaceinvaders.game.Sprite;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Random;

/**
 * The class with utility functions
 */
public class Utility {
    private static final Random RANDOMIZER = new Random();

    /**
     * Convert nanosecond to milliseconds
     */
    public static double nanoToMilliseconds(long nanoseconds) {
        final int NANO_TO_MILLI = 1_000_000;

        double milliseconds = (double) (nanoseconds / NANO_TO_MILLI);
        milliseconds += ((double) (nanoseconds % NANO_TO_MILLI)) / NANO_TO_MILLI;

        return milliseconds;
    }

    /**
     * Load font of given size
     * The font is loaded at the path specified in fontResource
     * If the font cannot be loaded, the javafx default is set
     */
    public static Font loadFont(Resource fontResource, int fontSize) {
        Font toReturn;
        try {
            toReturn = Font.loadFont(new FileInputStream(fontResource.getPath()), fontSize);
        }
        catch (FileNotFoundException exception) {
            toReturn = new Font(fontSize);
        }
        return toReturn;
    }

    /**
     * Set the sprite to a random position above the canvas
     */
    public static void setPositionAboveCanvas(Sprite sprite) {
        //calculate position
        double posY = -RANDOMIZER.nextDouble() - sprite.getHeight();
        double posX = (1 - sprite.getWidth()) * RANDOMIZER.nextDouble();

        //set position
        sprite.setPosY(posY);
        sprite.setPosX(posX);
    }

    /**
     * Load the image
     */
    public static Image getImage(Resource resource) {
        return new Image("file:" + resource.getPath());
    }
}
