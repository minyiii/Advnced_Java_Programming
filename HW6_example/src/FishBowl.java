//�i����
//105403551
//��ޤGB
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.concurrent.*;
import javax.swing.*;

public class FishBowl extends JFrame{

	private final String[] buttonName = {"�s�W��", "�s�W�Q�t", "�������", "��������"};
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
			if (e.getSource() == newf)//���U�s�W�����s
			{
				bowl.removehandler();//����label��click handler
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
				bowl.addhandler();//�[�Wlabel��click handler
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
	
	public class Bowl extends JPanel//���ڽcpanel
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
					es.execute(fish);//����thread
					fn++;
					break;
					
				case 1:
					JLabel tlabel = new JLabel();
					Turtle turtle = new Turtle(new Point(e.getX(), e.getY()), bowl, tlabel);
					add(tlabel);
					tlabel.setVisible(true);
					turtleslabel.add(tlabel);
					turtles.add(turtle);
					es.execute(turtle);//����thread
					tn++;
					break;

				default:
				}
				statuslabel.setText(changeStatusString(fn, tn, buttonNumber));
			}
			
		}
		
		
		
		public void addhandler()
		{
			for(JLabel fl : fisheslabel)	//�@�@�[�Wevent handler
			{
				fl.addMouseListener(rf); 
			}
			for(JLabel tl : turtleslabel)	//�@�@�[�Wevent handler
			{
				tl.addMouseListener(rt); 
			}
		}
		
		public void removehandler()
		{
			for(JLabel fl : fisheslabel)	//�@�@�h��event handler
			{
				fl.removeMouseListener(rf);
			}
			for(JLabel tl : turtleslabel)	//�@�@�h��event handler
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
				fishes.get(findex).interrupted = true;//����thread
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
				turtles.get(tindex).interrupted = true;//����thread
				tn--;
				statuslabel.setText(changeStatusString(fn, tn, buttonNumber));
		    }  
		}
	}
	
	public String changeStatusString(int fn, int tn, int buttonNumber) //���ܪ��A�C
	{
		statusString = "�ثe�\��G" + buttonName[buttonNumber] + "�@�@�@���ƶq:"+fn+"�@�@�Q�t�ƶq:"+tn;
		return statusString;
	}
	
	
	
	public static void main(String[] args)
	{
		FishBowl application = new FishBowl();
		application.setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	
}