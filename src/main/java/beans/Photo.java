/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import java.awt.Image;
import java.awt.Toolkit;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 *
 * @author zfsn
 */
public class Photo {
    private String msg;
    private String path;
    private Image img;

    public Photo(String msg, String path) {
        this.msg = msg;
        this.path = path;
    }

    public Photo() {
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
    
    public Image getImage(){
        try {
            if(img==null)
                //img=Toolkit.getDefaultToolkit().getImage(this.getClass().getResource(path));
                img=ImageIO.read(this.getClass().getResource(path));
            
        } catch (IOException ex) {
            Logger.getLogger(Photo.class.getName()).log(Level.SEVERE, null, ex);
        }
        return img;
    }
}
