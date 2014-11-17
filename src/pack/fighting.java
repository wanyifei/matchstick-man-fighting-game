package pack;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.net.URL;

import javax.swing.*;

public class fighting extends JFrame
{
  private static final long serialVersionUID = 1L;

  Image act1;
  Image act2;
  Image act3;
  Image act4;
  Image act5;
  
  int x;
  int y;
  int prevX, prevY;
  boolean isRun;
  boolean right;
  boolean left;
boolean up;
  
  int upUp;
  
  
  public boolean isRun()
  {
    return isRun;
  }

  public void setRun(boolean isRun)
  {
    this.isRun = isRun;
  }


  // 是否令图像移动

  public int getX()
  {
    return x;
  }

  public boolean isRight()
  {
    return right;
  }

  public void setRight(boolean right)
  {
    this.right = right;
  }

  public boolean isLeft()
  {
    return left;
  }

  public void setLeft(boolean left)
  {
    this.left = left;
  }

  public boolean isUp()
  {
    return up;
  }

  public void setUp(boolean up)
  {
    this.up = up;
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
  
  public int getP()
  {
    return this.actPhase;
  }

  boolean actMoving = false;

  // 初始图像编号

  int actPhase = 0;

  Canvas c = new Canvas() {
      private static final long serialVersionUID = 1L;
      /**
       * 绘制图像，直接调用update方法（顺便说一下，若以paint调用update, 再用update调用paint，会出现很好玩的事。^^）
       */
      public void paint(Graphics g) {
          update(g);
      }
      /**
       * 此方法仅在在发生变更时绘制图形
       */
      public void update(Graphics g) {
            switch (actPhase) {
              case 0:
                drawOne(g, act1);
                break;
              case 1:
                drawOne(g, act2);
                break;
             }
      }
      private void drawOne(Graphics g, Image act) {
        g.clearRect(prevX, prevY, act
            .getWidth(null), act.getHeight(null));
          g.drawImage(act, x, y, act
                      .getWidth(null), act.getHeight(null), this);
          prevX=x;
          prevY=y;
      }
  };

  /**
   * 简单动画的构造
   * 
   */
  public fighting() {
      isRun=false;
      up=right=left=false;
      x=0;y=150;upUp=10;
      prevX=0;
      prevY=150;
      setSize(400, 400);
      // 设定背景为黑色
      setBackground(Color.BLACK);
      //这只是一个图像加载的演示，我们完全可以定义一个Image数组，然后动态加载
      //做成动画播放。
      URL imageUrl = getClass().getResource("/image/enemy5.gif");
      act1 = Toolkit.getDefaultToolkit().createImage(imageUrl);
      imageUrl = getClass().getResource("/image/enemy6.gif");
      act2 = Toolkit.getDefaultToolkit().createImage(imageUrl);
      add(c);
      Thread t = new Thread(new Timer());
      t.start();
  }

  /**
   * 事件循环处理线程
   * 
   * @author chenpeng
   */
  class Timer implements Runnable {
      public void run() {
          while (true) {
              if (isRun) {
                actPhase= actPhase==0 ? 1:0;
                if (left){
                  x-=5;
                }
                if (right) {
                  x+=5;
                }
              }
              else {
                actPhase=1;
              }
              if (up) {
                if (!right && !left) actPhase=1;
                if (upUp!=0) {
                  upUp--;
                  y-=10;
                }
                else {
                  up=false;
                  upUp=10;
                }
              }
              if (y<150 && !up) {
                y+=10;
              }
              c.repaint();
              try {
                  Thread.sleep(100);
              } catch (InterruptedException e) {
                  e.printStackTrace();
              }
          }
      }
  }
  
  
  
  /**
   * 主函数
   * 
   * @param args
   */
  public static void main(String[] args) {
      // 实例化本类,并触发循环事件
      fighting sa = new fighting();
      sa.addKeyListener(new KeyListener() {
        public void keyPressed(KeyEvent e) {  
          int keyCode = e.getKeyCode();  
          // System.out.println(keyCode); // 打印按键的keyCode   
          if (keyCode == 37) { // 左按键  
             sa.setRun(true);
             sa.setLeft(true);
          }

          if (keyCode == 39) { // 右按键  
            sa.setRun(true);
            sa.setRight(true);
          }
          
          if (keyCode == 38) { // up按键  
            sa.setUp(true);
          }
        }
        
        @Override
        public void keyTyped(KeyEvent e)
        {
        }

        @Override
        public void keyReleased(KeyEvent e)
        {
          int keyCode = e.getKeyCode();  
          if (keyCode == 37) { // 左按键  
            sa.setRun(false);
            sa.setLeft(false);
         }

         if (keyCode == 39) { // 右按键  
           sa.setRun(false);
           sa.setRight(false);
         }
         
        }
      });
      sa.setVisible(true);
  }
}
