package com.fan.cn;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.swetake.util.Qrcode;

public class QRCode_1 {
	public static void main(String[] args) {
		String str = "BEGIN:VCARD\r\n" + 
		   "FN:姓名:范清涛\r\n"+
		   "TITLE:河北科技师范学院CEO\r\n" + 
		   "TEL;CELL;VOICE:18713332933\r\n"+
		   "URL:http://www.baidu.com\r\n"+
		   "END:VCARD";
		createQrcode(15, str, "qw.png", 255, 0, 100, 0, 200, 255);
	}

	public static void createQrcode(int version, String str, String path,
			int startR, int startG, int startB, int endR, int endG, int endB) {
		Qrcode qrcode = null;
		Graphics2D gs = null;
		try {
			qrcode = new Qrcode();
			qrcode.setQrcodeVersion(version);
			qrcode.setQrcodeErrorCorrect('L');
			int imgSize = 67 + (version - 1) * 12;
			BufferedImage bi = new BufferedImage(imgSize, imgSize,
					BufferedImage.TYPE_INT_RGB);
			gs = bi.createGraphics();
			gs.setBackground(Color.WHITE);
			gs.clearRect(0, 0, imgSize, imgSize);
			boolean[][] calQrcode = qrcode.calQrcode(str.getBytes());
			Color color;
			int pixoff = 2;
			for (int i = 0; i < calQrcode.length; i++) {
				for (int j = 0; j < calQrcode[i].length; j++) {
					int r = startR + (endR - startR) * (i + 1)
							/ calQrcode.length;
					int g = startG + (endG - startG) * (i + 1)
							/ calQrcode.length;
					int b = startB + (endB - startB) * (i + 1)
							/ calQrcode.length;
					color = new Color(r, g, b);
					gs.setColor(color);
					if (calQrcode[i][j]) {
						gs.fillRect(i * 3 + pixoff, j * 3 + pixoff, 3, 3);
					}
				}
			}
			int len = imgSize / 2 - 10;
			Image img = ImageIO.read(new File("2.jpg"));
			gs.drawImage(img, len, len, 20, 20, null);
			bi.flush();
			gs.dispose();

			ImageIO.write(bi, "png", new File(path));
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			gs.dispose();
		}
	}
}
