package pack.net;

import java.io.IOException;
import java.net.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JOptionPane;

import pack.Constants;
import pack.MultiPlayer;
import pack.Player;
import pack.Player.State;
import pack.net.Packet.PacketTypes;

public class GameClient extends Thread
{
  private InetAddress ipAddress;
  private DatagramSocket socket;
  
  public int screen=-1;

    private List<MultiPlayer> connectedPlayers = new ArrayList<MultiPlayer>();

    private String userID;
  private int x;
  private int y;
  private int dir;
  private int state;
  private int [] p=new int [4];
  private int isUp;
  private boolean isSuper;
  private boolean isSpeedUp;
  public MultiPlayer self;
  public int time;
  public boolean running=true;
  
  public int getSize()
  {
    return connectedPlayers.size();
  }
  
  public MultiPlayer getP1() {
    return connectedPlayers.get(0);
  }

  public MultiPlayer getP2() {
    return connectedPlayers.get(1);
  }
  
  public MultiPlayer getP3() {
    return connectedPlayers.get(2);
  }
  
  public MultiPlayer getP4() {
    return connectedPlayers.get(3);
  }

  private int size;
  //private Game game;
  
  public GameClient(String ipAddress)
  {
     //this.game = game;
     try 
     {
       this.socket = new DatagramSocket();
       this.ipAddress = InetAddress.getByName(ipAddress);
     } catch (SocketException e) 
     {
      e.printStackTrace();
      JOptionPane.showMessageDialog(null, "Invalid IP Address!", "ERROR!", 
          JOptionPane.ERROR_MESSAGE);
      System.exit(0);
     } catch (UnknownHostException e)
     {
      e.printStackTrace();
      JOptionPane.showMessageDialog(null, "Invalid IP Address!", "ERROR!", 
          JOptionPane.ERROR_MESSAGE);
      System.exit(0);
     }
  }
  
  public void run()
  {
    while (running)
    {
      byte[] data = new byte[512];
      DatagramPacket packet = new DatagramPacket(data, data.length);
      try 
      {
        socket.receive(packet);
        
      } catch (IOException e) 
      {
        e.printStackTrace();
      }
      this.parsePacket(packet.getData(), packet.getAddress(), packet.getPort());
    }
  }
  
  
  
  private void parsePacket(byte[] data, InetAddress address, int port) {
    String message = new String(data).trim();
    PacketTypes type = Packet.lookupPacket(message.substring(0, 2));
    Packet packet = null;
    switch (type)
    {
    default:
    case INVALID:
      break;

    case LOGIN:
      packet = new Packet00Login(data);
//      System.out.println(((Packet00Login)packet).getUsername());
      handleLogin((Packet00Login) packet, address, port);
      break;

    case DISCONNECT:
      packet = new Packet01Disconnect(data);
//      System.out.println("[" + address.getHostAddress() + ":" + port + "] " + ((Packet01Disconnect) packet).getUsername() + " has left the world...");
      //game.level.removeMultiPlayer(((Packet01Disconnect) packet).getUsername());
      break;

    case KEY:
      //process the loggic of client...
       packet = new Packet03Key(data);
      userID = ((Packet03Key)packet).getUserID();
      x =((Packet03Key)packet).getx();
      y =((Packet03Key)packet).gety();
      dir = ((Packet03Key)packet).getdir();
      state = ((Packet03Key)packet).getstate();
      isUp=((Packet03Key)packet).getisUp();
      isSuper=((Packet03Key)packet).getisSuper();
      isSpeedUp=((Packet03Key)packet).isSpeedUp();
  //private Game game;
      logic ();
      update ();
      break;
    }
  }
  
  private void update () {
//    for (int i=0;i<connectedPlayers.size();i++) {
//      MultiPlayer player=connectedPlayers.get(i);
//      if (userID.equals(player.getUsername())) {
//        player.setX(x);
//        player.setY(y);
//        player.setDir(Player.IntToDir(dir));
//        player.setState(Player.IntToState(state));
//      }
//    }
    if (p[0]!=0) {
//      System.out.println("P1 hit");
      MultiPlayer player=connectedPlayers.get(0);
      player.setIsHit(p[0]);
      
    }
    if (p[1]!=0) {
      MultiPlayer player=connectedPlayers.get(1);
      player.setIsHit(p[1]);
    }
    if (p[2]!=0) {
      MultiPlayer player=connectedPlayers.get(2);
      player.setIsHit(p[2]);
    }
    if (p[3]!=0) {
      MultiPlayer player=connectedPlayers.get(3);
      player.setIsHit(p[3]);
    }
  }
  
  
  private void handleLogin(Packet00Login packet, InetAddress address, int port) 
  {
    if (packet.getUsername().equalsIgnoreCase("a123")) {
//      System.out.println("setImage");
      screen=packet.getScreen();
      time=packet.getTime();
//      System.out.println(screen);
    }
    else if (self!=null && self.getUsername().equalsIgnoreCase(packet.getUsername())) {
      MultiPlayer a=this.getPlayerMP(self.getUsername());
      System.out.println("Change Syle to #"+packet.getStyle());
      a.setStyle(packet.getStyle());
    }
    else if (self!=null) {
      System.out.println("[" + address.getHostAddress() + ":" + port + "] "
          + packet.getUsername() + " with Style #"+ packet.getStyle() +
          " has joined the Game...");
        MultiPlayer player = new MultiPlayer(((Packet00Login) packet).getX(), 
            ((Packet00Login) packet).getY()
            , ((Packet00Login) packet).getUsername(), address, port);
        player.setStyle(packet.getStyle());
//        System.out.println(((Packet00Login) packet).getStyle());
        addMultiPlayer(player);
    }
  }
  
  //
  //
  //
  //
  //
  //
  // 这里加一个找index的函数
  private int getIndex(String username)
  {
    int i = 0;
    for (;i<getSize();i++)
    {
      MultiPlayer m=connectedPlayers.get(i);
      if (m.getUsername() == username)
        break;
    }
    return i;
  }
  

  
  public void addMultiPlayer(MultiPlayer player) 
  {
    getMultiPlayer().add(player);
  }
  
  public synchronized List<MultiPlayer> getMultiPlayer() 
  {
    return connectedPlayers;
  }
  
  
  
  
  public void sendData(byte[] data)
  {
    //System.out.println("SENT");
    DatagramPacket packet = new DatagramPacket(data, data.length, ipAddress, 1331);
    try {
      socket.send(packet);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
  
  public MultiPlayer getPlayerMP(String username) {
    for (MultiPlayer player : connectedPlayers) {
      if (player.getUsername().equals(username)) {
        return player;
      }
    }
    return null;
  }
  
  public int getPlayerMPIndex(String username) {
    int index = 0;
    for (MultiPlayer player : this.connectedPlayers) {
      if (player.getUsername().equals(username)) {
        break;
      }
      index++;
    }
    return index;
  }
  
  void logic () {
//   System.out.println(userID);
//    System.out.println(connectedPlayers.size());
    MultiPlayer thePlayer=getPlayerMP(userID);
//    System.out.println(thePlayer);
    thePlayer.setX(x);
    thePlayer.setY(y);
    thePlayer.setState(Player.IntToState(state));
    thePlayer.setDir(Player.IntToDir(dir));
    thePlayer.isSuper=isSuper;
    thePlayer.isSpeedUp=isSpeedUp;
    if (isUp==1) thePlayer.setNotFall(true);
    Player.State s=Player.IntToState(state);
    p[0]=0;p[1]=0;p[2]=0;p[3]=0;
    MultiPlayer pp[]=new MultiPlayer[4];
    int size=connectedPlayers.size();
    if (size!=0) {
      pp[0]=connectedPlayers.get(0);
      size--;
    }
    if (size!=0) {
      pp[1]=connectedPlayers.get(1);
      size--;
    }
    if (size!=0) {
      pp[2]=connectedPlayers.get(2);
      size--;
    }
    if (size!=0) {
      pp[3]=connectedPlayers.get(3);
      size--;
    }
    if (s==Player.State.Hit1 || s==Player.State.Hit2 ||
        s==Player.State.Chop1 || s==Player.State.Chop2 ||
        s==Player.State.Kick1 || s==Player.State.Kick2 ||
        s==Player.State.Kick3 || s==Player.State.Kick4 ||
        s==Player.State.Kick5) {
      //System.out.println("Some Hit");
      int victimSize=connectedPlayers.size();
      int i=0;
      while (victimSize!=0) {
        if (!thePlayer.equals(pp[i])) {
          p[i]=pp[i].isHit(thePlayer);
          if (p[i]!=0) {
            if (thePlayer.getpoint()<100) thePlayer.setpoint(thePlayer.getpoint()+5);
            if (pp[i].getpoint()<100) pp[i].setpoint(pp[i].getpoint()+5);
          }
          
        }
        i++;
        victimSize--;
      }
    }
//    for (int i=0;i<size;i++) {
//      pp[i].tick();
//    }
  }
  
  
  public void addSelf(MultiPlayer player) {
    connectedPlayers.add(player);
    self=player;
  }
}
