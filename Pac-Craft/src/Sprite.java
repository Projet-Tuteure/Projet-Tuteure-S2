import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.util.Duration;

public class Sprite{
    enum Direction {
        HAUT,
        DROITE,
        BAS,
        GAUCHE,
        STATIQUE
    };

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
    private int direction;
    Direction newDirection;
    Direction currentDirection;
    private double width;
    private double height;
    private double index;
    private Timeline dyingAnimation;

    /** generate a new Sprite
     * @param initialXSpriteAlive
     * @param initialYSpriteAlive
     * @param nbImage
     * @param posX
     * @param posY
     * @param defaultSpeed
     * @param width
     * @param height
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
        this.direction = 0;
        this.newDirection = Direction.STATIQUE;
        this.currentDirection = Direction.STATIQUE;
        this.width = width;
        this.height = height;
        this.index = 0;
    }

    /**
     * @return Image the Sprite's sheet
     */
    public Image getImage() {
        return this.image;
    }

    /**
     * @param image Image Sprite's sheet
     */
    public void setImage(Image image) {
        this.image = image;
    }

    /**
     * @return int initial X position of the Sprite in the Sprite's sheet
     */
    public int getInitialXSpriteAlive() {
        return this.initialXSpriteAlive;
    }

    /**
     * @param initialXSpriteAlive int
     */
    public void setInitialXSpriteAlive(int initialXSpriteAlive) {
        this.initialXSpriteAlive = initialXSpriteAlive;
    }

    /**
     * @return int initial Y position of the Sprite in the Sprite's sheet
     */
    public int getInitialYSpriteAlive() {
        return this.initialYSpriteAlive;
    }

    /**
     * @param initialYSpriteAlive int
     */
    public void setInitialYSpriteAlive(int initialYSpriteAlive) {
        this.initialYSpriteAlive = initialYSpriteAlive;
    }

    /**
     * @return int number of image of the Sprite
     */
    public int getNbImage() {
        return nbImage;
    }

    /**
     * @param nbImage int
     */
    public void setNbImage(int nbImage) {
        this.nbImage = nbImage;
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
     * @param defaultSpeed double
     */
    public void setDefaultSpeed(double defaultSpeed) {
        this.defaultSpeed = defaultSpeed;
    }

    /**
     * @return double actual speed of Sprite
     */
    public double getActualSpeed() {
        return this.actualSpeed;
    }

    /**
     * @param actualSpeed double
     */
    public void setActualSpeed(double actualSpeed) {
        this.actualSpeed = actualSpeed;
    }

    /**
     * @return boolean True if alive, False if not
     */
    public boolean isAlive() {
        return this.isAlive;
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
     * @param width double
     */
    public void setWidth(double width) {
        this.width = width;
    }

    /**
     * @return double index used to display a specific image of Sprite
     */
    public double getIndex() {
        return this.index;
    }

    /**
     *
     * @param index
     */
    public void setIndex(double index) {
        this.index = index;
    }

    /**
     * @return double Height of Sprite
     */
    public double getHeight() {
        return this.height;
    }

    /**
     * @param height of Sprite
     */
    public void setHeight(double height) {
        this.height = height;
    }

    /**
     * @return int direction of Sprite, 0 -> top ; 1 -> right ; 2 -> bottom ; 3 -> left
     */
    public int getDirection() {
        return this.direction;
    }

    /**
     * @param direction int : 0 -> top ; 1 -> right ; 2 -> bottom ; 3 -> left
     */
    public void setDirection(int direction) {
        this.direction = direction;
    }

    /**
     * retourne le centre du sprite en X
     * @return
     */
    public int getCenterPosX(){
        return (int) (this.positionX+this.SPRITEWIDTH /2);
    }

    /**
     * Retourne le centre du sprite en Y
     * @return
     */
    public int getCenterPosY(){
        return (int) (this.positionY+this.SPRITEWIDTH /2);
    }

    /**
     * Définit la nouvelle direction du sprite
     * @param direction
     */
    public void setNewDirection(Direction direction){
        this.newDirection = direction;
    }

    /** Used to update the Sprite's position in the canva
     * @param t double time passed in the AnimationTimer
     */
    public void update(double t){
        this.index = (int) ((t%(this.nbImage * 0.1 )) / 0.1);
        switch (this.currentDirection){
            case HAUT:
                this.positionY -=this.actualSpeed;
                break;
            case BAS:
                this.positionY +=this.actualSpeed;
                break;
            case DROITE:
                this.positionX +=this.actualSpeed;
                break;
            case GAUCHE:
                this.positionX -=this.actualSpeed;
                break;
            default:
                break;
        }
    }

    /** Display the Sprite to the Canvas
     * @param gc GraphicsContext the canvas to draw in
     */
    public void render(GraphicsContext gc) { // à modifier ici pour les collisions
        double sX;
        if (this.actualSpeed == 0 && isAlive){
            gc.drawImage(this.image, this.initialXSpriteAlive, this.currentDirection.ordinal() * this.height, this.width, this.height, this.positionX, this.positionY, this.width, this.height);
        } else if (this.actualSpeed != 0 && isAlive){
            sX = this.width * this.index;
            gc.drawImage(this.image, sX, (this.currentDirection.ordinal()+4) * this.height ,this.width, this.height, this.positionX,this.positionY, this.width,this.height);
        }
    }

    /**
     * Appelle les différentes fonctions gérant l'affichage du sprite à la prochaine frame
     * @param gc GraphicContext dans lequel dessiner
     * @param t Temps écoulé entre les 2 frame
     */
    public void nextFrame(Tilemap tilemap,GraphicsContext gc, double t){
        if (tilemap.isCenter(this.getCenterPosX(), this.getCenterPosY())){
            if (Collision.notCollidingWithWalls(this, tilemap)){
                this.currentDirection = this.newDirection;
            } else if (this.currentDirection == this.newDirection){
                this.currentDirection = Sprite.Direction.STATIQUE;
            }
        }

        this.update(t);

        this.render(gc);
    }

    /** Useful for the collision detection
     * @return Rectangle2D a hit box of the Sprite
     */
    public Rectangle2D getBoundary() {
        return new Rectangle2D(this.positionX,this.positionY,this.width,this.height);
    }

    /**
     * @param s Sprite to compare position with
     * @return boolean True if collide, False if not
     */
    public boolean isColliding(Sprite s) {
        if (s.getBoundary().intersects(this.getBoundary()) && this.isAlive){
            return true;
        }
        return false;
    }

    /** Respawn the Sprite to (X,Y) position with update characteristics
     * @param posX int X position to respawn
     * @param posY int Y position to respawn
     */
    public void respawn(int posX, int posY){
        this.positionX = posX;
        this.positionY = posY;
        this.isAlive = true;
    }

    /**
     * @return Timeline the dying animation
     */
    public Timeline getDyingAnimation() {
        return this.dyingAnimation;
    }

    /**
     * animation of dead
     * @param gc the canva to draw in
     * @param xPositionOfDyingImageInSpriteSheet int the initial position of the dying image in the Sprite's sheet
     * @param firstImage int the initial index of dying image
     * @param lastImage int the last index of dying image
     * @param nbRepeat int number of time to repeat the animation
     */
    public void setDyingAnimation(GraphicsContext gc, int xPositionOfDyingImageInSpriteSheet, int firstImage, int lastImage, int nbRepeat){
        this.dyingAnimation = new Timeline();
        this.dyingAnimation.setCycleCount(nbRepeat);
        for (int i = firstImage; i < lastImage; i++){
            int finalI = i;
            this.dyingAnimation.getKeyFrames().add(new KeyFrame(Duration.millis(i * 100 + 100), (ActionEvent event) -> {
                gc.clearRect(this.positionX,this.positionY,this.width,this.height);
                gc.drawImage(this.image, xPositionOfDyingImageInSpriteSheet, this.height * finalI, this.width, this.height, this.positionX, this.positionY, this.width, this.height);
            }));
        }
    }

    /**
     * Reset speed of Sprite with default
     */
    public void reset(){
        this.actualSpeed = this.defaultSpeed;
    }

    @Override
    public String toString() {
        return "Sprite{" +
                "image=" + image +
                ", initialXSpriteAlive=" + initialXSpriteAlive +
                ", initialYSpriteAlive=" + initialYSpriteAlive +
                ", nbImage=" + nbImage +
                ", positionX=" + positionX +
                ", positionY=" + positionY +
                ", defaultSpeed=" + defaultSpeed +
                ", actualSpeed=" + actualSpeed +
                ", isAlive=" + isAlive +
                ", killable=" + killable +
                ", direction=" + direction +
                ", width=" + width +
                ", height=" + height +
                ", index=" + index +
                ", dyingAnimation=" + dyingAnimation +
                '}';
    }
}