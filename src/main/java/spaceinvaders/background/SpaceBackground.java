package spaceinvaders.background;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import spaceinvaders.NodeManager;
import spaceinvaders.Resource;
import spaceinvaders.Utility;


/**
 * Background node.
 * The background is a moving space.
 * </p>
 * Made with a grid filled with identical images of space
 * This grid moves down at a certain speed.
 * When it is moved down by the image size, it is shifted up by the image size.
 * Thus, the movement looks seamless.
 */
public class SpaceBackground implements NodeManager {
    //constants
    private static final double BACKGROUND_SPEED = 0.07;
    private static final double FRAME_TIME = 0.02;
    private static final Image BACKGROUND_IMAGE = Utility.getImage(Resource.SPACE_BACKGROUND);

    private final Pane outerPane;

    public SpaceBackground(double width, double height) {
        outerPane = new Pane();

        double speed = height * BACKGROUND_SPEED;
        GridPane movingPane = createMovingPane(width, height, speed);

        outerPane.getChildren().add(movingPane);
    }

    @Override
    public Node getNode() {
        return outerPane;
    }

    private GridPane createMovingPane(double outerWidth, double outerHeight, double speed) {
        GridPane movingPane = new GridPane();

        double imageHeight = BACKGROUND_IMAGE.getHeight();
        double imageWidth = BACKGROUND_IMAGE.getWidth();
        double backgroundHeight = outerHeight + imageHeight;
        int columns = (int) Math.ceil(outerWidth / imageWidth);
        int rows = (int) Math.ceil(backgroundHeight / imageHeight);

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                ImageView imageView = new ImageView(BACKGROUND_IMAGE);
                movingPane.add(imageView, j, i);
            }
        }

        movingPane.setLayoutY(-imageHeight);

        addMovingAnimation(movingPane, speed);

        return movingPane;
    }

    private void addMovingAnimation(GridPane movingPane, double speed) {
        Timeline movingTimeline = new Timeline();
        movingTimeline.setCycleCount(Timeline.INDEFINITE);
        KeyFrame keyFrame = new KeyFrame(Duration.seconds(FRAME_TIME), actionEvent -> {
            double newY = movingPane.getLayoutY() + FRAME_TIME * speed;
            while (newY >= 0) {
                newY -= BACKGROUND_IMAGE.getHeight();
            }
            movingPane.setLayoutY(newY);
        });
        movingTimeline.getKeyFrames().add(keyFrame);
        movingTimeline.play();
    }
}
