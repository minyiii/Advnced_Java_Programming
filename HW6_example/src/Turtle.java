//�i����
//105403551
//��ޤGB
import java.awt.*;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.security.*;

public class Turtle implements Runnable{
	//-----------------------------------		//�ӱ�reference
	private static final SecureRandom random = new SecureRandom();
	private final JPanel bowl;
	private final JLabel tlabel;
	//-----------------------------------		//�t�ݩ�
	private Point positionNow;
	private int width;
	private int height;
	public int speed;

	public Image fishright;
	public Image fishleft;
	public double sizeratio;
	public boolean faceright;
	public boolean interrupted = false;
	public int error;//�ץ��C���Q�t���U���ťճB
	
	public Turtle(Point positionNow, JPanel bowl, JLabel tlabel)
	{
		this.positionNow = positionNow;
		this.bowl = bowl;
		this.tlabel = tlabel;
		
		kind();//���J�Ϥ�
		size(random.nextInt(100)+50);//random�j�p
		dir(random.nextBoolean());//random��V
		speed = random.nextInt(20)+15;//random�t��
		error = (int) (height*0.27);//�o�Q�t�ť�
	}
	
	public void run()
	{
		try
		{
			int i = 0;
			while(true)
			{
				fixPositionMethod();//�ץ�����ܤƮɦ�m�վ�
				if(interrupted)
					throw new InterruptedException();

				tlabel.setBounds(positionNow.x, positionNow.y, width, height);//��ܦbpanel�W
				positionNext();//��U�@���I
				Thread.sleep(speed);//����t��
				
				
				i++;
				if(i > random.nextInt(40)+40)//���w��random�t�׸��V
				{
					speed = random.nextInt(50)+50;
					dir(random.nextBoolean());
					i = 0;
				}
			}
		}
		catch(InterruptedException e)
		{
			Thread.currentThread().interrupt();
		}
	}
	
	public void drop()//�U�Y
	{
		tlabel.setBounds(positionNow.x, positionNow.y, width, height);//��ܦbpanel�W
		while(!reachground())//�S��Ĳ�a
		{
			tlabel.setBounds(positionNow.x, positionNow.y, width, height);
			positionNow.y++;
			try
			{
				Thread.sleep(3);//�U�Y�t��
			}
			catch(InterruptedException e)
			{
				Thread.currentThread().interrupt();
			}
		}
	}
	
	public void elevate()//��ɦܦa��
	{
		//tlabel.setBounds(positionNow.x, positionNow.y, width, height);//��ܦbpanel�W
		while(reachborder())//�b�a���U
		{
			tlabel.setBounds(positionNow.x, positionNow.y, width, height);
			positionNow.y--;
			try
			{
				Thread.sleep(0);//��ɳt��
			}
			catch(InterruptedException e)
			{
				Thread.currentThread().interrupt();
			}
		}
	}
	
	public void kind()//���J�Ϥ�
	{
		try
		{
			
			fishright = ImageIO.read(new File("w.png"));
			fishleft = ImageIO.read(new File("w2.png"));
			sizeratio = 145/113;
		}
		catch(Exception e)
		{
			System.out.println("No File");
		}
	}
	
	public void size(int w)//�M�w�j�p
	{
		width = w;
		height = (int)(width * sizeratio);
		fishright = fishright.getScaledInstance(width, height, Image.SCALE_DEFAULT);
		fishleft = fishleft.getScaledInstance(width, height, Image.SCALE_DEFAULT);
		positionNow = new Point(positionNow.x-width/2, positionNow.y-height/2);
	}
	
	public void dir(boolean face)//�M�w��V
	{
		faceright = face;
		if(faceright)
			tlabel.setIcon(new ImageIcon(fishright));
		else
			tlabel.setIcon(new ImageIcon(fishleft));
	}
	
	public void positionNext()
	{
		int x = positionNow.x;
		int y = positionNow.y;
		
		if(faceright)		//�t���k��
			positionNow.x++;
		else				//�t������
			positionNow.x--;
		if(reachborder())
		{
			positionNow.x = x;
			positionNow.y = y;
			faceright = !faceright;//��V
			if(faceright)
				tlabel.setIcon(new ImageIcon(fishright));
			else
				tlabel.setIcon(new ImageIcon(fishleft));
		}
	}
	
	public boolean reachborder()//�I��ζW�L�W�U���k���
	{
		if((positionNow.x <= 0) || (positionNow.x + width >= bowl.getBounds().getWidth()))
			return true;
		if(positionNow.y + height >= bowl.getBounds().getHeight()+error)
			return true;
		
		return false;
	}
	
	public boolean reachground()//Ĳ�a
	{
		if(positionNow.y + height >= bowl.getBounds().getHeight()+error)
			return true;
		return false;
	}
	
	public void fixPositionMethod()//�ץ�����ܤƮɦ�m�վ� 
	{
		drop();
		while(reachborder())//�I��ζW�L�W�U���k���
		{
			tlabel.setBounds(positionNow.x, positionNow.y, width, height);
			if((positionNow.x <= 0) || (positionNow.x + width >= bowl.getBounds().getWidth()))
				positionNow.x--;
			if(positionNow.y + height >= bowl.getBounds().getHeight()+error)
				positionNow.y--;
			try
			{
				Thread.sleep(0);//���ʳt��
			}
			catch(InterruptedException e)
			{
				Thread.currentThread().interrupt();
			}
		}
	}
	
}
