//姓名:蘇敏宜 學號:106403034 系級:資管三B
import java.awt.BasicStroke;
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
import javax.swing.JColorChooser;
import javax.swing.JOptionPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

public class PainterFrame extends JFrame{
	private final JPanel toolPanel;		//工具區
	private final JPanel drawPanel;		//畫布區 
	private final JLabel statusBar;		//狀態列
	private String details;				//狀態列文字
	int x1,y1,x2,y2;					//畫圖形用的起點座標(x1,y1)，終點座標(x2,y2)
	private int currentX, currentY;		//滑鼠drag當下的座標
	//private int currentColor = 0;		//當下的顏色，預設為黑色(下方colors陣列索引值0)
	//private final Color[] colors = {Color.BLACK, Color.BLUE, Color.RED, Color.YELLOW};
	private Color chooseColor;
	private Color bgColor=Color.WHITE;
	
	private final JPanel comboBoxPanel;
	private final JPanel radioButtonPanel;
	private final JLabel toolLabel;				//"繪圖工具"
	private final JComboBox<String> comboBox;	//繪圖工具comboBox
	private static final String[] drawTools = {"筆刷","直線","橢圓形","矩形","圓角矩形"};	//comboBox選項
	private int selectedTool;					//工具索引
	private final JLabel brushLabel;			//"筆刷大小"
	private final JRadioButton smallSize;		//筆刷大小選項(小)
	private final JRadioButton mediumSize;		//筆刷大小選項(中)
	private final JRadioButton bigSize;			//筆刷大小選項(大)
	private final ButtonGroup brushSize; 		//筆刷大小的RadioButton組合
	private int brushSizeInt=5;					//default(小筆刷)
	private final JCheckBox fillCheckBox;		//填滿
	private final JButton colorButton;			//筆刷顏色button
	private final JButton eraserButton;			//橡皮擦button
	private final JButton clearButton;			//清除畫面button
	
	public PainterFrame(){	//constructor
		super("小畫家");
		JOptionPane.showConfirmDialog(null, "Welcome", "訊息",JOptionPane.DEFAULT_OPTION,JOptionPane.INFORMATION_MESSAGE);	//迎賓訊息
		
		///// 工具列 /////
		toolPanel = new JPanel();	//工具列
		toolPanel.setLayout(new FlowLayout());
		//工具列-工具選擇
		comboBoxPanel = new JPanel();
		comboBoxPanel.setLayout(new BorderLayout());
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
						if(selectedTool==0){	//若選擇工具為筆刷，則填滿checkBox不能使用
							fillCheckBox.setEnabled(false);
						}
						else	//若選擇工具為其他，填滿checkBox可正常使用
							fillCheckBox.setEnabled(true);
					}
				}
			}	
		);
		comboBoxPanel.add(comboBox,BorderLayout.SOUTH);
		toolPanel.add(comboBoxPanel);
		
		//工具列-筆刷大小
		radioButtonPanel = new JPanel();
		radioButtonPanel.setLayout(new BorderLayout());
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
		fillCheckBox.setEnabled(false);
		//因繪圖工具JComboBOx預設值為筆刷，故此填滿checkBox需預設為false(不能使用)
		toolPanel.add(fillCheckBox);
				
		//工具列-改顏色button
		colorButton = new JButton("筆刷顏色");
		colorButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent event){
				String showButton = event.getActionCommand();
				System.out.println("點選 " + showButton);
				chooseColor = JColorChooser.showDialog(drawPanel,"選擇顏色",Color.BLACK);
			}
		});
		toolPanel.add(colorButton);
		
		//工具列-清畫面button
		clearButton = new JButton("清除畫面");
		clearButton.addActionListener(
			new ActionListener(){
				@Override
				public void actionPerformed(ActionEvent event){
					//Graphics g = drawPanel.getGraphics();	//利用Graphics物件獲取畫筆
					String showButton = event.getActionCommand();
					System.out.println("點選 " + showButton);
					drawPanel.repaint();	//清畫面
					//上面這行直接repaint()也行
				}
			}
		);
		toolPanel.add(clearButton);
		
		//工具列-橡皮擦button
		eraserButton = new JButton("橡皮擦");
		//橡皮擦JButton按兩次會變回黑色
		eraserButton.addActionListener(
			new ActionListener(){
				@Override
				public void actionPerformed(ActionEvent event){
					if(chooseColor != bgColor) {
						//Graphics g = drawPanel.getGraphics();
						String showButton = event.getActionCommand();
						System.out.println("點選 " + showButton);
						chooseColor = bgColor;
					}
					else
						chooseColor = Color.BLACK;
				}
			}	
		);
		toolPanel.add(eraserButton);
		
		add(toolPanel, BorderLayout.NORTH);
		
		//畫布區
		drawPanel = new JPanel();
		drawPanel.setBackground(bgColor);	//畫布為白背景
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
		g.setColor(chooseColor);
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
			g.setColor(chooseColor);
			if(!fillCheckBox.isSelected()){	//若沒勾選填滿，虛線
				float dash[]={4.0f, 30.0f};
				//setStroke()用來設定筆刷粗細
				g.setStroke(new BasicStroke((float)brushSizeInt, 2, 0, 1.0f, dash ,1.0f));
			}
			else	//若勾選填滿，實線
				g.setStroke(new BasicStroke((float)brushSizeInt));	//設定筆刷粗細
			g.drawLine(x1, y1, x2, y2);	//畫線
			break;
		case 2:	//橢圓
			g.setColor(chooseColor);
			if(fillCheckBox.isSelected())	//若有勾選填滿
				g.fillOval(x1, y1, width, height);
			else{	//沒勾選填滿
				g.setStroke(new BasicStroke((float)brushSizeInt));	//調整粗細
				g.drawOval(x1, y1, width, height);
			}
			break;
		case 3:	//矩形
			g.setColor(chooseColor);
			if(fillCheckBox.isSelected())
				g.fillRect(x1, y1, width, height);
			else{
				g.setStroke(new BasicStroke((float)brushSizeInt));	//調整粗細
				g.drawRect(x1, y1, width, height);
			}
			break;
		case 4:	//圓角矩形
			//g.setColor(colors[currentColor]);
			g.setColor(chooseColor);
			if(fillCheckBox.isSelected())
				g.fillRoundRect(x1, y1, width, height, 30, 30);
			else{
				g.setStroke(new BasicStroke((float)brushSizeInt));	//調整粗細
				g.drawRoundRect(x1, y1, width, height, 30, 30);
			}
			break;
		}
	}
}