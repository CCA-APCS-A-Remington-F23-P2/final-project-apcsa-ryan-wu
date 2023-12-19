public class Cannon extends Block{
  public Cannon(){
    super(10,10,"cannon");
  }
  public Cannon(int x, int y){
    super(x,y,"cannon");
  }
  public boolean onCannon(Player player){
    return false;
  }
  
}