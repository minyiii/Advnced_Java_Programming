//�m�W:Ĭ�өy  �Ǹ�:106403034 �t��:��ޤTB

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
	private JButton addFishBtn;		//�s�W����JButton
	private JButton addTurtleBtn;	//�s�W�Q�t��JButton
	private JButton removeSelectBtn;//���������JButton
	private JButton removeAllBtn;	//����������JButton
	private JButton eatBtn;			//���}�ƪ�JButton
	private String[] btnName = {"�s�W��", "�������", "�s�W�Q�t", "�����Ҧ�", "���}��"};
	//private JPanel btnPanel;		//�W��:���s��
	private int state=-1;			//�ثe�����ӫ��s(0:�s�W���B1:��������B2:�s�W�Q�t�B3�����Ҧ��B4:���}��)
	private JLabel stringLabel;		//����:��r��(��ܥثe�\��μƶq)
	private JPanel notBowlPanel;	//�D���ڽc��(���L��borderlayput���_�A�����Ϥ~���center)
	private String showFeature;		//������r�Ϫ�"�ثe�\��:XXX"��r
	private String showAmount;		//������r�Ϫ�"X�ƶq:XXX"��r
	private JPanel bowlPanel;		//�U��:���ڽc��
	private MouseMotionHandler bowlPanelMMH;
	private Color bowlBG = new Color(150,219,236);	//���ڽc�I����
	
	private int turtleCount = 0;	//�Q�t�ƶq
	private int fishCount = 0;		//���ƶq
	
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
		
		//���ͤ@��ExecutorService�h�޲zthread
		executorService = Executors.newCachedThreadPool();
		
		//�W����s��
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
		
		//������r��
		showFeature = String.format("�ثe�\��: ");	//event.getActionCommand()
		showAmount = String.format("���ƶq: %d, �Q�t�ƶq: %d", fishCount, turtleCount);
		stringLabel = new JLabel(showFeature+"    "+showAmount);
		stringLabel.setForeground(new Color(100,138,188));
		
		//���s+��r��
		notBowlPanel = new JPanel();
		notBowlPanel.setLayout(new GridLayout(3,2,5,5));
		notBowlPanel.add(addFishBtn);
		notBowlPanel.add(removeSelectBtn);
		notBowlPanel.add(addTurtleBtn);
		notBowlPanel.add(removeAllBtn);
		notBowlPanel.add(stringLabel);
		notBowlPanel.add(eatBtn);
		add(notBowlPanel, BorderLayout.NORTH);
		
		//�U������
		bowlPanel = new JPanel();
		bowlPanel.setLayout(null);
		bowlPanel.setBackground(bowlBG);
		bowlPanel.setPreferredSize(new Dimension(this.getWidth(), 500));
		add(bowlPanel, BorderLayout.CENTER);
		bowlPanelMMH = new MouseMotionHandler();
		bowlPanel.addMouseListener(bowlPanelMMH);
		
		//���������N����executorService
		addWindowListener(new WindowAdapter(){
			@Override
			public void windowClosing(WindowEvent e) {
				executorService.shutdown();
				System.out.println("close JFrame");
			}
		});
	}
	
	//-----------------------method--------------------//
	//�����r��
	public void setStringLabel() {
		showFeature = String.format("�ثe�\��: %s", btnName[state]);
		showAmount = String.format("���ƶq: %d, �Q�t�ƶq: %d", fList.size(), tList.size());
		stringLabel.setText(showFeature+"    "+showAmount);
	}
	
	//��C�ӯQ�t�M�����[�Wmouselistener
	private void addML() {
		for(JLabel t: tList) {
			t.addMouseListener(tmh);
		}
		for(JLabel f: fList) {
			f.addMouseListener(fmh);
		}
	}
		
	//�������M�Q�t��mouselistener
	private void removeML() {
		for(JLabel t: tList) {
			t.removeMouseListener(tmh);
		}
		for(JLabel f: fList) {
			f.removeMouseListener(fmh);
		}
	}
	
	//-----------------------class--------------------//
	
	//��button��actionlistener
	private class BtnListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent event) {
			if(event.getActionCommand()==btnName[0]) {	//�W�[��
				state=0;
				removeML();
			}
			else if(event.getActionCommand()==btnName[1]) {	//�R�����
				state=1;
				addML();
			}
			else if(event.getActionCommand()==btnName[2]) {	//�W�[�Q�t
				state=2;
				removeML();
			}
			else if(event.getActionCommand()==btnName[3]) {	//�R����
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
			else if(event.getActionCommand()==btnName[4]) {	//���}��
				state=4;
				removeML();
			}
			setStringLabel();
		}
	}
	
	//��Q�tJLabel��MouseMotionHandler
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
	
	//�ﳽJLabel��MouseMotionHandler
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
	
	//�ﳽ���Ϫ�mousemotionhandler
	private class MouseMotionHandler extends MouseAdapter{
		@Override
		public void mouseClicked(MouseEvent event) {
			int xPos = event.getX();
			int yPos = event.getY();
			switch(state) {
			case 0:	//�W�[��
				JLabel fLabel = new JLabel();
				Fish f = new Fish(bowlPanel, fLabel, xPos, yPos, cookies);
				fishes.add(f);
				fList.add(fLabel);	//�⳽JLabel�[�iArrayList��
				executorService.execute(f);
				break;
			case 1:
				break;
			case 2:	//�W�[�Q�t
				JLabel tLabel = new JLabel();
				Turtle t = new Turtle(bowlPanel, tLabel, xPos, yPos, cookies);
				turtles.add(t);
				tList.add(tLabel);	//��Q�tJLabel�[�iArrayList��
				executorService.execute(t);		
				break;
			case 3:
				break;
			case 4:	//���}��
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
