//張哲嘉
//105403551
//資管二B
import java.awt.*;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.security.*;

public class Fish implements Runnable{
	//--------------------------------		//承接reference
	private static final SecureRandom random = new SecureRandom();
	private final JPanel bowl;
	private final JLabel flabel;
	//--------------------------------		//魚屬性
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
		kind(random.nextInt(3));//random種類
		size(random.nextInt(100)+70);//random大小
		dir(random.nextInt(bowl.getWidth()), random.nextInt(bowl.getHeight()));//random方向
		speed = random.nextInt(20)+15;//random速度
	}
	
	public void run()
	{
		try
		{
			int i = 0;
			while(true)
			{
			
				fixPositionMethod();//修正邊界變化時位置調整 
				
				if(interrupted)//結束thread
					throw new InterruptedException();
				
				flabel.setBounds(positionNow.x, positionNow.y, width, height);//顯示在panel上
				positionNext();//到下一個點
				Thread.sleep(speed);//控制速度
				
				
				i++;
				if(i > random.nextInt(40)+50)//不定期random速度跟方向
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
	
	public void kind(int k)//載入圖片
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
	
	public void size(int w)//決定大小
	{
		width = w;
		height = (int)(width * sizeratio);
		fishright = fishright.getScaledInstance(width, height, Image.SCALE_DEFAULT);
		fishleft = fishleft.getScaledInstance(width, height, Image.SCALE_DEFAULT);
		positionNow = new Point(positionNow.x-width/2, positionNow.y-height/2);
	}
	
	public void dir(int x,int y)//決定方向
	{
		slope = (y-positionNow.getY())/(x-positionNow.getX());//算出斜率
		if(slope>=3 || slope<=-3) {
			slope=3;
		}
		faceright = random.nextBoolean();//決定面向哪
		if(faceright)
			flabel.setIcon(new ImageIcon(fishright));
		else
			flabel.setIcon(new ImageIcon(fishleft));
	}
	
	public void positionNext()//往下一步
	{
		double k = positionNow.getY()-slope*positionNow.getX();//截距(得方程式)
		int x = positionNow.x;
		int y = positionNow.y;
		
		if(faceright)	//魚往右游
			positionNow = new Point(x+1, (int) (slope*(x+1)+k));
		else			//魚往左游
			positionNow = new Point(x-1, (int) (slope*(x-1)+k));
		if(reachborder())//碰到邊界(若碰到，退回上一步)
		{
			positionNow.x = x;
			positionNow.y = y;
			dir(random.nextInt(bowl.getWidth()), random.nextInt(bowl.getHeight()));
		
		}
	}
	
	public boolean reachborder()//判斷是否碰到邊界
	{
		if((positionNow.x + width/2 <= 0) || (positionNow.x + width/2 >= bowl.getBounds().getWidth()))
			return true;	
		if((positionNow.y + height/2 <= 0) || (positionNow.y + height/2 >= bowl.getBounds().getHeight()))
			return true;
		return false;
	}
	
	public void fixPositionMethod()//修正邊界變化時位置調整 
	{
		while(reachborder())//碰到邊界
		{
			flabel.setBounds(positionNow.x, positionNow.y, width, height);
			if((positionNow.x + width/2 <= 0) || (positionNow.x + width/2 >= bowl.getBounds().getWidth()))
				positionNow.x--;
			if((positionNow.y + height/2 <= 0) || (positionNow.y + height/2 >= bowl.getBounds().getHeight()))
				positionNow.y--;
			try
			{
				Thread.sleep(0);//移動速度
			}
			catch(InterruptedException e)
			{
				Thread.currentThread().interrupt();
			}
		}
	}
	
}