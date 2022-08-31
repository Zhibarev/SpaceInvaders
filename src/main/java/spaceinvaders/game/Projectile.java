package spaceinvaders.game;

import javafx.scene.image.Image;
import spaceinvaders.Resource;
import spaceinvaders.Utility;

/**
 * Projectile sprite. Inside the class, the sprite is configured.
 */
public class Projectile extends Sprite {
    //projectile constant parameters
    private static final Image SHOT_IMAGE = Utility.getImage(Resource.SHOT);
    private static final double HEIGHT = 0.035;
    private static final double WIDTH = SHOT_IMAGE.getWidth() * (HEIGHT / SHOT_IMAGE.getHeight());
    private static final double SPEED = -0.0006;

    /**
     * Create a projectile that moves at a specific angle to the y-axis
     */
    public Projectile(double angle) {
        super(SHOT_IMAGE);

        //set size
        setSize(WIDTH, HEIGHT);

        //set angle
        setRotationAngle(angle);

        //set speed
        double angleInRadians = angle * (Math.PI / 180);
        double speedX = SPEED * Math.sin(angleInRadians);
        double speedY = SPEED * Math.cos(angleInRadians);
        setSpeedX(speedX);
        setSpeedY(speedY);
    }
}
