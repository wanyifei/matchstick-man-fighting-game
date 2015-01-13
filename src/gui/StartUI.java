package gui;

import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.MalformedURLException;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class StartUI extends JFrame{
	JButton Start;
	JButton Exit;
	JButton Help;
	public boolean isHelp=false;
	public boolean isStart=false;
	public boolean isClient=false;
	 SelectMap selectMap;
	public HelpFrame help=null;
	
	public  SelectMap getSelectMap()
  {
    return selectMap;
  }


  static int NumPlayer; 
	static int TotalTime;
	public AudioClip bgm;
	
	public StartUI(int inNumPlayer, int inTotalTime){
		NumPlayer = inNumPlayer;
		TotalTime = inTotalTime;
		setMinimumSize(new Dimension(1024, 678));
		setMaximumSize(new Dimension(1024, 678));
		setPreferredSize(new Dimension(1024, 678));
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
		setLayout(new BorderLayout());
		setContentPane(new JLabel(new ImageIcon("image/wolf.gif")));
		setLayout(new GridLayout(1,2));
		try
    {
      UIManager.setLookAndFeel("com.birosoft.liquid.LiquidLookAndFeel");
    }
    catch( UnsupportedLookAndFeelException e )
    {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    catch( ClassNotFoundException e )
    {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    catch( InstantiationException e )
    {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    catch( IllegalAccessException e )
    {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
		
		add(left());
		add(new JLabel(""));
		
		try{
      bgm = Applet.newAudioClip(new URL("file:video/startUI.wav"));
      bgm.loop();
    }
    //if there is a problem with the URL
    //then this is the exception to be used
    catch (MalformedURLException mfe){
      while(true) {
        System.out.println("An error occured, please try again...");
      }
    }
		
		pack();
	
	}
	
	private JPanel left(){
		JPanel tempt = new JPanel();
		tempt.setLayout(new BorderLayout());
		JLabel Select = new JLabel("          Matchstick War");
		Select.setFont(new Font("BOLD",Font.ITALIC, 48));
		Select.setForeground(Color.WHITE);
		Select.setHorizontalAlignment(JLabel.LEFT);
		tempt.add(Select, BorderLayout.NORTH);

		JPanel down = new JPanel(new GridLayout(7,3));
		down.add(new JLabel(""));
		down.add(new JLabel(""));
		down.add(new JLabel(""));
		Start = new JButton("Start");
		Start.addActionListener(new StartListener());
		Start.setFont(new Font("BOLD",Font.PLAIN, 30));
		down.add(new JLabel(""));
		down.add(Start);
		down.add(new JLabel(""));
		down.add(new JLabel(""));
		down.add(new JLabel(""));
		down.add(new JLabel(""));

		
		Help = new JButton("Help");
		Help.addActionListener(new StartListener());
		Help.setFont(new Font("BOLD",Font.PLAIN, 30));
		down.add(new JLabel(""));
		down.add(Help);
		down.add(new JLabel(""));
		down.add(new JLabel(""));
		down.add(new JLabel(""));
		down.add(new JLabel(""));

		
		Exit = new JButton("Exit");
		Exit.addActionListener(new StartListener());
		Exit.setFont(new Font("BOLD",Font.PLAIN, 30));
		down.add(new JLabel(""));
		down.add(Exit);
		down.add(new JLabel(""));
		down.add(new JLabel(""));
		down.add(new JLabel(""));
		down.add(new JLabel(""));
		
		down.setOpaque(false);
		
		tempt.add(down, BorderLayout.CENTER);
		tempt.setOpaque(false);
		return tempt;
		
	}
	
	public Content getContent(){
		return selectMap.GetContent();
	}
	
	
	public class StartListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			if(e.getSource() == Start){	
				setVisible(false);
				if (!isClient) selectMap = new SelectMap(4, TotalTime);
				isStart=true;
			}
			else if(e.getSource() == Help){
			  help=new HelpFrame();
				setVisible(false);
				isHelp=true;
			}
			else if(e.getSource() == Exit){
				setVisible(false);
				System.exit(0);
			}
		}
		
	}

	/*public static void getSystemLookAndView(){
		UIManager.LookAndFeelInfo []info=UIManager.getInstalledLookAndFeels() ; 
	    try{
	    UIManager.setLookAndFeel("com.birosoft.liquid.LiquidLookAndFeel");
	    }
	    catch(Exception e){
	      e.printStackTrace();
	    }
	    JFrame testframe = new JFrame("testing");
	    JPanel testpanel = new JPanel();
	    JButton ok = new JButton();
	    ok.setFont(new Font("BOLD", 3, 34));
	    
	    ok.setPreferredSize(new Dimension(150,50));
	    ok.setText("Start");
	    ok.setToolTipText("start the game!");
	    JButton cancel = new JButton("Exit");
	    cancel.setPreferredSize(new Dimension(150,50));
	    cancel.setFont(new Font("BOLD", 3, 34));
	    ok.setSelected(true);
	    JTextField inputip=new JTextField(15);
	    inputip.setSelectedTextColor(Color.yellow);
	    testpanel.add(inputip);
	    //ok.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
	    testpanel.add(ok);
	    testpanel.add(cancel);
	    testframe.add(testpanel);
	    testframe.setVisible(true);
	    testframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    testframe.setSize(500, 500);
	    testframe.setResizable(false);
	  }*/
	
	/*
	public static void main(String[] args){
		new StartUI(4, 50);
	}
	*/
}


