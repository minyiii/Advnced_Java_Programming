//張哲嘉
//105403551
//資管二B
import java.awt.*;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.security.*;

public class Turtle implements Runnable{
	//-----------------------------------		//承接reference
	private static final SecureRandom random = new SecureRandom();
	private final JPanel bowl;
	private final JLabel tlabel;
	//-----------------------------------		//龜屬性
	private Point positionNow;
	private int width;
	private int height;
	public int speed;

	public Image fishright;
	public Image fishleft;
	public double sizeratio;
	public boolean faceright;
	public boolean interrupted = false;
	public int error;//修正每隻烏龜底下的空白處
	
	public Turtle(Point positionNow, JPanel bowl, JLabel tlabel)
	{
		this.positionNow = positionNow;
		this.bowl = bowl;
		this.tlabel = tlabel;
		
		kind();//載入圖片
		size(random.nextInt(100)+50);//random大小
		dir(random.nextBoolean());//random方向
		speed = random.nextInt(20)+15;//random速度
		error = (int) (height*0.27);//得烏龜空白
	}
	
	public void run()
	{
		try
		{
			int i = 0;
			while(true)
			{
				fixPositionMethod();//修正邊界變化時位置調整
				if(interrupted)
					throw new InterruptedException();

				tlabel.setBounds(positionNow.x, positionNow.y, width, height);//顯示在panel上
				positionNext();//到下一個點
				Thread.sleep(speed);//控制速度
				
				
				i++;
				if(i > random.nextInt(40)+40)//不定期random速度跟方向
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
	
	public void drop()//下墜
	{
		tlabel.setBounds(positionNow.x, positionNow.y, width, height);//顯示在panel上
		while(!reachground())//沒有觸地
		{
			tlabel.setBounds(positionNow.x, positionNow.y, width, height);
			positionNow.y++;
			try
			{
				Thread.sleep(3);//下墜速度
			}
			catch(InterruptedException e)
			{
				Thread.currentThread().interrupt();
			}
		}
	}
	
	public void elevate()//抬升至地面
	{
		//tlabel.setBounds(positionNow.x, positionNow.y, width, height);//顯示在panel上
		while(reachborder())//在地底下
		{
			tlabel.setBounds(positionNow.x, positionNow.y, width, height);
			positionNow.y--;
			try
			{
				Thread.sleep(0);//抬升速度
			}
			catch(InterruptedException e)
			{
				Thread.currentThread().interrupt();
			}
		}
	}
	
	public void kind()//載入圖片
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
	
	public void size(int w)//決定大小
	{
		width = w;
		height = (int)(width * sizeratio);
		fishright = fishright.getScaledInstance(width, height, Image.SCALE_DEFAULT);
		fishleft = fishleft.getScaledInstance(width, height, Image.SCALE_DEFAULT);
		positionNow = new Point(positionNow.x-width/2, positionNow.y-height/2);
	}
	
	public void dir(boolean face)//決定方向
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
		
		if(faceright)		//龜往右爬
			positionNow.x++;
		else				//龜往左爬
			positionNow.x--;
		if(reachborder())
		{
			positionNow.x = x;
			positionNow.y = y;
			faceright = !faceright;//轉向
			if(faceright)
				tlabel.setIcon(new ImageIcon(fishright));
			else
				tlabel.setIcon(new ImageIcon(fishleft));
		}
	}
	
	public boolean reachborder()//碰到或超過上下左右邊界
	{
		if((positionNow.x <= 0) || (positionNow.x + width >= bowl.getBounds().getWidth()))
			return true;
		if(positionNow.y + height >= bowl.getBounds().getHeight()+error)
			return true;
		
		return false;
	}
	
	public boolean reachground()//觸地
	{
		if(positionNow.y + height >= bowl.getBounds().getHeight()+error)
			return true;
		return false;
	}
	
	public void fixPositionMethod()//修正邊界變化時位置調整 
	{
		drop();
		while(reachborder())//碰到或超過上下左右邊界
		{
			tlabel.setBounds(positionNow.x, positionNow.y, width, height);
			if((positionNow.x <= 0) || (positionNow.x + width >= bowl.getBounds().getWidth()))
				positionNow.x--;
			if(positionNow.y + height >= bowl.getBounds().getHeight()+error)
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
