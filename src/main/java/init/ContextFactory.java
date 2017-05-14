/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package init;

/**
 *
 * @author zfsn
 */
public class ContextFactory {
    /**
     * 获取Context实例
     * @return Context实例
     */
    public static Context getContext(){
        return Context.getInstance();
    }
    
}
