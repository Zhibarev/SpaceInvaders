package spaceinvaders.menu;

import javafx.beans.binding.Bindings;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Labeled;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import spaceinvaders.NodeManager;
import spaceinvaders.Resource;
import spaceinvaders.Utility;


/**
 * The class representing the menu.
 */
public class Menu implements NodeManager {
    //menu constants
    private static final int LOGO_PERCENT = 40;
    private static final int MENU_PERCENT = 40;
    private static final int FOOTER_PERCENT = 20;
    private static final double LOGO_FONT_SIZE = 0.07;
    private static final double SCORE_LABEL_FONT_SIZE = 0.035;
    private static final double BUTTON_SPACING = 0.03;
    private static final double BUTTON_HEIGHT = 0.04;
    private static final double BUTTON_WIDTH = 0.4;
    private static final double BUTTON_FONT_SIZE = 0.037;
    private static final Image BUTTON_IMAGE = Utility.getImage(Resource.BLUE_BUTTON);

    private final GridPane gridPane;
    private Button startButton;
    private Button exitButton;
    private Label scoreLabel;

    public Menu() {
        gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);

        var rows = gridPane.getRowConstraints();
        RowConstraints row1 = new RowConstraints();
        row1.setPercentHeight(LOGO_PERCENT);
        rows.add(row1);

        RowConstraints row2 = new RowConstraints();
        row2.setPercentHeight(MENU_PERCENT);
        rows.add(row2);

        RowConstraints row3 = new RowConstraints();
        row3.setPercentHeight(FOOTER_PERCENT);
        rows.add(row3);

        gridPane.add(createLogo(), 0, 0);
        gridPane.add(createButtons(), 0, 1);
        gridPane.add(createScoreLabel(), 0, 2);
    }

    @Override
    public Node getNode() {
        return gridPane;
    }

    /**
     * Set a function that will be called when the user clicks on start
     */
    public void setOnStartAction(Runnable runnable) {
        startButton.setOnAction(actionEvent -> runnable.run());
    }

    /**
     * Set a function that will be called when the user clicks on exit
     */
    public void setOnExitAction(Runnable runnable) {
        exitButton.setOnAction(actionEvent -> runnable.run());
    }

    public void setLastGameScore(int score) {
        scoreLabel.setText("Score: " + score);
    }

    private Label createLogo() {
        Label label = new Label("SPACE INVADERS");

        bindFontProperty(label, LOGO_FONT_SIZE);

        label.setTextFill(Color.YELLOW);
        label.setAlignment(Pos.CENTER);
        return label;
    }

    private VBox createButtons() {
        VBox buttons = new VBox();
        buttons.spacingProperty().bind(gridPane.heightProperty().multiply(BUTTON_SPACING));
        buttons.setAlignment(Pos.TOP_CENTER);

        startButton = createButton("START");
        buttons.getChildren().add(startButton);

        exitButton = createButton("EXIT");
        buttons.getChildren().add(exitButton);

        return buttons;
    }

    private Label createScoreLabel() {
        scoreLabel = new Label();

        bindFontProperty(scoreLabel, SCORE_LABEL_FONT_SIZE);

        scoreLabel.setTextFill(Color.ALICEBLUE);
        return scoreLabel;
    }

    private Button createButton(String message) {
        Button button = new Button(message);

        bindFontProperty(button, BUTTON_FONT_SIZE);

        ReadOnlyDoubleProperty heightProperty = gridPane.heightProperty();
        ReadOnlyDoubleProperty widthProperty = gridPane.widthProperty();
        button.prefWidthProperty().bind(widthProperty.multiply(BUTTON_WIDTH));
        button.prefHeightProperty().bind(heightProperty.multiply(BUTTON_HEIGHT));

        BackgroundImage buttonBackground = new BackgroundImage(
               BUTTON_IMAGE,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT,
                new BackgroundSize(1.0, 1.0, true, true, false, false)
        );
        Background background = new Background(buttonBackground);
        button.setBackground(background);

        return button;
    }

    private void bindFontProperty(Labeled labeled, double fontSize) {
        ReadOnlyDoubleProperty widthProperty = gridPane.widthProperty();

        labeled.fontProperty().bind(Bindings.createObjectBinding(() ->
                Utility.loadFont(Resource.MENU_FONT, (int) widthProperty.multiply(fontSize).get()),
                widthProperty));
    }
}
