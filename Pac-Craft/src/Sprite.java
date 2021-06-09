import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.util.Duration;

public abstract class Sprite{
    enum Direction {
        UP,
        RIGHT,
        DOWN,
        LEFT,
        STATIC
    }

    final int SPRITEWIDTH = 40;

    private Image image;
    private int initialXSpriteAlive;
    private int initialYSpriteAlive;
    private int nbImage;
    private double positionX;
    private double positionY;
    private double defaultSpeed;
    private double actualSpeed;
    private boolean isAlive;
    private boolean killable;
    Direction newDirection;
    Direction currentDirection;
    private double width;
    private double height;
    private double index;
    private Timeline dyingAnimation;

    /**
     * generate a new Sprite
     * @param initialXSpriteAlive
     * @param initialYSpriteAlive
     * @param nbImage number of image for character
     * @param posX position X
     * @param posY position Y
     * @param defaultSpeed default speed for character
     * @param width width of picture
     * @param height height of picture
     */
    public Sprite(String path, int initialXSpriteAlive, int initialYSpriteAlive, int nbImage, int posX, int posY, double defaultSpeed, int width, int height, boolean killable){
        this.image = new Image(path);
        this.nbImage = nbImage;
        this.initialXSpriteAlive = initialXSpriteAlive;
        this.initialYSpriteAlive = initialYSpriteAlive;
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
     * @param initialYSpriteAlive int
     */
    public void setInitialYSpriteAlive(int initialYSpriteAlive) {
        this.initialYSpriteAlive = initialYSpriteAlive;
    }

    /**
     * @return double X position of Sprite
     */
    public double getPositionX() {
        return this.positionX;
    }

    /**
     * @param positionX double
     */
    public void setPositionX(double positionX) {
        this.positionX = positionX;
    }

    /**
     * @return double Y position of Sprite
     */
    public double getPositionY() {
        return this.positionY;
    }

    /**
     * @param positionY double
     */
    public void setPositionY(double positionY) {
        this.positionY = positionY;
    }

    /**
     * @return double default speed of Sprite
     */
    public double getDefaultSpeed() {
        return this.defaultSpeed;
    }

    /**
     * @param actualSpeed double
     */
    public void setActualSpeed(double actualSpeed) {
        this.actualSpeed = actualSpeed;
    }

    /**
     * @param alive boolean to set Sprite alive or not
     */
    public void setAlive(boolean alive) {
        this.isAlive = alive;
    }

    /**
     * @return Boolean True if can be killed, False if not
     */
    public boolean isKillable() {
        return this.killable;
    }

    /**
     * @param killable Boolean
     */
    public void setKillable(boolean killable) {
        this.killable = killable;
    }

    /**
     * @return double width of Sprite
     */
    public double getWidth() {
        return this.width;
    }

    /**
     * Return the current direction of character
     * @return current direction
     */
    public Sprite.Direction getCurrentDirection(){
        return this.currentDirection;
    }

    /**
     * Return the center of the sprite to X
     * @return Center of position character
     */
    public int getCenterPosX(){
        return (int) (this.positionX+this.SPRITEWIDTH /2);
    }

    /**
     * Return the center of the sprite to Y
     * @return Center of position character
     */
    public int getCenterPosY(){
        return (int) (this.positionY+this.SPRITEWIDTH /2);
    }

    /**
     * Defines the new direction of the sprite
     * @param direction new direction
     */
    public void setNewDirection(Direction direction){
        this.newDirection = direction;
    }

    /**
     * Used to update the Sprite's position in the canvas
     * @param t double time passed in the AnimationTimer
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
     * Display the Sprite to the Canvas
     * @param gc GraphicsContext the canvas to draw in
     */
    public void render(GraphicsContext gc){
        double indexX = this.width * this.index;
        if (this.currentDirection == Direction.STATIC && isAlive)
            gc.drawImage(this.image, indexX, (this.newDirection.ordinal()+this.initialYSpriteAlive) * this.height, this.width, this.height, this.positionX, this.positionY, this.width, this.height);
        else if (this.currentDirection!=Direction.STATIC && isAlive)
            gc.drawImage(this.image, indexX, (this.currentDirection.ordinal()+this.initialYSpriteAlive+4) * this.height ,this.width, this.height, this.positionX,this.positionY, this.width,this.height);
    }

    /**
     * Calls the different functions managing the display of the sprite at the next frame
     * @param gc GraphicContext for draw
     * @param time Time elapsed between the 2 frames
     */
    public abstract void nextFrame(Tilemap tilemap, GraphicsContext gc, double time, Player player);

    /**
     * Useful for the collision detection
     * @return Rectangle2D a hit box of the Sprite
     */
    public Rectangle2D getBoundary(){
        return new Rectangle2D(this.positionX,this.positionY,this.width,this.height);
    }

    /**
     * @param s Sprite to compare position with
     * @return boolean True if collide, False if not
     */
    public boolean isColliding(Sprite s){
        return s.getBoundary().intersects(this.getBoundary()) && this.isAlive;
    }

    /**
     * Respawn the Sprite to (X,Y) position with update characteristics
     * @param posX int X position to respawn
     * @param posY int Y position to respawn
     */
    public void respawn(int posX, int posY){
        this.positionX = posX;
        this.positionY = posY;
        this.isAlive = true;
/*        this.killable = false;
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                killable = true;
            }
        }, 5000);*/
    }

    /**
     * @return Timeline the dying animation
     */
    public Timeline getDyingAnimation(){
        return this.dyingAnimation;
    }

    /**
     * Animation of dead
     * @param gc the canvas to draw in
     * @param yPositionOfDyingImageInSpriteSheet int the initial position of the dying image in the Sprite's sheet
     * @param firstImage int the initial index of dying image
     * @param lastImage int the last index of dying image
     * @param nbRepeat int number of time to repeat the animation
     */
    public void setDyingAnimation(GraphicsContext gc, int yPositionOfDyingImageInSpriteSheet, int firstImage, int lastImage, int nbRepeat){
        Image imageWood = new Image("img/0.png");
        Image imageBlood = new Image("img/blood.png");
        this.dyingAnimation = new Timeline();
        this.dyingAnimation.setCycleCount(nbRepeat);
        for (int i = firstImage; i < lastImage; i++){
            int finalI = i;
            this.dyingAnimation.getKeyFrames().add(new KeyFrame(Duration.millis(i * 100 + 100), (ActionEvent event) -> {
                gc.clearRect(this.positionX,this.positionY,this.width,this.height);
                gc.drawImage(imageWood, this.positionX, this.positionY, this.width, this.height);
                gc.drawImage(imageBlood, this.width * finalI, yPositionOfDyingImageInSpriteSheet, this.width, this.height, this.positionX, this.positionY, this.width, this.height);
            }));
        }
    }

    /**
     * Methode to set character dead
     */
    public abstract void dead();

    /**
     * Reset speed of Sprite with default
     */
    public void reset(){
        this.actualSpeed = this.defaultSpeed;
    }
}