//張哲嘉
//105403551
//資管二B
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.concurrent.*;
import javax.swing.*;

public class FishBowl extends JFrame{

	private final String[] buttonName = {"新增魚", "新增烏龜", "移除選取", "移除全部"};
	private int buttonNumber = 0;
	private final JButton newf = new JButton(buttonName[0]);
	private final JButton newt = new JButton(buttonName[1]);
	private final JButton removeone = new JButton(buttonName[2]);
	private final JButton removeall = new JButton(buttonName[3]);
	private final JLabel statuslabel;
	public Bowl bowl= new Bowl();
	ExecutorService es;
	
	private ArrayList<Fish> fishes = new ArrayList<>();
	private ArrayList<Turtle> turtles = new ArrayList<>();
	private int fn = 0;
	private int tn = 0;
	private  String statusString;
	
	public FishBowl()
	{
		super("FishBowl");
		JPanel buttonpanel = new JPanel();
		statuslabel = new JLabel(changeStatusString(fn, tn, buttonNumber));
		buttonpanel.setLayout(new GridLayout(3, 2));
		buttonpanel.add(newf);
		buttonpanel.add(removeone);
		buttonpanel.add(newt);
		buttonpanel.add(removeall);
		buttonpanel.add(statuslabel);
		newf.addActionListener(new ButtonHandler());
		removeone.addActionListener(new ButtonHandler());
		newt.addActionListener(new ButtonHandler());
		removeall.addActionListener(new ButtonHandler());
		
		
		add(buttonpanel, BorderLayout.NORTH);
		add(bowl, BorderLayout.CENTER);
		setSize(900, 900);
		setVisible(true);
		
		es = Executors.newCachedThreadPool();
	}
	
	private class ButtonHandler implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			if (e.getSource() == newf)//按下新增魚按鈕
			{
				bowl.removehandler();//移除label的click handler
				buttonNumber = 0;
			}
			else if(e.getSource() == newt)
			{
				bowl.removehandler();
				buttonNumber = 1;
			}
			else if(e.getSource() == removeone)
			{
				buttonNumber = 2;
				bowl.addhandler();//加上label的click handler
			}
			else if(e.getSource() == removeall)
			{
				buttonNumber = 3;
				for(Fish f : fishes)
					f.interrupted = true;
				fishes.clear();
				for(Turtle t : turtles)
					t.interrupted = true;
				turtles.clear();
				fn = 0;
				tn = 0;
				bowl.removeAll();
				bowl.repaint();
				bowl.fisheslabel.clear();
				bowl.turtleslabel.clear();
			}
			statuslabel.setText(changeStatusString(fn, tn, buttonNumber));
		}
	}
	
	public class Bowl extends JPanel//水族箱panel
	{
		public ArrayList<JLabel> fisheslabel = new ArrayList<JLabel>();
		public ArrayList<JLabel> turtleslabel = new ArrayList<JLabel>();
		public Removef rf = new Removef();//click handler for label
		public Removet rt = new Removet();//click handler for label
		
		public Bowl()
		{
			setLayout(null);
			setBackground(Color.decode("#99FFFF"));
			addMouseListener(new ClickHandler());//click handler for panel
		}
		
		
		
		private class ClickHandler extends MouseAdapter//for panel
		{
			public void mouseClicked(MouseEvent e)
			{
				switch(buttonNumber)
				{
				case 0:
					JLabel flabel = new JLabel();
					Fish fish = new Fish(new Point(e.getX(), e.getY()), bowl, flabel);
					add(flabel);
					flabel.setVisible(true);
					fisheslabel.add(flabel);
					fishes.add(fish);
					es.execute(fish);//執行thread
					fn++;
					break;
					
				case 1:
					JLabel tlabel = new JLabel();
					Turtle turtle = new Turtle(new Point(e.getX(), e.getY()), bowl, tlabel);
					add(tlabel);
					tlabel.setVisible(true);
					turtleslabel.add(tlabel);
					turtles.add(turtle);
					es.execute(turtle);//執行thread
					tn++;
					break;

				default:
				}
				statuslabel.setText(changeStatusString(fn, tn, buttonNumber));
			}
			
		}
		
		
		
		public void addhandler()
		{
			for(JLabel fl : fisheslabel)	//一一加上event handler
			{
				fl.addMouseListener(rf); 
			}
			for(JLabel tl : turtleslabel)	//一一加上event handler
			{
				tl.addMouseListener(rt); 
			}
		}
		
		public void removehandler()
		{
			for(JLabel fl : fisheslabel)	//一一去掉event handler
			{
				fl.removeMouseListener(rf);
			}
			for(JLabel tl : turtleslabel)	//一一去掉event handler
			{
				tl.removeMouseListener(rt);
			}
		}
		
		private class Removef extends MouseAdapter//click handler for fishlabel
		{
			public void mouseClicked(MouseEvent e)  
		    {  
				bowl.remove(e.getComponent());
				bowl.revalidate();
				bowl.repaint();
				int findex = fisheslabel.indexOf(e.getComponent());
				fishes.get(findex).interrupted = true;//結束thread
				fn--;
				statuslabel.setText(changeStatusString(fn, tn, buttonNumber));
		    }  
		}
		
		private class Removet extends MouseAdapter//click handler for turtlelabel
		{
			public void mouseClicked(MouseEvent e)  
		    {  
				bowl.remove(e.getComponent());
				bowl.revalidate();
				bowl.repaint();
				int tindex = turtleslabel.indexOf(e.getComponent());
				turtles.get(tindex).interrupted = true;//結束thread
				tn--;
				statuslabel.setText(changeStatusString(fn, tn, buttonNumber));
		    }  
		}
	}
	
	public String changeStatusString(int fn, int tn, int buttonNumber) //改變狀態列
	{
		statusString = "目前功能：" + buttonName[buttonNumber] + "　　　魚數量:"+fn+"　　烏龜數量:"+tn;
		return statusString;
	}
	
	
	
	public static void main(String[] args)
	{
		FishBowl application = new FishBowl();
		application.setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	
}