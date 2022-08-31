package spaceinvaders.hud;

import javafx.beans.binding.Bindings;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.layout.BorderPane;
import spaceinvaders.NodeManager;

/**
 * The class representing a game HUD.
 * Displays the number of lives and the current score.
 */
public class HUD implements NodeManager {
    public static final double HUD_PADDING = 0.05;
    public static final double LIVES_SPACING = 0.01;
    public static final double ELEMENTS_HEIGHT = 0.04;

    private final BorderPane hudPane;

    private final LivesView livesView;
    private final NumberView scoreView;

    public HUD() {
        hudPane = new BorderPane();
        hudPane.setPadding(new Insets(HUD_PADDING));

        ReadOnlyDoubleProperty heightProperty = hudPane.heightProperty();
        ReadOnlyDoubleProperty widthProperty = hudPane.widthProperty();

        //padding
        hudPane.paddingProperty().bind(Bindings.createObjectBinding(() -> {
            return new Insets(calculatePadding(heightProperty),
                    calculatePadding(widthProperty),
                    calculatePadding(heightProperty),
                    calculatePadding(widthProperty));
        }, widthProperty, heightProperty));

        //create top container
        BorderPane topPane = new BorderPane();
        hudPane.setTop(topPane);

        //create lives
        livesView = new LivesView();
        livesView.prefHeightProperty().bind(heightProperty.multiply(ELEMENTS_HEIGHT));
        livesView.spacingProperty().bind(widthProperty.multiply(LIVES_SPACING));
        topPane.setLeft(livesView.getNode());

        //create score
        scoreView = new NumberView();
        scoreView.prefHeightProperty().bind(heightProperty.multiply(ELEMENTS_HEIGHT));
        topPane.setRight(scoreView.getNode());
    }

    @Override
    public Node getNode() {
        return hudPane;
    }

    public void setNumLives(int numLives) {
        livesView.setLives(numLives);
    }

    public void setScore(int score) {
        scoreView.setNumber(score);
    }

    private double calculatePadding(ReadOnlyDoubleProperty property) {
        return property.doubleValue() * HUD_PADDING;
    }
}
