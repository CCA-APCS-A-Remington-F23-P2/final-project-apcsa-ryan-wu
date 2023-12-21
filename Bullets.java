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

    public void explode(List<Block> blocks, int x, int y, int r, Player one, Player two){
        for(int i = 0; i < blocks.size(); i++){
            Block b = blocks.get(i);
            if(b.segmentsOverlap(x-20*r, x+20*r, b.getX(), b.getX()+b.getWidth())
              && b.segmentsOverlap(y-20*r, y+20*4, b.getY(), b.getY()+b.getHeight())){
                b.setHealth(b.getHealth()-1);
                if(b.getHealth() < 1){
                    blocks.remove(i--);
                }
            }
        }
        Player[] players = {one, two};
        for(Player p : players){
            if(p.segmentsOverlap(x-20*r, x+20*r, p.getX(), p.getX()+p.getWidth())
                  && p.segmentsOverlap(y-20*r, y+20*4, p.getY(), p.getY()+p.getHeight())){
                    p.setLives(p.getLives()-1);
                  }
        }
    }

    public void detectCollision(List<Block> blocks, Player one, Player two){
        for(int i = 0; i < blocks.size(); i++){
            for(int j = 0; j < ammo.size(); j++){
                Block b = blocks.get(i);
                Ammo a = ammo.get(j);
                if(b.didCollide(a)){
                    if(a.getCannonAmmo()){
                        explode(blocks, b.getX(), b.getY(), 4, one, two);
                    }
                     else{
                        b.setHealth(b.getHealth()-1);
                        if(b.getHealth() < 1){
                            blocks.remove(i--);
                        }
                    }
                    if(!a.getPiercingAmmo()) ammo.remove(j--);
                }
            }
        }
        Player[] players = {one, two};
        for(int i = 0; i < ammo.size(); i++){
            for(Player p : players){
                Ammo a = ammo.get(i);
                if(p.didCollide(a)){
                    if(a.getCannonAmmo()){
                        explode(blocks, p.getX(), p.getY(), 4, one, two);
                    }
                    p.setLives(p.getLives()-1);
                    if(!a.getPiercingAmmo()) ammo.remove(i--);
                }
            }

        }
    }

  // remove any Ammo which has reached the edge of the screen
  public void cleanUpEdges()
  {
    for (int i = 0; i < ammo.size(); i++) {
      if ((ammo.get(i).getX()<0) || (ammo.get(i).getX()>1000)) {
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
