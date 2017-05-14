/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mytool;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JLabel;

import beans.Message;
import beans.Photo;
import init.Context;
import init.ContextFactory;

/**
 *
 * @author zfsn
 */
public class MyShowLabel extends JLabel {

	private int						bgWidth		= 240;
	private int						bgHight		= 240;
	/**
	 * 圆点位置
	 */
	private double					ox			= bgWidth / 2;
	private double					oy			= bgHight / 5 - 20;
	private double					x;
	private double					y;
	/**
	 * 心形半径
	 */
	private double					r;
	/**
	 * 图像缩放参数
	 */
	private float					tm			= 0f;
	/**
	 * 待绘制图像
	 */
	private BufferedImage			img;
	/**
	 * 获取配置信息
	 */
	private Context					ct;
	private Graphics2D				gp;
	/**
	 * 信息列表
	 */
	private java.util.List<Message>	msgs		= new ArrayList<Message>();
	/**
	 * 已经取出信息条数
	 */
	private int						index		= 0;

	/**
	 * 是否为显示信息内容时刻
	 */
	private int						showState	= 1;

	public MyShowLabel() {
		// setBackground(Color.black);
		ct = ContextFactory.getContext();
		img = new BufferedImage(bgWidth, bgHight, BufferedImage.TYPE_4BYTE_ABGR);
		gp = img.createGraphics();
		img = gp.getDeviceConfiguration().createCompatibleImage(bgWidth, bgHight, Transparency.TRANSLUCENT);
		gp.dispose();
		gp = img.createGraphics();

		new Thread(new Runnable() {

			public void run() {
				while (true) {
					showState = 1;
					// 显示欢迎动画
					for (int i = 0; i < 1;) {
						tm = tm + 0.2f;
						try {
							if (tm > 18)
								Thread.sleep(8000);
							else
								Thread.sleep(100);
						} catch (InterruptedException ex) {
							Logger.getLogger(MyShowLabel.class.getName()).log(Level.SEVERE, null, ex);
						}
						if (tm > 18) {
							i++;
							tm = 0;
						}
						repaint();
					}
					showState = 2;
					while (showState == 2) {
						// 显示信息内容
						repaint();
						try {
							Thread.sleep(1000);
						} catch (InterruptedException ex) {
							Logger.getLogger(MyShowLabel.class.getName()).log(Level.SEVERE, null, ex);
						}
					}
					showState = 3;
					while (showState == 3) {
						// 显示信息内容
						repaint();
						try {
							Thread.sleep(5000);
						} catch (InterruptedException ex) {
							Logger.getLogger(MyShowLabel.class.getName()).log(Level.SEVERE, null, ex);
						}
					}

				}
			}

		}).start();
	}

	public void paint(Graphics g) {
		if (showState == 1) {
			// 显示欢迎动画
			img = gp.getDeviceConfiguration().createCompatibleImage(bgWidth, bgHight, Transparency.TRANSLUCENT);
			gp.dispose();
			gp = img.createGraphics();

			int i, j;
			for (i = 0; i <= 90; i++) {
				for (j = 0; j <= 90; j++) {
					r = Math.PI / 45 * i * (1 - Math.sin(Math.PI / 45 * j)) * tm;
					x = r * Math.cos(Math.PI / 45 * j) * Math.sin(Math.PI / 45 * i) + ox;
					y = -r * Math.sin(Math.PI / 45 * j) + oy;
					gp.setColor(Color.PINK);
					gp.setFont(new Font("宋体", Font.BOLD, 18));
					gp.drawString((String) ct.get("toName"), 20, 20);

					gp.setColor(Color.GREEN);
					gp.fillOval((int) x, (int) y, 2, 2);
					gp.setColor(Color.red);
					gp.setFont(new Font("宋体", Font.BOLD, (int) (tm + 14)));
					String title = ((String) ct.get("title")).trim();
					int[] loaction = getSuitLocation(title);
					gp.drawString(title, loaction[0], loaction[1]);
					gp.setColor(Color.PINK);
					gp.setFont(new Font("宋体", Font.BOLD, 18));
					gp.drawString("——" + (String) ct.get("sender"), 140, 230);

				}
			}
			g.drawImage(img, 0, 0, null);
		} else if (showState == 2) {
			// 显示信息内容
			createMsg();
			g.setColor(Color.red);
			for (Message msg : msgs) {
				g.drawString(msg.getMsg(), msg.getX(), msg.getY());
			}

		} else {
			if (index < ((java.util.List<Photo>) ct.get("photoes")).size()) {
				Photo p = ((java.util.List<Photo>) ct.get("photoes")).get(index++);
				int[] size = getSuitSize(p.getImage());
				g.drawImage(p.getImage(), (bgWidth - size[0]) / 2, (bgHight - size[1]) / 2, size[0], size[1], null);
				String photoMsg = p.getMsg();
				g.setColor(Color.white);
				if (photoMsg != null && photoMsg.length() > 0)
					g.drawString(photoMsg, (int) (ox - photoMsg.length() * 6), 230);

			} else {
				index = 0;
				showState = 1;
			}

		}
	}

	/**
	 * 计算标题的合适位置
	 * 
	 * @param str
	 * @return
	 */
	public int[] getSuitLocation(String str) {
		int[] d = new int[2];
		d[0] = (int) (ox - (tm + 14) * str.length() / 2);
		d[1] = (int) (oy + tm * 2 + 40);
		return d;
	}

	public int getBgHight() {
		return bgHight;
	}

	public void setBgHight(int bgHight) {
		this.bgHight = bgHight;
		oy = bgHight / 4;
	}

	public int getBgWidth() {
		return bgWidth;
	}

	public void setBgWidth(int bgWidth) {
		this.bgWidth = bgWidth;
		ox = bgWidth / 2;
	}

	/**
	 * 获取新的信息及计算要显示信息的位置
	 */
	private void createMsg() {
		if ((String[]) ct.get("textContent") != null) {
			for (Message msg : msgs) {
				msg.setY(msg.getY() - 15);
			}
			if (msgs.size() > 0 && msgs.get(0).getY() < 0)
				msgs.remove(0);
			if (((String[]) ct.get("textContent")).length > index) {
				String str = ((String[]) ct.get("textContent"))[index++].trim();
				Message m = new Message((int) (ox - str.length() * 6), 230, str);
				msgs.add(m);
			}
		}
		if (msgs.size() == 0) {
			showState = 3;
			index = 0;
		}
	}

	/**
	 * 计算图片合适大小
	 * 
	 * @param img
	 * @return 返回图片宽和高
	 */
	private int[] getSuitSize(Image img) {
		int[] size = new int[2];
		if (img.getWidth(null) > img.getHeight(null)) {
			if (bgWidth > img.getWidth(null)) {
				size[0] = img.getWidth(null);
				size[1] = img.getHeight(null);
			} else {
				size[0] = bgWidth;
				size[1] = img.getHeight(null) * bgHight / img.getWidth(null);
			}
		} else {
			if (bgHight > img.getHeight(null)) {
				size[0] = img.getWidth(null);
				size[1] = img.getHeight(null);
			} else {
				size[0] = img.getWidth(null) * bgWidth / img.getHeight(null);
				size[1] = bgHight;
			}
		}
		return size;
	}
}
