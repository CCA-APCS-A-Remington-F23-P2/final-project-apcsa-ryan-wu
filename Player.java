import java.io.File;
import java.net.URL;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import javax.imageio.ImageIO;
import java.util.List;
import java.lang.Thread;

public class Player extends MovingThing {
  private int ySpeed;
  private int xSpeed;
  private Image image;
  private int count = 0;
  private long lastJump = 0;

  public Player(int player) {
    this(10, 10, 10, 10, 1, 1, player);
  }

  public Player(int x, int y, int player) {
    //add code here
    this(x, y, 10, 10, 1, 1, player);
  }

  public Player(int x, int y, int ys, int xs, int player) {
    //add code here
    this(x, y, 10, 10, ys, xs, player);

  }

  // all ctors call this ctor
  public Player(int x, int y, int w, int h, int ys, int xs, int player) {
    super(x, y, w, h);
    ySpeed = ys;
    xSpeed = xs;
    if (player == 1) {
      try {
        URL url = getClass().getResource("Square.png");
        image = ImageIO.read(url);
      } catch (Exception e) {
        //feel free to do something here
      }
    } else {
      try {
        URL url = getClass().getResource("RedSquare.png");
        image = ImageIO.read(url);
      } catch (Exception e) {
        //feel free to do something here
      }
    }
  }

  public void setYSpeed(int s) {
    //add more code
    ySpeed = s;
  }

  public void setXSpeed(int s) {
    //add more code
    xSpeed = s;
  }

  public int getYSpeed() {
    return ySpeed;
  }

  public int getXSpeed() {
    return xSpeed;
  }

  public void move(String direction) {
      setX(getX() + getXSpeed());
      setY(getY() - getYSpeed());
  }

    // public boolean inAir(List<Block> blocks){
    //     return didCollideTop(blocks) == null;
    // }

  public void gravity() {
    setYSpeed(getYSpeed() - 1);
  }

  public void build() {
    System.out.println("building");
  }

  public String toString() {
    return super.toString();
  }
  public void draw(Graphics window) {
    window.drawImage(image, getX(), getY(), getWidth(), getHeight(), null);
  }

  private boolean segmentsOverlap(int s1, int e1, int s2, int e2) {
    if (s1 < s2) return e1 >= s2;
    else return e2 >= s1;
  }

    public boolean isTouching(Block b){
        return segmentsOverlap(getX(), getX()+getWidth(), b.getX(), b.getX()+b.getS())
            && segmentsOverlap(getY(), getY()+getHeight(), b.getY(), b.getY()+b.getS());
    }

    public boolean isTouching(List<Block> blocks){
        for(Block b : blocks) if(isTouching(b)) return true; return false;
    }

    public void standingOnBlock(List<Block> blocks){}

    public void handleCollisions(List<Block> blocks){
        int dx = (xSpeed==0 ? 0 : (xSpeed>0 ? -1 : 1));
        int dy = (ySpeed==0 ? 0 : (ySpeed>0 ? 1 : -1));
        boolean touched = false;
        while(isTouching(blocks)){
            touched = true;
            setX(getX()+dx);
            setY(getY()+dy);
        }
        if(touched){
            setX(getX()-dx);
            setY(getY()-dy);
        }
    }
}