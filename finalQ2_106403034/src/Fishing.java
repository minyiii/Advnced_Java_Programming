import java.awt.Image;
import java.security.SecureRandom;
import java.util.ArrayList;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.text.Position;

//釣竿
public class Fishing implements Runnable {
	private FishBowlFrame frame;
	private JPanel bowlPanel;
	private JLabel fgLabel;
	private int xPos, yPos;			//點擊當下的x,y座標
	//fishing屬性
	private SecureRandom random = new SecureRandom();
	private Image image;
	private Icon icon;
	private int fishingH, fishingW;		//random後的圖片大小
	private float fgRatio = 240/199;	//寬:?,高:?*240/199
	private boolean isContinue = true;	//設為false時，此thread就停止執行
	private ArrayList<Fishing> fishings = new ArrayList<Fishing>();	//釣竿List
	private ArrayList<JLabel> tList = new ArrayList<JLabel>();	//烏龜圖片List
	private ArrayList<JLabel> fList = new ArrayList<JLabel>();	//魚圖片List
	
	//constructor
	public Fishing(FishBowlFrame frame, JPanel bowlPanel, JLabel fgLabel, int xPos, int yPos
			, ArrayList<Fishing> fishings, ArrayList<JLabel> tList, ArrayList<JLabel> fList) {	//constructor
		this.frame = frame;
		this.bowlPanel = bowlPanel;
		this.fgLabel = fgLabel;
		this.xPos = xPos;
		this.yPos = yPos;
		this.tList = tList;
		this.fList = fList;
		
		setImage();
		setSize(random.nextInt(30)+60);	//size可能是30-80
	}
	
	public void run() {
		try {
			if(isContinue) {
				//將圖片放進tLabel，並讓tLabel顯示在bowlPanel上
				fgLabel.setIcon(icon);
				fgLabel.setBounds(xPos, yPos, icon.getIconWidth(), icon.getIconHeight());
				bowlPanel.add(fgLabel);
				bowlPanel.repaint();
			
				//掉落
				do {
					if(random.nextBoolean()==true) {
						yPos += random.nextInt(7)+5;
					}
					else {
						yPos -= random.nextInt(7)+5;
						if(yPos<0) {	//避免它比浮出魚缸
							yPos=0;
						}
					}
					fgLabel.setLocation(xPos, yPos);
					Thread.sleep(random.nextInt(1500));
					
				}while((yPos+icon.getIconHeight()<bowlPanel.getBounds().height)&&isContinue);
				
			}
		}
		catch(InterruptedException exception) {
			exception.printStackTrace();
			Thread.currentThread().interrupt();
		}
	}
	
	//---------method-------------//
	//依照傳入之boolean值來決定image是哪個圖片，並依圖片設定移動方向
	public void setImage() {
		ImageIcon imgIcon;
		imgIcon = new ImageIcon(getClass().getResource("fishing.png"));
		image = imgIcon.getImage();
	}
	
	//利用傳入之int來設image大小，並設定該Fishing物件的fishingH、fishingW、icon
	public void setSize(int randomW) {
		this.fishingW = randomW;
		this.fishingH = (int)(randomW*fgRatio);
		image = image.getScaledInstance(fishingW, fishingH, java.awt.Image.SCALE_SMOOTH);
		icon = new ImageIcon(image);
		//blankH = (int)(randomH*30/113);	//高113的話，空白就有30
	}
	
	//如果超過魚缸範圍要調整
	public void overBottom() {
		if(yPos+fishingH>=bowlPanel.getBounds().height) {	//超過底部
			yPos=bowlPanel.getBounds().height-fishingH;
			fgLabel.setLocation(xPos, yPos);
		}
	}
	
	//
	public void startFg() {
		int fgX = fgLabel.getX();
		int fgY = fgLabel.getY();
		int fgH = fgLabel.getHeight();
		int fgW = fgLabel.getWidth();
		
		for(JLabel t : tList) {
			int tCentralY = t.getY() + t.getWidth()/2;
			if(t.getX()<fgY+fgH && tCentralY<fgX+fgW && tCentralY>fgX) {	//釣成功
				deleteObj(1, tList.indexOf(t));
				System.out.println("釣烏龜");
			}
			//Thread.sleep(1500);
		}
		
		for(JLabel f : fList) {
			int fCentralY = f.getY() + f.getWidth()/2;
			if(f.getX()<fgY+fgH && fCentralY<fgX+fgW && fCentralY>fgX) {	//釣成功
				deleteObj(2, tList.indexOf(f));
				System.out.println("釣魚");
			}
		}
	}
	
	//
	public int getX() {
		return xPos;
	}
	
	public int getY() {
		return yPos;
	}
	
	public int getW() {
		return fishingW;
	}
	
	public int getH() {
		return fishingH;
	}
	
	//
	public void checkFishing() {
		
	}
	
	public void deleteObj(int objType, int delIndex) {	//1:烏龜, 2:魚
		switch(objType) {
		case 1:
			//frame.deleteT(delIndex);
			break;
		case 2:
			//frame.deleteF(delIndex);
			break;
		}
	}
	
	//停止
	public void donotContinue() {
		isContinue = false;
	}
}
