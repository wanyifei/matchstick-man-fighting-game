package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class SelectMap extends JFrame
{
	JButton Grass, Flower, Snow;
	String MapMode;
	 Screen screen;
	 
	 
	 //public boolean isReady=false;
	public  Screen getScreen()
  {
    return screen;
  }

  int NumPlayer, TotalTime;
	public SelectMap(int inNumPlayer, int inTotalTime){
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
		add(Left());
		add(new JLabel(""));
		pack();
	}
	
	private JPanel Left(){
		JPanel tempt = new JPanel();
		tempt.setLayout(new BorderLayout());
		JLabel Select = new JLabel("       Select Your Battlefield:");
		Select.setFont(new Font("BOLD",Font.ITALIC, 36));
		Select.setForeground(Color.WHITE);
		Select.setHorizontalAlignment(JLabel.LEFT);
		tempt.add(Select, BorderLayout.NORTH);

		JPanel down = new JPanel(new GridLayout(7,3));
		down.add(new JLabel(""));
		down.add(new JLabel(""));
		down.add(new JLabel(""));
		Grass = new JButton("Grass");
		Grass.addActionListener(new MapListener());
		Grass.setFont(new Font("BOLD",Font.PLAIN, 30));
		down.add(new JLabel(""));
		down.add(Grass);
		down.add(new JLabel(""));
		down.add(new JLabel(""));
		down.add(new JLabel(""));
		down.add(new JLabel(""));

		
		Snow = new JButton("Snow");
		Snow.addActionListener(new MapListener());
		Snow.setFont(new Font("BOLD",Font.PLAIN, 30));
		down.add(new JLabel(""));
		down.add(Snow);
		down.add(new JLabel(""));
		down.add(new JLabel(""));
		down.add(new JLabel(""));
		down.add(new JLabel(""));

		
		Flower = new JButton("Flower");
		Flower.addActionListener(new MapListener());
		Flower.setFont(new Font("BOLD",Font.PLAIN, 30));
		down.add(new JLabel(""));
		down.add(Flower);
		down.add(new JLabel(""));
		down.add(new JLabel(""));
		down.add(new JLabel(""));
		down.add(new JLabel(""));
		
		down.setOpaque(false);
		
		tempt.add(down, BorderLayout.CENTER);
		tempt.setOpaque(false);
		return tempt;
		
	}
	
	public Content GetContent(){
		return screen.getContent();
	}
	
	public class MapListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			if(e.getSource() == Grass){	
				setVisible(false);
				//isReady=true;
				screen = new Screen(NumPlayer,TotalTime, "Grass");
			}
			else if(e.getSource() == Snow){
				setVisible(false);
				//isReady=true;
				screen = new Screen(NumPlayer, TotalTime, "Snow");
			}
			else if(e.getSource() == Flower){
				setVisible(false);
				//isReady=true;
				screen = new Screen(NumPlayer,TotalTime,"Flower");
			}
		}
		
	}
	
	//public static void main(String[] args){
		//StartUI hahaha = new StartUI();
	//}	

}
