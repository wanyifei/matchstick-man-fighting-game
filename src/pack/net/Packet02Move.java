package pack.net;

//packet for data sent to client....
public class Packet02Move extends Packet 
{ 
  private String userID;
  private int x;
  private int y;
  private int dir;
  private int state;
  private int p1_behit;
  private int p2_behit;
  private int p3_behit;
  private int p4_behit;
  //private Game game = new Game();

  public Packet02Move(byte[] data) 
  {
      super(02);
      String[] dataArray = readData(data).split(",");
      this.userID = dataArray[0];
      this.x = Integer.parseInt(dataArray[1]);
      this.y = Integer.parseInt(dataArray[2]);
      
      this.dir = Integer.parseInt(dataArray[3]);
      this.state = Integer.parseInt(dataArray[4]);
      this.p1_behit = Integer.parseInt(dataArray[5]);
      this.p2_behit = Integer.parseInt(dataArray[6]);
      this.p3_behit = Integer.parseInt(dataArray[7]);
      this.p4_behit = Integer.parseInt(dataArray[8]);
  }
  
  public Packet02Move(String UserID, int X, int Y, int Dir, int State, int P1, int P2, int P3, int P4)
  {
    super(02);
    
    this.userID = UserID;
    this.x = X;
    this.y = Y;
    this.dir = Dir;
    this.state = State;
    this.p1_behit = P1;
    this.p2_behit = P2;
    this.p3_behit = P3;
    this.p4_behit = P4;

  }
  
  
  public void writeData(GameClient client) 
  {
    // if(Game.isApplet == false)
    {
      client.sendData(getData());
    }
  }

  @Override
  public void writeData(GameServer server)
  {
      server.sendDataToAllClients(getData());
  }

  @Override
  public byte[] getData() {
      return ("02" + this.userID  + "," + this.x + "," + this.y + "," + this.dir + "," + this.state
              + "," + this.p1_behit +"," + this.p2_behit + "," +this.p3_behit + this.p4_behit).getBytes();

  }

  public String getuserID() 
  {
      return userID;
  }

  public int getx() 
  {
      return this.x;
  }

  public int gety() 
  {
      return this.y;
  }

  public int getdir() 
  {
      return dir;
  }

  public int getstate() 
  {
      return state;
  }

  public int get_p1_behit()
  {
      return p1_behit;
  }
  

  public int get_p2_behit()
  {
      return p2_behit;
  }

  public int get_p3_behit()
  {
      return p3_behit;
  }

  public int get_p4_behit()
  {
      return p4_behit;
  }
  
}
