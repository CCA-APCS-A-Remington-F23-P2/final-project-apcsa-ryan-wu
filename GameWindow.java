import javax.swing.JFrame;
import java.awt.Component;

public class GameWindow extends JFrame {
  private static final int WIDTH = 1000;
  private static final int HEIGHT = 600;

  public GameWindow() {
    super("GAME NAME HERE");
    setSize(WIDTH, HEIGHT);

    Game theGame = new Game();
    ((Component) theGame).setFocusable(true);

    getContentPane().add(theGame);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setVisible(true);
  }

  public static void main(String args[]) {
    GameWindow run = new GameWindow();
  }
}