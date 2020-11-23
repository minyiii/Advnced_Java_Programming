//姓名:蘇敏宜 學號:106403034 系級:資管三B
import java.awt.FlowLayout;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Point;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseEvent;
import java.awt.Graphics2D;

import java.util.ArrayList;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JOptionPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

public class PainterFrame extends JFrame{
	private final JPanel toolPanel;	//工具區
	private final JPanel drawPanel;	//畫布區 
	private final JLabel statusBar;	//狀態列
	private String details;	//狀態列文字
	int x1,y1,x2,y2;	//畫圖形用的起點座標(x1,y1)，終點座標(x2,y2)
	private int currentX, currentY;	//滑鼠drag當下的座標
	private int currentColor = 0;	//當下的顏色，預設為黑色(下方colors陣列索引值0)
	private final Color[] colors = {Color.BLACK, Color.BLUE, Color.RED, Color.YELLOW};
	
	private final JPanel comboBoxPanel;
	private final JPanel radioButtonPanel;
	private final JLabel toolLabel;	//"繪圖工具"
	private final JComboBox<String> comboBox;	//繪圖工具comboBox
	private static final String[] drawTools = {"筆刷","直線","橢圓形","矩形","圓角矩形"};	//comboBox選項
	private int selectedTool;	//工具索引
	private final JLabel brushLabel;	//"筆刷大小"
	private final JRadioButton smallSize;	//筆刷大小選項(小)
	private final JRadioButton mediumSize;	//筆刷大小選項(中)
	private final JRadioButton bigSize;	//筆刷大小選項(大)
	private final ButtonGroup brushSize; //筆刷大小的RadioButton組合
	private int brushSizeInt=5;	//default(小筆刷)
	private final JCheckBox fillCheckBox;	//填滿
	private final JButton colorButton;	//筆刷顏色button
	private final JButton clearButton;	//清除畫面button
	
	public PainterFrame(){	//constructor
		super("小畫家");
		JOptionPane.showConfirmDialog(null, "Welcome", "訊息",JOptionPane.DEFAULT_OPTION,JOptionPane.INFORMATION_MESSAGE);	//迎賓訊息
		
		///// 工具列 /////
		toolPanel = new JPanel();	//工具列
		toolPanel.setLayout(new FlowLayout());
		//工具列-工具選擇
		comboBoxPanel = new JPanel();
		toolLabel = new JLabel("繪圖工具");
		comboBoxPanel.add(toolLabel,BorderLayout.NORTH);
		comboBox = new JComboBox<String>(drawTools);
		comboBox.setMaximumRowCount(3);	//一次最多顯示三個選項
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
				
		//工具列-改顏色button
		colorButton = new JButton("筆刷顏色");
		colorButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent event){
				String showButton = event.getActionCommand();
				System.out.println("點選 " + showButton);
				//每按1下索引值會+1變換顏色，到最後一個後會回到索引值0繼續
				if(currentColor >= colors.length-1)
					currentColor=0;
				else
					currentColor ++;
			}
		});
		toolPanel.add(colorButton);
		
		//工具列-清畫面button
		clearButton = new JButton("清除畫面");
		clearButton.addActionListener(
			new ActionListener(){
				@Override
				public void actionPerformed(ActionEvent event){
					Graphics g = drawPanel.getGraphics();
					String showButton = event.getActionCommand();
					System.out.println("點選 " + showButton);
					repaint();	//清畫面
				}
			}
		);
		toolPanel.add(clearButton);
		add(toolPanel, BorderLayout.NORTH);
		
		//畫布區
		drawPanel = new JPanel();
		drawPanel.setBackground(Color.WHITE);	//畫布為白背景
		drawPanel.addMouseListener(new MouseHandler());
		drawPanel.addMouseMotionListener(new MouseMoveHandler());
		add(drawPanel, BorderLayout.CENTER);
		
		//狀態列JPanel
		statusBar = new JLabel("指標非範圍內");
		add(statusBar, BorderLayout.SOUTH);
	}
	
	// class RadioButtonHandler //
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
	
	// class MouseHandler //
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
	
	// class MouseMoveHandler //
	private class MouseMoveHandler extends MouseMotionAdapter{
		@Override
		public void mouseMoved(MouseEvent event){	//滑鼠移動會顯示座標
			int xPos = event.getX();
			int yPos = event.getY();
			details = String.format("指標位置:(%d, %d)", xPos, yPos);
			statusBar.setText(details);
		}
		@Override
		public void mouseDragged(MouseEvent event){	//畫筆刷用，邊移動邊記當下位置並畫橢圓(drawBrush())
			currentX = event.getX();
			currentY = event.getY();
			if(selectedTool==0){
				drawBrush();
			}
		}
	}
	
	// function drawBrush() //
	private void drawBrush(){
		Graphics g = drawPanel.getGraphics();
		g.setColor(colors[currentColor]);
		g.fillOval(currentX, currentY, brushSizeInt, brushSizeInt);
	}
	
	// function drawShape() //
	private void drawShape(){
		Graphics2D g = (Graphics2D)drawPanel.getGraphics();
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
