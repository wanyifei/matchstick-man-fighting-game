package pack.net;

public class Packet04CLogin extends Packet
{
  private String username;
  private int x, y;
  private int ID;
  
  public Packet04CLogin(byte[] data)
  {
    super(04);
    String[] dataArray = readData(data).split(",");
    this.username = dataArray[0];
    this.x = Integer.parseInt(dataArray[1]);
    this.y = Integer.parseInt(dataArray[2]);
    this.ID = Integer.parseInt(dataArray[3]);
    
  }
  
  public Packet04CLogin(String username, int x, int y, int id) {
    super(04);
    this.username = username;
    this.x = x;
    this.y = y;
    this.ID = id;
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
    return ("00"+ this.username + "," + getX() + "," + getY()).getBytes();
  }
  
  public String getUsername()
  {
    return username;
  }

  public int getX()
  {
    return this.x;
  }
  
  public int  getY()
  {
    return this.y;
  }

  public int getID()
  {
  
   return this.ID;
  }
  
}
