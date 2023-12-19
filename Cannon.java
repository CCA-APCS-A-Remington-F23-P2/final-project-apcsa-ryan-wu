public class Cannon extends Block{
  public Cannon(){
    super(10,10,"cannon");
  }
  public Cannon(int x, int y){
    super(x,y,"cannon");
  }
  public Cannon(int x, int y, String t){
        super(x, y, t);
    }
  public boolean onCannon(Player player){
    return false;
  }
  
}