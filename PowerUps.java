import java.util.*;
import java.awt.Color;

public class PowerUps extends Player {
  ArrayList<String> playerpowers = new ArrayList<String>();

  public void addSpeed(Player player) {
    player.setXSpeed(getXSpeed() + 5);
    player.setYSpeed(getYSpeed() + 5);
  }

  public void AOE(Player player) {
    for (int i = player.getX() - 50; i <= player.getX() + 50; i++) {
      for (int j = player.getY() - 50; j <= player.getY() + 50; j++) {
        // if collide with something subtract life
      }
    }
  }

  public void heal(Player player) {
    // add health to player, prevent damage for short duration
  }

  public void piercingShots(Player player) {
    // bullets are not destroyed after collisions
  }

  public void wall(Player player) {
    // build metal wall in front of player
  }

  public void conjureEvilSpirit(Player player) {
    // create an evil man that follows the other player
  }

  public void shrink(Player player) {
    // shrink player dimensions
  }
}