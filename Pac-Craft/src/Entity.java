import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.util.Duration;

public abstract class Entity {

    final int SPRITEWIDTH = 40;

    private Image image;
    private int initialXEntityAlive;
    private int initialYEntityAlive;
    private int nbImage;
    private double positionX;
    private double positionY;
    private double defaultSpeed;
    private double actualSpeed;
    private boolean isAlive;
    private boolean killable;
    private double width;
    private double height;
    private double index;
    private Timeline dyingAnimation;
    Direction newDirection;
    Direction currentDirection;

    /**
     * Generates a new Entities
     * @param path path to image
     * @param initialXEntityAlive
     * @param initialYEntityAlive
     * @param nbImage number of image for character
     * @param posX position X
     * @param posY position Y
     * @param defaultSpeed default speed for character
     * @param width width of picture
     * @param height height of picture
     * @param killable is killable
     */
    public Entity(String path, int initialXEntityAlive, int initialYEntityAlive, int nbImage, int posX, int posY, double defaultSpeed, int width, int height, boolean killable){
        this.image = new Image(path);
        this.nbImage = nbImage;
        this.initialXEntityAlive = initialXEntityAlive;
        this.initialYEntityAlive = initialYEntityAlive;
        this.positionX = posX;
        this.positionY = posY;
        this.defaultSpeed = defaultSpeed;
        this.actualSpeed = defaultSpeed;
        this.isAlive = true;
        this.killable = killable;
        this.newDirection = Direction.UP;
        this.currentDirection = Direction.STATIC;
        this.width = width;
        this.height = height;
        this.index = 0;
    }

    /**
     * Sets the origin Y coordinate of the Entity when entity's alive
     * @param initialYEntityAlive
     */
    public void setInitialYEntityAlive(int initialYEntityAlive) {
        this.initialYEntityAlive = initialYEntityAlive;
    }

    /**
     * @return X position of Entity
     */
    public double getPositionX() {
        return this.positionX;
    }

    /**
     * Sets the X position
     * @param positionX X position
     */
    public void setPositionX(double positionX) {
        this.positionX = positionX;
    }

    /**
     * @return Y position of Entity
     */
    public double getPositionY() {
        return this.positionY;
    }

    /**
     * Sets the Y position of Entity
     * @param positionY Y position
     */
    public void setPositionY(double positionY) {
        this.positionY = positionY;
    }

    /**
     * @return double default speed of Entity
     */
    public double getDefaultSpeed() {
        return this.defaultSpeed;
    }

    /**
     * Sets the Entities speed to given actualSpeed
     * @param actualSpeed the speed at which the Entity goes
     */
    public void setActualSpeed(double actualSpeed) {
        this.actualSpeed = actualSpeed;
    }

    /**
     * Sets alive state to given state
     * @param alive is alive
     */
    public void setAlive(boolean alive) {
        this.isAlive = alive;
    }

    /**
     * @return entity is killable
     */
    public boolean isKillable() {
        return this.killable;
    }

    /**
     * Sets killable state to given state
     * @param killable is killable
     */
    public void setKillable(boolean killable) {
        this.killable = killable;
    }

    /**
     * @return width of the Entity
     */
    public double getWidth() {
        return this.width;
    }

    /**
     * Return the current direction of character
     * @return current direction
     */
    public Direction getCurrentDirection(){
        return this.currentDirection;
    }

    /**
     * Return the center of the Entity to X
     * @return Center of position character
     */
    public int getCenterPosX(){
        return (int) (this.positionX+this.SPRITEWIDTH /2);
    }

    /**
     * Return the center of the Entity to Y
     * @return Center of position character
     */
    public int getCenterPosY(){
        return (int) (this.positionY+this.SPRITEWIDTH /2);
    }

    /**
     * Sets the new direction of the Entity
     * @param direction new direction
     */
    public void setNewDirection(Direction direction){
        this.newDirection = direction;
    }

    /**
     * Update the Entity's position in the canvas
     * @param t time elapsed in AnimationTimer
     */
    public void updatePositionCanvas(double t){
        this.index = (int) ((t % (this.nbImage * 0.1 )) / 0.1);
        switch (this.currentDirection){
            case UP:
                this.positionY -= this.actualSpeed;
                break;
            case DOWN:
                this.positionY += this.actualSpeed;
                break;
            case RIGHT:
                this.positionX += this.actualSpeed;
                break;
            case LEFT:
                this.positionX -= this.actualSpeed;
                break;
            default:
                break;
        }
    }

    /**
     * Display the Entity to the Canvas
     * @param gc canvas to draw in
     */
    public void render(GraphicsContext gc){
        double indexX = this.width * this.index;
        if (this.currentDirection == Direction.STATIC && isAlive)
            gc.drawImage(this.image, indexX, (this.newDirection.ordinal()+this.initialYEntityAlive) * this.height, this.width, this.height, this.positionX, this.positionY, this.width, this.height);
        else if (this.currentDirection != Direction.STATIC && isAlive)
            gc.drawImage(this.image, indexX, (this.currentDirection.ordinal()+this.initialYEntityAlive+4) * this.height ,this.width, this.height, this.positionX,this.positionY, this.width,this.height);
    }

    /**
     * Manages transition between frames
     * @param gc for draw
     * @param time time elapsed between the 2 frames
     */
    public abstract void nextFrame(Tilemap tilemap, GraphicsContext gc, double time, Player player);

    /**
     * Gets gitbox boundaries
     * @return Rectangle2D a hit box of the Entity
     */
    public Rectangle2D getBoundary(){
        return new Rectangle2D(this.positionX,this.positionY,this.width,this.height);
    }

    /**
     * @param entity entity to compare position with
     * @return entity is colliding with given entity
     */
    public boolean isColliding(Entity entity){
        return entity.getBoundary().intersects(this.getBoundary()) && this.isAlive;
    }

    /**
     * Respawn the Entity to (X,Y) position with updated characteristics
     * @param posX int X position to respawn
     * @param posY int Y position to respawn
     */
    public void respawn(int posX, int posY){
        this.positionX = posX;
        this.positionY = posY;
        this.isAlive = true;
    }

    /**
     * @return the dying animation timeline
     */
    public Timeline getDyingAnimation(){
        return this.dyingAnimation;
    }

    /**
     * Sets dying animation of entity
     * @param gc the canvas to draw in
     * @param yPositionOfDyingImageInEntitySheet int the initial position of the dying image in the Entity's sheet
     * @param firstImage int the initial index of dying image
     * @param lastImage int the last index of dying image
     * @param nbRepeat int number of time to repeat the animation
     */
    public void setDyingAnimation(GraphicsContext gc, int yPositionOfDyingImageInEntitySheet, int firstImage, int lastImage, int nbRepeat){
        Image imageWood = new Image("img/texture-0.png");
        Image imageBlood = new Image("img/blood.png");
        this.dyingAnimation = new Timeline();
        this.dyingAnimation.setCycleCount(nbRepeat);
        for (int i = firstImage; i < lastImage; i++){
            int finalI = i;
            this.dyingAnimation.getKeyFrames().add(new KeyFrame(Duration.millis(i * 100 + 100), (ActionEvent event) -> {
                gc.clearRect(this.positionX,this.positionY,this.width,this.height);
                gc.drawImage(imageWood, this.positionX, this.positionY, this.width, this.height);
                gc.drawImage(imageBlood, this.width * finalI, yPositionOfDyingImageInEntitySheet, this.width, this.height, this.positionX, this.positionY, this.width, this.height);
            }));
        }
    }

    /**
     * Make entity die
     */
    public abstract void dead();

    /**
     * Reset speed of Entity with the default one
     */
    public void reset(){
        this.actualSpeed = this.defaultSpeed;
    }

    @Override
    public String toString() {
        return "Entity{" +
                "SPRITEWIDTH=" + SPRITEWIDTH +
                ", image=" + image +
                ", initialXEntityAlive=" + initialXEntityAlive +
                ", initialYEntityAlive=" + initialYEntityAlive +
                ", nbImage=" + nbImage +
                ", positionX=" + positionX +
                ", positionY=" + positionY +
                ", defaultSpeed=" + defaultSpeed +
                ", actualSpeed=" + actualSpeed +
                ", isAlive=" + isAlive +
                ", killable=" + killable +
                ", newDirection=" + newDirection +
                ", currentDirection=" + currentDirection +
                ", width=" + width +
                ", height=" + height +
                ", index=" + index +
                ", dyingAnimation=" + dyingAnimation +
                '}';
    }
}