package pack;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

import pack.net.Packet03Key;

public class Player
{
  //if lower than 0, died
  int health;
  //The point each player get
  int point;
  //Mega each player get, if reach 100, special skill
  double mega;
  int x;
  int y;
  boolean special;
  boolean normalhit;
  boolean isJump;
  boolean shouldChange;
  int isLeft;
  int isRight;
  int jumpNum;
  String name;
  KeyBoardLis key;
  int style;
  public int width, height;
  
  public int getStyle()
  {
    return style;
  }
  public void setStyle(int style)
  {
    setImage(style);
    this.style = style;
  }

  boolean change=false;
  
  public boolean isSuper=false;
  public boolean isSpeedUp=false;
  
  public boolean isDe=false;
  
  int isHitToBack;
  
  int prevX;
  int prevY;
  int prevHeight;
  int prevWidth;
  
  int isHit;
  
  Dir dir;
  
  Image actNow;
  
  public String getUsername()
  {
    return name;
  }
  public void setName(String name)
  {
    this.name = name;
  }

  Map<String,Image> act;
  
  public static enum State {
    Leftmove1, Leftmove2, Leftmove3, Leftmove4, Leftmove5,
    Rightmove1, Rightmove2, Rightmove3, Rightmove4, Rightmove5,
    Hit1, Hit2, Hit3,
    Kick1, Kick2, Kick3, Kick4, Kick5,
    Chop1, Chop2,
    Die1, Die2, Die3, Die4, skill,
    Reborn1, Reborn2, Idle,
    Fall1, Fall2, Fall3, Fall4, NULL;
  }
  
  public static enum Dir {
    Left, Right
  }
  
  public static int StateToInt(Player.State inState) {
    switch (inState) {
      case Leftmove1:        return 0;
      case Leftmove2:        return 1;
      case Leftmove3:        return 2;
      case Leftmove4:        return 3;
      case Leftmove5:        return 4;
      case Rightmove1:        return 5;
      case Rightmove2:        return 6;
      case Rightmove3:        return 7;
      case Rightmove4:        return 8;
      case Rightmove5:       return 9;
      case Hit1:       return 10;
      case Hit2:        return 11;
      case Kick1:       return 12;      
      case Kick2:        return 13;
      case Kick3:        return 14;
      case Kick4:       return 15;
      case Kick5:       return 16;
      case Chop1:      return 17;
      case Chop2:      return 18;
      case Idle:        return 19;
      case Hit3:        return 20;
    }
    return -1;
  }
  public static Player.State IntToState(int inInt) {
    switch (inInt) {
      case 0:        return Player.State.Leftmove1;
      case 1:        return Player.State.Leftmove2;
      case 2:        return Player.State.Leftmove3;
      case 3:        return Player.State.Leftmove4;
      case 4:        return Player.State.Leftmove5;
      case 5:        return Player.State.Rightmove1;
      case 6:        return Player.State.Rightmove2;
      case 7:        return Player.State.Rightmove3;
      case 8:        return Player.State.Rightmove4;
      case 9:       return Player.State.Rightmove5;
      case 10:       return Player.State.Hit1;
      case 11:        return Player.State.Hit2;
      case 12:       return Player.State.Kick1;      
      case 13:        return Player.State.Kick2;
      case 14:        return Player.State.Kick3;
      case 15:       return Player.State.Kick4;
      case 16:       return Player.State.Kick5;
      case 17:      return Player.State.Chop1;
      case 18:      return Player.State.Chop2;
      case 19:        return Player.State.Idle;
      case 20:        return Player.State.Hit3;
    }
    return Player.State.Idle;
  }
  public static int DirToInt(Player.Dir inDir) {
    switch(inDir) {
      case Left: return 0;
      case Right: return 1;
    }
    return -1;
  }
  public static Player.Dir IntToDir(int inInt) {
    switch(inInt) {
      case 0: return Player.Dir.Left;
      case 1: return Player.Dir.Right;
    }
    return Player.Dir.Right;
  }
  
   public Dir getDir()
  {
    return dir;
  }
  public void setDir(Dir dir)
  {
    this.dir = dir;
  }

  State state;
  public Player(int inX, int inY, Map<String, Image> typeAct, String inName, KeyBoardLis inKey){
    this.act=typeAct;
    this.name=inName;
    health = Constants.PLAYER_HEALTH;
    mega = 0;
    key=inKey;
    x=inX;
    y=inY;
    prevX=x;
    prevY=y;
    isJump=false;
    isRight=0;
    isLeft=0;
    jumpNum=Constants.JUMP_STEP;
    actNow=act.get("idle");
    prevHeight=actNow.getHeight(null);
    prevWidth=actNow.getWidth(null);
    dir=Player.Dir.Right;
    isHitToBack=Constants.BACK_STEP;
    isHit=0;
  }
  
  // test constructor
  public Player(int inX, int inY, KeyBoardLis inKey,
     String inName){
    this.name=inName;
    health = Constants.PLAYER_HEALTH;
    mega = 0;
    key=inKey;
    x=inX;
    y=inY;
    prevX=x;
    prevY=y;
    isJump=false;
    isRight=0;
    isLeft=0;
    jumpNum=0;
    state=Player.State.Idle;
    
    act=new HashMap<String, Image>();
    
    //setImage(inStyle);
    
    actNow=act.get("idle");

    //prevHeight=actNow.getHeight(null);
    //prevWidth=actNow.getWidth(null);
    actNow=act.get("idle");
    
    dir=Player.Dir.Right;
    isHitToBack=0;
    isHit=0;
  }
  
  private void setImage(int style) {
    try {
      switch (style) {
        case 0:
          act.put("run1", ImageIO.read(this.getClass().getResourceAsStream("../image/run/run_0001_blue.png")));
          act.put("run2", ImageIO.read(this.getClass().getResourceAsStream("../image/run/run_0002_blue.png")));
          act.put("run3", ImageIO.read(this.getClass().getResourceAsStream("../image/run/run_0003_blue.png")));
          act.put("run4", ImageIO.read(this.getClass().getResourceAsStream("../image/run/run_0004_blue.png")));
          act.put("run5", ImageIO.read(this.getClass().getResourceAsStream("../image/run/run_0005_blue.png")));
          act.put("rrun1", ImageIO.read(this.getClass().getResourceAsStream("../image/run/run_r_0001_blue.png")));
          act.put("rrun2", ImageIO.read(this.getClass().getResourceAsStream("../image/run/run_r_0002_blue.png")));
          act.put("rrun3", ImageIO.read(this.getClass().getResourceAsStream("../image/run/run_r_0003_blue.png")));
          act.put("rrun4", ImageIO.read(this.getClass().getResourceAsStream("../image/run/run_r_0004_blue.png")));
          act.put("rrun5", ImageIO.read(this.getClass().getResourceAsStream("../image/run/run_r_0005_blue.png")));
          act.put("kick1", ImageIO.read(this.getClass().getResourceAsStream("../image/kick/kick0001_blue.png")));
          act.put("kick2", ImageIO.read(this.getClass().getResourceAsStream("../image/kick/kick0002_blue.png")));
          act.put("kick3", ImageIO.read(this.getClass().getResourceAsStream("../image/kick/kick0003_blue.png")));
          act.put("kick4", ImageIO.read(this.getClass().getResourceAsStream("../image/kick/kick0004_blue.png")));
          act.put("kick5", ImageIO.read(this.getClass().getResourceAsStream("../image/kick/kick0005_blue.png")));
          act.put("idle", ImageIO.read(this.getClass().getResourceAsStream("../image/hit/hit0001_blue.png")));
          act.put("hit1", ImageIO.read(this.getClass().getResourceAsStream("../image/hit/hit0002_blue.png")));
          act.put("hit2", ImageIO.read(this.getClass().getResourceAsStream("../image/hit/hit0003_blue.png")));
          act.put("hit3", ImageIO.read(this.getClass().getResourceAsStream("../image/hit/hit0004_blue.png")));
//          act.put("chop1", ImageIO.read(this.getClass().getResourceAsStream("../image/chop0001.png")));
//          act.put("chop2", ImageIO.read(this.getClass().getResourceAsStream("../image/chop0005.png")));
          act.put("rkick1", ImageIO.read(this.getClass().getResourceAsStream("../image/kick/kick0001_blue_r.png")));
          act.put("rkick2", ImageIO.read(this.getClass().getResourceAsStream("../image/kick/kick0002_blue_r.png")));
          act.put("rkick3", ImageIO.read(this.getClass().getResourceAsStream("../image/kick/kick0003_blue_r.png")));
          act.put("rkick4", ImageIO.read(this.getClass().getResourceAsStream("../image/kick/kick0004_blue_r.png")));
          act.put("rkick5", ImageIO.read(this.getClass().getResourceAsStream("../image/kick/kick0005_blue_r.png")));
          act.put("ridle", ImageIO.read(this.getClass().getResourceAsStream("../image/hit/hit0001_blue_r.png")));
          act.put("rhit1", ImageIO.read(this.getClass().getResourceAsStream("../image/hit/hit0002_blue_r.png")));
          act.put("rhit2", ImageIO.read(this.getClass().getResourceAsStream("../image/hit/hit0003_blue_r.png")));
          act.put("rhit3", ImageIO.read(this.getClass().getResourceAsStream("../image/hit/hit0004_blue_r.png")));
          act.put("fall1", ImageIO.read(this.getClass().getResourceAsStream("../image/fall/fall_0001_blue.png")));
          act.put("fall2", ImageIO.read(this.getClass().getResourceAsStream("../image/fall/fall_0002_blue.png")));
          act.put("fall3", ImageIO.read(this.getClass().getResourceAsStream("../image/fall/fall_0003_blue.png")));
          act.put("rfall1", ImageIO.read(this.getClass().getResourceAsStream("../image/fall/fall_0001_r_blue.png")));
          act.put("rfall2", ImageIO.read(this.getClass().getResourceAsStream("../image/fall/fall_0002_r_blue.png")));
          act.put("rfall3", ImageIO.read(this.getClass().getResourceAsStream("../image/fall/fall_0003_r_blue.png")));
          act.put("fall4", ImageIO.read(this.getClass().getResourceAsStream("../image/fall/fall_0000_blue.png")));
          act.put("rfall4", ImageIO.read(this.getClass().getResourceAsStream("../image/fall/fall_0000_r_blue.png")));
          //act.put("rchop1", ImageIO.read(this.getClass().getResourceAsStream("../image/chop0001_blue_r.png")));
          //act.put("rchop2", ImageIO.read(this.getClass().getResourceAsStream("../image/chop0005_blue_r.png")));
          break;
        case 1:
          act.put("run1", ImageIO.read(this.getClass().getResourceAsStream("../image/run/run_0001_orange.png")));
          act.put("run2", ImageIO.read(this.getClass().getResourceAsStream("../image/run/run_0002_orange.png")));
          act.put("run3", ImageIO.read(this.getClass().getResourceAsStream("../image/run/run_0003_orange.png")));
          act.put("run4", ImageIO.read(this.getClass().getResourceAsStream("../image/run/run_0004_orange.png")));
          act.put("run5", ImageIO.read(this.getClass().getResourceAsStream("../image/run/run_0005_orange.png")));
          act.put("rrun1", ImageIO.read(this.getClass().getResourceAsStream("../image/run/run_r_0001_orange.png")));
          act.put("rrun2", ImageIO.read(this.getClass().getResourceAsStream("../image/run/run_r_0002_orange.png")));
          act.put("rrun3", ImageIO.read(this.getClass().getResourceAsStream("../image/run/run_r_0003_orange.png")));
          act.put("rrun4", ImageIO.read(this.getClass().getResourceAsStream("../image/run/run_r_0004_orange.png")));
          act.put("rrun5", ImageIO.read(this.getClass().getResourceAsStream("../image/run/run_r_0005_orange.png")));
          act.put("kick1", ImageIO.read(this.getClass().getResourceAsStream("../image/kick/kick0001_orange.png")));
          act.put("kick2", ImageIO.read(this.getClass().getResourceAsStream("../image/kick/kick0002_orange.png")));
          act.put("kick3", ImageIO.read(this.getClass().getResourceAsStream("../image/kick/kick0003_orange.png")));
          act.put("kick4", ImageIO.read(this.getClass().getResourceAsStream("../image/kick/kick0004_orange.png")));
          act.put("kick5", ImageIO.read(this.getClass().getResourceAsStream("../image/kick/kick0005_orange.png")));
          act.put("idle", ImageIO.read(this.getClass().getResourceAsStream("../image/hit/hit0001_orange.png")));
          act.put("hit1", ImageIO.read(this.getClass().getResourceAsStream("../image/hit/hit0002_orange.png")));
          act.put("hit2", ImageIO.read(this.getClass().getResourceAsStream("../image/hit/hit0003_orange.png")));
          act.put("hit3", ImageIO.read(this.getClass().getResourceAsStream("../image/hit/hit0004_orange.png")));
//          act.put("chop1", ImageIO.read(this.getClass().getResourceAsStream("../image/chop0001.png")));
//          act.put("chop2", ImageIO.read(this.getClass().getResourceAsStream("../image/chop0005.png")));
          act.put("rkick1", ImageIO.read(this.getClass().getResourceAsStream("../image/kick/kick0001_orange_r.png")));
          act.put("rkick2", ImageIO.read(this.getClass().getResourceAsStream("../image/kick/kick0002_orange_r.png")));
          act.put("rkick3", ImageIO.read(this.getClass().getResourceAsStream("../image/kick/kick0003_orange_r.png")));
          act.put("rkick4", ImageIO.read(this.getClass().getResourceAsStream("../image/kick/kick0004_orange_r.png")));
          act.put("rkick5", ImageIO.read(this.getClass().getResourceAsStream("../image/kick/kick0005_orange_r.png")));
          act.put("ridle", ImageIO.read(this.getClass().getResourceAsStream("../image/hit/hit0001_orange_r.png")));
          act.put("rhit1", ImageIO.read(this.getClass().getResourceAsStream("../image/hit/hit0002_orange_r.png")));
          act.put("rhit2", ImageIO.read(this.getClass().getResourceAsStream("../image/hit/hit0003_orange_r.png")));
          act.put("rhit3", ImageIO.read(this.getClass().getResourceAsStream("../image/hit/hit0004_orange_r.png")));
          act.put("fall1", ImageIO.read(this.getClass().getResourceAsStream("../image/fall/fall_0001_orange.png")));
          act.put("fall2", ImageIO.read(this.getClass().getResourceAsStream("../image/fall/fall_0002_orange.png")));
          act.put("fall3", ImageIO.read(this.getClass().getResourceAsStream("../image/fall/fall_0003_orange.png")));
          act.put("rfall1", ImageIO.read(this.getClass().getResourceAsStream("../image/fall/fall_0001_r_orange.png")));
          act.put("rfall2", ImageIO.read(this.getClass().getResourceAsStream("../image/fall/fall_0002_r_orange.png")));
          act.put("rfall3", ImageIO.read(this.getClass().getResourceAsStream("../image/fall/fall_0003_r_orange.png")));
          act.put("fall4", ImageIO.read(this.getClass().getResourceAsStream("../image/fall/fall_0000_orange.png")));
          act.put("rfall4", ImageIO.read(this.getClass().getResourceAsStream("../image/fall/fall_0000_r_orange.png")));
          //act.put("rchop1", ImageIO.read(this.getClass().getResourceAsStream("../image/chop0001_blue_r.png")));
          //act.put("rchop2", ImageIO.read(this.getClass().getResourceAsStream("../image/chop0005_blue_r.png")));
          break;
        case 2:
          act.put("run1", ImageIO.read(this.getClass().getResourceAsStream("../image/run/run_0001_red.png")));
          act.put("run2", ImageIO.read(this.getClass().getResourceAsStream("../image/run/run_0002_red.png")));
          act.put("run3", ImageIO.read(this.getClass().getResourceAsStream("../image/run/run_0003_red.png")));
          act.put("run4", ImageIO.read(this.getClass().getResourceAsStream("../image/run/run_0004_red.png")));
          act.put("run5", ImageIO.read(this.getClass().getResourceAsStream("../image/run/run_0005_red.png")));
          act.put("rrun1", ImageIO.read(this.getClass().getResourceAsStream("../image/run/run_r_0001_red.png")));
          act.put("rrun2", ImageIO.read(this.getClass().getResourceAsStream("../image/run/run_r_0002_red.png")));
          act.put("rrun3", ImageIO.read(this.getClass().getResourceAsStream("../image/run/run_r_0003_red.png")));
          act.put("rrun4", ImageIO.read(this.getClass().getResourceAsStream("../image/run/run_r_0004_red.png")));
          act.put("rrun5", ImageIO.read(this.getClass().getResourceAsStream("../image/run/run_r_0005_red.png")));
          act.put("kick1", ImageIO.read(this.getClass().getResourceAsStream("../image/kick/kick0001_red.png")));
          act.put("kick2", ImageIO.read(this.getClass().getResourceAsStream("../image/kick/kick0002_red.png")));
          act.put("kick3", ImageIO.read(this.getClass().getResourceAsStream("../image/kick/kick0003_red.png")));
          act.put("kick4", ImageIO.read(this.getClass().getResourceAsStream("../image/kick/kick0004_red.png")));
          act.put("kick5", ImageIO.read(this.getClass().getResourceAsStream("../image/kick/kick0005_red.png")));
          act.put("idle", ImageIO.read(this.getClass().getResourceAsStream("../image/hit/hit0001_red.png")));
          act.put("hit1", ImageIO.read(this.getClass().getResourceAsStream("../image/hit/hit0002_red.png")));
          act.put("hit2", ImageIO.read(this.getClass().getResourceAsStream("../image/hit/hit0003_red.png")));
          act.put("hit3", ImageIO.read(this.getClass().getResourceAsStream("../image/hit/hit0004_red.png")));
//          act.put("chop1", ImageIO.read(this.getClass().getResourceAsStream("../image/chop0001.png")));
//          act.put("chop2", ImageIO.read(this.getClass().getResourceAsStream("../image/chop0005.png")));
          act.put("rkick1", ImageIO.read(this.getClass().getResourceAsStream("../image/kick/kick0001_red_r.png")));
          act.put("rkick2", ImageIO.read(this.getClass().getResourceAsStream("../image/kick/kick0002_red_r.png")));
          act.put("rkick3", ImageIO.read(this.getClass().getResourceAsStream("../image/kick/kick0003_red_r.png")));
          act.put("rkick4", ImageIO.read(this.getClass().getResourceAsStream("../image/kick/kick0004_red_r.png")));
          act.put("rkick5", ImageIO.read(this.getClass().getResourceAsStream("../image/kick/kick0005_red_r.png")));
          act.put("ridle", ImageIO.read(this.getClass().getResourceAsStream("../image/hit/hit0001_red_r.png")));
          act.put("rhit1", ImageIO.read(this.getClass().getResourceAsStream("../image/hit/hit0002_red_r.png")));
          act.put("rhit2", ImageIO.read(this.getClass().getResourceAsStream("../image/hit/hit0003_red_r.png")));
          act.put("rhit3", ImageIO.read(this.getClass().getResourceAsStream("../image/hit/hit0004_red_r.png")));
          act.put("fall1", ImageIO.read(this.getClass().getResourceAsStream("../image/fall/fall_0001_red.png")));
          act.put("fall2", ImageIO.read(this.getClass().getResourceAsStream("../image/fall/fall_0002_red.png")));
          act.put("fall3", ImageIO.read(this.getClass().getResourceAsStream("../image/fall/fall_0003_red.png")));
          act.put("rfall1", ImageIO.read(this.getClass().getResourceAsStream("../image/fall/fall_0001_r_red.png")));
          act.put("rfall2", ImageIO.read(this.getClass().getResourceAsStream("../image/fall/fall_0002_r_red.png")));
          act.put("rfall3", ImageIO.read(this.getClass().getResourceAsStream("../image/fall/fall_0003_r_red.png")));
          act.put("fall4", ImageIO.read(this.getClass().getResourceAsStream("../image/fall/fall_0000_red.png")));
          act.put("rfall4", ImageIO.read(this.getClass().getResourceAsStream("../image/fall/fall_0000_r_red.png")));
          //act.put("rchop1", ImageIO.read(this.getClass().getResourceAsStream("../image/chop0001_blue_r.png")));
          //act.put("rchop2", ImageIO.read(this.getClass().getResourceAsStream("../image/chop0005_blue_r.png")));
          break;
        case 3:
          act.put("run1", ImageIO.read(this.getClass().getResourceAsStream("../image/run/run0001.png")));
          act.put("run2", ImageIO.read(this.getClass().getResourceAsStream("../image/run/run0002.png")));
          act.put("run3", ImageIO.read(this.getClass().getResourceAsStream("../image/run/run0003.png")));
          act.put("run4", ImageIO.read(this.getClass().getResourceAsStream("../image/run/run0004.png")));
          act.put("run5", ImageIO.read(this.getClass().getResourceAsStream("../image/run/run0005.png")));
          act.put("rrun1", ImageIO.read(this.getClass().getResourceAsStream("../image/run/run_r_0001.png")));
          act.put("rrun2", ImageIO.read(this.getClass().getResourceAsStream("../image/run/run_r_0002.png")));
          act.put("rrun3", ImageIO.read(this.getClass().getResourceAsStream("../image/run/run_r_0003.png")));
          act.put("rrun4", ImageIO.read(this.getClass().getResourceAsStream("../image/run/run_r_0004.png")));
          act.put("rrun5", ImageIO.read(this.getClass().getResourceAsStream("../image/run/run_r_0005.png")));
          act.put("kick1", ImageIO.read(this.getClass().getResourceAsStream("../image/kick/kick0001.png")));
          act.put("kick2", ImageIO.read(this.getClass().getResourceAsStream("../image/kick/kick0002.png")));
          act.put("kick3", ImageIO.read(this.getClass().getResourceAsStream("../image/kick/kick0003.png")));
          act.put("kick4", ImageIO.read(this.getClass().getResourceAsStream("../image/kick/kick0004.png")));
          act.put("kick5", ImageIO.read(this.getClass().getResourceAsStream("../image/kick/kick0005.png")));
          act.put("idle", ImageIO.read(this.getClass().getResourceAsStream("../image/hit/hit0001.png")));
          act.put("hit1", ImageIO.read(this.getClass().getResourceAsStream("../image/hit/hit0002.png")));
          act.put("hit2", ImageIO.read(this.getClass().getResourceAsStream("../image/hit/hit0003.png")));
          act.put("hit3", ImageIO.read(this.getClass().getResourceAsStream("../image/hit/hit0004.png")));
//          act.put("chop1", ImageIO.read(this.getClass().getResourceAsStream("../image/chop0001.png")));
//          act.put("chop2", ImageIO.read(this.getClass().getResourceAsStream("../image/chop0005.png")));
          act.put("rkick1", ImageIO.read(this.getClass().getResourceAsStream("../image/kick/kick0001_r.png")));
          act.put("rkick2", ImageIO.read(this.getClass().getResourceAsStream("../image/kick/kick0002_r.png")));
          act.put("rkick3", ImageIO.read(this.getClass().getResourceAsStream("../image/kick/kick0003_r.png")));
          act.put("rkick4", ImageIO.read(this.getClass().getResourceAsStream("../image/kick/kick0004_r.png")));
          act.put("rkick5", ImageIO.read(this.getClass().getResourceAsStream("../image/kick/kick0005_r.png")));
          act.put("ridle", ImageIO.read(this.getClass().getResourceAsStream("../image/hit/hit0001_r.png")));
          act.put("rhit1", ImageIO.read(this.getClass().getResourceAsStream("../image/hit/hit0002_r.png")));
          act.put("rhit2", ImageIO.read(this.getClass().getResourceAsStream("../image/hit/hit0003_r.png")));
          act.put("rhit3", ImageIO.read(this.getClass().getResourceAsStream("../image/hit/hit0004_r.png")));
          act.put("fall4", ImageIO.read(this.getClass().getResourceAsStream("../image/fall/fall_0000.png")));
          act.put("rfall4", ImageIO.read(this.getClass().getResourceAsStream("../image/fall/fall_0000_r.png")));
          act.put("fall1", ImageIO.read(this.getClass().getResourceAsStream("../image/fall/fall_0001.png")));
          act.put("fall2", ImageIO.read(this.getClass().getResourceAsStream("../image/fall/fall_0002.png")));
          act.put("fall3", ImageIO.read(this.getClass().getResourceAsStream("../image/fall/fall_0003.png")));
          act.put("rfall1", ImageIO.read(this.getClass().getResourceAsStream("../image/fall/fall_0001_r.png")));
          act.put("rfall2", ImageIO.read(this.getClass().getResourceAsStream("../image/fall/fall_0002_r.png")));
          act.put("rfall3", ImageIO.read(this.getClass().getResourceAsStream("../image/fall/fall_0003_r.png")));
          //act.put("rchop1", ImageIO.read(this.getClass().getResourceAsStream("../image/chop0001_blue_r.png")));
          //act.put("rchop2", ImageIO.read(this.getClass().getResourceAsStream("../image/chop0005_blue_r.png")));
          break;
      }
      
    } catch (IOException e) {
        e.printStackTrace();
    }
  }
  
  
  public void setJump(boolean Jump)
  {
    if (Jump) jumpNum=Constants.JUMP_STEP;
    isJump=Jump;
  }
  
  
  public boolean getJump()
  {
    return isJump;
  }
  
  public  void setState(Player.State now){
    state=now;
  }
  
  public  State getState(){
    return state;
  }
  
  public int getX()
  {
    return x;
  }
  public void setX(int x)
  {
    this.x = x;
  }
  public int getY()
  {
    return y;
  }
  public void setY(int y)
  {
    this.y = y;
  }
  public  boolean gethit(){
    return normalhit;
  }
  
  public  void sethit(boolean hitornot){
    normalhit = hitornot;
  }
  
  public  boolean getspecial(){
    return special;
  }
  
  public  void setspecial(boolean specialornot){
    special = specialornot;
  }
  
  public  int getpoint(){
    return point;
  }
  
  public  void setpoint(int pt){
    point = pt;
  }
  
  public boolean isAlive(){
    if(health<=0){
      return false;
    }
    else if(y >= Constants.LOWER_BOUND){
      return false;
    }
    return true;
  }
  
  public int isHit(Player hitter){
    int distance=hitter.getX()-x>0?hitter.getX()-x:x-hitter.getX();
    int distanceY=hitter.getY()-y>0?hitter.getY()-y:y-hitter.getY();
    if (distanceY>=100) return 0;
    if((hitter.dir==Player.Dir.Left) && 
    (distance <= Constants.MINIMUM_HIT_D))
    {
      HitNormalDecreaseHealth();
      this.isHitToBack=Constants.BACK_STEP;
      this.isHit=2;
      return 2;
    }
    else if((hitter.dir==Player.Dir.Right)  && 
        (distance <= Constants.MINIMUM_HIT_D))
    {
      HitNormalDecreaseHealth();
      this.isHitToBack=Constants.BACK_STEP;
      this.isHit=1;
      return 1;
    }
    else if( (hitter.dir==Player.Dir.Left)  &&
    (hitter.getX()-x <= Constants.SPECIAL_ATTACK_LENGTH) &&
    hitter.getspecial())
    {
      HitNormalDecreaseHealth();
      this.isHitToBack=Constants.BACK_STEP;
      this.isHit=2;
      return 2;
    }
    else if( (hitter.dir==Player.Dir.Left)  &&
    (x-hitter.getX() <= Constants.SPECIAL_ATTACK_LENGTH) &&
    hitter.getspecial())
    {
      HitNormalDecreaseHealth();
      this.isHitToBack=Constants.BACK_STEP;
      this.isHit=1;
      return 1;
    }
    return 0;
  }
  public void HitIncreaseMega(){
    if((mega+Constants.HIT_INCREASE_MEGA > Constants.HIGHEST_MEGA) && (mega<Constants.HIGHEST_MEGA)){
      mega=Constants.HIGHEST_MEGA;
    }
    else if(mega<Constants.HIGHEST_MEGA){
      mega=mega+Constants.HIT_INCREASE_MEGA;
    }
  }
  
  public void HitNormalDecreaseHealth(){
    if ((health > 0) && (health - Constants.HIT_BY_NORMAL<= 0)){
      health=0;
    }
    else if (health > 0) {
      health = health-Constants.HIT_BY_NORMAL;
    }
  }
  
  public void HitSpecialDecreaseHealth(){
    if ((health > 0) && (health - Constants.HIT_BY_SPECIAL<= 0)){
      health=0;
    }
    else if (health > 0) {
      health = health-Constants.HIT_BY_SPECIAL;
    }
  }
  
  
  
  public boolean isfalldown()
  {
    if (Game.game.socketClient.screen==1) {
      if (x>Constants.HOLE_X_LEFT && x<Constants.HOLE_X_RIGHT) return true;
      else {
        if (y>Constants.FLOWER_Y+20) return true;
        else return y<Constants.FLOWER_Y;
      }
    }
    else if (Game.game.socketClient.screen==2) {
      return y<Constants.FLOOR_HEIGHT_GRASS;
    }
    else if (Game.game.socketClient.screen==0) {
      return y<Constants.FLOOR_HEIGHT_SNOW;
    }
    else return true;
  }
  boolean notFall;
  public boolean isNotFall()
  {
    return notFall;
  }
  public void setNotFall(boolean notFall)
  {
    this.notFall = notFall;
  }
  public void tick () {
    if (health!=0) {
      shouldChange=false;
      notFall=false;
      if (key!=null) {
        if (key.isLeft() && !key.isHit() && !key.isKick()) {
          if (state!=Player.State.Leftmove1 && state!=Player.State.Leftmove2 && 
              state!=Player.State.Leftmove3 && state!=Player.State.Leftmove4 && 
              state!=Player.State.Leftmove5 && state!=Player.State.Rightmove1 && state!=Player.State.Rightmove2 && 
              state!=Player.State.Rightmove3 && state!=Player.State.Rightmove4 && 
              state!=Player.State.Rightmove5) 
          setState(Player.State.Leftmove1);
          dir=Player.Dir.Left;
          if (Game.game.socketClient.screen==1) {
            if (x-Constants.MOVE_SPEED>0 && !(y>Constants.FLOWER_Y+20 && x-Constants.MOVE_SPEED
                <Constants.HOLE_X_LEFT)) {
              if (isSpeedUp) x-=2*Constants.MOVE_SPEED;
              else x-=Constants.MOVE_SPEED;
            }
          }
          else {
            if (x-Constants.MOVE_SPEED>0) {
              if (isSpeedUp) x-=2*Constants.MOVE_SPEED;
              else x-=Constants.MOVE_SPEED;
            }
          }
          shouldChange=true;
        }
        if (key.isRight() && !key.isHit() && !key.isKick()) {
          if (state!=Player.State.Rightmove1 && state!=Player.State.Rightmove2 && 
              state!=Player.State.Rightmove3 && state!=Player.State.Rightmove4 && 
              state!=Player.State.Rightmove5 && state!=Player.State.Leftmove1 && state!=Player.State.Leftmove2 && 
              state!=Player.State.Leftmove3 && state!=Player.State.Leftmove4 && 
              state!=Player.State.Leftmove5) 
          setState(Player.State.Rightmove1); 
          dir=Player.Dir.Right;
          if (Game.game.socketClient.screen==1) {
            if (x-Constants.MOVE_SPEED<900 && !(y>Constants.FLOWER_Y+20 && x+Constants.MOVE_SPEED
                >Constants.HOLE_X_RIGHT)) {
              if (isSpeedUp) x+=2*Constants.MOVE_SPEED;
              else x+=Constants.MOVE_SPEED;
            }
          }
          else {
            if (x-Constants.MOVE_SPEED<900) {
              if (isSpeedUp) x+=2*Constants.MOVE_SPEED;
              else x+=Constants.MOVE_SPEED;
            }
          }
          shouldChange=true;
        }
        if (key.isHit() && state!=Player.State.Hit1 && state!=Player.State.Hit2 && state!=Player.State.Kick1 && state!=Player.State.Kick2 
            && state!=Player.State.Kick3 && state!=Player.State.Kick4
            && state!=Player.State.Kick5) {
          setState(Player.State.Hit1);
          shouldChange=true;
        }
        if (key.isKick() && state!=Player.State.Kick1 && state!=Player.State.Kick2 
            && state!=Player.State.Kick3 && state!=Player.State.Kick4
            && state!=Player.State.Kick5 && state!=Player.State.Hit1 && state!=Player.State.Hit2) {
          setState(Player.State.Kick1);
          shouldChange=true;
        }
        if (key.isUp() && !isfalldown()) {
          isJump=true;
          jumpNum=Constants.JUMP_STEP;
          shouldChange=true;
        }
        if (key.isSuper() && point==100) {
          isSuper=true;
          shouldChange=true;
        }
        if (key.isSpeedUp() && point>=50) {
          isSpeedUp=true;
          shouldChange=true;
        }
      }
      if (isSuper && point==100) {
        point=0;
        shouldChange=true;
      }
      if (isSpeedUp && point>=50) {
        if (!isDe) point-=50;
        isDe=true;
        shouldChange=true;
      }
      if (!isSpeedUp) isDe=false;
      if (y>700) {
        health=0;
      }
      if (isJump && jumpNum!=0) {
        if (y-Constants.JUMP_HEIGHT_PERSTEP>50) y-=Constants.JUMP_HEIGHT_PERSTEP;
        jumpNum--;
        shouldChange=true;
      }
      if (jumpNum==0) {
        jumpNum=Constants.JUMP_STEP;
        isJump=false;
        shouldChange=true;
        if (key!=null) key.setUp(false);
      }
      if (!isJump && isfalldown() && !notFall) {
        y+=Constants.DOWN_SPEED;
      }
      if (this.isHitToBack!=0 && isHit!=0) {
        if (isHit==1) if (x+Constants.MOVE_SPEED<900) x+=Constants.HIT_BY_NORMAL;
        if (isHit==2) if (x-Constants.MOVE_SPEED>0) x-=Constants.HIT_BY_NORMAL;
        this.isHitToBack--;
        shouldChange=true;
      }
      if (this.isHitToBack<=0 && isHit!=0) {
        if (key!=null) key.setHit(false);
         isHit=0;
         shouldChange=true;
      }
      if (shouldChange) {
        int trans=2;
        if (key!=null) trans=isfalldown() ? 1:0;
        Packet03Key packet = new Packet03Key(name, x, y, 
            Player.StateToInt(state), Player.DirToInt(dir), trans, isSuper, isSpeedUp);
        packet.writeData(Game.game.socketClient);
      }
    }
    
  }
  
  public boolean isDe()
  {
    return isDe;
  }
  public void setDe(boolean isDe)
  {
    this.isDe = isDe;
  }
  public int getIsHit()
  {
    return isHit;
  }
  public void setIsHit(int isHit)
  {
    this.isHit = isHit;
  }
  public void render () {
    if (health==0 && state!=Player.State.Fall4 
        && state!=Player.State.Fall1 && state!=Player.State.Fall2
        && state!=Player.State.Fall3 && state!=Player.State.NULL) {
      state=Player.State.Fall4;
    }
    switch (state) {
      case Idle:
        if (dir==Player.Dir.Left) actNow=act.get("idle");
        if (dir==Player.Dir.Right) actNow=act.get("ridle");
        width=43;
        height=106;
        break;
      case Leftmove1:
        actNow=act.get("run1");
        state=Player.State.Leftmove2;
        width=91;
        height=109;
        break;
      case Leftmove2:
        actNow=act.get("run2");
        state=Player.State.Leftmove3;
        width=91;
        height=109;
        break;
      case Leftmove3:
        actNow=act.get("run3");
        state=Player.State.Leftmove4;
        width=91;
        height=109;
        break;
      case Leftmove4:
        actNow=act.get("run4");
        state=Player.State.Leftmove5;
        width=91;
        height=109;
        break;
      case Leftmove5:
        actNow=act.get("run5");
        state=Player.State.Idle;
        width=91;
        height=109;
        break;
      case Rightmove1:
        actNow=act.get("rrun1");
        state=Player.State.Rightmove2;
        width=91;
        height=109;
        break;
      case Rightmove2:
        actNow=act.get("rrun2");
        state=Player.State.Rightmove3;
        width=91;
        height=109;
        break;
      case Rightmove3:
        actNow=act.get("rrun3");
        state=Player.State.Rightmove4;
        width=91;
        height=109;
        break;
      case Rightmove4:
        actNow=act.get("rrun4");
        state=Player.State.Rightmove5;
        width=91;
        height=109;
        break;
      case Rightmove5:
        actNow=act.get("rrun5");
        state=Player.State.Idle;
        width=91;
        height=109;
        break;
      case Hit1:
        if (dir==Player.Dir.Left)  actNow=act.get("hit1");
        if (dir==Player.Dir.Right) actNow=act.get("rhit1");
          state=Player.State.Hit2;
          width=57;
          height=106;
        break;
      case Hit2:
        if (dir==Player.Dir.Left)  actNow=act.get("hit2");
        if (dir==Player.Dir.Right) actNow=act.get("rhit2");
        state=Player.State.Hit3;
        if (key!=null) key.setHit(false);
        width=57;
        height=106;
        break;
      case Hit3:
        if (dir==Player.Dir.Left)  actNow=act.get("hit3");
        if (dir==Player.Dir.Right) actNow=act.get("rhit3");
        state=Player.State.Idle;
        if (key!=null) key.setHit(false);
        width=57;
        height=106;
        break;
      case Kick1:
        if (dir==Player.Dir.Left)  actNow=act.get("kick1");
        if (dir==Player.Dir.Right) actNow=act.get("rkick1");
        state=Player.State.Kick2;
        width=57;
        height=105;
        break;
      case Kick2:
        if (dir==Player.Dir.Left)  actNow=act.get("kick2");
        if (dir==Player.Dir.Right) actNow=act.get("rkick2");
        state=Player.State.Kick3;
        width=57;
        height=105;
        break;
      case Kick3:
        if (dir==Player.Dir.Left)  actNow=act.get("kick3");
        if (dir==Player.Dir.Right) actNow=act.get("rkick3");
        state=Player.State.Kick4;
        width=57;
        height=105;
        break;
      case Kick4:
        if (dir==Player.Dir.Left)  actNow=act.get("kick4");
        if (dir==Player.Dir.Right) actNow=act.get("rkick4");
        state=Player.State.Kick5;
        width=57;
        height=105;
        break;
      case Kick5:
        if (dir==Player.Dir.Left)  actNow=act.get("kick5");
        if (dir==Player.Dir.Right) actNow=act.get("rkick5");
        state=Player.State.Idle;
        if (key!=null) key.setKick(false);
        width=57;
        height=105;
        break;
      case Chop1:
        if (dir==Player.Dir.Left)  actNow=act.get("chop1");
        if (dir==Player.Dir.Right) actNow=act.get("rchop1");
          state=Player.State.Chop2;
          break;
      case Chop2:
        if (dir==Player.Dir.Left)  actNow=act.get("chop2");
        if (dir==Player.Dir.Right) actNow=act.get("rchop2");
        state=Player.State.Idle;
        break;
      case Fall4:
        if (dir==Player.Dir.Left)  actNow=act.get("fall4");
        if (dir==Player.Dir.Right) actNow=act.get("rfall4");
        state=Player.State.Fall1;
        width=51;
        height=126;
        break;
      case Fall1:
        if (dir==Player.Dir.Left)  actNow=act.get("fall1");
        if (dir==Player.Dir.Right) actNow=act.get("rfall1");
        state=Player.State.Fall2;
        width=83;
        height=109;
        break;
      case Fall2:
        if (dir==Player.Dir.Left)  actNow=act.get("fall2");
        if (dir==Player.Dir.Right) actNow=act.get("rfall2");
        state=Player.State.Fall3;
        width=109;
        height=100;
        break;
      case Fall3:
        if (dir==Player.Dir.Left)  actNow=act.get("fall3");
        if (dir==Player.Dir.Right) actNow=act.get("rfall3");
        state=Player.State.NULL;
        width=126;
        height=51;
        break;
      case NULL:
        actNow=null;
        break;
      default:
        if (dir==Player.Dir.Left) actNow=act.get("idle");
        if (dir==Player.Dir.Right) actNow=act.get("ridle");
        break;
    }
  }
  public int getPrevX()
  {
    return prevX;
  }
  public void setPrevX(int prevX)
  {
    this.prevX = prevX;
  }
  public int getPrevY()
  {
    return prevY;
  }
  public void setPrevY(int prevY)
  {
    this.prevY = prevY;
  }
  public int getPrevHeight()
  {
    return prevHeight;
  }
  public int getPrevWidth()
  {
    return prevWidth;
  }
}


