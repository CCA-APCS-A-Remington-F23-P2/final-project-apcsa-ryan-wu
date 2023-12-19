import java.awt.Color;
import java.awt.Graphics;
import java.util.*;

public class Ammo extends MovingThing {
  private int speed;

  public Ammo() {
    this(0, 0, 1);
  }

  public Ammo(int x, int y, int w, int h, int s) {
    super(x, y, w, h);
    speed = s;
  }
  
  public Ammo(int x, int y) {
    this(x, y, 1);
  }

  public Ammo(int x, int y, int s) {
    setPos(x, y);
    speed = s;
  }

  public void setSpeed(int s) {
    speed = s;
  }

  public int getSpeed() {
    return speed;
  }

  public void draw(Graphics window) {
    window.setColor(Color.BLUE);
    window.fillRect(getX(), getY(), getWidth(), getHeight());
  }
  public void draw(Graphics window, Color color) {
    window.setColor(color);
    window.fillRect(getX(), getY(), 5, 5);
  }

  public void move(String direction) {
    if (direction.equalsIgnoreCase("LEFT")) {
      setX(getX() - speed);
    } else if (direction.equalsIgnoreCase("RIGHT")) {
      setX(getX() + speed);
    } else if (direction.equalsIgnoreCase("UP")) {
      setY(getY() - speed);
    } else if (direction.equalsIgnoreCase("DOWN")) {
      setY(getY() + speed);
    }
  }

  public void move(List<Block> blocks){}

  public String toString() {
    return super.toString() + getSpeed();
  }
}
