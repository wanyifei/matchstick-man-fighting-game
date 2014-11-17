package pack;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Player
{
  //player number P1, P2, P3, P4
  int playernumber;
  //if lower than 0, died
  double health;
  //The point each player get
  int point;
  //Mega each player get, if reach 100, special skill
  double mega;
  Location currentlocate;   
  boolean special;
  boolean normalhit;
  boolean isJump;
  
  public static enum State {
    Leftmove1, Leftmove2, Leftmove3, Leftmove4, Leftmove5,
    Rightmove1, Rightmove2, Rightmove3, Rightmove4, Rightmove5,
    Attack1, Attack2, Attack3, Attack4, Attack5,
    Attack6, Attack7, Attack8, Attack9, Attack10,
    Die1, Die2, Die3, Die4, skill,
    Reborn1, Reborn2;
  }
  
   State state;
  public Player(int play){
    playernumber = play;
    health = Constants.PLAYER_HEALTH;
    mega = 0;
    currentlocate = new Location (Constants.STARTX, Constants.STARTY);
  }
  public void setJump(boolean Jump)
  {
    isJump=Jump;
  }
  
  public boolean getJump()
  {
    return isJump;
  }
  
  public  void setState(State now){
    state=now;
  }
  
  public  State getState(){
    return state;
  }
  
  public  void setLocation(double X, double Y){
    currentlocate = new Location (X, Y);
  }
  
  public  Location getLocation(){
    return currentlocate;
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
    else if(currentlocate.getY() <= Constants.LOWER_BOUND){
      return false;
    }
    return true;
  }
  
  //need to refresh every frame
  public void isHit(Player hitter){
    if((hitter.getLocation().getX()-currentlocate.getX() > 0) && 
    (hitter.getLocation().getX()-currentlocate.getX() <= Constants.MINIMUM_HIT_D) && 
    hitter.gethit())
    {
      setLocation(currentlocate.getX()-Constants.HIT_BY_NORMAL, currentlocate.getY());
      hitter.setpoint(hitter.getpoint() + Constants.INCREASE_POINT);
      HitNormalDecreaseHealth();
      setState(Player.State.Die1);
    }
    else if((hitter.getLocation().getX()-currentlocate.getX() < 0) && 
        (currentlocate.getX()-hitter.getLocation().getX() <= Constants.MINIMUM_HIT_D) && 
    hitter.gethit())
    {
      setLocation(currentlocate.getX()+Constants.HIT_BY_NORMAL, currentlocate.getY());
      HitNormalDecreaseHealth();
    }
    else if( (hitter.getLocation().getX()-currentlocate.getX() > 0) &&
    (hitter.getLocation().getX()-currentlocate.getX() <= Constants.SPECIAL_ATTACK_LENGTH) &&
    hitter.getspecial())
    {
      setLocation(currentlocate.getX()-Constants.HIT_BY_SPECIAL, currentlocate.getY());
      hitter.setpoint(hitter.getpoint() + Constants.INCREASE_POINT);
      HitSpecialDecreaseHealth();
      setState(Player.State.Die1);
    }
    else if( (hitter.getLocation().getX()-currentlocate.getX() < 0) &&
    (currentlocate.getX()-hitter.getLocation().getX() <= Constants.SPECIAL_ATTACK_LENGTH) &&
    hitter.getspecial())
    {
      setLocation(currentlocate.getX()-Constants.HIT_BY_SPECIAL, currentlocate.getY());
      HitSpecialDecreaseHealth();
    }
  }
  
  //Call one time, no refreshing
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
  //public void 
  
  public void reborn(){
    for(int i=0; i<Constants.REBORN_TIME; i++){
      //Keep Died (Lying)
    }
  }
  
  public void godown()
  {
    //If player can fall down
    //if(currentlocate.getX())
  }
  
  public boolean isfalldown()
  {
    if((currentlocate.getX() < Constants.FLOORX_RANGE1) && 
    (currentlocate.getX() > Constants.FLOORX_RANGE2) && 
    (currentlocate.getY() < Constants.FLOOR_HEIGHT)){
      return true;
    }
    return false;
  }
  
  public class Movement implements KeyListener
  {
    public void keyPressed(KeyEvent e) {  
      int keyCode = e.getKeyCode();  
      // System.out.println(keyCode); // 打印按键的keyCode  
      if (keyCode == 38) { // 上按键  
          setJump(true);
      }  
      if (keyCode == 40) { // 下按键  
          //y++;  
      }  
      if (keyCode == 37) { // 左按键  
         setState(Player.State.Leftmove1);
         setLocation(currentlocate.getX()-Constants.MOVE_SPEED, currentlocate.getY());
      }

      if (keyCode == 39) { // 右按键  
         setState(Player.State.Rightmove1); 
         setLocation(currentlocate.getX()+Constants.MOVE_SPEED, currentlocate.getY());
      }
      
      if (keyCode == 'J'){
        setState(Player.State.Attack1);
      }
      
      if (keyCode == 'K'){
        setState(Player.State.Attack6);
      }
      
      if (keyCode == ' '){
        setState(Player.State.skill);
      }
    }

    @Override
    public void keyTyped(KeyEvent e)
    {
      
    }

    @Override
    public void keyReleased(KeyEvent e)
    {
      // TODO Auto-generated method stub
      
    }
  }
}


