/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package init;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

import beans.Photo;

/**
 *
 * @author zfsn
 */
public class ContextInit {

	private static final String	xmlPath	= "InitData.xml";
	private static Document		doc;

	static {
		try {
			SAXReader reader = new SAXReader();
			doc = reader.read(xmlPath);
		} catch (DocumentException ex) {
			Logger.getLogger(ContextInit.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	public static Document getDocument() throws DocumentException {
		if (doc == null) {
			SAXReader reader = new SAXReader();
			doc = reader.read(xmlPath);
		}
		return doc;
	}

	public static void initComtext(Context ct) {

		String toName = doc.selectSingleNode("//base/toName").getText();
		String sender = doc.selectSingleNode("//base/sender").getText();
		String title = doc.selectSingleNode("//base/title").getText();
		String[] textContent = doc.selectSingleNode("//base/textContent").getText().split("#|，|。|？|！|,|!|\\.");
		ct.put("sender", sender);
		ct.put("textContent", textContent);
		ct.put("title", title);
		ct.put("toName", toName);

		List<Node> nodes = doc.selectNodes("//base/photoes/photo");
		List<Photo> photoes = new ArrayList<Photo>();
		for (Node node : nodes) {
			String imgPath = node.getText();
			String msg = node.valueOf("@msg");
			Photo pt = new Photo(msg, imgPath);
			photoes.add(pt);
		}
		ct.put("photoes", photoes);

		String music = doc.selectSingleNode("//base/system/music").getText();
		String trayIco = doc.selectSingleNode("//base/system/trayIco").getText();
		String snow = doc.selectSingleNode("//base/system/snow").getText();
		String homeBg = doc.selectSingleNode("//base/system/homeBg").getText();
		String exitBg = doc.selectSingleNode("//base/system/exitBg").getText();

		ct.put("music", music);
		ct.put("trayIco", trayIco);
		ct.put("snow", snow);
		ct.put("homeBg", homeBg);
		ct.put("exitBg", exitBg);

		String musicIco = doc.selectSingleNode("//base/system/buttonIcoes/musicIco").getText();
		String exitIco = doc.selectSingleNode("//base/system/buttonIcoes/exitIco").getText();
		String snowIco = doc.selectSingleNode("//base/system/buttonIcoes/snowIco").getText();
		String backIco = doc.selectSingleNode("//base/system/buttonIcoes/backIco").getText();
		String exitSureIco = doc.selectSingleNode("//base/system/buttonIcoes/exitSureIco").getText();
		ct.put("musicIco", musicIco);
		ct.put("exitIco", exitIco);
		ct.put("snowIco", snowIco);
		ct.put("backIco", backIco);
		ct.put("exitSureIco", exitSureIco);

	}

	public static void main(String[] args) {
		Context ct = ContextFactory.getContext();
		String[] data = (String[]) ct.get("textContent");
		for (String str : data) {
			System.out.println(str);
		}
	}
}
