/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package window;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JFrame;

import com.sun.awt.AWTUtilities;

import init.ContextFactory;
import mytool.MyButton;
import mytool.MyPanel;
import mytool.ShowMsgLabel;

/**
 *
 * @author zfsn
 */
public class ExitDialog extends JFrame implements ActionListener, MouseMotionListener, MouseListener {
	private int			choice	= 0;
	private boolean		isChose	= false;
	private MyButton	exit;
	private MyButton	back;
	private MyPanel		mainPanel;
	/**
	 * 记录鼠标点击的位置
	 */
	private int			rx;
	private int			ry;

	public ExitDialog() {
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

		this.setSize(344, 213);
		mainPanel = new MyPanel((String) ContextFactory.getContext().get("exitBg"));
		mainPanel.setSize(344, 213);
		mainPanel.setLayout(null);

		exit = new MyButton((String) ContextFactory.getContext().get("exitSureIco"));
		exit.setBounds(185, 130, 49, 19);
		exit.addActionListener(this);

		back = new MyButton((String) ContextFactory.getContext().get("backIco"));
		back.setBounds(255, 130, 49, 19);
		back.addActionListener(this);

		ShowMsgLabel sml = new ShowMsgLabel("亲：#      记得回来哟！！！");
		sml.setBounds(175, 70, 200, 120);

		mainPanel.add(sml);

		mainPanel.add(exit);
		mainPanel.add(back);

		this.setContentPane(mainPanel);
		this.setLocation(((int) screenSize.getWidth() - 344) / 2, ((int) screenSize.getHeight() - 213) / 2);
		this.setUndecorated(true);
		AWTUtilities.setWindowOpaque(this, false); // 窗体的透明
		this.addMouseListener(this);
		this.addMouseMotionListener(this);
		this.setAlwaysOnTop(true); // 设置始终在屏幕的最上面
		this.setVisible(true);
		this.validate();
	}

	public int getOption() {
		while (!isChose) {
			try {
				Thread.sleep(200);
			} catch (InterruptedException ex) {
				Logger.getLogger(ExitDialog.class.getName()).log(Level.SEVERE, null, ex);
			}
		}
		return choice;

	}

	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	public void mousePressed(MouseEvent e) {
		rx = e.getX();
		ry = e.getY();
	}

	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	public void mouseDragged(MouseEvent e) {
		this.setLocation(e.getXOnScreen() - rx, e.getYOnScreen() - ry);
	}

	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == exit) {
			choice = 0;

		} else if (e.getSource() == back) {
			choice = 1;

		}
		isChose = true;
	}

	public static void main(String[] args) {
		new ExitDialog();
	}

}
