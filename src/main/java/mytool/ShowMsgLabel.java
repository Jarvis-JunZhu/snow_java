/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mytool;

import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JLabel;

/**
 *
 * @author zfsn
 */
public class ShowMsgLabel extends JLabel{
    private String msg;
    private Color color=Color.black;
    public ShowMsgLabel(String msg){
        this.msg=msg;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
    
    
    
     public void paint(Graphics g) {
         g.setColor(color);
         int x=10;
         int y=10;
         if(msg!=null){
             String[] msgs=msg.split("#");
             for(String str:msgs){
                 g.drawString(str, x, y);
                 y=y+20;
             }
             
         }
         
     }
}
