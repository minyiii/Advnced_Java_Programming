import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class MapFrame extends JFrame{
	List<String> list = new ArrayList<String>();	//檔案讀進來的0,1,2
	private BloodPanel bloodJPanel;				//血量圖區
	private JPanel mapJPanel;					//地圖區
	private static int bloodValue=100;			//血量
	private Icon brick, heart, diamond;			//圖片
	
	public MapFrame() {	//constructor
		super("電流急急棒");
		setLayout(new BorderLayout());
		SecureRandom secureRandom = new SecureRandom();
		
		// 血量圖JPanel //
		bloodJPanel = new BloodPanel();
		bloodJPanel.setPreferredSize(new Dimension(550, 50));
		add(bloodJPanel, BorderLayout.NORTH);
		
		// 匯入map.txt檔，得一個List<>String //
		list = ReadMapFile.readFromFile();
		
		//地圖區JPanel //
		mapJPanel = new JPanel();
		mapJPanel.setLayout(new GridLayout(10,10));
		add(mapJPanel, BorderLayout.CENTER);
		
		// 圖片 //
		brick = changeSize("brickwall.png");
		heart = changeSize("heart.png");
		diamond = changeSize("diamond.png");
		
		//數字轉地圖(functional programming)
		list.stream()
			.forEach(element->{
				switch(Integer.parseInt(element)) {
				case 0:
					addRoad();
					break;
				case 1:	//隨機將brick轉成heart
					//設定成：大概每5個，會有1個牆壁轉愛心
					if(secureRandom.ints(1, 6).findFirst().getAsInt()==1)
						addHeart();
					else
						addBrick();
					break;
				case 2:
					addDiamond();
					break;
				}
			});
	}
	
	public Icon changeSize(String str){	//更改圖片大小
		ImageIcon imgIcon = new ImageIcon(getClass().getResource(str));
		Image img = imgIcon.getImage().getScaledInstance(40, 40, java.awt.Image.SCALE_SMOOTH);
		Icon icon = new ImageIcon(img);
		return icon;
	}
	
	private void addRoad() {
		JLabel roadLabel = new JLabel();
		roadLabel.addMouseListener(new RoadHandler());
		mapJPanel.add(roadLabel);
	}
	
	private void addBrick() {
		JLabel brickLabel = new JLabel(brick);
		brickLabel.addMouseListener(new BrickHandler());
		mapJPanel.add(brickLabel);
	}
	
	private void addHeart() {
		JLabel heartLabel = new JLabel(heart);
		heartLabel.addMouseListener(new HeartHandler());
		mapJPanel.add(heartLabel);
	}
	
	private void addDiamond() {
		JLabel diamondLabel = new JLabel(diamond);
		diamondLabel.addMouseListener(new MouseAdapter(){
			@Override
			public void mouseEntered(MouseEvent event){
				JOptionPane.showMessageDialog(null, "成功拿到鑽石", "恭喜", JOptionPane.PLAIN_MESSAGE);
				dispose();
			}
		});
		mapJPanel.add(diamondLabel);
	}
	
	private void draw(){		//重畫血量圖
		bloodJPanel.setValue(bloodValue);
		Graphics g = bloodJPanel.getGraphics();
		g.setColor(Color.BLUE);
		g.drawRect(15, 20, 500, 10);
		g.fillRect(15, 20, bloodValue*5, 10);
		repaint();
	}
	
	private class RoadHandler extends MouseAdapter{
		@Override
		public void mouseEntered(MouseEvent event){
			bloodValue -= 5;
			draw();
			noBlood();
		}
	}
	
	private class HeartHandler extends MouseAdapter{
		@Override
		public void mouseEntered(MouseEvent event){
			bloodValue += 5;
			if(bloodValue>100)	//若血量加超過100皆視為100
				bloodValue = 100;
			draw();
		}
	}
	
	private class BrickHandler extends MouseAdapter{
		@Override
		public void mouseEntered(MouseEvent event){
			bloodValue -= 20;
			//System.out.printf("brick %d\n",bloodValue);
			draw();
			noBlood();
		}
	}
	
	private void noBlood(){	//確認還有沒有血(是否小於0)
		if(bloodValue<0) {
			JOptionPane.showMessageDialog(null, "沒血了啦，下次再加油QQ", "失敗", JOptionPane.PLAIN_MESSAGE);
			dispose();	//關閉視窗
		}
	}
}
