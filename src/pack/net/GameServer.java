package pack.net;

import java.util.*;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.ArrayList;

import pack.Constants;
import pack.Game;
import pack.MultiPlayer;
import pack.Player;
import pack.net.Packet.PacketTypes;

public class GameServer extends Thread
{
  private DatagramSocket socket;
  //private Game game; 
  public List<MultiPlayer> connectedPlayers = new ArrayList<MultiPlayer>();
  
  private String UserID;
  private int x;
  private int y;
  private int state;
  private int dir;
  private int ID;
  private int screen;
  private static int portin=1331;
  public boolean running=true;
  
  private byte[]  prevPacket;
//  private Game game;
  
  
  private int p[];
  
  public GameServer(Game game)
  {
//     this.game = game;
     ID=0;
     try 
     {
       
       this.socket = new DatagramSocket(portin);
       portin++;
   
       //open the socket
     } catch (SocketException e) 
     {
       
      e.printStackTrace();
     }
     p=new int[4];
     p[0]=0;p[1]=0;p[2]=0;p[3]=0;
  }
  
  public void run()
  {
    while (running)
    {
      
      byte[] data = new byte[512];
      DatagramPacket packet = new DatagramPacket(data, data.length);
      
      try 
      {
        //System.out.println("Try HERE!");
        socket.receive(packet);
      } catch (IOException e) 
      {
        //System.out.println("HERE!");
        e.printStackTrace();
      }
      //System.out.println("End Try HERE!");
      this.parsePacket(packet.getData(), packet.getAddress(), packet.getPort());
    }
  }
  
  private void parsePacket(byte[] data, InetAddress address, int port) 
  {
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
      if (((Packet00Login) packet).getUsername().equalsIgnoreCase("a123")) {
        System.out.println("[" + address.getHostAddress() + ":" + port + "] "
            + ((Packet00Login) packet).getUsername() + " wants screen...");
        Packet00Login newpacket;
        newpacket = new Packet00Login("a123", 0, 0, 
            0, screen, Game.game.screen.getContent().GameTime);
        sendData(newpacket.getData(), address, port);
      }
      else if (((Packet00Login) packet).getUsername().equalsIgnoreCase("b123")) {
        System.out.println("[" + address.getHostAddress() + ":" + port + "] "
            + ((Packet00Login) packet).getUsername() + " defines screen...");
        screen=((Packet00Login) packet).getScreen();
      }
      else {
        System.out.println("[" + address.getHostAddress() + ":" + port + "] "
            + ((Packet00Login) packet).getUsername() + " has connected...");
          MultiPlayer player = new MultiPlayer(Constants.STARTX, Constants.STARTY,
              ((Packet00Login) packet).getUsername(), address, port);
          player.setStyle(connectedPlayers.size());
          this.addConnection(player, ((Packet00Login) packet));
      }
      break; 
      
    case DISCONNECT:
      packet = new Packet01Disconnect(data);
      System.out.println("[" + address.getHostAddress() + ":" + port + "] " + ((Packet01Disconnect) packet).getUsername() + " has left...");
      this.removeConnection((Packet01Disconnect) packet);
      break;

    /*case MOVE:
      packet = new Packet02Move(data);
      this.handleMove(((Packet02Move) packet));
      update(packet)；
      logic();
      break;*/
      
    case KEY:
      packet = new Packet03Key(data);
      this.handleMove(((Packet03Key) packet));
//         UserID = ((Packet03Key)packet).getUserID();
//         x = ((Packet03Key)packet).getx();
//         y = ((Packet03Key)packet).gety();
//         state = ((Packet03Key)packet).getstate();
//         dir = ((Packet03Key)packet).getdir();   
//      
//      // need a new packet
//      Packet02Move sendP=new Packet02Move(UserID, 
//          x, y, dir, state, p[0], p[1], p[2], p[3]);
//      packet.writeData(this);
      break;

      
    }
  }
  
  private void handleMove(Packet03Key packet) {
    if (getPlayerMP(packet.getUserID()) != null) {
      int index = getPlayerMPIndex(packet.getUserID());
      MultiPlayer player = this.connectedPlayers.get(index);
      player.setX(packet.getx());
      player.setY(packet.gety());
      player.setState(Player.IntToState(packet.getstate()));
      player.setDir(Player.IntToDir(packet.getdir()));
      if (packet.getisUp()==1) player.setNotFall(true);
      packet.writeData(this);
    }
  }
  
  public void addConnection(MultiPlayer player, Packet00Login packet) {
    boolean alreadyConnected = false;
//    System.out.println(connectedPlayers.size());
    for (MultiPlayer p : this.connectedPlayers) {
      if (player.getUsername().equalsIgnoreCase(p.getUsername())) {
        if (p.ipAddress == null) {
          p.ipAddress = player.ipAddress;
        }
        if (p.port == -1) {
          p.port = player.port;
        }
//        if (p.getStyle()==-1) {
//          p.setStyle(connectedPlayers.size());
//        }
        alreadyConnected = true;
      } else {
        Packet00Login newpacket;
        newpacket = new Packet00Login(packet.getUsername(), packet.getX(), packet.getY(), 
            player.getStyle(),screen,0);
          sendData(newpacket.getData(), p.ipAddress, p.port);
        // relay to the new player that currently connect player
        // exits
          newpacket = new Packet00Login(p.getUsername(), p.getX(), p.getY(), p.getStyle(),screen,0);
        sendData(newpacket.getData(), player.ipAddress, player.port);
      }
    }
    if (!alreadyConnected) {
      if (connectedPlayers.size()!=0) {
        Packet00Login newpacket;
        newpacket = new Packet00Login(player.getUsername(), player.getX(), player.getY(), 
            connectedPlayers.size(),screen,0);
        sendData(newpacket.getData(), player.ipAddress, player.port);
      }
      this.connectedPlayers.add(player);
    }
  }

  public void removeConnection(Packet01Disconnect packet) {
    this.connectedPlayers.remove(getPlayerMPIndex(packet.getUsername()));
    packet.writeData(this);
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

  public void sendData(byte[] data, InetAddress ipAddress, int port) {

            DatagramPacket packet = new DatagramPacket(data, data.length, ipAddress, port);
            try {
                this.socket.send(packet);
            } catch (IOException e) {
                e.printStackTrace();
            }
    }

  public void sendDataToAllClients(byte[] data) {
    for (MultiPlayer p : connectedPlayers) {
      sendData(data, p.ipAddress, p.port);
    }
  }
  

//  public void addConnection(MultiPlayer player, Packet00Login packet) 
//  {
//    boolean alreadyConnected = false;
//    MultiPlayer Player;
//    for(int i = 0; i<ID;i++)
//    { 
//      Player = connectedPlayers.get(i);
//      if (player.getUsername().equalsIgnoreCase(Player.getUsername())) 
//      {
//        if (Player.ipAddress == null) 
//        {
//          Player.ipAddress = player.ipAddress;
//        }
//        if (Player.port == -1) 
//        {
//          Player.port = player.port;
//        }
//        alreadyConnected = true;
//      } 
//      else
//      {
//
//        Packet04CLogin sendPacket = new Packet04CLogin(player.getUsername(),
//            player.getX(), player.getY(), ID+1);
//        sendData(sendPacket.getData(), Player.ipAddress, Player.port);
//
//         sendPacket = new Packet04CLogin(Player.getUsername(), Player.getX(), Player.getY(),i);
//        sendData(sendPacket.getData(), player.ipAddress, player.port);
//      }
//    }
//    if (!alreadyConnected) 
//    {
//      ID++;
//        Packet04CLogin sendPacket = new Packet04CLogin(player.getUsername(), 
//            player.getX(), player.getY(), ID);
//        sendData(sendPacket.getData(), player.ipAddress, player.port);
//      this.connectedPlayers.put(ID,player);
//    }
//  }
//  
//  
//  
//  public void removeConnection(Packet01Disconnect packet) 
//  {
//    this.connectedPlayers.remove(getMultiPlayerIndex(packet.getUsername()));
//    packet.writeData(this);
//  }
//  
//  public MultiPlayer getMultiPlayer(String username) 
//  {
//    for (int i=0;i<connectedPlayers.size();i++) 
//    {
//      MultiPlayer player=connectedPlayers.get(i);
//      if (player.getUsername().equals(username)) 
//      {
//        return player;
//      }
//    }
//    return null;
//  }
//  
//  public int getMultiPlayerIndex(String username) 
//  {
//    int index = 0;
//    for (int i=0;i<connectedPlayers.size();i++) 
//    {
//      MultiPlayer player=connectedPlayers.get(i);
//      if (player.getUsername().equals(username))
//      {
//        break;
//      }
//      index++;
//    }
//    return index;
//  }
//  
//
//  public void sendData(byte[] data, InetAddress ipAddress, int port)
//  {
//    DatagramPacket packet = new DatagramPacket(data, data.length, ipAddress, port);
//    try {
//      if (socket==null) System.out.println("NULL");
//      System.out.println(port);
//      this.socket.send(packet);
//    } catch (IOException e) {
//      e.printStackTrace();
//    }
//  }
//
//  public void sendDataToAllClients(byte[] data)
//  {
//    for (int i=0;i<connectedPlayers.size();i++) 
//    {
//      MultiPlayer p=connectedPlayers.get(i);
//      sendData(data, p.ipAddress, p.port);
//    }    
//  }
  
}
