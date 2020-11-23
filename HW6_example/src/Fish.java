//�i����
//105403551
//��ޤGB
import java.awt.*;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.security.*;

public class Fish implements Runnable{
	//--------------------------------		//�ӱ�reference
	private static final SecureRandom random = new SecureRandom();
	private final JPanel bowl;
	private final JLabel flabel;
	//--------------------------------		//���ݩ�
	private Point positionNow;
	private int width;
	private int height;
	public int kind;
	public int speed;

	public Image fishright;
	public Image fishleft;
	public double sizeratio;
	public double slope;
	public boolean faceright;
	public boolean interrupted = false;
	
	public Fish(Point positionNow, JPanel bowl, JLabel flabel)
	{
		//-----------------------------		
		this.positionNow = positionNow;
		this.bowl = bowl;
		this.flabel = flabel;
		//-----------------------------
		kind(random.nextInt(3));//random����
		size(random.nextInt(100)+70);//random�j�p
		dir(random.nextInt(bowl.getWidth()), random.nextInt(bowl.getHeight()));//random��V
		speed = random.nextInt(20)+15;//random�t��
	}
	
	public void run()
	{
		try
		{
			int i = 0;
			while(true)
			{
			
				fixPositionMethod();//�ץ�����ܤƮɦ�m�վ� 
				
				if(interrupted)//����thread
					throw new InterruptedException();
				
				flabel.setBounds(positionNow.x, positionNow.y, width, height);//��ܦbpanel�W
				positionNext();//��U�@���I
				Thread.sleep(speed);//����t��
				
				
				i++;
				if(i > random.nextInt(40)+50)//���w��random�t�׸��V
				{
					speed = random.nextInt(50)+50;
					dir(random.nextInt(bowl.getWidth()), random.nextInt(bowl.getHeight()));
					i = 0;
				}
			}
		}
		catch(InterruptedException e)
		{
			Thread.currentThread().interrupt();
		}
	}
	
	public void kind(int k)//���J�Ϥ�
	{
		try
		{
			switch(k)
			{
			case 0:
				fishright = ImageIO.read(new File("1.png"));
				fishleft = ImageIO.read(new File("2.png"));
				sizeratio = 225/225;
				break;
			case 1:
				fishright = ImageIO.read(new File("3.png"));
				fishleft = ImageIO.read(new File("4.png"));
				sizeratio = 151/139;
				break;
			case 2:
				fishright = ImageIO.read(new File("5.png"));
				fishleft = ImageIO.read(new File("6.png"));
				sizeratio = 160/160;
				break;
			}
			
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
	
	public void dir(int x,int y)//�M�w��V
	{
		slope = (y-positionNow.getY())/(x-positionNow.getX());//��X�ײv
		if(slope>=3 || slope<=-3) {
			slope=3;
		}
		faceright = random.nextBoolean();//�M�w���V��
		if(faceright)
			flabel.setIcon(new ImageIcon(fishright));
		else
			flabel.setIcon(new ImageIcon(fishleft));
	}
	
	public void positionNext()//���U�@�B
	{
		double k = positionNow.getY()-slope*positionNow.getX();//�I�Z(�o��{��)
		int x = positionNow.x;
		int y = positionNow.y;
		
		if(faceright)	//�����k��
			positionNow = new Point(x+1, (int) (slope*(x+1)+k));
		else			//��������
			positionNow = new Point(x-1, (int) (slope*(x-1)+k));
		if(reachborder())//�I�����(�Y�I��A�h�^�W�@�B)
		{
			positionNow.x = x;
			positionNow.y = y;
			dir(random.nextInt(bowl.getWidth()), random.nextInt(bowl.getHeight()));
		
		}
	}
	
	public boolean reachborder()//�P�_�O�_�I�����
	{
		if((positionNow.x + width/2 <= 0) || (positionNow.x + width/2 >= bowl.getBounds().getWidth()))
			return true;	
		if((positionNow.y + height/2 <= 0) || (positionNow.y + height/2 >= bowl.getBounds().getHeight()))
			return true;
		return false;
	}
	
	public void fixPositionMethod()//�ץ�����ܤƮɦ�m�վ� 
	{
		while(reachborder())//�I�����
		{
			flabel.setBounds(positionNow.x, positionNow.y, width, height);
			if((positionNow.x + width/2 <= 0) || (positionNow.x + width/2 >= bowl.getBounds().getWidth()))
				positionNow.x--;
			if((positionNow.y + height/2 <= 0) || (positionNow.y + height/2 >= bowl.getBounds().getHeight()))
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