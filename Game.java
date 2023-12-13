import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Canvas;
import java.awt.event.ActionEvent;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import static java.lang.Character.*;
import java.awt.image.BufferedImage;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class Game extends Canvas implements KeyListener, Runnable {

  private boolean[] keys;
  private BufferedImage back;
  private Player playerOne;
  private Player playerTwo;
  private List <Block> blocks;

  public Game() {
    setBackground(Color.black);

    keys = new boolean[8];

    playerOne = new Player(400, 480, 20, 20, 3, 3, 1);
    playerTwo = new Player(50, 480, 20, 20, 3, 5, 2);

    keys = new boolean[8];
    playerOne = new Player(50, 480, 20, 20, 15, 3, 1);
    playerTwo = new Player(400, 480, 20, 20, 15, 5, 2);
    blocks = new ArrayList<Block>();
      blocks.add(new Block(50, 50, "metal"));
      blocks.add(new Block(100, 100, "wood"));

    this.addKeyListener(this);
    new Thread(this).start();

    setVisible(true);
  }

  public void update(Graphics window) {
    paint(window);
  }

  public void paint(Graphics window) {
    //set up the double buffering to make the game animation nice and smooth
    Graphics2D twoDGraph = (Graphics2D) window;

    //take a snap shop of the current screen and same it as an image
    //that is the exact same width and height as the current screen
    if (back == null)
      back = (BufferedImage)(createImage(getWidth(), getHeight()));

    //create a graphics reference to the back ground image
    //we will draw all changes on the background image
    Graphics graphToBack = back.createGraphics();

    graphToBack.setColor(Color.BLACK);
    graphToBack.fillRect(0, 0, 800, 600);
    graphToBack.setColor(Color.GREEN);
    graphToBack.fillRect(0, 500, 800, 100);

    playerOne.draw(graphToBack);
    playerTwo.draw(graphToBack);

    if (keys[0]) {
      playerOne.move("LEFT");
    }
    if (keys[1]) {
      playerOne.move("RIGHT");
    }
    if (keys[2]) {
      playerOne.move("UP");
    }
    if (keys[3]) {
      playerOne.move("DOWN");
    }
    if (keys[4]) {
      playerTwo.move("LEFT");
    }
    if (keys[5]) {
      playerTwo.move("RIGHT");
    }
    if (keys[6]) {
      playerTwo.move("UP");
    }
    if (keys[7]) {
      playerTwo.move("DOWN");
    }
      for(Block b : blocks){
          b.draw(graphToBack);
      }

    twoDGraph.drawImage(back, null, 0, 0);
  }

  public void keyPressed(KeyEvent e) {
    if (e.getKeyCode() == KeyEvent.VK_LEFT) {
      keys[0] = true;
    }
    if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
      keys[1] = true;
    }
    if (e.getKeyCode() == KeyEvent.VK_UP) {
      keys[2] = true;
    }
    if (e.getKeyCode() == KeyEvent.VK_DOWN) {
      keys[3] = true;
    }
    if (e.getKeyCode() == KeyEvent.VK_A) {
      keys[4] = true;
    }
    if (e.getKeyCode() == KeyEvent.VK_D) {
      keys[5] = true;
    }
    if (e.getKeyCode() == KeyEvent.VK_W) {
      keys[6] = true;
    }
    if (e.getKeyCode() == KeyEvent.VK_S) {
      keys[7] = true;
    }
    repaint();
  }

  public void keyReleased(KeyEvent e) {
    if (e.getKeyCode() == KeyEvent.VK_LEFT) {
      keys[0] = false;
    }
    if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
      keys[1] = false;
    }
    if (e.getKeyCode() == KeyEvent.VK_UP) {
      keys[2] = false;
    }
    if (e.getKeyCode() == KeyEvent.VK_DOWN) {
      keys[3] = false;
    }
    if (e.getKeyCode() == KeyEvent.VK_A) {
      keys[4] = false;
    }
    if (e.getKeyCode() == KeyEvent.VK_D) {
      keys[5] = false;
    }
    if (e.getKeyCode() == KeyEvent.VK_W) {
      keys[6] = false;
    }
    if (e.getKeyCode() == KeyEvent.VK_S) {
      keys[7] = false;
    }
    repaint();
  }

  public void keyTyped(KeyEvent e) {
    //no code needed here
  }

  public void run() {
    try {
      while (true) {
        Thread.currentThread().sleep(5);
        repaint();
      }
    } catch (Exception e) {}
  }

}