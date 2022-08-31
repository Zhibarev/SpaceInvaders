package spaceinvaders.game;

import java.util.*;

/**
 * Class representing the ship's weapon.
 * The weapon can be upgraded, currently there are four levels of weapon described below
 */
public class Weapon {
    //max level of the weapon
    private static final int MAX_LEVEL = 4;

    /**
     * hash table that returns shots at the current weapon level
     */
    private final Map<Integer, List<Sprite>> levelToProjectilesMap;

    //current level of the weapon
    private int currentLevel;

    /**
     * Create level one weapon
     */
    public Weapon() {
        currentLevel = 1;
        levelToProjectilesMap = new HashMap<>();

        initLevelOne();
        initLevelTwo();
        initLevelThree();
        initLevelFour();
    }

    /**
     * Shoot a weapon of the current level
     * @param ship a sprite that is used to calculate the position of the weapon
     * @return list of projectiles fired
     */
    public List<Sprite> fire(Sprite ship) {
        List<Sprite> levelProjectiles = levelToProjectilesMap.get(currentLevel);

        List<Sprite> firedProjectiles = new ArrayList<>();
        for (Sprite shot: levelProjectiles) {
            firedProjectiles.add(new Sprite(shot));
        }

        double weaponPosX = ship.getPosX() + ship.getWidth() / 2;
        double weaponPosY = ship.getPosY();

        /*
         * move all shots according to the position of the weapon
         * initially they were created relative to the zero position
         */
        for (Sprite shot: firedProjectiles)
            shot.setPos(shot.getPosX() + weaponPosX, shot.getPosY() + weaponPosY);

        return firedProjectiles;
    }

    /**
     * Upgrade weapon level.
     * If it is already the maximum level nothing happens.
     */
    public void upgrade() {
        if (currentLevel != MAX_LEVEL)
            currentLevel++;
    }

    /**
     * Create sprite of the single shot at zero position
     * Sprite moves at a given angle (relative to y-axis)
     */
    private Sprite createSingleShot(double angle) {
        Sprite shot = new Projectile(angle);
        shot.setPosX(-shot.getWidth() / 2);
        return shot;
    }

    /**
     * Create sprites of the double shot at zero position
     * Sprites moves at a given angle (relative to y-axis)
     */
    private List<Sprite> createDoubleShot(double angle) {
        List<Sprite> shots = new ArrayList<>();

        //create left shot
        Sprite leftShot = createSingleShot(angle);
        leftShot.setPosX(-leftShot.getWidth());
        shots.add(leftShot);

        //create right shot
        Sprite rightShot = createSingleShot(angle);
        rightShot.setPosX(0);
        shots.add(rightShot);

        return shots;
    }

    /**
     * The first level is one sprite that moves vertically upward
     * Sprites are created relative to the zero position of the weapon
     */
    private void initLevelOne() {
        List<Sprite> projectiles = List.of(createSingleShot(0));
        levelToProjectilesMap.put(1, projectiles);
    }

    /**
     * The second level is two sprites that move vertically upward
     * Sprites are created relative to the zero position of the weapon
     */
    private void initLevelTwo() {
        List<Sprite> projectiles = createDoubleShot(0);
        levelToProjectilesMap.put(2, projectiles);
    }

    /**
     * The third level is three sprites directed at an angle of 30 degrees to each other
     * Sprites are created relative to the zero position of the weapon
     */
    private void initLevelThree() {
        final double ROTATION_ANGLE = 30;

        List<Sprite> projectiles = new ArrayList<>();

        for (double angle = -ROTATION_ANGLE; Double.compare(angle, ROTATION_ANGLE) != 1; angle += ROTATION_ANGLE) {
            Sprite shot = createSingleShot(angle);
            projectiles.add(shot);
        }

        levelToProjectilesMap.put(3, projectiles);
    }

    /**
     * The fourth level - six sprites, two on each side
     * Sprites are created relative to the zero position of the weapon
     */
    private void initLevelFour() {
        final double ROTATION_ANGLE = 30;

        List<Sprite> projectiles = new ArrayList<>();

        for (double angle = -ROTATION_ANGLE; Double.compare(angle, ROTATION_ANGLE) != 1; angle += ROTATION_ANGLE) {
            List<Sprite> doubleShot = createDoubleShot(angle);
            projectiles.addAll(doubleShot);
        }

        levelToProjectilesMap.put(4, projectiles);
    }
}