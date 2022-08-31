package spaceinvaders.hud;

import javafx.beans.property.DoubleProperty;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.Node;
import spaceinvaders.NodeManager;
import spaceinvaders.Resource;
import spaceinvaders.Utility;


/**
 * The class that display player lives
 */
public class LivesView implements NodeManager {
    private static final Image LIVE_IMAGE = Utility.getImage(Resource.LIVE);
    private static final Image X_IMAGE = Utility.getImage(Resource.NUMERAL_X);

    private final HBox hBox;
    private final NumberView prettyNumber;

    public LivesView() {
        hBox = new HBox();

        ImageView liveImageView = new ImageView(LIVE_IMAGE);
        hBox.getChildren().add(liveImageView);

        ImageView xImageView = new ImageView(X_IMAGE);
        hBox.getChildren().add(xImageView);

        prettyNumber = new NumberView();
        hBox.getChildren().add(prettyNumber.getNode());
    }

    public void setLives(int numLives) {
        prettyNumber.setNumber(numLives);
    }

    @Override
    public Node getNode() {
        return hBox;
    }

    public DoubleProperty prefHeightProperty() {
        return hBox.prefHeightProperty();
    }

    public DoubleProperty spacingProperty() {
        return hBox.spacingProperty();
    }
}
