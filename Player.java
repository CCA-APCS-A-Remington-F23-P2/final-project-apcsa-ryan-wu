import java.io.File;
import java.net.URL;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import javax.imageio.ImageIO;
import java.util.List;

public class Player extends MovingThing {
    private int speed;
  private Image image;

  public Player(int player)
  {
    this(10,10,10,10,1, player);
  }

  public Player(int x, int y, int player)
  {
    //add code here
    this(x, y, 10, 10, 1, player);
  }

  public Player(int x, int y, int s, int player)
  {
    //add code here
    this(x, y, 10, 10, s, player);
    
  }

  // all ctors call this ctor
  public Player(int x, int y, int w, int h, int s, int player)
  {
    super(x, y, w, h);
    speed=s;
    if (player == 1) {
        try
        {
        URL url = getClass().getResource("Square.png");
        image = ImageIO.read(url);
        }
        catch(Exception e)
        {
        //feel free to do something here
        }
    } else {
        try
        {
        URL url = getClass().getResource("RedSquare.png");
        image = ImageIO.read(url);
        }
        catch(Exception e)
        {
        //feel free to do something here
        }   
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

}
