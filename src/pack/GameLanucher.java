package pack;

import java.applet.Applet;

public class GameLanucher extends Applet
{
  
  private static Game game = new Game(null);

  @Override
  public void init() {
  }

  @Override
  public void start() {
      game.start();
  }
//
  @Override
  public void stop() {
      System.exit(0);
      
  }

  public static void main(String[] args) {
      game.start();
  }
  
}
