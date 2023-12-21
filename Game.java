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
import java.awt.Font;

public class Game extends Canvas implements KeyListener, Runnable {

  private boolean[] keys;
  private BufferedImage back;
  private Player playerOne;
  private Player playerTwo;
  public static List<Block> blocks;
  private final long gravInterval = 75;
  private long lastGrav1 = 0;
  private long lastGrav2 = 0;
  private final int xSpeed = 2;
  private final int ySpeed = 4;
  long lastPrintTime = System.currentTimeMillis();
  private long lastCannon1Shot = 0;
  private long lastPlayer2Shot = 0;
  private long lastCannon2Shot = 0;
  private long lastPlayer1Shot = 0;
  private Bullets cannon1;
  private Bullets player2;
  private Bullets cannon2;
  private Bullets player1;
  
  public static void addBlock(Block a){
    blocks.add(a);
  }
  public static Block getBlocks(int i){
    return blocks.get(i);
  }
  public static int getBlockSize(){
    return blocks.size();
  }
  
  public Game() {
    setBackground(Color.black);
    keys = new boolean[10];
    playerOne = new Player(880, 30, 18, 18, 15, 0, 0, 1);
    playerTwo = new Player(80, 30, 18, 18, 15, 0, 0, 2);
    blocks = new ArrayList<Block>();
    loadBlocks(true);
    cannon1 = new Bullets();
    player2 = new Bullets();
    cannon2 = new Bullets();
    player1 = new Bullets();
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
        if(t.equals("cannon")){
            blocks.add(new Cannon(x, y, "cannon"));
            if(mirror) blocks.add(new Cannon(960-x, y, "reversecannon"));
        } else{
            blocks.add(new Block(x, y, t));
            if(mirror) blocks.add(new Block(960-x, y, t));
        }
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
    graphToBack.fillRect(0, 0, 1000, 600);

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
    if (keys[0] && !keys[1]){
      playerOne.setXSpeed(-xSpeed);
      playerOne.setFaceRight(false);
    }else if (!keys[0] && keys[1]){
      playerOne.setXSpeed(xSpeed);
      playerOne.setFaceRight(true);
    }else
      playerOne.setXSpeed(0);
    if (keys[2] && playerOne.blockBelow(blocks)) {
      playerOne.setYSpeed(ySpeed);
      lastGrav1 = System.currentTimeMillis();
    }
    if (keys[3])
      playerOne.build();
    if (keys[4] && !keys[5]){
      playerTwo.setXSpeed(-xSpeed);
      playerTwo.setFaceRight(false);
    }else if (!keys[4] && keys[5]){
      playerTwo.setXSpeed(xSpeed);
      playerTwo.setFaceRight(true);
    }else
      playerTwo.setXSpeed(0);
    if (keys[6] && playerTwo.blockBelow(blocks)) {
      playerTwo.setYSpeed(ySpeed);
      lastGrav2 = System.currentTimeMillis();
    }
    if (keys[7])
      playerTwo.build();

    playerOne.move(blocks);
    playerTwo.move(blocks);
    cannon1.move("RIGHT");
    player2.move("RIGHT");
    cannon2.move("LEFT");
    player1.move("LEFT");
    player1.detectCollision(blocks, playerOne, playerTwo);
    player2.detectCollision(blocks, playerOne, playerTwo);
    cannon1.detectCollision(blocks, playerOne, playerTwo);
    cannon2.detectCollision(blocks, playerOne, playerTwo);

    playerOne.draw(graphToBack);
    playerTwo.draw(graphToBack);
    cannon1.draw(graphToBack);
    player2.draw(graphToBack);
    cannon2.draw(graphToBack);
    player1.draw(graphToBack);

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
      boolean player1Shot = true;
      for (Block b : blocks) {
        if (b.getClass() == Cannon.class && playerOne.blockLeft(b)) {
          player1Shot = false;
          if (System.currentTimeMillis() - lastCannon2Shot > 500) {
            cannon2.add(new Ammo(b.getX()-10, b.getY() + 5, 10, 10, 3, true));
            lastCannon2Shot = System.currentTimeMillis();
          }
        }
      }
      if (player1Shot) {
        if (System.currentTimeMillis() - lastPlayer1Shot > 250 && !playerOne.getFaceRight()) {
          if(playerOne.getPiercingAmmo()){
          player1.add(new Ammo(playerOne.getX() - 10, playerOne.getY() + 6, 5, 5, 5, false, true));
            lastPlayer1Shot = System.currentTimeMillis();
          } else{
          player1.add(new Ammo(playerOne.getX() - 10, playerOne.getY() + 6, 5, 5, 5, false));
          lastPlayer1Shot = System.currentTimeMillis();
        }
        }
        else if (System.currentTimeMillis() - lastPlayer1Shot > 250 && playerOne.getFaceRight()) {
          if(playerOne.getPiercingAmmo()){
          player1.add(new Ammo(playerOne.getX() + 18, playerOne.getY() + 6, 5, 5, -5, false, true));
            lastPlayer1Shot = System.currentTimeMillis();
          } else{
          player1.add(new Ammo(playerOne.getX() + 18, playerOne.getY() + 6, 5, 5, -5, false));
          lastPlayer1Shot = System.currentTimeMillis();
          }
        }
      }
    }
    if (keys[9]) {
      boolean player2Shot = true;
      for (Block b : blocks) {
        if (b.getClass() == Cannon.class && playerTwo.blockRight(b)) {
          player2Shot = false;
          if (System.currentTimeMillis() - lastCannon1Shot > 500) {
            cannon1.add(new Ammo(b.getX() + 15, b.getY() + 5, 10, 10, 3, true));
            lastCannon1Shot = System.currentTimeMillis();
          }
        }
      }
      if (player2Shot) {
        if (System.currentTimeMillis() - lastPlayer2Shot > 250 && playerTwo.getFaceRight()) {
          if(playerTwo.getPiercingAmmo()){
          player2.add(new Ammo(playerTwo.getX() + 18, playerTwo.getY() + 6, 5, 5, 7, false, true));
            lastPlayer1Shot = System.currentTimeMillis();
          } else{
          player2.add(new Ammo(playerTwo.getX() + 18, playerTwo.getY() + 6, 5, 5, 7, false));
          lastPlayer2Shot = System.currentTimeMillis();
        }
        }
        else if (System.currentTimeMillis() - lastPlayer2Shot > 250 && !playerTwo.getFaceRight()) {
          if(playerTwo.getPiercingAmmo()){
          player2.add(new Ammo(playerTwo.getX() - 10, playerTwo.getY() + 6, 5, 5, -7,  false, true));
            lastPlayer1Shot = System.currentTimeMillis();
          } else{
          player2.add(new Ammo(playerTwo.getX() - 10, playerTwo.getY() + 6, 5, 5, -7, false));
          lastPlayer2Shot = System.currentTimeMillis();
        }
        }
      }
    }
    //remove destroyed blocks
    for (int i = 0; i < blocks.size(); i++) {
    if(blocks.get(i).getHealth()<=0) 
      blocks.remove(i);
    }

    for (Block b : blocks) {
      b.draw(graphToBack);
    }
      
      if(playerOne.getY() > 580){
          playerOne.setLivesFinal(0);
      }
      if(playerTwo.getY() > 580){
          playerTwo.setLivesFinal(0);
      }
      graphToBack.setColor(Color.GREEN);
      graphToBack.drawString("lives: " + playerTwo.getLives(), 20, 20);
      graphToBack.drawString("lives: " + playerOne.getLives(), 700, 20);
      if(playerOne.getLives()==0 || playerTwo.getLives()==0){
          graphToBack.setFont(new Font("TimesRoman", Font.PLAIN, 30));
          graphToBack.setColor(Color.RED);
          graphToBack.drawString("GAME OVER", 300, 250);
      }

    twoDGraph.drawImage(back, null, 0, 0);
  }

  public void keyPressed(KeyEvent e) {
    if (e.getKeyCode() == KeyEvent.VK_J) {
      keys[0] = true;
    }
    if (e.getKeyCode() == KeyEvent.VK_L) {
      keys[1] = true;
    }
    if (e.getKeyCode() == KeyEvent.VK_I) {
      keys[2] = true;
    }
    if (e.getKeyCode() == KeyEvent.VK_K) {
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
    if (e.getKeyCode() == KeyEvent.VK_U) {
      keys[8] = true;
    }
    if (e.getKeyCode() == KeyEvent.VK_E) {
      keys[9] = true;
    }
    repaint();
  }

  public void keyReleased(KeyEvent e) {
    if (e.getKeyCode() == KeyEvent.VK_J) {
      keys[0] = false;
    }
    if (e.getKeyCode() == KeyEvent.VK_L) {
      keys[1] = false;
    }
    if (e.getKeyCode() == KeyEvent.VK_I) {
      keys[2] = false;
    }
    if (e.getKeyCode() == KeyEvent.VK_K) {
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
    if (e.getKeyCode() == KeyEvent.VK_U) {
      keys[8] = false;
    }
    if (e.getKeyCode() == KeyEvent.VK_E) {
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