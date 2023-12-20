import java.util.*;
import java.awt.Color;

public class PowerUps extends Player {
  ArrayList<String> playerpowers = new ArrayList<String>();
  Player otherplayer;

  public void empower(Player player) {
    // give player temporary speed and damage boost
    player.setXSpeed(getXSpeed() + 5);
    player.setYSpeed(getYSpeed() + 5);
  }

  public void AOE(Player player, Player otherplayer) {
    // deal damage to all blocks and enemies around player
  for(int i=0; i<Game.getBlockSize(); i++){
    if(Game.getBlocks(i).getX()>player.getX()-100 && Game.getBlocks(i).getX()<player.getX()+100 && Game.getBlocks(i).getY()>player.getY()-100 && Game.getBlocks(i).getY()<player.getY()+100){
      Game.getBlocks(i).setHealth(Game.getBlocks(i).getHealth()-2);
    }
    if(otherplayer.getX()>player.getX()-100 && otherplayer.getX()<player.getX()+100 && otherplayer.getY()>player.getY()-100 && otherplayer.getY()<player.getY()+100){
      otherplayer.setHealth(otherplayer.getHealth()-4);
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
    // build a 2x4 metal wall in front of player
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

  public void flashStrike(Player player, Player otherplayer){
    // teleport player ~7.5 blocks in direction they are going, damaging everything between starting and ending point
    int yd=0;
    if(player.getYSpeed()==0) yd=0;
    if(player.getYSpeed()>0) yd=1;
    if(player.getYSpeed()<0) yd=-1;
    int xd=0;
    if(player.getFaceRight()==true) xd=1;
    if(player.getFaceRight()==false) xd=-1;
    int startx=player.getX();
    int starty=player.getY();
    int endx=player.getX()+xd*150;
    int endy=player.getY()+yd*150;
    if(endx>800) endx=800;
    if(endx<0) endx=0;
    if(endy>600) endy=600;
    if(endy<0) endy=0;
    int biggerx=0;
    int smallerx=0;
    int biggery=0;
    int smallery=0;
    if(startx>endx){
      biggerx=startx;
      smallerx=endx;
    }
    if(startx<=endx){
      biggerx=endx;
      smallerx=endx;
    }
    if(starty>endy){
      biggery=starty;
      smallery=endy;
    }
    if(starty<=endy){
      biggery=endy;
      smallery=starty;
    }
    boolean horizontal=false;
    if(yd!=0)
      horizontal=true;
    if(horizontal==false){
      player.setX(endx);
      for(int i=0; i<Game.getBlockSize(); i++){
    if(Game.getBlocks(i).getX()>smallerx && Game.getBlocks(i).getX()<biggerx && Game.getBlocks(i).getY()>starty+40 && Game.getBlocks(i).getY()<endy-40){
      Game.getBlocks(i).setHealth(Game.getBlocks(i).getHealth()-1);
      
    }
    }
      if(otherplayer.getX()>smallerx && otherplayer.getX()<biggerx && otherplayer.getY()>smallery && otherplayer.getY()<biggery){
        otherplayer.setHealth(otherplayer.getHealth()-3);
      }
  }
    if(horizontal==true){
      player.setX(endx);
      player.setY(endy);
      int slope=-1;
      if(endy-starty>0 && endx-startx>0 || starty-endy>0 && startx-endx>0)
        slope=1;
      for(int i=0; i<Game.getBlockSize(); i++){
        for(int j=smallerx; j<biggerx; j+=20){
          if(Game.getBlocks(i).getX()==j && Game.getBlocks(i).getY()<=slope*j+starty+40 && Game.getBlocks(i).getY()>=slope*j+starty-40){
            Game.getBlocks(i).setHealth(Game.getBlocks(i).getHealth()-1);
            }
          }
        }
        for(int j=smallerx; j<biggerx; j+=20){
          if(otherplayer.getX()>smallerx && otherplayer.getX()<biggerx && otherplayer.getY()>=slope*j*starty+40 && otherplayer.getY()<=slope*j+starty-40){
            otherplayer.setHealth(otherplayer.getHealth()-3);
        }
    }
    }
  }
}