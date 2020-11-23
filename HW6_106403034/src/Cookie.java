//姓名:蘇敏宜  學號:106403034 系級:資管三B
import java.awt.Image;
import java.awt.Point;
import java.security.SecureRandom;
import java.util.ArrayList;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;


public class Cookie implements Runnable{
	//constructor傳入之參數
	private JPanel bowlPanel;
	private JLabel cLabel;
	private int xPos;
	private int yPos;
	private ArrayList<JLabel> cList;
	private ArrayList<Cookie> cookies;
	//圖片相關
	private Image image;
	private Icon icon;
	private int cookieSize;	//邊長
	private int speed;	//cookie掉下的速度
	
	private boolean isContinue = true;
	
	private SecureRandom random =  new SecureRandom();
	public Cookie(JPanel bowlPanel, JLabel cLabel, int xPos, int yPos, ArrayList<Cookie> cookies, ArrayList<JLabel> cList){	//constructor
		this.bowlPanel = bowlPanel;
		this.cLabel = cLabel;
		this.xPos = xPos;
		this.yPos = yPos;
		this.cList = cList;
		this.cookies = cookies;
		
		setImage();
		setSize(random.nextInt(20)+20);
		setSpeed(random.nextInt(15)+5);
	}
	public void run() {
		try {
			if(isContinue) {
				//圖片設定、放進bowlPanel
				cLabel.setIcon(icon);
				cLabel.setBounds(xPos, yPos, icon.getIconWidth(), icon.getIconHeight());
				bowlPanel.add(cLabel);
				bowlPanel.repaint();
				
				//掉落
				while(yPos+cookieSize<bowlPanel.getBounds().height&&isContinue){
					yPos += speed;
					cLabel.setLocation(xPos, yPos);
					Thread.sleep(1000);
				}
				yPos = bowlPanel.getBounds().height-cookieSize;
				cLabel.setLocation(xPos, yPos);
			}
		}
		catch(InterruptedException exception) {
			exception.printStackTrace();
			Thread.currentThread().interrupt();
		}
	}
	
	//----method
	private void setImage() {
		ImageIcon imgIcon = new ImageIcon(getClass().getResource("cookie.png"));
		image = imgIcon.getImage();
	}
	
	private void setSize(int randomSize) {
		cookieSize = randomSize;
		image = image.getScaledInstance(cookieSize, cookieSize, java.awt.Image.SCALE_SMOOTH);
		icon = new ImageIcon(image);
	}
	
	//設定降落速度
	private void setSpeed(int speed) {
		this.speed = speed;
	}
	
	//回傳size
	public int getCSize() {
		return cookieSize;
	}
	
	//回傳座標
	public Point getCPos() {
		Point p = new Point(xPos, yPos);
		return p;
	}
	
	//被吃掉的飼料要刪掉
	public void beEaten(int delIndex) {
		//System.out.printf("be eaten\n");
		bowlPanel.remove(cList.get(delIndex));
		bowlPanel.repaint();
		cList.remove(delIndex);
		cookies.get(delIndex).donotContinue();
		cookies.remove(delIndex);
	}
	
	//停止
	public void donotContinue() {
		isContinue = false;
	}
}
