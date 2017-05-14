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

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import com.sun.awt.AWTUtilities;

import init.ContextFactory;
import mytool.MyButton;
import mytool.MyPanel;
import mytool.MyShowLabel;
import snow.Snow;

/**
 *
 * @author zfsn
 */
public class Home extends JFrame implements ActionListener, MouseMotionListener, MouseListener {

	private JPanel		rootPanel;
	private JButton		musicButton;
	private JButton		snowButton;
	private JButton		closeButton;
	private Snow		main;
	private MyShowLabel	showLabel;
	/**
	 * 记录鼠标点击的位置
	 */
	private int			rx;
	private int			ry;

	public Home(Snow m) {
		this.main = m;

		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

		this.setSize(600, 600);
		rootPanel = new MyPanel((String) ContextFactory.getContext().get("homeBg"));
		rootPanel.setSize(600, 600);
		rootPanel.setLayout(null);

		musicButton = new MyButton((String) ContextFactory.getContext().get("musicIco"));
		musicButton.setBounds(240, 150, 60, 30);
		musicButton.addActionListener(this);

		snowButton = new MyButton((String) ContextFactory.getContext().get("snowIco"));
		snowButton.setBounds(310, 150, 60, 30);
		snowButton.addActionListener(this);

		closeButton = new MyButton((String) ContextFactory.getContext().get("exitIco"));
		closeButton.setBounds(380, 150, 60, 30);
		closeButton.addActionListener(this);

		showLabel = new MyShowLabel();
		showLabel.setSize(240, 240);
		showLabel.setBounds(220, 190, 240, 240);

		rootPanel.add(musicButton);
		rootPanel.add(snowButton);
		rootPanel.add(closeButton);
		rootPanel.add(showLabel);

		this.addMouseListener(this);
		this.addMouseMotionListener(this);

		this.setContentPane(rootPanel);
		this.setLocation(((int) screenSize.getWidth() - 600) / 2, ((int) screenSize.getHeight() - 600) / 2);
		this.setUndecorated(true);
		AWTUtilities.setWindowOpaque(this, false); // 窗体的透明
		this.setVisible(true);
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
		if (e.getSource() == musicButton) {
			if (main.isIsPlayMusic()) {
				main.getAudio().stop();
				main.setIsPlayMusic(false);

			} else {
				main.getAudio().loop();
				main.setIsPlayMusic(true);
			}

		} else if (e.getSource() == snowButton) {
			if (main.isIsSnow()) {
				main.setIsSnow(false);
			} else {
				main.setIsSnow(true);
			}
			main.getS().setVisible(main.isIsSnow());
		} else if (e.getSource() == closeButton) {
			this.setVisible(false);
		}
	}

}
