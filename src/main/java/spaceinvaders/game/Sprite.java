package spaceinvaders.game;

import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;


/**
 * Sprite class, used to set up and render a single object on the canvas (used for ship, shot, meteor, etc.).
 * <p>
 * Has the function update(deltaTime) that is used to change the state of the sprite.
 * And the function draw(canvas) that is used to draw an object to the canvas.
 * <p>
 * Also, all sprite parameters are calculated as if the canvas was 1 by 1
 * and then scaled to the actual canvas size in the draw function
 * This will allow you not to be tied to a specific size of the canvas and change it if necessary.
 * */
public class Sprite {
    private final Image image;
    private double width;
    private double height;
    private double posX;
    private double posY;
    private double speedX;
    private double speedY;
    private double rotationAngle;

    public Sprite(Image image) {
        this.image = image;
    }

    public Sprite(Sprite another) {
        this.image = another.image;
        this.posX = another.posX;
        this.posY = another.posY;
        this.speedY = another.speedY;
        this.speedX = another.speedX;
        this.width = another.width;
        this.height = another.height;
        this.rotationAngle = another.rotationAngle;
    }

    public void draw(Canvas canvas) {
        GraphicsContext graphicsContext = canvas.getGraphicsContext2D();

        double centerPosX = canvas.getWidth() * (posX + width / 2);
        double centerPosY = canvas.getHeight() * (posY + height / 2);
        double realPosX = canvas.getWidth() * posX;
        double realPosY = canvas.getHeight() * posY;
        double realWidth = canvas.getWidth() * width;
        double realHeight = canvas.getHeight() * height;

        graphicsContext.save();
        graphicsContext.translate(centerPosX, centerPosY);
        graphicsContext.rotate(-rotationAngle);
        graphicsContext.translate(-centerPosX, -centerPosY);
        graphicsContext.drawImage(image, realPosX, realPosY, realWidth, realHeight);
        graphicsContext.restore();
    }

    public void update(double deltaTime) {
        posX += speedX * deltaTime;
        posY += speedY * deltaTime;
    }

    /**
     * Checks if two sprites collide
     * For collision detection, rectangular collision detection is used
     */
    public boolean checkCollision(Sprite sprite) {
        Rectangle2D thisRect = new Rectangle2D(posX, posY, width, height);
        Rectangle2D anotherRect = new Rectangle2D(sprite.posX, sprite.posY, sprite.width, sprite.height);
        return thisRect.intersects(anotherRect);
    }

    public void setSpeedX(double speedX) {
        this.speedX = speedX;
    }

    public void setSpeedY(double speedY) {
        this.speedY = speedY;
    }

    public void setPosX(double posX) {
        this.posX = posX;
    }

    public void setPosY(double posY) {
        this.posY = posY;
    }

    public void setPos(double posX, double posY) {
        this.posX = posX;
        this.posY = posY;
    }

    public void setSize(double width, double height) {
        this.width = width;
        this.height = height;
    }

    public void setRotationAngle(double rotationAngle) {
        this.rotationAngle = rotationAngle;
    }

    public double getPosX() {
        return posX;
    }

    public double getPosY() {
        return posY;
    }

    public double getSpeedX() {
        return speedX;
    }

    public double getSpeedY() {
        return speedY;
    }

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }

    public Image getImage() {
        return image;
    }
}
