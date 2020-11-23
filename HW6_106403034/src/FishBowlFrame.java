//姓名:蘇敏宜  學號:106403034 系級:資管三B

import java.awt.BorderLayout;
import java.awt.Color;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class FishBowlFrame extends JFrame{
	private ExecutorService executorService;
	private JButton addFishBtn;		//新增魚的JButton
	private JButton addTurtleBtn;	//新增烏龜的JButton
	private JButton removeSelectBtn;//移除選取的JButton
	private JButton removeAllBtn;	//移除全部的JButton
	private JButton eatBtn;			//餵飼料的JButton
	private String[] btnName = {"新增魚", "移除選取", "新增烏龜", "移除所有", "餵飼料"};
	//private JPanel btnPanel;		//上方:按鈕區
	private int state=-1;			//目前按哪個按鈕(0:新增魚、1:移除選取、2:新增烏龜、3移除所有、4:餵飼料)
	private JLabel stringLabel;		//中間:文字區(顯示目前功能及數量)
	private JPanel notBowlPanel;	//非水族箱區(讓他放borderlayput的北，魚缸區才能放center)
	private String showFeature;		//中間文字區的"目前功能:XXX"文字
	private String showAmount;		//中間文字區的"X數量:XXX"文字
	private JPanel bowlPanel;		//下方:水族箱區
	private MouseMotionHandler bowlPanelMMH;
	private Color bowlBG = new Color(150,219,236);	//水族箱背景色
	
	private int turtleCount = 0;	//烏龜數量
	private int fishCount = 0;		//魚數量
	
	private TurtleMH tmh = new TurtleMH();
	private FishMH fmh = new FishMH();
	
	private ArrayList<Turtle> turtles = new ArrayList<Turtle>();
	private ArrayList<Fish> fishes = new ArrayList<Fish>();
	private ArrayList<Cookie> cookies = new ArrayList<Cookie>();
	private ArrayList<JLabel> tList = new ArrayList<JLabel>();
	private ArrayList<JLabel> fList = new ArrayList<JLabel>();
	private ArrayList<JLabel> cList = new ArrayList<JLabel>();
	
	public FishBowlFrame() {	//construcotr
		super("Fish Bowl");
		setLayout(new BorderLayout());
		
		//產生一個ExecutorService去管理thread
		executorService = Executors.newCachedThreadPool();
		
		//上方按鈕區
		addFishBtn = new JButton(btnName[0]);
		addFishBtn.addActionListener(new BtnListener());
		removeSelectBtn = new JButton(btnName[1]);
		removeSelectBtn.addActionListener(new BtnListener());
		addTurtleBtn = new JButton(btnName[2]);
		addTurtleBtn.addActionListener(new BtnListener());
		removeAllBtn = new JButton(btnName[3]);
		removeAllBtn.addActionListener(new BtnListener());
		eatBtn = new JButton(btnName[4]);
		eatBtn.addActionListener(new BtnListener());
		
		//中間文字區
		showFeature = String.format("目前功能: ");	//event.getActionCommand()
		showAmount = String.format("魚數量: %d, 烏龜數量: %d", fishCount, turtleCount);
		stringLabel = new JLabel(showFeature+"    "+showAmount);
		stringLabel.setForeground(new Color(100,138,188));
		
		//按鈕+文字區
		notBowlPanel = new JPanel();
		notBowlPanel.setLayout(new GridLayout(3,2,5,5));
		notBowlPanel.add(addFishBtn);
		notBowlPanel.add(removeSelectBtn);
		notBowlPanel.add(addTurtleBtn);
		notBowlPanel.add(removeAllBtn);
		notBowlPanel.add(stringLabel);
		notBowlPanel.add(eatBtn);
		add(notBowlPanel, BorderLayout.NORTH);
		
		//下面魚缸
		bowlPanel = new JPanel();
		bowlPanel.setLayout(null);
		bowlPanel.setBackground(bowlBG);
		bowlPanel.setPreferredSize(new Dimension(this.getWidth(), 500));
		add(bowlPanel, BorderLayout.CENTER);
		bowlPanelMMH = new MouseMotionHandler();
		bowlPanel.addMouseListener(bowlPanelMMH);
		
		//視窗關閉就關掉executorService
		addWindowListener(new WindowAdapter(){
			@Override
			public void windowClosing(WindowEvent e) {
				executorService.shutdown();
				System.out.println("close JFrame");
			}
		});
	}
	
	//-----------------------method--------------------//
	//重整文字區
	public void setStringLabel() {
		showFeature = String.format("目前功能: %s", btnName[state]);
		showAmount = String.format("魚數量: %d, 烏龜數量: %d", fList.size(), tList.size());
		stringLabel.setText(showFeature+"    "+showAmount);
	}
	
	//對每個烏龜和魚都加上mouselistener
	private void addML() {
		for(JLabel t: tList) {
			t.addMouseListener(tmh);
		}
		for(JLabel f: fList) {
			f.addMouseListener(fmh);
		}
	}
		
	//移除魚和烏龜的mouselistener
	private void removeML() {
		for(JLabel t: tList) {
			t.removeMouseListener(tmh);
		}
		for(JLabel f: fList) {
			f.removeMouseListener(fmh);
		}
	}
	
	//-----------------------class--------------------//
	
	//對button的actionlistener
	private class BtnListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent event) {
			if(event.getActionCommand()==btnName[0]) {	//增加魚
				state=0;
				removeML();
			}
			else if(event.getActionCommand()==btnName[1]) {	//刪除選取
				state=1;
				addML();
			}
			else if(event.getActionCommand()==btnName[2]) {	//增加烏龜
				state=2;
				removeML();
			}
			else if(event.getActionCommand()==btnName[3]) {	//刪全部
				removeML();
				for(Turtle t: turtles) {
					t.donotContinue();
				}
				for(Fish f: fishes) {
					f.donotContinue();
				}
				for(Cookie c: cookies) {
					c.donotContinue();
				}
				turtles.removeAll(turtles);
				fishes.removeAll(fishes);
				cookies.removeAll(cookies);
				tList.removeAll(tList);
				fList.removeAll(fList);
				cList.removeAll(cList);
				bowlPanel.removeAll();
				bowlPanel.repaint();
				state=3;
			}
			else if(event.getActionCommand()==btnName[4]) {	//餵飼料
				state=4;
				removeML();
			}
			setStringLabel();
		}
	}
	
	//對烏龜JLabel的MouseMotionHandler
	private class TurtleMH extends MouseAdapter{
		@Override
		public void mouseClicked(MouseEvent event) {
			int delIndex = tList.indexOf(event.getComponent());
			tList.remove(delIndex);
			turtles.get(delIndex).donotContinue();
			turtles.remove(delIndex);
			bowlPanel.remove(event.getComponent());
			bowlPanel.repaint();
			setStringLabel();
		}
	}
	
	//對魚JLabel的MouseMotionHandler
	private class FishMH extends MouseAdapter{
		@Override
		public void mouseClicked(MouseEvent event) {
			int delIndex = fList.indexOf(event.getComponent());
			fList.remove(delIndex);
			fishes.get(delIndex).donotContinue();
			fishes.remove(delIndex);
			bowlPanel.remove(event.getComponent());
			bowlPanel.repaint();
			setStringLabel();
		}
	}
	
	//對魚缸區的mousemotionhandler
	private class MouseMotionHandler extends MouseAdapter{
		@Override
		public void mouseClicked(MouseEvent event) {
			int xPos = event.getX();
			int yPos = event.getY();
			switch(state) {
			case 0:	//增加魚
				JLabel fLabel = new JLabel();
				Fish f = new Fish(bowlPanel, fLabel, xPos, yPos, cookies);
				fishes.add(f);
				fList.add(fLabel);	//把魚JLabel加進ArrayList中
				executorService.execute(f);
				break;
			case 1:
				break;
			case 2:	//增加烏龜
				JLabel tLabel = new JLabel();
				Turtle t = new Turtle(bowlPanel, tLabel, xPos, yPos, cookies);
				turtles.add(t);
				tList.add(tLabel);	//把烏龜JLabel加進ArrayList中
				executorService.execute(t);		
				break;
			case 3:
				break;
			case 4:	//餵飼料
				JLabel cLabel = new JLabel();
				Cookie c = new Cookie(bowlPanel, cLabel, xPos, yPos, cookies, cList);
				cookies.add(c);
				cList.add(cLabel);
				executorService.execute(c);	
				break;
			}
			setStringLabel();
		}
	}
}
