import java.awt.Color;
import java.awt.Graphics;
import java.util.List;

public abstract class MovingThing {
  private int xPos;
  private int yPos;
  private int width;
  private int height;
  private int health;

  public MovingThing() {
    xPos = 10;
    yPos = 10;
    width = 10;
    height = 10;
    health = 1;
  }

  public MovingThing(int x, int y) {
    xPos = x;
    yPos = y;
    width = 10;
    height = 10;
    health = 1;
  }

  public MovingThing(int x, int y, int w, int h, int hp) {
    // add code here
    xPos = x;
    yPos = y;
    width = w;
    height = h;
    health = hp;
  }

  public MovingThing(int x, int y, int w, int h) {
    // add code here
    xPos = x;
    yPos = y;
    width = w;
    height = h;
  }
  
  public void setPos(int x, int y) {
    // add code here
    xPos = x;
    yPos = y;
  }

  public void setX(int x) {
    // add code here
    xPos = x;
  }

  public void setY(int y) {
    // add code here
    yPos = y;
  }

  public int getX() {
    return xPos; // finish this method
  }

  public int getY() {
    return yPos; // finish this method
  }

  public void setWidth(int w) {
    // add code here
    width = w;
  }

  public void setHeight(int h) {
    // add code here
    height = h;
  }

  public int getWidth() {
    return width; // finish this method
  }

  public int getHeight() {
    return height; // finish this method
  }
  public void setHealth(int hp) {
    health = hp;
  }
  public int getHealth() {
    return health;
  }
  public abstract void move(List<Block> blocks);

  public abstract void draw(Graphics window);

  private boolean segmentsOverlap(int s1, int e1, int s2, int e2) {
    if (s1 < s2) {
      return e1 >= s2;
    } else {
      return e2 >= s1;
    }
  }

  public boolean didCollide(MovingThing a) {
    return segmentsOverlap(getX(), getX() + getWidth(), a.getX(), a.getX() + a.getWidth())
        && segmentsOverlap(getY(), getY() + getHeight(), a.getY(), a.getY() + a.getHeight());
  }

  public String toString() {
    return getX() + " " + getY() + " " + getWidth() + " " + getHeight();
  }
}