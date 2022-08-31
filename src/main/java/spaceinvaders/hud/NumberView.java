package spaceinvaders.hud;

import javafx.beans.property.DoubleProperty;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import spaceinvaders.NodeManager;
import spaceinvaders.Resource;
import spaceinvaders.Utility;

import java.util.Map;

/**
 * The class that displays a number using images of digits
 */
public class NumberView implements NodeManager {
    private static final Map<Integer, Image> digitToImage = Map.of(
            0, Utility.getImage(Resource.NUMERAL_ZERO),
            1, Utility.getImage(Resource.NUMERAL_ONE),
            2, Utility.getImage(Resource.NUMERAL_TWO),
            3, Utility.getImage(Resource.NUMERAL_THREE),
            4, Utility.getImage(Resource.NUMERAL_FOUR),
            5, Utility.getImage(Resource.NUMERAL_FIVE),
            6, Utility.getImage(Resource.NUMERAL_SIX),
            7, Utility.getImage(Resource.NUMERAL_SEVEN),
            8, Utility.getImage(Resource.NUMERAL_EIGHT),
            9, Utility.getImage(Resource.NUMERAL_NINE)
    );

    private final HBox hBox;

    private int number;

    public NumberView() {
        hBox = new HBox();
        this.number = -1;
        setNumber(0);
    }

    public void setNumber(int number)  {
        if (this.number == number)
            return;

        this.number = number;

        hBox.getChildren().clear();
        do {
            int digit = number % 10;
            Image digitImage = digitToImage.get(digit);
            ImageView digitImageView = new ImageView(digitImage);
            hBox.getChildren().add(0, digitImageView);
            number /= 10;
        } while (number != 0);
    }

    @Override
    public Node getNode() {
        return hBox;
    }

    public DoubleProperty prefHeightProperty() {
        return hBox.prefHeightProperty();
    }
}
