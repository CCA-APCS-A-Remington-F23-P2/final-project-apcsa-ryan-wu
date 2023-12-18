import java.awt.Graphics;
import java.awt.Image;
import java.net.URL;
import javax.imageio.ImageIO;

public class Block {

  private int x;
  private int y;
  private int s; // s by s block
  private int h=1; // # hits to die
  private Image img;
  private String type;
  public Block(){
    this.x=0;
    this.y=0;
    s=20;
    h=1;
  }
  public Block(int x, int y, String type) {
    this.x = x;
    this.y = y;
    s = 20;
    if (type.equals("wood")) {
      try {
        URL url = getClass().getResource("BrownBlock.jpeg");
        img = ImageIO.read(url);
        h=1;
      } catch (Exception e) {
      }
    } else if (type.equals("metal")) {
      try {
        URL url = getClass().getResource("GreyBlock.png");
        img = ImageIO.read(url);
        h=3;
      } catch (Exception e) {
      }
    } else if (type.equals("cannon")){
      try {
        URL url = getClass().getResource("Cannon.png");
        img = ImageIO.read(url);
        h=1000;
      } catch (Exception e) {
      }
    }

  }

  public int getX() {
    return x;
  }

  public int getY() {
    return y;
  }

  public int getS() {
    return s;
  }

  public int getHealth() {
    return h;
  }

  public void setX(int x) {
    this.x = x;
  }

  public void setY(int y) {
    this.y = y;
  }

  public void changeX(int dx) {
    x += dx;
  }

  public void changeY(int dy) {
    y += dy;
  }

  public void decHealth() {
    h--;
  }

  public void draw(Graphics window) {
    window.drawImage(img, getX(), getY(), getS(), getS(), null);
  }

  public static boolean updateTextFile() {
    return true;
  }
}