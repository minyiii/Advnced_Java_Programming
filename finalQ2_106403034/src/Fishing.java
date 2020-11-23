import java.awt.Image;
import java.security.SecureRandom;
import java.util.ArrayList;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.text.Position;

//����
public class Fishing implements Runnable {
	private FishBowlFrame frame;
	private JPanel bowlPanel;
	private JLabel fgLabel;
	private int xPos, yPos;			//�I����U��x,y�y��
	//fishing�ݩ�
	private SecureRandom random = new SecureRandom();
	private Image image;
	private Icon icon;
	private int fishingH, fishingW;		//random�᪺�Ϥ��j�p
	private float fgRatio = 240/199;	//�e:?,��:?*240/199
	private boolean isContinue = true;	//�]��false�ɡA��thread�N�������
	private ArrayList<Fishing> fishings = new ArrayList<Fishing>();	//����List
	private ArrayList<JLabel> tList = new ArrayList<JLabel>();	//�Q�t�Ϥ�List
	private ArrayList<JLabel> fList = new ArrayList<JLabel>();	//���Ϥ�List
	
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
		setSize(random.nextInt(30)+60);	//size�i��O30-80
	}
	
	public void run() {
		try {
			if(isContinue) {
				//�N�Ϥ���itLabel�A����tLabel��ܦbbowlPanel�W
				fgLabel.setIcon(icon);
				fgLabel.setBounds(xPos, yPos, icon.getIconWidth(), icon.getIconHeight());
				bowlPanel.add(fgLabel);
				bowlPanel.repaint();
			
				//����
				do {
					if(random.nextBoolean()==true) {
						yPos += random.nextInt(7)+5;
					}
					else {
						yPos -= random.nextInt(7)+5;
						if(yPos<0) {	//�קK����B�X����
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
	//�̷ӶǤJ��boolean�ȨӨM�wimage�O���ӹϤ��A�è̹Ϥ��]�w���ʤ�V
	public void setImage() {
		ImageIcon imgIcon;
		imgIcon = new ImageIcon(getClass().getResource("fishing.png"));
		image = imgIcon.getImage();
	}
	
	//�Q�ζǤJ��int�ӳ]image�j�p�A�ó]�w��Fishing����fishingH�BfishingW�Bicon
	public void setSize(int randomW) {
		this.fishingW = randomW;
		this.fishingH = (int)(randomW*fgRatio);
		image = image.getScaledInstance(fishingW, fishingH, java.awt.Image.SCALE_SMOOTH);
		icon = new ImageIcon(image);
		//blankH = (int)(randomH*30/113);	//��113���ܡA�ťմN��30
	}
	
	//�p�G�W�L�����d��n�վ�
	public void overBottom() {
		if(yPos+fishingH>=bowlPanel.getBounds().height) {	//�W�L����
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
			if(t.getX()<fgY+fgH && tCentralY<fgX+fgW && tCentralY>fgX) {	//�����\
				deleteObj(1, tList.indexOf(t));
				System.out.println("���Q�t");
			}
			//Thread.sleep(1500);
		}
		
		for(JLabel f : fList) {
			int fCentralY = f.getY() + f.getWidth()/2;
			if(f.getX()<fgY+fgH && fCentralY<fgX+fgW && fCentralY>fgX) {	//�����\
				deleteObj(2, tList.indexOf(f));
				System.out.println("����");
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
	
	public void deleteObj(int objType, int delIndex) {	//1:�Q�t, 2:��
		switch(objType) {
		case 1:
			//frame.deleteT(delIndex);
			break;
		case 2:
			//frame.deleteF(delIndex);
			break;
		}
	}
	
	//����
	public void donotContinue() {
		isContinue = false;
	}
}
