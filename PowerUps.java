import java.util.*;
import java.io.File;
import java.net.URL;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import javax.imageio.ImageIO;

import java.lang.Thread;

public class PowerUps extends Player {
  ArrayList < String > playerpowers = new ArrayList < String > ();
  Player p;
  long timerE = System.currentTimeMillis();
  long timerH = System.currentTimeMillis();
  long timerP = System.currentTimeMillis();

  Image image;

  public void empower(Player player) {
    timerP = System.currentTimeMillis();
    // give player temporary speed boost
    player.setXSpeed(player.getXSpeed() + 5);
    player.setYSpeed(player.getYSpeed() + 5);
    if (timerP - System.currentTimeMillis() > 30000) {
      player.setXSpeed(player.getXSpeed() - 5);
      player.setYSpeed(player.getYSpeed() - 5);
    }
  }

  public void wall(Player player, int x, int y) {
    // build a 2x4 metal wall in front of player
    if (player.getFaceRight()) {
      for (int i = 0; i < 4; i++) {
        Game.addBlock(new Block(x + 35, y - 20 * i, "metal"));
        Game.addBlock(new Block(x + 55, y - 20 * i, "metal"));
      }
    }
    if (!player.getFaceRight()) {
      for (int i = 0; i < 4; i++) {
        Game.addBlock(new Block(x - 25, y - 20 * i, "metal"));
        Game.addBlock(new Block(x - 45, y - 20 * i, "metal"));
      }
    }
  }

  public void heal(Player player) {
    // add health to player
    if(player.getLives()>=11)
      player.setLivesFinal(15);
    else {
    player.setLivesFinal(player.getLives() + 4);
    }
    // player.setImmunity(true);
    // timerH = System.currentTimeMillis();
    // if (timerH - System.currentTimeMillis() > 5000)
    //   player.setImmunity(false);
  }

  public void AOE(int x, int y, Player player, Player p) {
    // deal damage to all blocks and enemies around player
    for (int i = 0; i < Game.getBlockSize(); i++) {
      Block b = Game.getBlocks(i);
      if (b.segmentsOverlap(x - 100, x + 100, b.getX(), b.getX() + b.getWidth()) &&
        b.segmentsOverlap(y - 100, y + 100, b.getY(), b.getY() + b.getHeight())) {
        b.setHealth(b.getHealth() - 3);
        if (b.getHealth() < 1) {
          Game.removeBlock(i--);
        }
      }
    }
    if (p.segmentsOverlap(x - 100, x + 100, p.getX(), p.getX() + p.getWidth()) &&
      p.segmentsOverlap(y - 100, y + 100, p.getY(), p.getY() + p.getHeight())) {
      p.setLives(p.getLives() - 3);
    }
  }

  public void piercingShots(Player player) {
    // next few bullets are not destroyed after collisions and travel faster
    // timerP = System.currentTimeMillis();
    player.setPiercingAmmo(true);
    // if (timerP - System.currentTimeMillis() > 10000)
    //   player.setPiercingAmmo(false);
  }

  // public void conjureEvilSpirit(Graphics window, Player player, int x, int y) {
  //   // create an evil spirit that follows the other player in both x and y axis. Phases through walls and damages enemies during contact
  //   try {
  //     URL url = getClass().getResource("evilSpirit.png");
  //     image = ImageIO.read(url);
  //   } catch (Exception e) {}

  //   window.drawImage(image, x+20, y, null);
  // }

  public void flashStrike(Player player, Player p) {
    // teleport player ~7.5 blocks in direction they are going, damaging everything between starting and ending point
    int yd = 0;
    if (player.getYSpeed() == 0) yd = 0;
    if (player.getYSpeed() > 0) yd = 1;
    if (player.getYSpeed() < 0) yd = -1;
    int xd=1;
    if (player.getFaceRight() == true) xd = 1;
    if (player.getFaceRight() == false) xd = -1;
    int startx = player.getX();
    int starty = player.getY();
    int endx = player.getX() + xd * 150;
    int endy = player.getY() + yd * 150;
    if (endx > 900) endx = 900;
    if (endx < 0) endx = 0;
    if (endy > 500) endy = 500;
    if (endy < 0) endy = 0;
    int biggerx = 0;
    int smallerx = 0;
    int biggery = 0;
    int smallery = 0;
    if (startx > endx) {
      biggerx = startx;
      smallerx = endx;
    }
    if (startx <= endx) {
      biggerx = endx;
      smallerx = endx;
    }
    if (starty > endy) {
      biggery = starty;
      smallery = endy;
    }
    if (starty <= endy) {
      biggery = endy;
      smallery = starty;
    }
    boolean horizontal = false;
    if (yd != 0)
      horizontal = true;
    if (horizontal == false) {
      player.setX(endx);
      for (int i = 0; i < Game.getBlockSize(); i++) {
        Block b = Game.getBlocks(i);
        if (b.segmentsOverlap(smallerx, biggerx, b.getX(), b.getX() + b.getWidth()) &&
          b.segmentsOverlap(starty-50, starty+50, b.getY(), b.getY() + b.getHeight())) {
          b.setHealth(b.getHealth() - 1);
          if (b.getHealth() < 1) {
            Game.removeBlock(i--);
          }
        }
      }
      if (p.segmentsOverlap(smallerx, biggerx, p.getX(), p.getX() + p.getWidth()) &&
        p.segmentsOverlap(smallery, biggery, p.getY(), p.getY() + p.getHeight())) {
        p.setLives(p.getLives() - 2);
      }
    if (horizontal == true) {
      player.setX(endx);
      player.setY(endy);
      int slope = -1;
      if (endy - starty > 0 && endx - startx > 0 || starty - endy > 0 && startx - endx > 0)
        slope = 1;
      int k=smallery;
      for (int i = 0; i < Game.getBlockSize(); i++) {
        for (int j = smallerx; j < biggerx; j += 20) {
          Block b = Game.getBlocks(i);
          if (b.segmentsOverlap(smallerx+j-50, smallerx+j+50, b.getX(), b.getX()+b.getHeight()) && b.segmentsOverlap(smallery+k-50, smallery+k+50, b.getY(), b.getY()+b.getHeight())) {
            b.setHealth(b.getHealth() - 1);
            if (b.getHealth() < 1) {
              Game.removeBlock(i);
            }
          }
        }
        k+=20*slope;
      }
      k=smallery;
      for (int j = smallerx; j < biggerx; j += 20) {
        if (p.segmentsOverlap(smallerx+j-50, smallerx+j+50, p.getX(), p.getX()+p.getWidth()) && p.segmentsOverlap(smallery+k-50, smallery+k+50, p.getY(), p.getY()+p.getHeight())) {
          p.setLives(p.getLives() - 2);
        }
        k+=20*slope;
      }
    }
  }
  }
}