import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Canvas;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.io.File;
import java.util.Scanner;

public class Game extends Canvas implements KeyListener, Runnable {

  private boolean[] keys;
  private BufferedImage back;
  private Player playerOne;
  private Player playerTwo;
  private List<Block> blocks;
  private final long gravInterval = 75;
  private long lastGrav1 = 0;
  private long lastGrav2 = 0;
  private final int xSpeed = 2;
  private final int ySpeed = 3;
  long lastPrintTime = System.currentTimeMillis();

  public Game() {
    setBackground(Color.black);
    keys = new boolean[10];
    playerOne = new Player(400, 20, 20, 20, 0, 0, 1);
    playerTwo = new Player(80, 20, 20, 20, 0, 0, 2);
    blocks = new ArrayList<Block>();
    loadBlocks(true);
    this.addKeyListener(this);
    new Thread(this).start();

    setVisible(true);
  }

  public void loadBlocks(boolean newMap) {
    Scanner s;
    boolean mirror = false;
    try {
      if (newMap) {
        s = new Scanner(new File("BlockDataDefault.txt"));
        mirror = true;
      } else
        s = new Scanner(new File("BlockData.txt"));
      while (s.hasNextLine()) {
        int x = s.nextInt() * 20;
        int y = s.nextInt() * 20;
        String t = s.next();
        blocks.add(new Block(x, y, t));
        if (mirror)
          blocks.add(new Block(760 - x, y, t));
      }
    } catch (Exception e) {
    }
  }

  public void update(Graphics window) {
    paint(window);
  }

  public void paint(Graphics window) {
    // set up the double buffering to make the game animation nice and smooth
    Graphics2D twoDGraph = (Graphics2D) window;

    // take a snap shop of the current screen and same it as an image
    // that is the exact same width and height as the current screen
    if (back == null)
      back = (BufferedImage) (createImage(getWidth(), getHeight()));

    // create a graphics reference to the back ground image
    // we will draw all changes on the background image
    Graphics graphToBack = back.createGraphics();

    graphToBack.setColor(Color.BLACK);
    graphToBack.fillRect(0, 0, 800, 600);

    if (System.currentTimeMillis() - lastGrav1 > gravInterval) {
      playerOne.gravity();
      lastGrav1 = System.currentTimeMillis();
    }
    if (System.currentTimeMillis() - lastGrav2 > gravInterval) {
      playerTwo.gravity();
      lastGrav2 = System.currentTimeMillis();
    }
    if (playerOne.blockBelow(blocks))
      playerOne.setYSpeed(0);
    if (playerTwo.blockBelow(blocks))
      playerTwo.setYSpeed(0);
    if (playerOne.blockAbove(blocks))
      playerOne.setYSpeed(-1);
    if (playerTwo.blockAbove(blocks))
      playerTwo.setYSpeed(-1);
    if (keys[0] && !keys[1])
      playerOne.setXSpeed(-xSpeed);
    else if (!keys[0] && keys[1])
      playerOne.setXSpeed(xSpeed);
    else
      playerOne.setXSpeed(0);
    if (keys[2] && playerOne.blockBelow(blocks)) {
      playerOne.setYSpeed(ySpeed);
      lastGrav1 = System.currentTimeMillis();
    }
    if (keys[3])
      playerOne.build();
    if (keys[4] && !keys[5])
      playerTwo.setXSpeed(-xSpeed);
    else if (!keys[4] && keys[5])
      playerTwo.setXSpeed(xSpeed);
    else
      playerTwo.setXSpeed(0);
    if (keys[6] && playerTwo.blockBelow(blocks)) {
      playerTwo.setYSpeed(ySpeed);
      lastGrav2 = System.currentTimeMillis();
    }
    if (keys[7])
      playerTwo.build();

    playerOne.move(blocks);
    playerTwo.move(blocks);

    playerOne.draw(graphToBack);
    playerTwo.draw(graphToBack);

    /* Player 1 Build Block on top */
    if (keys[3] && !playerOne.blockBelow(blocks)) {
      if (System.currentTimeMillis() - lastPrintTime > 500) {
        blocks.add(new Block(playerOne.getX(), playerOne.getY() + (playerOne.getHeight() + 5), "wood"));
        System.out.println("Block added");
        lastPrintTime = System.currentTimeMillis();
      }
    }
    if (keys[7] && !playerTwo.blockBelow(blocks)) {
      if (System.currentTimeMillis() - lastPrintTime > 500) {
        blocks.add(new Block(playerTwo.getX(), playerTwo.getY() + (playerTwo.getHeight() + 5), "wood"));
        System.out.println("Block added");
        lastPrintTime = System.currentTimeMillis();
      }
    }

    if (keys[8]) {
      System.out.println("P1 Shooting");
    }
    if (keys[9]) {
      System.out.println("P2 Shooting");
    }

    for (Block b : blocks) {
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
    if (e.getKeyCode() == KeyEvent.VK_SPACE) {
      keys[8] = true;
    }
    if (e.getKeyCode() == KeyEvent.VK_Q) {
      keys[9] = true;
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
    if (e.getKeyCode() == KeyEvent.VK_SPACE) {
      keys[8] = false;
    }
    if (e.getKeyCode() == KeyEvent.VK_Q) {
      keys[9] = false;
    }
    repaint();
  }

  public void keyTyped(KeyEvent e) {
    // no code needed here
  }

  public void run() {
    try {
      while (true) {
        Thread.currentThread().sleep(5);
        repaint();
      }
    } catch (Exception e) {
    }
  }

}