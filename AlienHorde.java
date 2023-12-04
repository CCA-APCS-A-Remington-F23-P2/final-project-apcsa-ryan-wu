import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import javax.imageio.ImageIO;
import java.util.ArrayList;
import java.util.List;

public class AlienHorde
{
  private List<Alien> aliens;
  int size;
  boolean leftRight = true;
  int count = 0;
  int c = 0;
  
  public AlienHorde(int size)
  {
    aliens = new ArrayList<Alien>(size);
    for (int i = 0; i < 2; i++) {
      for (int j = 0; j < size; j++) {
        add(new Alien(100+50*j, 50+50*i, 50, 50, 1));
      }
    }
    this.size = size;
  }

  public void add(Alien al)
  {
    aliens.add(al);
  }

  public void addRow() {
    for (int i = 0; i < size; i++) {
      add(new Alien(aliens.get(0).getX()+50*i, aliens.get(0).getY()-50*c, 50, 50, 1));
    }
    c++;
  }

  public void draw(Graphics window)
  {
    for (Alien a : aliens) {
      a.draw(window);
    }
  }

  public void move(String direction)
  {
    if (direction.equals("DOWN")) {
      for(Alien a : aliens) {
        a.move("DOWN");
      }
    }
    for(Alien a : aliens) {
      a.move(leftRight ? "LEFT" : "RIGHT");
      
    }
    count++;
    if (count == 20) {
      leftRight = !leftRight;
      count=0;
    }
  }

  // calulate if Aliens are hit by shots, if so remove the shot and alien and return the number of hits
  public int calcHits(List<Ammo> shots)
  {
    int hits = 0;
    for (int i = 0; i < aliens.size(); i++) {
      for (int j = 0; j < shots.size(); j++) {
        Alien al = aliens.get(i);
        Ammo am = shots.get(j);
        if(al.didCollide(am)) {
          aliens.remove(i--);
          shots.remove(j--);
          hits++;
          break;
        }
      }
    }
    return hits;
  }

  public boolean crashesShip(Ship s) {
    boolean crashes = false;
    for (int i = 0; i < aliens.size(); i++) {
      if(aliens.get(i).didCollide(s)) {
        aliens.remove(i--);
        crashes=true;
      }
    }
    return crashes;
  }

  public Ammo randomShot() {
    if (aliens.size() == 0) {
      return null;
    }

    int maxY = -1;
    for(Alien a : aliens) {
      maxY = Math.max(maxY, a.getY());
    }
    
    Alien a;
    do {
      a = aliens.get((int)(Math.random()*aliens.size()));
    
    } while(a.getY() != maxY);
    return new Ammo(a.getX()+a.getWidth()/2-3, a.getY()+a.getHeight()+10, 6, 6, -4);
  }

  public String toString()
  {
    return "";
  }
}
