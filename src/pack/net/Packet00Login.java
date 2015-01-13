package pack.net;

public class Packet00Login extends Packet
{
  private String username;
  private int x, y, style, screen, time;
  
  public Packet00Login(byte[] data)
  {
    super(00);
    String[] dataArray = readData(data).split(",");
    this.username = dataArray[0];
    this.x = Integer.parseInt(dataArray[1]);
    this.y = Integer.parseInt(dataArray[2]);
    this.style = Integer.parseInt(dataArray[3]);
    this.screen = Integer.parseInt(dataArray[4]);
    this.time = Integer.parseInt(dataArray[5]);
  }
  
  public Packet00Login(String username, int x, int y, int style, int screen, int time) {
    super(00);
    this.username = username;
    this.x = x;
    this.y = y;
    this.style= style;
    this.screen=screen;
    this.time=time;
    }

  @Override
  public void writeData(GameClient client) {
    client.sendData(getData());    
  }

  @Override
  public void writeData(GameServer server)
  {
    server.sendDataToAllClients(getData());
  }

  @Override
  public byte[] getData()
  {
    return ("00"+ this.username + "," + getX() + "," + getY() + "," + getStyle() +
        "," + getScreen() + "," + time).getBytes();
  }
  
  public String getUsername()
  {
    return username;
  }

  public int getX()
  {
    return this.x;
  }
  
  public int getY()
  {
    return this.y;
  }
  
  public int getStyle()
  {
    return this.style;
  }
  
  public int getScreen()
  {
    return this.screen;
  }
  
  
  public int getTime()
  {
    return this.time;
  }
}
