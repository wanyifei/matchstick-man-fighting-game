package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class HelpFrame extends JFrame{
	JTextArea HelpText;
	JButton OK;
	
	public StartUI start=null;
	
	public HelpFrame(){
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
      System.out.println("aaaa");
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
		JPanel left = new JPanel();
		left.setLayout(new BoxLayout(left, BoxLayout.PAGE_AXIS));
		HelpText = new JTextArea();
		HelpText.setSize(new Dimension(300, 300));
		HelpText.setEditable(false);
		HelpText.setOpaque(false);
		HelpText.setFont(new Font("BOLD", 1, 24));
		HelpText.setText("/n");
		HelpText.setText("Once upon a time, the world of stick was\n"
				        +"peaceful. One day, the sticks are so \n"
				        +"boring that they decide to host the tour-\n"
				        +"nament to choose a strongest stickman\n"
				        +"to represent them. You can control one\n"
				        +"stickman and help it to be the best one!\n\n");
		HelpText.append("       '↑'----------Jump\n");
		HelpText.append("       '←'----------Back\n");
		HelpText.append("       '→'----------Forward\n");
		HelpText.append("       'S'-----------Kick\n");
		HelpText.append("       'Space'-------Hit\n\n");
		HelpText.append("       'D'-----------Accelerate for 1 sec,\n "
				      + "                               mana cost 50\n");
		HelpText.append("       'F'-----------Invisible for 5 sec,\n"
				      + "                               mana cost 100\n");
		HelpText.setForeground(Color.WHITE);
		
		JPanel OKPanel = new JPanel(new FlowLayout());
		OK = new JButton("OK");
		
		OK.addActionListener(new OKListener());
		OKPanel.add(OK);
		OKPanel.setOpaque(false);
		left.add(HelpText,BorderLayout.CENTER);
		left.add(OKPanel, BorderLayout.SOUTH);
		left.setOpaque(false);
		
		add(left);
		add(new JLabel(""));
		pack();
	}
	
	public class OKListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			setVisible(false);
			start=new StartUI(StartUI.NumPlayer, StartUI.TotalTime);
		}		
	}

	

}
