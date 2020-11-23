//�m�W:Ĭ�өy  �Ǹ�:106403034 �t��:��ޤTB

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
	//�Q�t�Ϥ����=>�e145:��113
	//�غc�l�Ѽ�
	private JPanel bowlPanel;
	private JLabel tLabel;
	private int xPos, yPos;			//�I����U��x,y�y��
	//Turtle�ݩ�
	private SecureRandom random = new SecureRandom();
	private Image image;
	private Icon icon;
	private int turtleH, turtleW;	//random�᪺�Ϥ��j�p
	private float tRatio = 145/113;	//��:?,�e:?*145/113
	private int blankH;				//�Q�t�Ϥ��U���ť�
	private boolean toRight;		//�Q�t�����ʤ�V(0:�V���B1:�V�k)
	private boolean isContinue = true;	//�]��false�ɡA��thread�N�������
	private ArrayList<Cookie> cookies = new ArrayList<Cookie>();	//�}��List
	
	public Turtle(JPanel bowlPanel, JLabel tLabel, int xPos, int yPos, ArrayList<Cookie> cookies) {	//constructor
		this.bowlPanel = bowlPanel;
		this.tLabel = tLabel;
		this.xPos = xPos;
		this.yPos = yPos;
		this.cookies = cookies;
		
		setImage(random.nextBoolean());
		setSize(random.nextInt(50)+30);	//size�i��O30-80
	}
	
	public void run() {
		try {
			if(isContinue) {
				//�N�Ϥ���itLabel�A����tLabel��ܦbbowlPanel�W
				tLabel.setIcon(icon);
				tLabel.setBounds(xPos, yPos, icon.getIconWidth(), icon.getIconHeight());
				bowlPanel.add(tLabel);
				bowlPanel.repaint();
			
				//����
				do {
					yPos += 10;
					tLabel.setLocation(xPos, yPos);
					Thread.sleep(random.nextInt(2000));
				}while((yPos+icon.getIconHeight()<bowlPanel.getBounds().height)&&isContinue);
				
				//�B�z�Ϥ��U��ťժ������A���Q�t�i�H���b����
				if(!atBottom()&&isContinue) {
					yPos = bowlPanel.getBounds().height-turtleH+blankH;
					tLabel.setLocation(xPos, yPos);
				}
				
				if(!random.nextBoolean()&&isContinue) {	//�۳����H���M�w�n���n�ܤ�V
					toRight=!toRight;
					setImage(toRight);
					setSize(turtleH);
					tLabel.setIcon(icon);
					tLabel.setBounds(xPos, yPos, icon.getIconWidth(), icon.getIconHeight());
					bowlPanel.add(tLabel);
					bowlPanel.repaint();
				}
				
				//����
				walk();
			}
		}
		catch(InterruptedException exception) {
			exception.printStackTrace();
			Thread.currentThread().interrupt();
		}
	}
	
	//�̷ӶǤJ��boolean�ȨӨM�wimage�O���ӹϤ��A�è̹Ϥ��]�w���ʤ�V
	public void setImage(boolean b) {
		ImageIcon imgIcon;
		if(b) {
			imgIcon = new ImageIcon(getClass().getResource("w.png"));
			toRight = true;	//�Ϥ��Y�¥k�A�]�w�¥k��
		}
		else {
			imgIcon = new ImageIcon(getClass().getResource("w2.png"));
			toRight = false;	//�Ϥ��Y�¥��A�]�w���¥���
		}
		image = imgIcon.getImage();
	}
	
	//�Q�ζǤJ��int�ӳ]image�j�p�A�ó]�w��Turtle����turtleH�BturtleW�Bicon�BblanckH
	public void setSize(int randomH) {
		this.turtleH = randomH;
		this.turtleW = (int)(randomH*tRatio);
		image = image.getScaledInstance(turtleW, turtleH, java.awt.Image.SCALE_SMOOTH);
		icon = new ImageIcon(image);
		blankH = (int)(randomH*30/113);	//��113���ܡA�ťմN��30
	}
	
	//�P�_�O�_��n�b����
	public boolean atBottom() {
		if(yPos+turtleH-blankH==bowlPanel.getBounds().height) {
			return true;
		}
		else
			return false;
	}
	
	//����
	public void walk() {
		int speed = random.nextInt(10)+5;
		try{
			if(isContinue) {
				if(toRight) {	//�p�G�O���k��
					while(xPos+turtleW<bowlPanel.getBounds().width) {	//�S�I��k���
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
				else {		//�p�G�O������
					while(xPos>0) {		//�S�I�쥪���
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
	
	//�b�}�ƽd��
	private boolean inCookie(Cookie c) {
		int cSize = c.getCSize();
		//�}�Ƥ����I�y��
		int cookieX = c.getCPos().x + cSize/2;
		int cookieY = c.getCPos().y + cSize/2;
		
		if((cookieX>xPos)&&(cookieX<xPos+turtleW)&&(cookieY>yPos)&&(cookieY<yPos+turtleH)) {
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
				setImage(toRight);
				setSize(turtleH+5);	//���j
				tLabel.setIcon(icon);
				tLabel.setBounds(xPos, yPos, icon.getIconWidth(), icon.getIconHeight());
				overBottom();
				c.beEaten(cookies.indexOf(c));
			}
		}
	}
	
	//�p�G�W�L�����n�վ�
	public void overBottom() {
		if(yPos+turtleH-blankH>=bowlPanel.getBounds().height) {
			yPos=bowlPanel.getBounds().height-turtleH+blankH;
			tLabel.setLocation(xPos, yPos);
		}
	}
	
	//����
	public void donotContinue() {
		isContinue = false;
	}
}