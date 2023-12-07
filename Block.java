import java.awt.Graphics;
import java.awt.Image;
import java.net.URL;
import javax.imageio.ImageIO;


public class Block {
    
    private int x;
    private int y;
    private int s; // s by s block
    private int h; // # hits to die
    private Image img;
    
    public Block(int x, int y){
        this.x = x;
        this.y = y;
        s = 20;
        try{
            URL url = getClass().getResource("BrownBlock.jpeg");
            img = ImageIO.read(url);
        } catch(Exception e){}
    }
    public int getX(){ return x; }
    public int getY(){ return y; }
    public int getS(){ return s; }
    public int getHealth(){ return h; }
    public void setX(int x){ this.x = x; }
    public void setY(int y){ this.y = y; }
    public void changeX(int dx){ x += dx; }
    public void changeY(int dy){ y += dy; }
    public void decHealth(){ h--; }
    public void draw(Graphics window){
        window.drawImage(img, getX(), getY(), getS(), getS(),null);
    }
}
