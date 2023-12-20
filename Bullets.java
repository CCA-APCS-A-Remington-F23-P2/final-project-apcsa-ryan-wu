import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.util.ArrayList;
import java.util.List;

public class Bullets
{
  private List<Ammo> ammo;

  public Bullets()
  {
    ammo = new ArrayList<Ammo>();
  }

  public void add(Ammo al)
  {
    ammo.add(al);
  }

  //post - draw each Ammo
  public void draw(Graphics window)
  {
    for (Ammo a : ammo) {
      a.draw(window);
    }
  }

  public void move(String direction)
  {
    for (Ammo a : ammo) {
      a.move(direction);
    }
  }

    public void detectCollision(List<Block> blocks){
        for(int i = 0; i < blocks.size(); i++){
            for(int j = 0; j < ammo.size(); j++){
                Block b = blocks.get(i);
                Ammo a = ammo.get(j);
                if(b.didCollide(a)){
                    if(a.getCannonAmmo()){
                        // explode
                        
                    }
                    // remove this ammo and dec block health by 1, if health<1 remove it
                    ammo.remove(j--);
                    b.setHealth(b.getHealth()-1);
                    if(b.getHealth() < 1){
                        blocks.remove(i--);
                    }
                }
            }
        }
    }

  // remove any Ammo which has reached the edge of the screen
  public void cleanUpEdges()
  {
    for (int i = 0; i < ammo.size(); i++) {
      if (ammo.get(i).getY()<0) {
        ammo.remove(i);
        i--;
      }
    }
  }

  public List<Ammo> getList()
  {
    return ammo;
  }

  public String toString()
  {
    return "";
  }
}
