package pack;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyBoardLis implements KeyListener
{
  private boolean isUp;
  private boolean isLeft;
  private boolean isRight;
  private boolean isHit;
  private boolean isKick;
  private boolean isSuper;
  private boolean isSpeedUp;
  
  public KeyBoardLis(Game game) {
    game.addKeyListener(this);
    isUp=false;
    isLeft=false;
    isRight=false;
    isHit=false;
    isKick=false;
    isSuper=false;
    isSpeedUp=false;
  }
  
  public void keyPressed(KeyEvent e) 
  {  
    int keyCode = e.getKeyCode();   
    if (keyCode == 37) 
    { 
      isLeft=true;
    }
    
    if (keyCode == 39) 
    { 
      isRight=true;
    }
    
    if (keyCode == 38) 
    { 
      isUp=true;
    }
    
    if (keyCode == KeyEvent.VK_SPACE) 
    {  
      isHit=true;
    }
    
    if (keyCode == KeyEvent.VK_S) 
    {
      isKick=true;
    }
    
    if (keyCode == KeyEvent.VK_F) 
    {   
      isSuper=true;
    }
    
    if (keyCode == KeyEvent.VK_D) 
    { 
      isSpeedUp=true;
    }
  }
  
  public boolean isUp()
  {
    return isUp;
  }

  public void setUp(boolean isUp)
  {
    this.isUp = isUp;
  }

  public boolean isKick()
  {
    return isKick;
  }

  public void setKick(boolean isKick)
  {
    this.isKick = isKick;
  }

  public boolean isRight()
  {
    return isRight;
  }

  public void setHit(boolean isHit)
  {
    this.isHit = isHit;
  }

  public boolean isLeft()
  {
    return isLeft;
  }

  public boolean isHit()
  {
    return isHit;
  }
  
  public boolean isSpeedUp()
  {
    return isSpeedUp;
  }

  public void setSpeedUp(boolean isSpeedUp)
  {
    this.isSpeedUp = isSpeedUp;
  }

  public boolean isSuper()
  {
    return isSuper;
  }

  @Override
  public void keyTyped(KeyEvent e)
  {
  }

  @Override
  public void keyReleased(KeyEvent e)
  {
    int keyCode = e.getKeyCode();  
    if (keyCode == 37) 
    { 
      isLeft=false;
    }

    if (keyCode == 39) 
    {  
      isRight=false;
    }
    
    if (keyCode == 38) 
    {   
      isUp=false;
    }
    
    if (keyCode == KeyEvent.VK_F) 
    {
      isSuper=false;
    }
    
    if (keyCode == KeyEvent.VK_D) 
    {  
      isSpeedUp=false;
    }
  }

}
