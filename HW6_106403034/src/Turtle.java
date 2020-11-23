//姓名:蘇敏宜  學號:106403034 系級:資管三B

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Turtle implements Runnable{
	//烏龜圖片比例=>寬145:高113
	//建構子參數
	private JPanel bowlPanel;
	private JLabel tLabel;
	private int xPos, yPos;			//點擊當下的x,y座標
	//Turtle屬性
	private SecureRandom random = new SecureRandom();
	private Image image;
	private Icon icon;
	private int turtleH, turtleW;	//random後的圖片大小
	private float tRatio = 145/113;	//高:?,寬:?*145/113
	private int blankH;				//烏龜圖片下的空白
	private boolean toRight;		//烏龜的移動方向(0:向左、1:向右)
	private boolean isContinue = true;	//設為false時，此thread就停止執行
	private ArrayList<Cookie> cookies = new ArrayList<Cookie>();	//飼料List
	
	public Turtle(JPanel bowlPanel, JLabel tLabel, int xPos, int yPos, ArrayList<Cookie> cookies) {	//constructor
		this.bowlPanel = bowlPanel;
		this.tLabel = tLabel;
		this.xPos = xPos;
		this.yPos = yPos;
		this.cookies = cookies;
		
		setImage(random.nextBoolean());
		setSize(random.nextInt(50)+30);	//size可能是30-80
	}
	
	public void run() {
		try {
			if(isContinue) {
				//將圖片放進tLabel，並讓tLabel顯示在bowlPanel上
				tLabel.setIcon(icon);
				tLabel.setBounds(xPos, yPos, icon.getIconWidth(), icon.getIconHeight());
				bowlPanel.add(tLabel);
				bowlPanel.repaint();
			
				//掉落
				do {
					yPos += 10;
					tLabel.setLocation(xPos, yPos);
					Thread.sleep(random.nextInt(2000));
				}while((yPos+icon.getIconHeight()<bowlPanel.getBounds().height)&&isContinue);
				
				//處理圖片下方空白的部分，讓烏龜可以站在底部
				if(!atBottom()&&isContinue) {
					yPos = bowlPanel.getBounds().height-turtleH+blankH;
					tLabel.setLocation(xPos, yPos);
				}
				
				if(!random.nextBoolean()&&isContinue) {	//著陸時隨機決定要不要變方向
					toRight=!toRight;
					setImage(toRight);
					setSize(turtleH);
					tLabel.setIcon(icon);
					tLabel.setBounds(xPos, yPos, icon.getIconWidth(), icon.getIconHeight());
					bowlPanel.add(tLabel);
					bowlPanel.repaint();
				}
				
				//走路
				walk();
			}
		}
		catch(InterruptedException exception) {
			exception.printStackTrace();
			Thread.currentThread().interrupt();
		}
	}
	
	//依照傳入之boolean值來決定image是哪個圖片，並依圖片設定移動方向
	public void setImage(boolean b) {
		ImageIcon imgIcon;
		if(b) {
			imgIcon = new ImageIcon(getClass().getResource("w.png"));
			toRight = true;	//圖片頭朝右，設定朝右走
		}
		else {
			imgIcon = new ImageIcon(getClass().getResource("w2.png"));
			toRight = false;	//圖片頭朝左，設定成朝左走
		}
		image = imgIcon.getImage();
	}
	
	//利用傳入之int來設image大小，並設定該Turtle物件的turtleH、turtleW、icon、blanckH
	public void setSize(int randomH) {
		this.turtleH = randomH;
		this.turtleW = (int)(randomH*tRatio);
		image = image.getScaledInstance(turtleW, turtleH, java.awt.Image.SCALE_SMOOTH);
		icon = new ImageIcon(image);
		blankH = (int)(randomH*30/113);	//高113的話，空白就有30
	}
	
	//判斷是否剛好在海底
	public boolean atBottom() {
		if(yPos+turtleH-blankH==bowlPanel.getBounds().height) {
			return true;
		}
		else
			return false;
	}
	
	//走路
	public void walk() {
		int speed = random.nextInt(10)+5;
		try{
			if(isContinue) {
				if(toRight) {	//如果是往右走
					while(xPos+turtleW<bowlPanel.getBounds().width) {	//沒碰到右邊界
						xPos += speed;
						tLabel.setLocation(xPos, yPos);
						checkCookie();
						Thread.sleep(3000);
					}
					setImage(false);
					setSize(turtleH);
					icon = new ImageIcon(image);
					tLabel.setIcon(icon);
					walk();
				}
				else {		//如果是往左走
					while(xPos>0) {		//沒碰到左邊界
						xPos -= speed;
						tLabel.setLocation(xPos, yPos);
						checkCookie();
						Thread.sleep(3000);
					}
					setImage(true);
					setSize(turtleH);
					icon = new ImageIcon(image);
					tLabel.setIcon(icon);
					walk();
				}
			}
		}
		catch(InterruptedException exception) {
			exception.printStackTrace();
			Thread.currentThread().interrupt();
		}
	}
	
	//在飼料範圍內
	private boolean inCookie(Cookie c) {
		int cSize = c.getCSize();
		//飼料中心點座標
		int cookieX = c.getCPos().x + cSize/2;
		int cookieY = c.getCPos().y + cSize/2;
		
		if((cookieX>xPos)&&(cookieX<xPos+turtleW)&&(cookieY>yPos)&&(cookieY<yPos+turtleH)) {
			return true;
		}
		else
			return false;
	}
	
	//確認有無吃到飼料
	private void checkCookie() {
		for(int i=0;i<cookies.size();i++) {
			Cookie c = cookies.get(i);
			if(inCookie(c)) {
				setImage(toRight);
				setSize(turtleH+5);	//長大
				tLabel.setIcon(icon);
				tLabel.setBounds(xPos, yPos, icon.getIconWidth(), icon.getIconHeight());
				overBottom();
				c.beEaten(cookies.indexOf(c));
			}
		}
	}
	
	//如果超過海底要調整
	public void overBottom() {
		if(yPos+turtleH-blankH>=bowlPanel.getBounds().height) {
			yPos=bowlPanel.getBounds().height-turtleH+blankH;
			tLabel.setLocation(xPos, yPos);
		}
	}
	
	//停止
	public void donotContinue() {
		isContinue = false;
	}
}