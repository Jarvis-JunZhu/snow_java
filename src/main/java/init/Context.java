/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package init;
import beans.Photo;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;

/**
 *
 * @author zfsn
 */
public class Context {
     private static Context context=new Context();
     private Map<String,Object> system=new HashMap<String,Object>(0);
     
     private Context(){
         ContextInit.initComtext(this);
     }
     public static Context getInstance(){
         return context;
     }

   
    public void put(String key,Object value){
        system.put(key, value);
    }
    public Object get(String key){
        return system.get(key);
    } 

}
