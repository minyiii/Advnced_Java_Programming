//�m�W:Ĭ�өy  �Ǹ�:106403034 �t��:��ޤTB
import java.awt.Image;
import java.awt.Point;
import java.security.SecureRandom;
import java.util.ArrayList;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;


public class Cookie implements Runnable{
	//constructor�ǤJ���Ѽ�
	private JPanel bowlPanel;
	private JLabel cLabel;
	private int xPos;
	private int yPos;
	private ArrayList<JLabel> cList;
	private ArrayList<Cookie> cookies;
	//�Ϥ�����
	private Image image;
	private Icon icon;
	private int cookieSize;	//���
	private int speed;	//cookie���U���t��
	
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
				//�Ϥ��]�w�B��ibowlPanel
				cLabel.setIcon(icon);
				cLabel.setBounds(xPos, yPos, icon.getIconWidth(), icon.getIconHeight());
				bowlPanel.add(cLabel);
				bowlPanel.repaint();
				
				//����
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
	
	//�]�w�����t��
	private void setSpeed(int speed) {
		this.speed = speed;
	}
	
	//�^��size
	public int getCSize() {
		return cookieSize;
	}
	
	//�^�Ǯy��
	public Point getCPos() {
		Point p = new Point(xPos, yPos);
		return p;
	}
	
	//�Q�Y�����}�ƭn�R��
	public void beEaten(int delIndex) {
		//System.out.printf("be eaten\n");
		bowlPanel.remove(cList.get(delIndex));
		bowlPanel.repaint();
		cList.remove(delIndex);
		cookies.get(delIndex).donotContinue();
		cookies.remove(delIndex);
	}
	
	//����
	public void donotContinue() {
		isContinue = false;
	}
}
