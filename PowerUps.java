import java.util.*;
import java.awt.Color;

public class PowerUps extends Player {
  ArrayList<String> playerpowers = new ArrayList<String>();

  public void empower(Player player) {
    // give player temporary speed and damage boost
    player.setXSpeed(getXSpeed() + 5);
    player.setYSpeed(getYSpeed() + 5);
  }

  public void AOE(Player player) {
    // deal damage to all blocks and enemies around player
  for(int i=0; i<Game.getBlockSize(); i++){
    if(Game.getBlocks(i).getX()>player.getX()-100 && Game.getBlocks(i).getX()<player.getX()+100 && Game.getBlocks(i).getY()>player.getY()-100 && Game.getBlocks(i).getY()<player.getY()+100){
      Game.getBlocks(i).setHealth(Game.getBlocks(i).getHealth()-2);
    }
  }
  }

  public void heal(Player player) {
    // add health to player, prevent damage for short duration
    player.setHealth(player.getHealth() + 4);
  }

  public void piercingShots(Player player) {
    // next few bullets are not destroyed after collisions and travel faster
  }

  public void wall(Player player) {
    // build a metal wall in front of player
    if(player.getFaceRight()){
    for(int i=0; i<4; i++){
    Game.addBlock(new Block(player.getX()+player.getWidth()+5, player.getY()-20*i, "metal"));
      Game.addBlock(new Block(player.getX()+player.getWidth()+25, player.getY()-20*i, "metal"));
    }
    }
      if(!player.getFaceRight()){
        for(int i=0; i<4; i++){
        Game.addBlock(new Block(player.getX()-5, player.getY()-20*i, "metal"));
        Game.addBlock(new Block(player.getX()-25, player.getY()-20*i, "metal"));
    }
  }
  }
  public void conjureEvilSpirit(Player player) {
    // create an evil spirit that follows the other player in both x and y axis. Phases through walls and damages enemies during contact
  }

  public void flashStrike(Player player){
    // teleport player ~7.5 blocks in direction they are going, damaging everything between starting and ending point
  }

}