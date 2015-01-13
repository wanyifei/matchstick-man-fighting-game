package gui;

import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Screen extends JFrame{
	Content tempt;
	int NumPlayer;
	int TotalTime;
	boolean WhetherFlower = false;
	public int num=-1;
	public AudioClip bgm;
	private String MapMode;
	
	public Screen(int inNumPlayer, int inTotalTime, String MapMode){
		super("Matchstick War");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
		int NumPlayer = inNumPlayer;
		int TotalTime = inTotalTime;
		
		this.MapMode=MapMode;
		
		if(MapMode == "Grass") {
			setContentPane(new JLabel(new ImageIcon("image/Grass.jpg")));
			num=2;
		}
		else if(MapMode == "Snow") {
			setContentPane(new JLabel(new ImageIcon("image/Snow.jpg")));
			num=0;
		}
		else if(MapMode == "Flower"){
			setContentPane(new JLabel(new ImageIcon("image/flower.gif")));
			num=1;
			WhetherFlower = true;
		}
		
		if(MapMode == "Grass") {
      setContentPane(new JLabel(new ImageIcon("image/Grass.jpg")));
      num=2;
      try{
        bgm = Applet.newAudioClip(new URL("file:video/grass.wav"));
        //bgm.loop();
      }
      //if there is a problem with the URL
      //then this is the exception to be used
      catch (MalformedURLException mfe){
        System.out.println("An error occured, please try again...");
      }
    }
    else if(MapMode == "Snow") {
      setContentPane(new JLabel(new ImageIcon("image/Snow.jpg")));
      num=0;
      try{
        bgm = Applet.newAudioClip(new URL("file:video/snow.wav"));
        //bgm.loop();
      }
      //if there is a problem with the URL
      //then this is the exception to be used
      catch (MalformedURLException mfe){
        System.out.println("An error occured, please try again...");
      }
    }
    else if(MapMode == "Flower"){
      setContentPane(new JLabel(new ImageIcon("image/flower.gif")));
      num=1;
      WhetherFlower = true;
      try{
        bgm = Applet.newAudioClip(new URL("file:video/flower.wav"));
        //bgm.loop();
      }
      //if there is a problem with the URL
      //then this is the exception to be used
      catch (MalformedURLException mfe){
        System.out.println("An error occured, please try again...");
      }
    }
		
		setLayout(new FlowLayout());
		tempt = new Content(NumPlayer, TotalTime, WhetherFlower);
		add(tempt);
		tempt.start();
				
		pack();
	}
	
	//Get the Content in this JFrame.
	public Content getContent(){
		return tempt;
	}
	

}
