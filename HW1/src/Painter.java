import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.image.BufferedImage;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.BorderLayout;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.ArrayList;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

public class Painter extends JFrame{
	private final JPanel toolPanel;
	private final DrawPanel drawPanel;
	private final JLabel statusBar;
	int x1,y1,x2,y2;	//起點座標，終點座標
	private final ArrayList<Point> points = new ArrayList();	//drag到的點都放這
	private String details;	//狀態列文字
	
	private int currentX,currentY;
	
	//Tool
	private final JPanel comboBoxPanel;
	private final JPanel radioButtonPanel;
	private final JComboBox<String> comboBox;	//繪圖工具comboBox
	private final JLabel toolLabel;	//"繪圖工具"
	private static final String[] drawTools = {"筆刷","直線","橢圓形","矩形","圓角矩形"};	//comboBox選項
	private int selectedTool;	//工具索引
	private int brushSizeInt=5;	//default(小筆刷)
	private final JRadioButton smallSize;	//筆刷大小選項(小)
	private final JRadioButton mediumSize;	//筆刷大小選項(中)
	private final JRadioButton bigSize;	//筆刷大小選項(大)
	private final ButtonGroup brushSize; //筆刷大小的RadioButton組合
	private final JCheckBox fillCheckBox;	//填滿
	private final JButton colorButton;	//筆刷顏色button
	private int currentColor = 0;	//目前顏色的索引值
	private final Color[] colors = {Color.BLACK, Color.BLUE, Color.RED, Color.YELLOW};
	private final JButton clearButton;	//清除畫面button	
	private final JLabel brushLabel;	//"筆刷大小"
	
	
	public Painter(){	//constructor
		super("小畫家");
		JOptionPane.showConfirmDialog(null, "Welcome", "訊息",JOptionPane.DEFAULT_OPTION,JOptionPane.INFORMATION_MESSAGE);	//迎賓訊息
		
		setLayout(new BorderLayout());
		
		///// 工具列 /////
		toolPanel = new JPanel();
		add(toolPanel, BorderLayout.NORTH);
		
		// 工具列-工具選擇
		comboBoxPanel = new JPanel();
		toolLabel = new JLabel("繪圖工具");
		comboBoxPanel.add(toolLabel,BorderLayout.NORTH);
		comboBox = new JComboBox<String>(drawTools);
		comboBox.setMaximumRowCount(3);
		comboBox.addItemListener(
			new ItemListener(){
				@Override
				public void itemStateChanged(ItemEvent event){
					selectedTool = comboBox.getSelectedIndex();	//選取之選項的索引值
					if(event.getStateChange()==ItemEvent.SELECTED){
						String showTool = (String)event.getItem();
						System.out.println("選擇 " + showTool);
					}
				}
			}	
		);
		comboBoxPanel.add(comboBox,BorderLayout.SOUTH);
		toolPanel.add(comboBoxPanel);
		
		//工具列-筆刷大小
		radioButtonPanel = new JPanel();
		brushLabel = new JLabel("筆刷大小");
		radioButtonPanel.add(brushLabel,BorderLayout.NORTH);
						
		smallSize = new JRadioButton("小",true);
		mediumSize = new JRadioButton("中",false);
		bigSize = new JRadioButton("大",false);
		radioButtonPanel.add(smallSize,BorderLayout.WEST);
		radioButtonPanel.add(mediumSize,BorderLayout.CENTER);
		radioButtonPanel.add(bigSize,BorderLayout.EAST);
						
		brushSize = new ButtonGroup();
		brushSize.add(smallSize);
		brushSize.add(mediumSize);
		brushSize.add(bigSize);
		toolPanel.add(radioButtonPanel);
				
		smallSize.addItemListener(new RadioButtonHandler());
		mediumSize.addItemListener(new RadioButtonHandler());
		bigSize.addItemListener(new RadioButtonHandler());
				
		//工具列-填滿(JCheckBox)
		fillCheckBox = new JCheckBox("填滿");
		fillCheckBox.addItemListener(new ItemListener(){
			@Override
			public void itemStateChanged(ItemEvent event){
				String showFill = fillCheckBox.getActionCommand();
				if(fillCheckBox.isSelected())
					System.out.println("選擇 "+showFill);
				else
					System.out.println("取消 "+showFill);
			}
		});
		toolPanel.add(fillCheckBox);
				
		//工具列-顏色button
		colorButton = new JButton("筆刷顏色");
		colorButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent event){
				String showButton = event.getActionCommand();
				System.out.println("點選 " + showButton);
				if(currentColor >= colors.length-1)
					currentColor=0;
				else
					currentColor ++;
			}
		});
		toolPanel.add(colorButton);
		//工具列-清除button
		clearButton = new JButton("清除畫面");
		clearButton.addActionListener(
			new ActionListener(){
				@Override
				public void actionPerformed(ActionEvent event){
					Graphics g = drawPanel.getGraphics();
					String showButton = event.getActionCommand();
					System.out.println("點選 " + showButton);
					//g.clearRect(0,0,drawPanel.getWidth(),drawPanel.getHeight());
					//drawPanel.removeAll();
					repaint();
					//drawPanel.setBackground(Color.WHITE);
				}
			}
		);
		toolPanel.add(clearButton);
		add(toolPanel, BorderLayout.NORTH);
			
		//// 畫布區 /////
		drawPanel = new DrawPanel();
		drawPanel.addMouseListener(new MouseHandler());
		drawPanel.addMouseMotionListener(new MouseMoveHandler());
		//drawPanel.setSize(850, 400);
		
		add(drawPanel, BorderLayout.CENTER);
		
		///// 狀態列 /////
		statusBar = new JLabel("指標非範圍內");
		add(statusBar, BorderLayout.SOUTH);
	}
	
	///// RadioButtonHandler /////
	private class RadioButtonHandler implements ItemListener{
		@Override
		public void itemStateChanged(ItemEvent event){
			if(smallSize.isSelected()){
				brushSizeInt = 5;
				System.out.println("選擇 小 筆刷");
			}
			else if(mediumSize.isSelected()){
				brushSizeInt = 10;
				System.out.println("選擇 中 筆刷");
			}
			else if(bigSize.isSelected()){
				brushSizeInt = 15;
				System.out.println("選擇 大 筆刷");
			}
		}
	}
	
	///// Class DrawPanel /////
	private class DrawPanel extends JPanel{
		private BufferedImage img;
		private Graphics2D g2;
		
		public DrawPanel(){	//constructor
			int drawWidth = 850;
			int drawHeight = 500;
			img = new BufferedImage(drawWidth,drawHeight,BufferedImage.TYPE_INT_RGB);
			g2 = (Graphics2D)img.getGraphics();
			
			g2.setColor(Color.WHITE);
			g2.fillRect(0, 0, drawWidth, drawHeight);
		}
		
		// functions //
		/*public void setting(){
			g2.setColor(Color.WHITE);
			g2.fillRect(0, 0, this.getWidth(), this.getHeight());
		}*/
		
		public void paintComponent(Graphics g){
			super.paintComponent(g);
			g.drawImage(img, 0, 0 ,null);
			/*for(Point point:points)
				g.fillOval(point.x, point.y, brushSizeInt, brushSizeInt);*/
		}
		
		public Graphics2D getGraphics2D(){
			return g2;
		}
	}
	
	///// Class MouseMoveHandler /////
	private class MouseMoveHandler extends MouseMotionAdapter{	//滑鼠移動會顯示座標
		@Override
		public void mouseMoved(MouseEvent event){
			int xPos = event.getX();
			int yPos = event.getY();
			details = String.format("指標位置:(%d, %d)", xPos, yPos);
			statusBar.setText(details);
		}
		@Override
		public void mouseDragged(MouseEvent event){
			points.add(event.getPoint());
			currentX = event.getX();
			currentY = event.getY();
			if(selectedTool==0){
				System.out.println("Drag!!!");
				drawBrush();
			}
		}
	}
	
	///// Class MouseHandler /////
	private class MouseHandler extends MouseAdapter{
		@Override
		public void mousePressed(MouseEvent event){	//按下去沒放開時=>起點
			x1 = event.getX();
			y1 = event.getY();
		}
		@Override
		public void mouseReleased(MouseEvent event){	//按下並拖曳再放開時
			x2 = event.getX();
			y2 = event.getY();
			drawShape();
		}
	}
	
	///// Function drawBrush /////
	private void drawBrush(){
		Graphics g = drawPanel.getGraphics();
		g.setColor(colors[currentColor]);
		System.out.println("in draw brush");
		/////下
		/*
		for(Point point:points){
			g.fillOval(point.x, point.y, brushSizeInt, brushSizeInt);
		}*/
		////上
		g.fillOval(currentX, currentY, brushSizeInt, brushSizeInt);
	}
	
	///// Function drawShape /////
	private void drawShape(){
		Graphics g = drawPanel.getGraphics();
		int width = Math.abs(x2-x1);
		int height = Math.abs(y2-y1);
		
		switch (selectedTool){
		case 0:	//筆刷
			break;
		case 1:	//直線
			g.setColor(colors[currentColor]);
			g.drawLine(x1, y1, x2, y2);
			break;
		case 2:	//橢圓
			g.setColor(colors[currentColor]);
			if(fillCheckBox.isSelected())	//若有勾選填滿
				g.fillOval(x1, y1, width, height);
			else	//沒勾選
				g.drawOval(x1, y1, width, height);
			break;
		case 3:	//矩形
			g.setColor(colors[currentColor]);
			if(fillCheckBox.isSelected())
				g.fillRect(x1, y1, width, height);
			else
				g.drawRect(x1, y1, width, height);
			break;
		case 4:	//圓角矩形
			g.setColor(colors[currentColor]);
			if(fillCheckBox.isSelected())
				g.fillRoundRect(x1, y1, width, height, 30, 30);
			else
				g.drawRoundRect(x1, y1, width, height, 30, 30);
			break;
		}
	}
}


