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

import gui.SelectMap.MapListener;

public class EndUI extends JFrame{
  JButton Start;
  JButton Exit;
  public boolean isStart=false;
  public boolean isClient=false;
   SelectMap selectMap;
  //public HelpFrame help=null;
  
//  public  SelectMap getSelectMap()
//  {
//    return selectMap;
//  }


  static int NumPlayer; 
  static int TotalTime;
  
  public EndUI( int wincolor){
    setMinimumSize(new Dimension(1024, 678));
    setMaximumSize(new Dimension(1024, 678));
    setPreferredSize(new Dimension(1024, 678));
    setDefaultCloseOperation(EXIT_ON_CLOSE);
    setVisible(true);
    setLayout(new BorderLayout());
    setContentPane(new JLabel(new ImageIcon("image/wolf.gif")));
    setLayout(new GridLayout(1,2));
    
    add(left(wincolor));
    add(new JLabel(""));
    
    pack();
  
  }
  
  private JPanel left(int wincolor){
    JPanel tempt = new JPanel();
    JLabel winner = new JLabel();
    if(wincolor == 0){
      winner.setText(("Blue wins!"));
      winner.setFont(new Font("BOLD",Font.ITALIC, 48));
      winner.setForeground(Color.BLUE);
      winner.setHorizontalAlignment(JLabel.LEFT);
    }
    else if(wincolor == 1){
      winner.setText(("Orange wins!"));
      winner.setFont(new Font("BOLD",Font.ITALIC, 48));
      winner.setForeground(Color.ORANGE);
      winner.setHorizontalAlignment(JLabel.LEFT);
    }
    else if(wincolor == 2){
      winner.setText(("Red wins!"));
      winner.setFont(new Font("BOLD",Font.ITALIC, 48));
      winner.setForeground(Color.RED);
      winner.setHorizontalAlignment(JLabel.LEFT);
    }
    else if(wincolor == 3){
      winner.setText(("Black wins!"));
      winner.setFont(new Font("BOLD",Font.ITALIC, 48));
      winner.setForeground(Color.WHITE);
      winner.setHorizontalAlignment(JLabel.LEFT);
    }
    tempt.add(winner, BorderLayout.NORTH);
    JPanel down = new JPanel(new GridLayout(7,3));
    down.add(new JLabel(""));
    down.add(new JLabel(""));
    down.add(new JLabel(""));
    Start = new JButton("ReStart");
    Start.addActionListener(new StartListener());
    Start.setFont(new Font("BOLD",Font.PLAIN, 30));
    down.add(new JLabel(""));
    down.add(Start);
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
        isStart=true;
      }
      else if(e.getSource() == Exit){
        setVisible(false);
        System.exit(0);
      }
    }
    
  }
}
