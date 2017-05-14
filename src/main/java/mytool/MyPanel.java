/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mytool;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import javax.swing.JButton;
import javax.swing.JPanel;

/**
 *
 * @author zfsn
 */
public class MyPanel extends JPanel{
    private Image backImg;
    
    
    public MyPanel(String imgPath){
         backImg = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource(imgPath));
         this.updateUI();
    }
    
    public MyPanel(){
       
    }
    
    @Override
     public void paintComponent(Graphics g) {
        super.paintComponent(g);
         if(backImg!=null)
             g.drawImage(backImg, 0, 0,this.getWidth(),this.getHeight(), null);
     }
    
    

    public Image getBackImg() {
        return backImg;
    }

    public void setBackImg(Image backImg) {
        this.backImg = backImg;
        this.repaint();
    }
    
    
    
     
    
}
