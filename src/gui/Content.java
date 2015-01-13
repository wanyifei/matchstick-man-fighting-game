package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class Content extends JPanel implements Runnable{
	int NumPlayer;
	public int GameTime;
	int P1X;
	int P1Y;
	int P1WIDTH;
  int P1HEIGHT;
	int P2X;
	int P2Y;
	int P2WIDTH;
  int P2HEIGHT;
	int P3X;
	int P3Y;
	int P3WIDTH;
  int P3HEIGHT;
	int P4X;
	int P4Y;
	int P4WIDTH;
  int P4HEIGHT;
	int P1Points = 0;
	int P2Points = 0;
	int P3Points = 0;
	int P4Points = 0;
	private int BloodP1;
	private int BloodP2;
	private int BloodP3;
	private static int BloodP4;
	Image P1;
	Image P2;
	Image P3;
	Image P4;
	int P1color=-1, P2color=-1, P3color=-1, P4color=-1;
	String P1name="", P2name="", P3name="", P4name="";
	String ip="";
	public int getP1color()
  {
    return P1color;
  }

  public String getP1name()
  {
    return P1name;
  }

  public void setP1name(String p1name)
  {
    P1name = p1name;
  }

  public String getP2name()
  {
    return P2name;
  }

  public void setP2name(String p2name)
  {
    P2name = p2name;
  }

  public String getP3name()
  {
    return P3name;
  }

  public void setP3name(String p3name)
  {
    P3name = p3name;
  }

  public String getP4name()
  {
    return P4name;
  }

  public void setP4name(String p4name)
  {
    P4name = p4name;
  }

  public String getIp()
  {
    return ip;
  }

  public void setIp(String ip)
  {
    this.ip = ip;
  }

  public void setP1color(int p1color)
  {
    P1color = p1color;
  }

  public int getP2color()
  {
    return P2color;
  }

  public void setP2color(int p2color)
  {
    P2color = p2color;
  }

  public int getNumPlayer()
  {
    return NumPlayer;
  }

  public void setNumPlayer(int numPlayer)
  {
    NumPlayer = numPlayer;
  }

  public int getP3color()
  {
    return P3color;
  }

  public void setP3color(int p3color)
  {
    P3color = p3color;
  }

  public int getP4color()
  {
    return P4color;
  }

  public void setP4color(int p4color)
  {
    P4color = p4color;
  }

  boolean WhetherFlower;
	
	private boolean running = false;
	
	public Content(int inNumPlayer, int inTime, boolean inWhetherFlower) {
		//Set the Content Size/
		setMinimumSize(new Dimension(1024, 678));
		setMaximumSize(new Dimension(1024, 678));
		setPreferredSize(new Dimension(1024, 678));
		WhetherFlower = inWhetherFlower;
		
		//Read in the GameName and Number of Players
		GameTime = inTime;
		NumPlayer = inNumPlayer;
		if(inNumPlayer == 2){
			BloodP1 = 100;
			BloodP2 = 100;
			BloodP3 = 0;
			BloodP4 = 0;
			//P1 = ImageIO.read(this.getClass().getResourceAsStream("../images/grass.jpg"));
			//P2 = ImageIO.read(this.getClass().getResourceAsStream("../images/grass.jpg"));
			//P1X =
			//P1Y =
			//P2X = 
			//P2Y = 
		}
		else if(inNumPlayer == 3){
			BloodP1 = 100;
			BloodP2 = 100;
			BloodP3 = 100;
			BloodP4 = 0;
			//P1 = ImageIO.read(this.getClass().getResourceAsStream("../images/grass.jpg"));
			//P2 = ImageIO.read(this.getClass().getResourceAsStream("../images/grass.jpg"));
			//P3 = ImageIO.read(this.getClass().getResourceAsStream("../images/grass.jpg"));
			//P1X =
			//P1Y =
			//P2X = 
			//P2Y =
			//P3X = 
			//P3Y =
		}
		else if(inNumPlayer == 4){
			BloodP1 = 100;
			BloodP2 = 100;
			BloodP3 = 100;
			BloodP4 = 100;
			//P1 = ImageIO.read(this.getClass().getResourceAsStream("../images/grass.jpg"));
			//P2 = ImageIO.read(this.getClass().getResourceAsStream("../images/grass.jpg"));
			//P3 = ImageIO.read(this.getClass().getResourceAsStream("../images/grass.jpg"));
			//P4 = ImageIO.read(this.getClass().getResourceAsStream("../images/grass.jpg"));
			//P1X = 
			//P1Y =
			//P2X = 
			//P2Y =
			//P3X = 
			//P3Y =
			//P4X = 
			//P4Y =
		}
		setOpaque(false);
	}
	
	private Color intToColor (int i) {
	  switch (i) {
	    case 0:
	      return new Color(12,0,255);
	    case 1:
	      return new Color(255,140,0);
	    case 2:
	      return new Color(220,20,60);
	    case 3:
	      return new Color(0,0,0);
	    default:
	      return new Color(255,255,255);
	  }
	}
	
	public void paint(Graphics g){
		
		if(NumPlayer >= 2){
			g.drawImage(P1, P1X, P1Y, P1WIDTH, P1HEIGHT, this);
			g.drawImage(P2, P2X, P2Y, P2WIDTH, P2HEIGHT, this);
		}
		if(NumPlayer >= 3)
			g.drawImage(P3, P3X, P3Y, P3WIDTH, P3HEIGHT, this);
		if(NumPlayer >= 4)
			g.drawImage(P4, P4X, P4Y, P4WIDTH, P4HEIGHT, this);
		
		g.setFont(new Font("BOLD", 1, 36));
		g.drawString(""+GameTime, 470, 40);
		
		if(NumPlayer >= 2){
			g.setColor(intToColor(P1color));
			g.fillRoundRect(10, 20, 400*BloodP1/100, 18, 22, 22);
			g.drawString(""+P1Points, 10, 80);
			g.drawString(""+P1name, 90, 80); 
			g.setColor(intToColor(3));
			g.setFont(new Font("BOLD", 1, 12));
			g.drawString("Server's IP Address: "+ ip, 10, 120);
			g.setFont(new Font("BOLD", 1, 36));
			g.setColor(intToColor(P2color));
			g.fillRoundRect(612, 20, 400*BloodP2/100, 18,22,22);
			g.drawString(""+P2Points, 612, 80);
			g.drawString(""+P2name, 692, 80); 
		}
		if(NumPlayer >= 3){
			g.setColor(intToColor(P3color));
			g.fillRoundRect(10, 640, 400*BloodP3/100, 18,22,22);
			g.drawString(""+P3Points, 10, 630);
			g.drawString(""+P3name, 90, 630); 
		}
		if(NumPlayer >= 4){
			g.setColor(intToColor(P4color));
			g.fillRoundRect(612, 640, 400*BloodP4/100, 18,22,22);
			g.drawString(""+P4Points, 612, 630);
			g.drawString(""+P4name, 692, 630); 
		}
		g.setColor(new Color(32,32,150));
		if(WhetherFlower){
			g.fillRoundRect(700, 400, 500, 200, 45, 45);
			g.fillRoundRect(-10, 400, 500, 200,45,45);
		}
		
	}
	
	public synchronized void start(){
		running = true;
		new Thread(this).start();
	}
	
	public synchronized void stop(){
		running = false;
	}
		
	public void run(){
		long lastTime= System.currentTimeMillis();
		while(running){
			repaint();
			if(System.currentTimeMillis() - lastTime >= 1000 && GameTime != 0){
				lastTime += 1000;
				GameTime--;				
			}
		}		
	}
	
	//Update the current Image of four players.
	public void setP1Image(Image p1, int width, int height){
		P1 = p1;
		P1WIDTH=width;
		P1HEIGHT=height;
	}
	
	public void setP2Image(Image p2, int width, int height){
		P2 = p2;
		P2WIDTH=width;
    P2HEIGHT=height;
	}
	
	public void setP3Image(Image p3, int width, int height){
		P3 = p3;
		P3WIDTH=width;
    P3HEIGHT=height;
	}
	
	public void setP4Image(Image p4, int width, int height){
		P4 = p4;
		P4WIDTH=width;
    P4HEIGHT=height;
	}
	
	//Update the current Positons of four players.
	public void setP1Positon(int x, int y){
		P1X = x;
		P1Y = y;
	}
	
	public void setP2Postion(int x, int y){
		P2X = x;
		P2Y = y;
	}
	
	public void setP3Postion(int x, int y){
		P3X = x;
		P3Y = y;
	}
	
	public void setP4Position(int x, int y){
		P4X = x;
		P4Y = y;
	}
	
	//Update the current Blood of four players.
	public void setP1Blood(int x){
		BloodP1 = x;
	}
	
	public void setP2Blood(int x){
		BloodP2 = x;
	}
	
	public void setP3Blood(int x){
		BloodP3 = x;
	}
	
	public void setP4Blood(int x){
		BloodP4 = x;
	}
	
	//Add the Game Points to four players
	public void addP1Points(int in){
		P1Points=in;
	}
	
	public void addP2Points(int in){
		P2Points=in;
	}
	
	public void addP3Points(int in){
		P3Points=in;
	}
	
	public void addP4Points(int in){
		P4Points=in;
	}
	
}


	
	
	

	

