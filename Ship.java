import java.io.File;
import java.net.URL;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import javax.imageio.ImageIO;
import java.util.List;

public class Ship extends MovingThing
{
  private int speed;
  private Image image;

  public Ship()
  {
    this(10,10,10,10,10);
  }

  public Ship(int x, int y)
  {
    //add code here
    this(x, y, 10, 10, 10);
  }

  public Ship(int x, int y, int s)
  {
    //add code here
    this(x, y, 10, 10, s);
    
  }

  // all ctors call this ctor
  public Ship(int x, int y, int w, int h, int s)
  {
    super(x, y, w, h);
    speed=s;
    try
    {
      URL url = getClass().getResource("ship.jpg");
      image = ImageIO.read(url);
    }
    catch(Exception e)
    {
      //feel free to do something here
    }
  }


  public void setSpeed(int s)
  {
    //add more code
    speed = s;
  }

  public int getSpeed()
  {
    return speed;
  }

  public void move(String direction)
  {
    //add code here
    if(direction.equals("LEFT")) {
      setX(getX() - speed);
    }
    if(direction.equals("RIGHT")) {
      setX(getX() + speed);
    }
    if(direction.equals("TOP")) {
      setY(getY() + speed);
    }
    if(direction.equals("BOTTOM")) {
      setY(getY() - speed);
    }
    
  }

  public void draw( Graphics window )
  {
    window.drawImage(image,getX(),getY(),getWidth(),getHeight(),null);
  }

  public String toString()
  {
    return super.toString() + getSpeed();
  }

  public boolean isHit(List<Ammo> shots) {
    boolean hit = false;
    for (int i = 0; i < shots.size(); i++) {
      if (didCollide(shots.get(i))) {
        shots.remove(i--);
        hit = true;
      }
    }
    return hit;
  }
}
