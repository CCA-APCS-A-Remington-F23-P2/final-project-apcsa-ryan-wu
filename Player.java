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
  private boolean faceRight;
  public Player() {
    this(10, 10, 10, 10, 10, 1, 1, 1);
  }

  public Player(int player) {
    this(10, 10, 10, 10, 10, 1, 1, player);
  }

  public Player(int x, int y, int player) {
    // add code here
    this(x, y, 10, 10, 10, 1, 1, player);
  }

  public Player(int x, int y, int ys, int xs, int player) {
    // add code here
    this(x, y, 10, 10, 10, ys, xs, player);

  }

  // all ctors call this ctor
  public Player(int x, int y, int w, int h, int hp, int ys, int xs, int player) {
    super(x, y, w, h, hp);
    ySpeed = ys;
    xSpeed = xs;
    if (player == 1) {
      try {
        URL url = getClass().getResource("Square.png");
        image = ImageIO.read(url);
      } catch (Exception e) {
        // feel free to do something here
      }
    } else {
      try {
        URL url = getClass().getResource("RedSquare.png");
        image = ImageIO.read(url);
      } catch (Exception e) {
      }
    }
  }

  public void setYSpeed(int s) {
    ySpeed = s;
  }

  public void setXSpeed(int s) {
    xSpeed = s;
  }

  public int getYSpeed() {
    return ySpeed;
  }

  public int getXSpeed() {
    return xSpeed;
  }
  public boolean getFaceRight() {
    return faceRight;
  }
  public void setFaceRight(boolean b) {
    faceRight = b;
  }

  public void move(List<Block> blocks) {
    double dx = getXSpeed() / 10.0;
    double dy = getYSpeed() / 10.0;
    double x = getX();
    double y = getY();
    boolean xDone = false;
    boolean yDone = false;
    for (int i = 0; i < 10; i++) {
      x += dx;
      y -= dy;
      if (!xDone) {
        setX((int) Math.round(x));
        if (isTouching(blocks)) {
          setX((int) Math.round(x - dx));
          xDone = true;
        }
      }
      if (!yDone) {
        setY((int) Math.round(y));
        if (isTouching(blocks)) {
          setY((int) Math.round(y + dy));
          yDone = true;
        }
      }

    }
  }

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
    if (s1 < s2)
      return e1 >= s2;
    else
      return e2 >= s1;
  }

  public boolean isTouching(Block b) {
    return segmentsOverlap(getX(), getX() + getWidth(), b.getX(), b.getX() + b.getS())
        && segmentsOverlap(getY(), getY() + getHeight(), b.getY(), b.getY() + b.getS());
  }

  public boolean isTouching(List<Block> blocks) {
    for (Block b : blocks)
      if (isTouching(b))
        return true;
    return false;
  }

  public boolean blockAbove(List<Block> blocks) {
    boolean ret = false;
    setY(getY() - 1);
    if (isTouching(blocks))
      ret = true;
    setY(getY() + 1);
    return ret;
  }

  public boolean blockBelow(List<Block> blocks) {
    boolean ret = false;
    setY(getY() + 1);
    if (isTouching(blocks))
      ret = true;
    setY(getY() - 1);
    return ret;
  }

  public boolean blockRight(Block b) {
    boolean ret = false;
    setX(getX() + 1);
    if (isTouching(b))
      ret = true;
    setX(getX() - 1);
    return ret;
  }

  public boolean blockLeft(Block b) {
    boolean ret = false;
    setX(getX() - 1);
    if (isTouching(b))
      ret = true;
    setX(getX() + 1);
    return ret;
  }

}