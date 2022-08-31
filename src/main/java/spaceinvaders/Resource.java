package spaceinvaders;

import java.util.Objects;

/**
 * The enum class that stores all resource paths.
 */
public enum Resource {
    SPACE_BACKGROUND("spaceBackground.png"),
    MAIN_SHIP("playerShip.png"),
    LIVE("playerLife.png"),
    NUMERAL_ZERO("numeral0.png"),
    NUMERAL_ONE("numeral1.png"),
    NUMERAL_TWO("numeral2.png"),
    NUMERAL_THREE("numeral3.png"),
    NUMERAL_FOUR("numeral4.png"),
    NUMERAL_FIVE("numeral5.png"),
    NUMERAL_SIX("numeral6.png"),
    NUMERAL_SEVEN("numeral7.png"),
    NUMERAL_EIGHT("numeral8.png"),
    NUMERAL_NINE("numeral9.png"),
    NUMERAL_X("numeralX.png"),
    SHOT("laser.png"),
    BLUE_BUTTON("buttonBlue.png"),
    HEALTH_UP("healthUp.png"),
    WEAPON_UP("weaponUp.png"),
    SCORE_UP("scoreUp.png"),
    MENU_FONT("kenvector_future.ttf"),
    METEOR_ONE("meteorBrown_big1.png"),
    METEOR_TWO("meteorBrown_big3.png"),
    METEOR_THREE("meteorBrown_big4.png"),
    LOGO("playerLife.png");

    private final String path;

    Resource(String path) {
        this.path = Objects.requireNonNull(Resource.class.getResource(path)).getPath();
    }

    public String getPath() {
        return path;
    }
}
