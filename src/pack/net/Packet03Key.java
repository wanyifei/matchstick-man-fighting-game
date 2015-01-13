package pack.net;

//Packet03Key for the data sent to server..

public class Packet03Key extends Packet {
  private String UserID;
  private int x;
  private int y;
  private int state;
  private int dir;
  private int isUp;
  private boolean isSuper;
  private boolean isSpeedUp;

  public Packet03Key(byte[] data) 
  {
      super(03);
      String[] dataArray = readData(data).split(",");
      this.UserID = dataArray[0];
      this.x = Integer.parseInt(dataArray[1]);
      this.y = Integer.parseInt(dataArray[2]);
      this.state = Integer.parseInt(dataArray[3]);
      this.dir = Integer.parseInt(dataArray[4]);
      this.isUp = Integer.parseInt(dataArray[5]);
      this.isSuper = Boolean.parseBoolean(dataArray[6]);
      this.isSpeedUp = Boolean.parseBoolean(dataArray[7]);
  }
  
  public Packet03Key(String USERID,int X,int Y, int State, int Dir, int Up, boolean insuper, 
      boolean isSpeedUp)
  {
    super(03);
    this.UserID = USERID;
    this.x = X;
    this.y = Y;
    this.state = State;
    this.dir = Dir;
    this.isUp = Up;
    this.isSuper=insuper;
    this.isSpeedUp=isSpeedUp;
  }
  
  public void writeData(GameClient client) 
  {
    // if(Game.isApplet == false)
    {
      client.sendData(getData());
    }
  }
  
  public void writeData(GameServer server)
  {
    server.sendDataToAllClients(getData());
  }
  
  public byte[] getData() 
  {
    return ("03" + this.UserID + "," + this.x + "," + this.y + "," +
      this.state + "," + this.dir+ "," +this.isUp + "," + this.isSuper + "," + this.isSpeedUp).getBytes();
  }
  
  public String getUserID() 
  {
      return UserID;
  }
  
  public boolean isSpeedUp()
  {
    return isSpeedUp;
  }

  public void setSpeedUp(boolean isSpeedUp)
  {
    this.isSpeedUp = isSpeedUp;
  }

  public int getx()
  {
    return x;
  }
  
  public int gety()
  {
    return y;
  }
  
  public int getstate()
  {
    return state;
  }
  
  public int getdir()
  {
    return dir;
  }
  
 public int getisUp()
 {
    return isUp; 
 }
 
 public boolean getisSuper()
 {
    return isSuper; 
 }
}
