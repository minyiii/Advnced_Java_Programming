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

import javax.swing.BoxLayout;
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
	private JButton fgBtn;			//新增釣竿的JButton
	private JButton fgDelBtn;			//新增釣竿的JButton
	private String[] btnName = {"新增魚", "移除選取", "新增烏龜", "移除所有", "餵飼料", "放釣竿", "收釣桿"};
	private JPanel btnPanel;		//上方:按鈕區
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
	private int dieTNum = 0;		//被調走的數量
	private int dieFNum = 0;
	
	private TurtleMH tmh = new TurtleMH();
	private FishMH fmh = new FishMH();
	private CookieMH cmh = new CookieMH();
	private FishingMH fgmh = new FishingMH();
	
	private ArrayList<Turtle> turtles = new ArrayList<Turtle>();
	private ArrayList<Fish> fishes = new ArrayList<Fish>();
	private ArrayList<Cookie> cookies = new ArrayList<Cookie>();
	private ArrayList<Fishing> fishings = new ArrayList<Fishing>();
	
	private ArrayList<JLabel> tList = new ArrayList<JLabel>();
	private ArrayList<JLabel> fList = new ArrayList<JLabel>();
	private ArrayList<JLabel> cList = new ArrayList<JLabel>();
	private ArrayList<JLabel> fgList = new ArrayList<JLabel>();
	
	public FishBowlFrame() {	//construcotr
		super("Fish Bowl");
		setLayout(new BorderLayout());
		
		//產生一個ExecutorService去管理thread
		executorService = Executors.newCachedThreadPool();
		
		//上方按鈕區
		btnPanel = new JPanel();
		btnPanel.setLayout(new GridLayout(3,3,5,5));
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
		fgBtn = new JButton(btnName[5]);
		fgBtn.addActionListener(new BtnListener());
		fgDelBtn =  new JButton(btnName[6]);
		fgDelBtn.addActionListener(new BtnListener());
		
		btnPanel.add(addFishBtn);
		btnPanel.add(removeSelectBtn);
		btnPanel.add(addTurtleBtn);
		btnPanel.add(removeAllBtn);
		btnPanel.add(eatBtn);
		btnPanel.add(fgBtn);
		btnPanel.add(fgDelBtn);
		
		//中間文字區
		showFeature = String.format("目前功能: ");	//event.getActionCommand()
		showAmount = String.format("魚數量: %d, 烏龜數量: %d, \n飼料數量: %d, 釣竿數量: %d\n"
				+ "已釣到的魚數量: %d, 已釣到的烏龜數量: %d"
				, fList.size(), tList.size(), cList.size(), fgList.size(), dieFNum, dieTNum);
		stringLabel = new JLabel(showFeature+"    "+showAmount);
		stringLabel.setForeground(new Color(100,138,188));
		
		//上方非魚缸區
		notBowlPanel = new JPanel();
		notBowlPanel.setLayout(new BoxLayout(notBowlPanel, BoxLayout.Y_AXIS));
		notBowlPanel.add(btnPanel);
		notBowlPanel.add(stringLabel);
		
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
		showAmount = String.format("魚數量: %d, 烏龜數量: %d, \n飼料數量: %d, 釣竿數量: %d\n"
				+ ", 已釣到的魚數量: %d, 已釣到的烏龜數量: %d"
				, fList.size(), tList.size(), cList.size(), fgList.size(), dieFNum, dieTNum);
		stringLabel.setText(showFeature+"    "+showAmount);
	}
	
	//對每個物件都加上mouselistener
	private void addML() {
		for(JLabel t: tList) {
			t.addMouseListener(tmh);
		}
		for(JLabel f: fList) {
			f.addMouseListener(fmh);
		}
		for(JLabel c: cList) {
			c.addMouseListener(cmh);
		}
		/*for(JLabel fg: fgList) {
			fg.addMouseListener(fgmh);
		}*/
	}
		
	//移除每個物件的mouselistener
	private void removeML() {
		for(JLabel t: tList) {
			t.removeMouseListener(tmh);
		}
		for(JLabel f: fList) {
			f.removeMouseListener(fmh);
		}
		for(JLabel c: cList) {
			c.removeMouseListener(cmh);
		}
		/*for(JLabel fg: fgList) {
			fg.removeMouseListener(fgmh);
		}*/
	}
	
	public void deleteT(Turtle t) {
		int delIndex = turtles.indexOf(t);
		bowlPanel.remove(tList.get(delIndex));
		tList.remove(delIndex);
		turtles.get(delIndex).donotContinue();
		turtles.remove(delIndex);
		
		bowlPanel.repaint();
		dieTNum++;
		setStringLabel();
	}
	
	public void deleteF(Fish f) {
		int delIndex = fishes.indexOf(f);
		bowlPanel.remove(fList.get(delIndex));
		fList.remove(delIndex);
		fishes.get(delIndex).donotContinue();
		fishes.remove(delIndex);
		
		bowlPanel.repaint();
		dieFNum++;
		setStringLabel();
	}
	
	public void deleteC(Cookie c) {
		int delIndex = cookies.indexOf(c);
		bowlPanel.remove(cList.get(delIndex));
		cList.remove(delIndex);
		cookies.get(delIndex).donotContinue();
		cookies.remove(delIndex);
		
		bowlPanel.repaint();
		setStringLabel();
	}
	
	public void deleteFG(Fishing fg) {
		int delIndex = fishings.indexOf(fg);
		bowlPanel.remove(fgList.get(delIndex));
		fgList.remove(delIndex);
		fishings.get(delIndex).donotContinue();
		fishings.remove(delIndex);
		
		bowlPanel.repaint();
		setStringLabel();
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
				for(Fishing fg: fishings) {
					fg.donotContinue();
				}
				turtles.removeAll(turtles);
				fishes.removeAll(fishes);
				cookies.removeAll(cookies);
				fishings.removeAll(fishings);
				tList.removeAll(tList);
				fList.removeAll(fList);
				cList.removeAll(cList);
				fgList.removeAll(fgList);
				bowlPanel.removeAll();
				bowlPanel.repaint();
				dieFNum = 0;
				dieTNum = 0;
				state=3;
			}
			else if(event.getActionCommand()==btnName[4]) {	//餵飼料
				state=4;
				removeML();
			}
			else if(event.getActionCommand()==btnName[5]) {	//放釣竿
				state=5;
				removeML();
			}
			else if(event.getActionCommand()==btnName[6]) {	//收釣竿
				state=6;
				removeML();
				for(JLabel fg: fgList) {
					fg.addMouseListener(fgmh);
				}
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
	
	//對餅乾JLabel的MouseMotionHandler
	private class CookieMH extends MouseAdapter{
		@Override
		public void mouseClicked(MouseEvent event) {
			int delIndex = cList.indexOf(event.getComponent());
			cList.remove(delIndex);
			cookies.get(delIndex).donotContinue();
			cookies.remove(delIndex);
			bowlPanel.remove(event.getComponent());
			bowlPanel.repaint();
			setStringLabel();
		}
	}
	
	//對JLabel的MouseMotionHandler
	private class FishingMH extends MouseAdapter{
		@Override
		public void mouseClicked(MouseEvent event) {
			System.out.println("click fishing");
			int delIndex = fgList.indexOf(event.getComponent());
			if(state==1) {	//是刪除選取就刪掉
				fgList.remove(delIndex);
				fishings.get(delIndex).donotContinue();
				fishings.remove(delIndex);
				bowlPanel.remove(event.getComponent());
				bowlPanel.repaint();
				setStringLabel();
			}
			else if(state==6){	//收釣桿
				for(int i=0;i<turtles.size();i++) {
					turtles.get(i).fishing(fishings.get(delIndex));
				}
				for(int i=0;i<fishes.size();i++) {
					fishes.get(i).fishing(fishings.get(delIndex));
				}
				System.out.println("收釣桿");
				deleteFG(fishings.get(delIndex));
			}
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
				Fish f = new Fish(FishBowlFrame.this, bowlPanel, fLabel, xPos, yPos, cookies, fishings);
				fishes.add(f);
				fList.add(fLabel);	//把魚JLabel加進ArrayList中
				executorService.execute(f);
				break;
			case 1:
				break;
			case 2:	//增加烏龜
				JLabel tLabel = new JLabel();
				Turtle t = new Turtle(FishBowlFrame.this, bowlPanel, tLabel, xPos, yPos, cookies, fishings);
				turtles.add(t);
				tList.add(tLabel);	//把烏龜JLabel加進ArrayList中
				executorService.execute(t);		
				break;
			case 3:
				break;
			case 4:	//餵飼料
				JLabel cLabel = new JLabel();
				Cookie c = new Cookie(FishBowlFrame.this, bowlPanel, cLabel, xPos, yPos, cookies, cList);
				cookies.add(c);
				cList.add(cLabel);
				executorService.execute(c);	
				break;
			case 5:	//放釣桿
				JLabel fgLabel = new JLabel();
				fgLabel.addMouseListener(fgmh);
				Fishing fg = new Fishing(FishBowlFrame.this, bowlPanel, fgLabel, xPos, yPos, fishings, tList, fList);
				fishings.add(fg);
				fgList.add(fgLabel);	//把釣竿JLabel加進ArrayList中
				executorService.execute(fg);		
				break;
			}
			setStringLabel();
		}
	}
}
