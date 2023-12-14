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
    //add code here
    if (direction.equals("LEFT")) {
      setX(getX() - xSpeed);
    }
    if (direction.equals("RIGHT")) {
      setX(getX() + xSpeed);
    }
    if (direction.equals("DOWN")) {
      build();
    }

  }

  public void gravity() {
    //add code here
    setYSpeed(getYSpeed() - 1);

  }

  public void jump() {
    setY(getY() - ySpeed);
    if (count == 2) {
      gravity();
      count = 0;
    }
    count++;
  }

  public boolean inAir(){
    return getY() <= 480;
  }

  public void reset() {
    setY(480);
    setYSpeed(10);
  }

  public void build() {
    System.out.println("building");
  }


  public String toString() {
    return super.toString();
  }
  public void draw(Graphics window) {
    window.drawImage(image, getX(), getY(), 30, 30, null);
  }
}