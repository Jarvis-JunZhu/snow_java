package snow;

import java.applet.AudioClip;
import java.awt.AWTException;
import java.awt.Image;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

import javax.swing.JApplet;

import init.Context;
import init.ContextFactory;
import window.ExitDialog;
import window.Home;
import window.SnowWindow;

public class Snow {

	private SnowWindow	s;
	private Home		home;
	private AudioClip	audio;				// 声音
	private boolean		isPlayMusic	= false;
	private boolean		isSnow		= false;
	private Context		ct;

	public Snow() {

		ct = ContextFactory.getContext();

		final Snow mainwindow = this;

		new Thread(new Runnable() {
			public void run() {
				home = new Home(mainwindow);
				home.setVisible(true);
			}

		}).start();

		new Thread(new Runnable() {
			public void run() {
				s = new SnowWindow();
				s.setVisible(true);
				isSnow = true;
			}

		}).start();

		tray();
		URL audioPath = this.getClass().getResource((String) ct.get("music"));
		audio = JApplet.newAudioClip(audioPath); // 实例化声音
		audio.loop(); // 循环播放
		isPlayMusic = true;
	}

	public static void main(String[] args) {
		new Snow();
		// this.setAlwaysOnTop(true); //设置始终在屏幕的最上面

	}

	/**
	 * 托盘方法
	 */
	public void tray() {
		TrayIcon trayIcon = null;
		if (SystemTray.isSupported()) // 判断系统是否支持系统托盘
		{
			SystemTray tray = SystemTray.getSystemTray(); // 创建系统托盘
			Image image = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource((String) ct.get("trayIco")));// 载入图片,这里要写你的图标路径哦
			ActionListener toHomeListener = new ActionListener() {

				public void actionPerformed(ActionEvent e) {
					if (home.isVisible()) {
						home.setVisible(false);
					} else {
						home.setVisible(true);
					}
				}
			};

			ActionListener toSnowListener = new ActionListener() {

				public void actionPerformed(ActionEvent e) {
					if (s.isVisible()) {
						s.setVisible(false);
					} else {
						s.setVisible(true);
					}
				}
			};

			ActionListener closeListener = new ActionListener() {

				public void actionPerformed(ActionEvent e) {

					new Thread(new Runnable() {

						public void run() {
							boolean snowVisible = s.isVisible();
							boolean homeVisible = home.isVisible();
							s.setVisible(false);
							home.setVisible(false);
							ExitDialog ed = new ExitDialog();
							ed.setVisible(true);
							if (ed.getOption() == 0) {
								System.exit(0);
							}
							ed.dispose();
							s.setVisible(snowVisible);
							home.setVisible(homeVisible);
						}

					}).start();

				}
			};
			ActionListener stopMusicListener = new ActionListener() {

				public void actionPerformed(ActionEvent e) {
					if (isPlayMusic) {

						audio.stop();
						isPlayMusic = false;

					} else {
						audio.loop();
						isPlayMusic = true;
					}
				}
			};
			// 创建弹出菜单
			PopupMenu popup = new PopupMenu();
			MenuItem homeItem = new MenuItem("home");
			MenuItem stopMusicItem = new MenuItem("music");
			MenuItem snowItem = new MenuItem("snow");
			MenuItem exitItem = new MenuItem("exit");
			homeItem.addActionListener(toHomeListener);
			snowItem.addActionListener(toSnowListener);
			stopMusicItem.addActionListener(stopMusicListener);
			exitItem.addActionListener(closeListener);
			popup.add(homeItem);
			popup.add(snowItem);
			popup.add(stopMusicItem);
			popup.addSeparator();
			popup.add(exitItem);
			trayIcon = new TrayIcon(image, "snow", popup);// 创建trayIcon
			trayIcon.addActionListener(toHomeListener);
			try {
				tray.add(trayIcon);
			} catch (AWTException e1) {
				e1.printStackTrace();
			}
		}
	}

	public AudioClip getAudio() {
		return audio;
	}

	public void setAudio(AudioClip audio) {
		this.audio = audio;
	}

	public Home getHome() {
		return home;
	}

	public void setHome(Home home) {
		this.home = home;
	}

	public boolean isIsPlayMusic() {
		return isPlayMusic;
	}

	public void setIsPlayMusic(boolean isPlayMusic) {
		this.isPlayMusic = isPlayMusic;
	}

	public boolean isIsSnow() {
		return isSnow;
	}

	public void setIsSnow(boolean isSnow) {
		this.isSnow = isSnow;
	}

	public SnowWindow getS() {
		return s;
	}

	public void setS(SnowWindow s) {
		this.s = s;
	}
}
