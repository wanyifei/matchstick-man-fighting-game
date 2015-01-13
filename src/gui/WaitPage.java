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

public class WaitPage extends JFrame{
  JButton Start;
  JButton Exit;
  public boolean isStart=false;
  public boolean isClient=false;
   SelectMap selectMap;

  static int NumPlayer; 
  static int TotalTime;
  
  public WaitPage(String IP){
    setMinimumSize(new Dimension(1024, 678));
    setMaximumSize(new Dimension(1024, 678));
    setPreferredSize(new Dimension(1024, 678));
    setDefaultCloseOperation(EXIT_ON_CLOSE);
    setVisible(true);
    setLayout(new BorderLayout());
    setContentPane(new JLabel(new ImageIcon("image/wolf.gif")));
    setLayout(new GridLayout(1,2));
    
    add(left(IP));
    add(new JLabel(""));
    
    pack();
  
  }
  
  private JPanel left(String IP){
    JPanel tempt = new JPanel();
    JLabel winner = new JLabel();
    JLabel wait = new JLabel();

    winner.setText(("Waiting for more players..."));
    winner.setFont(new Font("BOLD",Font.ITALIC, 36));
    winner.setForeground(Color.WHITE);
    winner.setHorizontalAlignment(JLabel.LEFT);
    
    tempt.add(winner, BorderLayout.NORTH);
    JPanel down = new JPanel(new GridLayout(7,3));
    down.add(new JLabel(""));
    down.add(new JLabel(""));
    down.add(new JLabel(""));
    wait.setText("Your IP address is :" + IP);
    wait.setFont(new Font("BOLD",Font.ITALIC, 18));
    wait.setForeground(Color.WHITE);
    down.add(wait);
    down.setOpaque(false);
    
    tempt.add(down, BorderLayout.CENTER);
    tempt.setOpaque(false);
    return tempt;
  }
  
  
}
