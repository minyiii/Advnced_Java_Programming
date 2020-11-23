//�m�W:Ĭ�өy  �Ǹ�:106403034 �t��:��ޤTB
import java.awt.Image;
import java.security.SecureRandom;
import java.util.ArrayList;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Fish implements Runnable{
	private SecureRandom random = new SecureRandom();
	//�Pconstructor�ǤJ���Ѽƹ���
	private JPanel bowlPanel;
	private JLabel fLabel;
	private int xPos;
	private int yPos; 
	
	private int type;		//��������(0,1,2)
	private Image image;
	private Icon icon;
	private boolean toRight;	//(�T�{��V)�O�_���k(0,1)
	private float[] fRatio = {1, 151/139, 1};	//�T�عϤ��U�۪����(��:?,�e:?*��)
	private int fishW, fishH;
	private int speed;
	private boolean isContinue = true;
	private ArrayList<Cookie> cookies = new ArrayList<Cookie>();	//�}��List
	
	public Fish(JPanel bowlPanel, JLabel fLabel, int xPos, int yPos, ArrayList<Cookie> cookies) {
		this.bowlPanel = bowlPanel;
		this.fLabel = fLabel;
		this.xPos = xPos;
		this.yPos = yPos;
		this.cookies = cookies;
		type=random.nextInt(60)%3;		//random��������(0~2)
		toRight=random.nextBoolean();	//random������V(true,false)
		
		setImage();
		setSize(random.nextInt(50)+30);
		randomSpeed();
	}
	
	public void run() {
		try {
			//�N�Ϥ���itLabel�A����tLabel��ܦbbowlPanel�W
			fLabel.setIcon(icon);
			fLabel.setBounds(xPos, yPos, icon.getIconWidth(), icon.getIconHeight());
			bowlPanel.add(fLabel);
			bowlPanel.repaint();
			overBottom();
			
			Thread.sleep(2000);
			swim();
		}
		catch(InterruptedException exception) {
			exception.printStackTrace();
			Thread.currentThread().interrupt();
		}
	}
	
	//�]�w�Ϥ�(�q�ɮ��নImageIcon)�B�]�^Image���A�B�̷ӹϤ��w��V
	public void setImage() {
		int num = type*2;
		if(!toRight){	//�Y�V��
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
	
	//�Q�ζǤJ��int�ӳ]image�j�p�A�ó]�w��Fish����fishH�BfishW�Bicon
	public void setSize(int randomH) {
		this.fishH = randomH;
		this.fishW = (int)(randomH*fRatio[type]);
		image = image.getScaledInstance(fishW, fishH, java.awt.Image.SCALE_SMOOTH);
		icon = new ImageIcon(image);
	}
	
	//��a
	public void swim() {
		try {
			if(isContinue) {
				if(toRight) {	//�Y���V�k��
					while((xPos+fishW < bowlPanel.getBounds().width)&&isContinue){	//��S�W�L�k���
						if(random.nextInt(15)==1) {	//random���L���w���ܴ���V(�|���Xwhile�j��)
							break;
						}
						if(random.nextInt(10)==1) {	//random���L���w�ɥh�վ�t��
							randomSpeed();
						}
						xPos += speed;
						fLabel.setLocation(xPos, yPos);
						checkCookie();
						Thread.sleep(1500);
					}
					//�W�L�k��ɫ�
					changeDir();
					swim();
				}
				else {	//�Y���V����
					while((xPos>0)&&isContinue){	//��S�W�L�����
						if(random.nextInt(15)==1) {	//random���L���w���ܴ���V(�|���Xwhile�j��)
							break;
						}
						if(random.nextInt(10)==1) {	//random���L���w�ɥh�վ�t��
							randomSpeed();
						}
						xPos -= speed;
						fLabel.setLocation(xPos, yPos);
						checkCookie();
						Thread.sleep(1500);
					}
					//�W�L����ɫ�
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
	
	//random�t��
	public void randomSpeed() {
		speed = random.nextInt(15)+10;
	}
	
	//��V�í��]JLabel�W���Ϥ�
	public void changeDir() {
		toRight = !toRight;
		setImage();
		setSize(fishH);
		icon = new ImageIcon(image);
		fLabel.setIcon(icon);
	}
	
	//�b�}�ƽd��
	private boolean inCookie(Cookie c) {
		int cSize = c.getCSize();
		//�}�Ƥ����I�y��
		int cookieX = c.getCPos().x + cSize/2;
		int cookieY = c.getCPos().y + cSize/2;
		
		if((cookieX>xPos)&&(cookieX<xPos+fishW)&&(cookieY>yPos)&&(cookieY<yPos+fishH)) {
			return true;
		}
		else
			return false;
	}
	
	//�T�{���L�Y��}��
	private void checkCookie() {
		for(int i=0;i<cookies.size();i++) {
			Cookie c = cookies.get(i);
			if(inCookie(c)) {
				setImage();
				setSize(fishH+5);	//���j
				fLabel.setIcon(icon);
				fLabel.setBounds(xPos, yPos, icon.getIconWidth(), icon.getIconHeight());
				overBottom();
				c.beEaten(cookies.indexOf(c));
			}
		}
	}
	
	//�p�G�W�L�����n�վ�
	public void overBottom() {
		if(yPos+fishH>=bowlPanel.getBounds().height) {
			yPos=bowlPanel.getBounds().height-fishH;
			fLabel.setLocation(xPos, yPos);
		}
	}
	
	//����
	public void donotContinue() {
		isContinue = false;
	}
}