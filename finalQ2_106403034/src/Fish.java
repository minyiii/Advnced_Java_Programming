//姓名:蘇敏宜  學號:106403034 系級:資管三B
import java.awt.Image;
import java.security.SecureRandom;
import java.util.ArrayList;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Fish implements Runnable{
	private FishBowlFrame frame;
	private SecureRandom random = new SecureRandom();
	//與constructor傳入之參數對應
	private JPanel bowlPanel;
	private JLabel fLabel;
	private int xPos;
	private int yPos; 
	
	private int type;		//魚的種類(0,1,2)
	private Image image;
	private Icon icon;
	private boolean toRight;	//(確認方向)是否往右(0,1)
	private float[] fRatio = {1, 151/139, 1};	//三種圖片各自的比例(高:?,寬:?*比)
	private int fishW, fishH;
	private int speed;
	private boolean isContinue = true;
	private ArrayList<Cookie> cookies = new ArrayList<Cookie>();	//飼料List
	private ArrayList<Fishing> fishings = new ArrayList<Fishing>();	//釣魚List
	
	public Fish(FishBowlFrame frame,JPanel bowlPanel, JLabel fLabel, int xPos, int yPos
			, ArrayList<Cookie> cookies, ArrayList<Fishing> fishings) {
		this.frame = frame;
		this.bowlPanel = bowlPanel;
		this.fLabel = fLabel;
		this.xPos = xPos;
		this.yPos = yPos;
		this.cookies = cookies;
		this.fishings = fishings;
		type=random.nextInt(60)%3;		//random魚的種類(0~2)
		toRight=random.nextBoolean();	//random魚的方向(true,false)
		
		setImage();
		setSize(random.nextInt(50)+40);
		randomSpeed();
	}
	
	public void run() {
		try {
			//將圖片放進tLabel，並讓tLabel顯示在bowlPanel上
			fLabel.setIcon(icon);
			fLabel.setBounds(xPos, yPos, icon.getIconWidth(), icon.getIconHeight());
			bowlPanel.add(fLabel);
			bowlPanel.repaint();
			overBottom();
			
			Thread.sleep(1500);
			swim();
		}
		catch(InterruptedException exception) {
			exception.printStackTrace();
			Thread.currentThread().interrupt();
		}
	}
	
	//設定圖片(從檔案轉成ImageIcon)、設回Image型態、依照圖片定方向
	public void setImage() {
		int num = type*2;
		if(!toRight){	//若向左
			num++;
		}
		
		ImageIcon imgIcon;
		switch(num) {
		case 0:
			imgIcon = new ImageIcon(getClass().getResource("1.png"));
			break;
		case 1:
			imgIcon = new ImageIcon(getClass().getResource("2.png"));
			break;
		case 2:
			imgIcon = new ImageIcon(getClass().getResource("3.png"));
			break;
		case 3:
			imgIcon = new ImageIcon(getClass().getResource("4.png"));
			break;
		case 4:
			imgIcon = new ImageIcon(getClass().getResource("5.png"));
			break;
		case 5:
			imgIcon = new ImageIcon(getClass().getResource("6.png"));
			break;
		default:
			imgIcon = new ImageIcon();
			break;
		}
		image = imgIcon.getImage();
	}
	
	//利用傳入之int來設image大小，並設定該Fish物件的fishH、fishW、icon
	public void setSize(int randomH) {
		this.fishH = randomH;
		this.fishW = (int)(randomH*fRatio[type]);
		image = image.getScaledInstance(fishW, fishH, java.awt.Image.SCALE_SMOOTH);
		icon = new ImageIcon(image);
	}
	
	//游泳
	public void swim() {
		try {
			if(isContinue) {
				if(random.nextInt(10)==1) {
					System.out.println("烏龜變小");
					
					setImage();
					setSize(fishH-10);
					fLabel.setIcon(icon);
					fLabel.setBounds(xPos, yPos, icon.getIconWidth(), icon.getIconHeight());
				}
				if(toRight) {	//若為向右游
					while((xPos+fishW < bowlPanel.getBounds().width)&&isContinue){	//當沒超過右邊界
						if(random.nextInt(15)==1) {	//random讓他不定時變換方向(會跳出while迴圈)
							break;
						}
						if(random.nextInt(10)==1) {	//random讓他不定時去調整速度
							randomSpeed();
						}
						xPos += speed;
						fLabel.setLocation(xPos, yPos);
						checkCookie();
						//fishing();
						Thread.sleep(1500);
					}
					//超過右邊界後
					changeDir();
					swim();
				}
				else {	//若為向左游
					while((xPos>0)&&isContinue){	//當沒超過左邊界
						if(random.nextInt(15)==1) {	//random讓他不定時變換方向(會跳出while迴圈)
							break;
						}
						if(random.nextInt(10)==1) {	//random讓他不定時去調整速度
							randomSpeed();
						}
						xPos -= speed;
						fLabel.setLocation(xPos, yPos);
						checkCookie();
						//fishing();
						Thread.sleep(1500);
					}
					//超過左邊界後
					changeDir();
					swim();
				}
			}
			
		}
		catch(InterruptedException exception) {
			exception.printStackTrace();
			Thread.currentThread().interrupt();
		}
	}
	
	//random速度
	public void randomSpeed() {
		speed = random.nextInt(15)+10;
	}
	
	//轉向並重設JLabel上的圖片
	public void changeDir() {
		toRight = !toRight;
		setImage();
		setSize(fishH);
		icon = new ImageIcon(image);
		fLabel.setIcon(icon);
	}
	
	//在飼料範圍內
	private boolean inCookie(Cookie c) {
		int cSize = c.getCSize();
		//飼料中心點座標
		int cookieX = c.getCPos().x + cSize/2;
		int cookieY = c.getCPos().y + cSize/2;
		
		if((cookieX>xPos)&&(cookieX<xPos+fishW)&&(cookieY>yPos)&&(cookieY<yPos+fishH)) {
			return true;
		}
		else
			return false;
	}
	
	//確認有無吃到飼料
	private void checkCookie() {
		for(int i=0;i<cookies.size();i++) {
			Cookie c = cookies.get(i);
			if(inCookie(c)&&canEat(c)) {
				setImage();
				setSize(fishH+8);	//長大
				fLabel.setIcon(icon);
				fLabel.setBounds(xPos, yPos, icon.getIconWidth(), icon.getIconHeight());
				overBottom();
				c.beEaten(cookies.indexOf(c));
			}
		}
	}
	
	//驅趕(不會太大隻就可以吃)
	public boolean canEat(Cookie c) {
		if(fishW<100) {
			System.out.printf("fishW: %d can eat\n", fishW);
			return true;
		}
		else {	//變換方向
			System.out.printf("fishW: %d can't eat\n", fishW);
			changeDir();
			
			if(toRight) {	//讓他遠離餅乾，不然會一直動不了，只在轉向
				xPos += c.getCSize();
			}
			else {
				xPos -= c.getCSize();
			}
			fLabel.setLocation(xPos, yPos);
			
			return false;
		}
	}
	
	//在被調走的範圍內
	private boolean inFishing(Fishing fg) {
		//飼料中心點座標
		int fgX = fg.getX()+fg.getW()/2;
		int fgY = fg.getY()+fg.getH()/2;
		
		if((fgX>xPos)&&(fgX<xPos+fishW)&&(fgY>yPos)&&(fgY<yPos+fishH)) {
			return true;
		}
		else
			return false;
	}
		
	//被調走
	public void fishing(Fishing fg) {
		//for(int i=0;i<fishings.size();i++) {
			//fg = fishings.get(i);
		if(inFishing(fg)) {
			frame.deleteF(this);
			frame.deleteFG(fg);
		}
			//}
	}
	
	//如果超過海底要調整
	public void overBottom() {
		if(yPos+fishH>=bowlPanel.getBounds().height) {
			yPos=bowlPanel.getBounds().height-fishH;
			fLabel.setLocation(xPos, yPos);
		}
	}
	
	//停止
	public void donotContinue() {
		isContinue = false;
	}
}