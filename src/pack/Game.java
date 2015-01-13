package pack;

import gui.*;

import java.awt.Canvas;
import java.awt.Frame;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import pack.net.*;

public class Game extends Canvas implements Runnable 
{
   
   public GameClient socketClient;
   public GameServer socketServer;
   
   public MultiPlayer [] players=new MultiPlayer [4];
   
   int time []= new int [4];
   int speedUptime []= new int [4];
   
   int endtime=0;
   
   private MultiPlayer player;
   
   int starttime=0;
   
   EndUI end=null;
   
   private boolean running=false;
   
   private boolean show [] = new boolean [4];
   private boolean runGame=false;
   private boolean timeUp=false;
   private Thread thread;
   
   private boolean shouldEnd=false;
   
   private KeyBoardLis key;
   private String ip;
   
   public Screen screen;
   public StartUI startUI;
  
  public static Game game;
  
  public Game (Screen inScreen) {
    this.screen=inScreen;
//    System.out.println("2");
  }
   public void initial () {
     game=this;
     runGame=true;
     screen=null;
     timeUp=false;
     shouldEnd=false;
     time[0]=0;time[1]=0;time[2]=0;time[3]=0;
     show[0]=false;show[1]=false;show[2]=false;show[3]=false;
     speedUptime[0]=0;speedUptime[1]=0;speedUptime[2]=0;speedUptime[3]=0;
     String name=JOptionPane.showInputDialog(screen, "Please enter a username");
     if (name==null) {
        System.exit(1);
     }
     if (name.equalsIgnoreCase("a123") || 
         name.equalsIgnoreCase("b123")) {
       JOptionPane.showMessageDialog(screen, "Invalid name!", "ERROR!", 
           JOptionPane.ERROR_MESSAGE);
        System.exit(1);
     }
     if (end!=null) {
       socketClient.screen=-1;
       while (socketClient.getMultiPlayer().size()!=0) socketClient.getMultiPlayer().remove(0);
       if (socketServer!=null) {
         while (socketServer.connectedPlayers.size()!=0) {
           socketServer.connectedPlayers.remove(0);
         }
       }
       end=null;
     }
     end=null;
     if (socketServer!=null) {
       StartUI gameUI = new StartUI(4, 101);
       while (gameUI.getSelectMap()==null || gameUI.getSelectMap().getScreen()==null) {
         try
        {
          Thread.sleep(50);
        }
        catch( InterruptedException e )
        {
          // TODO Auto-generated catch block
          e.printStackTrace();
        }
         System.out.println("Wait for start");
         if (gameUI.isHelp) {
           HelpFrame help=gameUI.help;
           while (help.start==null) {
             System.out.println("Wait for reading help");
           }
           gameUI=help.start;
         }
       }
//       System.out.println("HERE");
       screen=gameUI.getSelectMap().getScreen();
       if (socketServer!=null) screen.setVisible(false);
       socketClient.screen=screen.num;
       Packet00Login loginPacket = new Packet00Login("b123", 0, 0, -1, screen.num, 0);
       loginPacket.writeData(socketClient);
       startUI=gameUI;
     }
     else {
       StartUI gameUI = new StartUI(4, 101);
       gameUI.isClient=true;
       while (gameUI.getSelectMap()==null || gameUI.getSelectMap().getScreen()==null) {
         System.out.println("Wait for start");
         if (gameUI.isHelp) {
           HelpFrame help=gameUI.help;
           while (help.start==null) {
             try
            {
              Thread.sleep(50);
            }
            catch( InterruptedException e )
            {
              // TODO Auto-generated catch block
              e.printStackTrace();
            }
             System.out.println("Wait for reading help");
           }
           gameUI=help.start;
         }
         else if (gameUI.isStart) break;
       }
       Packet00Login loginPacket = new Packet00Login("a123", 0, 0, -1, -1, 0);
       loginPacket.writeData(socketClient);
       long lastTime=System.currentTimeMillis();
       int norespond=0;
       while (socketClient.screen==-1) {
         System.out.println("Wait for image");
         repaint();
         if(System.currentTimeMillis() - lastTime >= 1000){
           lastTime += 1000;
           norespond++;
         }
         if (norespond>5) {
           JOptionPane.showMessageDialog(screen, "Server Norespond", "ERROR!", 
              JOptionPane.ERROR_MESSAGE);
           System.exit(1);
         }
       }
//       System.out.println(socketClient.screen);
       
       try
      {
        Thread.sleep(300);
      }
      catch( InterruptedException e )
      {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
       switch (socketClient.screen) {
         case 0:
           screen=new Screen(4, socketClient.time, "Snow");
           break;
         case 1:
           screen=new Screen(4, socketClient.time, "Flower");
           break;
         case 2:
           screen=new Screen(4, socketClient.time, "Grass");
           break;
       }
       screen.setVisible(false);
       startUI=gameUI;
       gameUI.bgm.stop();
       screen.bgm.loop();
     }
     key=new KeyBoardLis(this);
     this.screen.addKeyListener(key);
     player=new MultiPlayer(Constants.STARTX, Constants.STARTY, key, 
         name,
         null, -1);
     Packet00Login loginPacket = new Packet00Login(player.getUsername(), player.x, player.y, -1, 
         socketClient.screen, 0);
     if (socketServer != null) {
         socketServer.addConnection((MultiPlayer) player, loginPacket);
         player.setStyle(0);
     }
     loginPacket.writeData(socketClient); 
     socketClient.addSelf(player);
     
//     System.out.println("HERE");
   }
   
   void waitForStart() {
     WaitPage wait=null;
     if (socketServer!=null) {
       try
      {
         ip=InetAddress.getLocalHost().getHostAddress();
        wait=new WaitPage(ip);
      }
      catch( UnknownHostException e )
      {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
       
     }
     while (socketServer!=null && socketServer.connectedPlayers.size()<=1) {
       
       screen.getContent().GameTime=101;
       try
      {
        Thread.sleep(50);
      }
      catch( InterruptedException e )
      {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
     }
     if (wait!=null) {
       wait.setVisible(false);
       startUI.bgm.stop();
       screen.bgm.loop();
     }
   }
   public synchronized void start() {
     running=false;
     socketServer=null;
     socketClient=null;
     thread = new Thread(this, "Game" + "_main");
     thread.start();
//     if (!isApplet) {
     int whetherServer=JOptionPane.showConfirmDialog(this, "Do you want to run the server");
         if (whetherServer == 0) {
             socketServer = new GameServer(this);
             socketServer.start();
             running = true;
         }
         if (whetherServer == 2) System.exit(0);
         if (socketServer==null) {
           ip=JOptionPane.showInputDialog(screen, "Server's IP address");
           if (ip==null) System.exit(0);
           socketClient = new GameClient(ip);
         }
         else {
           socketClient = new GameClient("localhost");
         }
           
           socketClient.start();
           running = true;
//     }
 }
   public void run () {
     while (!running) {
       try
      {
        Thread.sleep(50);
      }
      catch( InterruptedException e )
      {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
     }
       initial();
       waitForStart();
       //screen.setVisible(true);
       while (true) {
         screen.getContent().setNumPlayer(socketClient.getSize());
         if (end==null && !timeUp) {
           if (!shouldEnd) {
             if (starttime<1000) {
               starttime+=50;
             }
             else screen.setVisible(true);
           }
           render();
//         player.tick();
           int size=socketClient.getSize();
           if (size!=0) {
//           System.out.println("P1");
             if (socketClient.getP1().isSpeedUp && speedUptime[0]<=1000) {
               speedUptime[0]+=50;
             }
             else if (socketClient.getP1().isSpeedUp && speedUptime[0]>1000) {
               speedUptime[0]=0;
               socketClient.getP1().isSpeedUp=false;  
             }
           socketClient.getP1().tick();
//           System.out.println(players[size-1].x);
           size--;
         }
         if (size!=0) {
           if (socketClient.getP2().isSpeedUp && speedUptime[0]<=1000) {
             speedUptime[0]+=50;
           }
           else if (socketClient.getP2().isSpeedUp && speedUptime[0]>1000) {
             speedUptime[0]=0;
             socketClient.getP2().isSpeedUp=false;  
           }
           socketClient.getP2().tick();
           size--;
         }
         if (size!=0) {
           if (socketClient.getP3().isSpeedUp && speedUptime[0]<=1000) {
             speedUptime[0]+=50;
           }
           else if (socketClient.getP3().isSpeedUp && speedUptime[0]>1000) {
             speedUptime[0]=0;
             socketClient.getP3().isSpeedUp=false;  
           }
           socketClient.getP3().tick();
           size--;
         }
         if (size!=0) {
           if (socketClient.getP4().isSpeedUp && speedUptime[0]<=1000) {
             speedUptime[0]+=50;
           }
           else if (socketClient.getP4().isSpeedUp && speedUptime[0]>1000) {
             speedUptime[0]=0;
             socketClient.getP4().isSpeedUp=false;  
           }
           socketClient.getP4().tick();
           size--;
         }
         isEnd();
        }
//         System.out.println("RUN");
         
         else if (end!=null && end.isStart) {
         //runGame=false;
           
         initial();
         waitForStart();
         
//         while (!running) {
//           try
//          {
//            Thread.sleep(50);
//          }
//          catch( InterruptedException e )
//          {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//          }
//         }
//         initial();
       }
         if (end==null && screen.getContent().GameTime==0) {
           timeUp=true;
           timeUp();
         }
        try {
          Thread.sleep(50);
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
   }
   
   void timeUp() {
     int size;
     int p1=0, p2=0, p3=0, p4=0;
     int max, win;
     size=socketClient.getSize();
     if (size>0) {
         p1=socketClient.getP1().health;
     }
     size--;
     if (size>0) {
       p2=socketClient.getP2().health;
     }
     size--;
     if (size>0) {
       p3=socketClient.getP3().health;
     }
     size--;
     if (size>0) {
       p4=socketClient.getP4().health;
     }
     max=p1;
     win=1;
     if (max<p2) {
       max=p2;
       win=2;
     }
     if (max<p3) {
       max=p3;
       win=3;
     }
     if (max<p4) {
       max=p4;
       win=4;
     }
     if (endtime>=3000) {
       screen.setVisible(false);
       screen.bgm.stop();
       if (win==1) {
         end=new EndUI(socketClient.getP1().style);
       }
       else if (win==2) {
         end=new EndUI(socketClient.getP2().style);
       }
       else if (win==3) {
         end=new EndUI(socketClient.getP3().style);
       }
       else if (win==4) {
         end=new EndUI(socketClient.getP4().style);
       }
     }
     if (endtime<3000) {
       endtime+=50;
     }
   }
   int num=0;
   boolean p1=false, p2=false, p3=false, p4=false;
   void isEnd() {
     int size;
     size=socketClient.getSize();
     if (size>1) {
       if (!shouldEnd) {
         num=0;
         p1=false;
         p2=false; 
         p3=false;
         p4=false;
         if (size>0) {
           if (socketClient.getP1().health!=0) {
             p1=true;
             num++;
           }
         }
         size--;
         if (size>0) {
           if (socketClient.getP2().health!=0) {
             p2=true;
             num++;
           }
         }
         size--;
         if (size>0) {
           if (socketClient.getP3().health!=0) {
             p3=true;
             num++;
           }
         }
         size--;
         if (size>0) {
           if (socketClient.getP4().health!=0) {
             p4=true;
             num++;
           }
         }
         size--;
       }
       if (num<=1 && endtime>=2000) {
         shouldEnd=true;
         screen.setVisible(false);
         screen.bgm.stop();
         if (p1) {
           end=new EndUI(socketClient.getP1().style);
         }
         else if (p2) {
           end=new EndUI(socketClient.getP2().style);
         }
         else if (p3) {
           end=new EndUI(socketClient.getP3().style);
         }
         else if (p4) {
           end=new EndUI(socketClient.getP4().style);
         }
       }
       if (num<=1 && endtime<2000) {
         shouldEnd=true;
         endtime+=50;
       }
     }
   }
   
   void render () {
     int size;
     size=socketClient.getSize();
     screen.getContent().setIp(ip);
     //System.out.println(size);
     if (size!=0) {
//       System.out.println("P1");
       players[size-1]=socketClient.getP1();
//       System.out.println(players[size-1].x);
       size--;
     }
     if (size!=0) {
       players[size-1]=socketClient.getP2();
       size--;
     }
     if (size!=0) {
       players[size-1]=socketClient.getP3();
       size--;
     }
     if (size!=0) {
       players[size-1]=socketClient.getP4();
       size--;
     }
     size=socketClient.getSize();
     if (size!=0) {
       players[size-1].render();
       if (players[size-1].isSuper && time[0]<=5000) {
           if (!show[0]) {
             screen.getContent().setP1Image(null, 0, 0);
             show[0]=true;
           }
           else {
             if (socketClient.self.
                 getUsername().equalsIgnoreCase(players[size-1].getUsername())) {
               screen.getContent().setP1Image(players[size-1].actNow, players[size-1].width, players[size-1].height);
             }
             else {
               screen.getContent().setP1Image(null, 0, 0);
             }
             show[0]=false;
           }
         time[0]+=50;
       }
       else {
         screen.getContent().setP1Image(players[size-1].actNow, players[size-1].width, players[size-1].height);
         time[0]=0;
         players[size-1].isSuper=false;
       }
       screen.getContent().setP1Positon(players[size-1].getX(),players[size-1].getY());
       screen.getContent().setP1Blood(players[size-1].health);
       screen.getContent().addP1Points(players[size-1].getpoint());
         screen.getContent().setP1color(players[size-1].style);
         screen.getContent().setP1name(players[size-1].getUsername());
       size--;
     }
     if (size!=0) {
       players[size-1].render();
       if (players[size-1].isSuper && time[1]<=5000) {
         if (!show[1]) {
           screen.getContent().setP2Image(null, 0, 0);
           show[1]=true;
         }
         else {
 
           if (socketClient.self.
               getUsername().equalsIgnoreCase(players[size-1].getUsername())) {
             screen.getContent().setP2Image(players[size-1].actNow, players[size-1].width, players[size-1].height);
           }
           else {
             screen.getContent().setP2Image(null, 0, 0);
           }
           show[1]=false;
         }
       time[1]+=50;
     }
     else {
       screen.getContent().setP2Image(players[size-1].actNow, players[size-1].width, players[size-1].height);
       time[1]=0;
       players[size-1].isSuper=false;
     }
       screen.getContent().setP2Postion(players[size-1].getX(),players[size-1].getY());
       screen.getContent().setP2Blood(players[size-1].health);
       screen.getContent().addP2Points(players[size-1].getpoint());
         screen.getContent().setP2color(players[size-1].style);
         screen.getContent().setP2name(players[size-1].getUsername());
       size--;
     }
     if (size!=0) {
       players[size-1].render();
       if (players[size-1].isSuper && time[2]<=5000) {
         if (!show[2]) {
           screen.getContent().setP3Image(null, 0, 0);
           show[2]=true;
         }
         else {
           if (socketClient.self.
               getUsername().equalsIgnoreCase(players[size-1].getUsername())) {
             screen.getContent().setP3Image(players[size-1].actNow, players[size-1].width, players[size-1].height);
           }
           else {
             screen.getContent().setP3Image(null, 0, 0);
           }
           show[2]=false;
         }
       time[2]+=50;
     }
     else {
       screen.getContent().setP3Image(players[size-1].actNow, players[size-1].width, players[size-1].height);
       time[2]=0;
       players[size-1].isSuper=false;
     }
       screen.getContent().setP3Postion(players[size-1].getX(),players[size-1].getY());
       screen.getContent().setP3Blood(players[size-1].health);
       screen.getContent().addP3Points(players[size-1].getpoint());
         screen.getContent().setP3color(players[size-1].style);
         screen.getContent().setP3name(players[size-1].getUsername());
       size--;
     }
     if (size!=0) {
       players[size-1].render();
       if (players[size-1].isSuper && time[3]<=5000) {
         if (!show[3]) {
           screen.getContent().setP4Image(null, 0, 0);
           show[3]=true;
         }
         else {
           if (socketClient.self.
               getUsername().equalsIgnoreCase(players[size-1].getUsername())) {
             screen.getContent().setP4Image(players[size-1].actNow, players[size-1].width, players[size-1].height);
           }
           else {
             screen.getContent().setP4Image(null, 0, 0);
           }
           show[3]=false;
         }
       time[3]+=50;
     }
     else {
       screen.getContent().setP4Image(players[size-1].actNow, players[size-1].width, players[size-1].height);
       time[3]=0;
       players[size-1].isSuper=false;
     }
       screen.getContent().setP4Position(players[size-1].getX(),players[size-1].getY());
       screen.getContent().setP4Blood(players[size-1].health);
       screen.getContent().addP4Points(players[size-1].getpoint());
         screen.getContent().setP4color(players[size-1].style);
         screen.getContent().setP4name(players[size-1].getUsername());
       size--;
     }
   }
}
