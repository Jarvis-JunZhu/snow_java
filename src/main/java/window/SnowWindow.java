package window;

import beans.Snower;
import init.ContextFactory;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import javax.swing.*;
import snow.Snow;


public class SnowWindow extends JWindow {

    Dimension d;
    /**
     * 最大风力
     */
    int maxWindPower = 5;
    /**
     * 风力最大持久时间(秒)
     */
    int maxWindTime = 10;
    /**
     * 最大生产雪花数量 
     */
    int maxSnowCount = 10;
    /**
     * 雪花最大宽度
     */
    int maxWidth=10;
    /**
     * 雪花最大高度
     */
    int maxHight=10;
    /**
     * 图片坐标点颜色集合
     */
    List<List<Integer>> imgColor = new ArrayList<List<Integer>>();
    List<Snower> snowAll = new LinkedList<Snower>();
    BufferedImage img;
    Image imgsn ;
    /**
     * 绘画锁
     */
    Object clock = new Object();
    boolean readImg = false;
    /**
     * 障碍颜色集合
     */
    int cs[] = {Color.white.getRGB(), Color.black.getRGB(), Color.blue.getRGB(), Color.green.getRGB(), Color.red.getRGB()};

    public SnowWindow() {

        this.setAlwaysOnTop(true);   //设置始终在屏幕的最上面
        this.d = Toolkit.getDefaultToolkit().getScreenSize();
        this.setSize(d);
        imgsn= Toolkit.getDefaultToolkit().getImage(this.getClass().getResource((String)ContextFactory.getContext().get("snow")));
        com.sun.awt.AWTUtilities.setWindowOpaque(this, false);

        readImg = true;
        synchronized (clock) {
            clock.notifyAll();
        }
        thread();
    }
    Random r = new Random();
    /**
     * 当前风力
     */
    int nowWindPower = r.nextInt(maxWindPower + 1);
    /**
     * 当前风吹时间(验证次数)
     */
    int nowWindTimeCount = r.nextInt(maxWindTime * 1000 / 20);
    int windTimeCount = 0;

    /**
     * 控制风和重绘的线程
     */
    private void thread() {
        new Thread(new Runnable() {

            public void run() {
                int tempTimeCount = maxWindTime * 1000 / 20;
                while (true) {
                    if (windTimeCount == nowWindTimeCount) {
                        // 最少风吹2秒
                        nowWindTimeCount = r.nextInt(tempTimeCount / 10 * 8) + tempTimeCount / 10 * 2;
                        nowWindPower = r.nextInt(maxWindPower + 1) * ((r.nextInt(2) == 0) ? -1 : 1);
                        windTimeCount = 0;
                    } else {
                        windTimeCount++;
                    }

//                    try {
//                        img = new Robot().createScreenCapture(new Rectangle(0, 0, d.width, d.height));
//                    } catch (AWTException e) {
//                        e.printStackTrace();
//                    }

                    repaint();
                    try {
                        Thread.sleep(20);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    /**
     * 获取当前场景(屏幕)的某坐标点的颜色是否是颜色c(集合)
     */
    private boolean xyIsColor(int x, int y, int... cs) {
//        try {
//            // int rgb = img.getRGB(x, y);
//             int rgb = new Robot().getPixelColor(x, y).getRGB();
//            for (int c : cs) {
//                if (rgb == c) {
//                    return true;
//                }
//            }
//            return false;
//        } catch (Exception e) {
//            return true;
//        }
        return false;
    }

    /**
     * 检查当前雪花的下方 有没有别的雪花 或者 障碍物
     *
     * @return	应该移动的方向<br> [0：按风向]<br> [1：停止]<br> [2：往右]<br> [3：往左]<br> [4：往下]
     */
    private int checkDown(Snower sn) {
        // 是否在边界以内
        if (new Rectangle(0, 0, d.width, d.height).contains(new Point(sn.getX(), sn.getY()))) {

            // 附加颜色 &&!xyIsColor(sn.getX()+nowWindPower, sn.getY()+1, Color.black.getRGB())

            // 风向是否障碍
            if (!xyIsColor(sn.getX() + nowWindPower, sn.getY() + 1, cs)) {
                return 0;
            }
            // 往下移动
            if (!xyIsColor(sn.getX(), sn.getY() + 1, cs)) {
                return 4;
            }
            // 往右移动
            if (!xyIsColor(sn.getX() + 1, sn.getY() + 1, cs)) {
                if (sn.getX() - 1 <= 0) {
                    return 4;
                }
                return 2;
            }
            // 往左移动
            if (!xyIsColor(sn.getX() - 1, sn.getY() + 1, cs)) {
                if (sn.getX() + 1 >= d.width) {
                    return 4;
                }
                return 3;
            }
            return 1;
        } // 超出下边界(停止)
        else if (sn.getY() >= d.height) {
            return 1;
        } // 超出左右边界(垂直向下)
        else if (sn.getX() <= 0 || sn.getX() >= d.width) {
            return 4;
        }
        return 1;
    }

    /**
     * 重写JPanel的重绘
     */
    @Override
    public void paint(Graphics g) {
         super.paint(g);
//		try{
        if (!readImg) {
            synchronized (clock) {
                try {
                    clock.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        //g.drawImage(img, 0, 0, null);

        if (g.getColor().getRGB() != Color.white.getRGB()) {
            g.setColor(Color.white);
        }
        // 创建雪花
        for (int i = 0; i < r.nextInt(maxSnowCount + 1); i++) {
            snowAll.add(new Snower(r.nextInt(d.width - 3) + 1,r.nextInt(maxWidth)+2,r.nextInt(maxHight)+2));
            if (nowWindPower > 0) {
                snowAll.add(new Snower(1, r.nextInt(d.height - 3) + 1,r.nextInt(maxWidth)+2,r.nextInt(maxHight)+2));
            } else if (nowWindPower < 0) {
                snowAll.add(new Snower(d.width - 1, r.nextInt(d.height - 3) + 1,r.nextInt(maxWidth)+2,r.nextInt(maxHight)+2));
            }
        }
        // 画雪花
        for (int i = snowAll.size() - 1; i >= 0; i--) {
            Snower sn = snowAll.get(i);
            switch (checkDown(sn)) {
                case 0:
                    sn.setX(sn.getX() + nowWindPower);
                    sn.setY(sn.getY() + 1);
                    break;
                case 1:
//                    try {
//                        img.setRGB(sn.getX(), sn.getY(), Color.white.getRGB());
//                    } catch (Exception e) {
//                    }
                    //				backPanel.refreshBack();
//                    int tempX = sn.getX();
//                    int tempY = sn.getY();
//                    g.drawRect(tempX, tempY, 1, 1);
                    snowAll.remove(i);
                    continue;
                case 2:
                    sn.setX(sn.getX() + 1);
                    sn.setY(sn.getY() + 1);
                    break;
                case 3:
                    sn.setX(sn.getX() - 1);
                    sn.setY(sn.getY() + 1);
                    break;
                case 4:
                    sn.setY(sn.getY() + 1);
                    break;
            }
            int tempX = sn.getX();
            int tempY = sn.getY();
           // g.drawRect(tempX, tempY, 2, 2);
            g.drawImage(imgsn, tempX, tempY, sn.getWidth(), sn.getHight(), null);
        }
//		}catch(Exception e){
//			System.out.println("paint is error! Exception= " + e.getMessage());
//		}
    }
}
