package pack;

import java.net.*;

public class MultiPlayer extends Player
{
  public InetAddress ipAddress;
  public int port;
  
  public MultiPlayer(int x, int y, String username, InetAddress ipAddress, int port)
  {
    super(x, y, null, username);
    this.ipAddress = ipAddress;
    this.port = port;
  }  
  
  public MultiPlayer(int x, int y, KeyBoardLis inKey, String username, InetAddress ipAddress, int port)
  {
    super(x, y, inKey, username);
    this.ipAddress = ipAddress;
    this.port = port;
  }  
  
  
}
