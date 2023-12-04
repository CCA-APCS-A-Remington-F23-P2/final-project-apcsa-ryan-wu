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

public class OuterSpace extends Canvas implements KeyListener, Runnable
{
  private Ship ship;
  private AlienHorde horde;
  private Bullets shots;
  private long lastShot = 0;
  private long lastAlienShot = 0;
  private long lastRow = 0;
  private int score = 0;
  private int lives = 3;
  private boolean paused = false;
  private boolean ended = false;

  /* uncomment once you are ready for this part
   *
   private AlienHorde horde;
   private Bullets shots;
  */

  private boolean[] keys;
  private BufferedImage back;

  public OuterSpace()
  {
    setBackground(Color.black);

    keys = new boolean[5];
    ship = new Ship(200, 500, 50, 50, 5);
    horde = new AlienHorde(10);
    shots = new Bullets();
    //instantiate other instance variables
    //Ship, Alien

    this.addKeyListener(this);
    new Thread(this).start();

    setVisible(true);
  }

  public void update(Graphics window)
  {
    paint(window);
  }

  public void paint( Graphics window )
  {
    if (ended) {
      return;
    }
    if (paused) {
      if (keys[4]) {
        paused = false;
      } else {
        return;
      }
    }
    //set up the double buffering to make the game animation nice and smooth
    Graphics2D twoDGraph = (Graphics2D)window;

    //take a snap shop of the current screen and same it as an image
    //that is the exact same width and height as the current screen
    if (back==null)
      back = (BufferedImage)(createImage(getWidth(),getHeight()));

    //create a graphics reference to the back ground image
    //we will draw all changes on the background image
    Graphics graphToBack = back.createGraphics();

    graphToBack.setColor(Color.BLUE);
    graphToBack.drawString("StarFighter ", 25, 50 );
    graphToBack.setColor(Color.BLACK);
    graphToBack.fillRect(0,0,800,600);
    graphToBack.setColor(Color.YELLOW);

    score += horde.calcHits(shots.getList());
    graphToBack.drawString("Score: " + score, 25, 50);
    graphToBack.drawString("Lives: " + lives, 25, 75);

    ship.draw(graphToBack);
    horde.draw(graphToBack);
    shots.draw(graphToBack);
    
    

    if (keys[0])
    {
      ship.move("LEFT");
    }
    if (keys[1])
    {
      ship.move("RIGHT");
    }
    if (keys[2])
    {
      ship.move("BOTTOM");
    }
    if (keys[3])
    {
      ship.move("TOP");
    }
    if (keys[4]) {
      if (System.currentTimeMillis() - lastShot > 250) {
        shots.add(new Ammo(ship.getX() + 20, ship.getY() - 10, 6, 6, 4));
        lastShot = System.currentTimeMillis();
      }
    if (System.currentTimeMillis() - lastAlienShot > 500) {
      horde.move("DEFAULT");
      lastAlienShot = System.currentTimeMillis();
    }
    }
    if (System.currentTimeMillis()-lastAlienShot > 1000) {
      shots.add(horde.randomShot());
      lastAlienShot = System.currentTimeMillis();
    }
    if (System.currentTimeMillis() - lastRow > 3000) {
      horde.addRow();
      lastRow = System.currentTimeMillis();
    }
    if (horde.crashesShip(ship) || ship.isHit(shots.getList())) {
      lives--;
      paused = true;
      keys[4] = false;
      graphToBack.setColor(Color.RED);
      graphToBack.drawString("Press space to continue", 500, 300);
    }
    //add code to move Ship, Alien, etc.
    horde.move("DOWN");
    shots.move("DEFAULT");

    if (lives == 0) {
      ended = true;
      graphToBack.setColor(Color.BLACK);
      graphToBack.fillRect(0, 0, 1000, 1000);
      graphToBack.setColor(Color.RED);
      graphToBack.drawString("Game has ended. Final Score: " + score, 200, 200);
    }


    //add in collision detection to see if Bullets hit the Aliens and if Bullets hit the Ship


    twoDGraph.drawImage(back, null, 0, 0);
  }


  public void keyPressed(KeyEvent e)
  {
    if (e.getKeyCode() == KeyEvent.VK_LEFT)
    {
      keys[0] = true;
    }
    if (e.getKeyCode() == KeyEvent.VK_RIGHT)
    {
      keys[1] = true;
    }
    if (e.getKeyCode() == KeyEvent.VK_UP)
    {
      keys[2] = true;
    }
    if (e.getKeyCode() == KeyEvent.VK_DOWN)
    {
      keys[3] = true;
    }
    if (e.getKeyCode() == KeyEvent.VK_SPACE)
    {
      keys[4] = true;
    }
    repaint();
  }

  public void keyReleased(KeyEvent e)
  {
    if (e.getKeyCode() == KeyEvent.VK_LEFT)
    {
      keys[0] = false;
    }
    if (e.getKeyCode() == KeyEvent.VK_RIGHT)
    {
      keys[1] = false;
    }
    if (e.getKeyCode() == KeyEvent.VK_UP)
    {
      keys[2] = false;
    }
    if (e.getKeyCode() == KeyEvent.VK_DOWN)
    {
      keys[3] = false;
    }
    if (e.getKeyCode() == KeyEvent.VK_SPACE)
    {
      keys[4] = false;
    }
    repaint();
  }

  public void keyTyped(KeyEvent e)
  {
    //no code needed here
  }

  public void run()
  {
    try
    {
      while(true)
      {
        Thread.currentThread().sleep(30);
        repaint();
      }
    }catch(Exception e)
    {
    }
  }
}

