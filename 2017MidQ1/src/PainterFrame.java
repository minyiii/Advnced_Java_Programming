import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class PainterFrame extends JFrame{
	private final JPanel controlPanel;		//上方控制區
	private final JLabel posLabel;			//左上座標之JLabel
	private final JTextField inputX;		//輸入x座標
	private final JTextField inputY;		//輸入y座標
	private final JButton getPosButton;		//取點的button
	private boolean isGetPos;				//是否在取點中
	private boolean isSuccess;				//是否在取點成功
	private final JLabel LWLabel;			//圖形長寬之JLabel
	private final JTextField inputLength;	//輸入長
	private final JTextField inputWidth;	//輸入寬
	private final JButton drawButton;		//draw的button
	private final JLabel roundLWLabel;		//圓角長寬JLabel
	private final JTextField inputRLength;	//輸入圓角長
	private final JTextField inputRWidth;	//輸入圓角寬
	private final JButton deleteNewButton;	//刪最新圖形的button
	private final JPanel drawPanel;			//中間畫圖區
	private final JLabel mouseLabel;		//下方滑鼠座標位置區
	private String showPosition;			//下方顯示的滑鼠座標之字串
	private int xPos;	//滑鼠移動：x座標
	private int yPos;	//滑鼠移動：y座標
	
	public PainterFrame(){	//constructor
		super("小畫家");
		setLayout(new BorderLayout());
		GridBagConstraints c;
		
		// 上方控制區 //
		controlPanel = new JPanel();
		controlPanel.setLayout(new GridBagLayout());
		
		// 控制區：座標
		posLabel = new JLabel("左上座標：");
		c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 1;
		c.gridy = 0;
		c.gridwidth = 1;
		c.gridheight = 1;
		controlPanel.add(posLabel, c);
		
		inputX = new JTextField(10);
		c.gridx = 2;
		c.gridy = 0;
		c.gridwidth = 2;
		c.gridheight = 1;
		controlPanel.add(inputX, c);
		
		inputY = new JTextField(10);
		c.gridx = 4;
		c.gridy = 0;
		c.gridwidth = 2;
		c.gridheight = 1;
		controlPanel.add(inputY, c);
		
		// 控制區：取點
		isGetPos = false;		//一開始沒按，設為false，不是在取點
		isSuccess = false;
		getPosButton = new JButton("取點");
		getPosButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent event){
				//先跳視窗、mouseevent、跳視窗
				JOptionPane.showConfirmDialog(null, "開始取點", "訊息",JOptionPane.DEFAULT_OPTION,JOptionPane.INFORMATION_MESSAGE);
				isGetPos = true;	//是按了此button後，才允許讓click的結果當作座標
				/*if(isSuccess == true) {
					JOptionPane.showConfirmDialog(null, "取點成功", "訊息",JOptionPane.DEFAULT_OPTION,JOptionPane.INFORMATION_MESSAGE);
					isGetPos = false;
					isSuccess = false;
				}*/
			}
		});
		c.gridx = 8;
		c.gridy = 0;
		c.gridwidth = 4;
		c.gridheight = 1;
		controlPanel.add(getPosButton, c);
		
		// 控制區：圖形長寬
		LWLabel = new JLabel("圖形長寬：");
		c.gridx = 1;
		c.gridy = 1;
		c.gridwidth = 1;
		c.gridheight = 1;
		controlPanel.add(LWLabel, c);
		
		inputLength = new JTextField(10);
		c.gridx = 2;
		c.gridy = 1;
		c.gridwidth = 2;
		c.gridheight = 1;
		controlPanel.add(inputLength, c);
		
		inputWidth = new JTextField(10);
		c.gridx = 4;
		c.gridy = 1;
		c.gridwidth = 2;
		c.gridheight = 1;
		controlPanel.add(inputWidth, c);
		
		// 控制區：圓角長寬
		roundLWLabel = new JLabel("圓角長寬：");
		c.gridx = 7;
		c.gridy = 1;
		c.gridwidth = 1;
		c.gridheight = 1;
		controlPanel.add(roundLWLabel, c);
		
		inputRLength = new JTextField(10);
		c.gridx = 8;
		c.gridy = 1;
		c.gridwidth = 2;
		c.gridheight = 1;
		controlPanel.add(inputRLength, c);
		
		inputRWidth = new JTextField(10);
		c.gridx = 10;
		c.gridy = 1;
		c.gridwidth = 2;
		c.gridheight = 1;
		controlPanel.add(inputRWidth, c);
		
		// 控制區：draw button、刪除button
		drawButton = new JButton("Draw");
		drawButton.addActionListener(new DrawButtonHandler());
		c.gridx = 2;
		c.gridy = 2;
		c.gridwidth = 4;
		c.gridheight = 1;
		controlPanel.add(drawButton, c);
		
		deleteNewButton = new JButton("刪除最新圖形");
		deleteNewButton.addActionListener(new DeleteButtonHandler());
		c.gridx = 8;
		c.gridy = 2;
		c.gridwidth = 4;
		c.gridheight = 1;
		controlPanel.add(deleteNewButton, c);
		
		
		add(controlPanel, BorderLayout.NORTH);
		
		//中間畫圖區
		drawPanel = new JPanel();
		drawPanel.setBackground(Color.WHITE);
		drawPanel.addMouseListener(new MouseHandler());
		drawPanel.addMouseMotionListener(new MouseMotionHandler());
		add(drawPanel, BorderLayout.CENTER);
		
		//下方滑鼠座標位置區
		mouseLabel = new JLabel();
		add(mouseLabel, BorderLayout.SOUTH);
	}
	
	// class MouseHandler //
	private class MouseHandler extends MouseAdapter{
		@Override
		public void mouseClicked(MouseEvent event) {
			//isGetPos的意義：如果是按下取點扭，才會允許click的座標當成結果
			if(isGetPos==true) {
				inputX.setText(Integer.toString(event.getX()));	//int轉String
				inputY.setText(Integer.toString(event.getY()));	//int轉String
				//isSuccess = true;	//真的有取點才能繼續跳出下個視窗
				JOptionPane.showConfirmDialog(null, "取點成功", "訊息",JOptionPane.DEFAULT_OPTION,JOptionPane.INFORMATION_MESSAGE);
				isGetPos = false;
			}
		}
	}
	
	// class MouseMotionHandler //
	private class MouseMotionHandler extends MouseMotionAdapter {
		@Override
		public void mouseMoved(MouseEvent event) {
			showPosition = String.format("游標位置： (%d, %d)", event.getX(), event.getY());
			mouseLabel.setText(showPosition);
		}
	}
	
	// class DrawButtonHandler //
	private class DrawButtonHandler implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent event) {
			System.out.println("click drawButton");
			try {
				int startX = Integer.parseInt(inputX.getText());
				int startY = Integer.parseInt(inputY.getText());
				int L = Integer.parseInt(inputLength.getText());
				int W = Integer.parseInt(inputWidth.getText());
				int RL = Integer.parseInt(inputRLength.getText());
				int WL = Integer.parseInt(inputWidth.getText());
				
				System.out.println("input ok");
				Graphics2D g = (Graphics2D)drawPanel.getGraphics();
				g.setStroke(new BasicStroke((float)5));
				g.drawRoundRect(startX, startY, L, W, RL, WL);
				
				/*if( inputX.getText()!=null && inputY.getText()!=null &&
				checkPositiveInt(inputLength) && checkPositiveInt(inputWidth) &&
				checkPositiveInt(inputRLength) && checkPositiveInt(inputRWidth))
				{	//確認各欄位是否輸入正確
						System.out.println("input ok");
						Graphics2D g = (Graphics2D)drawPanel.getGraphics();
						g.drawRoundRect(, , width, height, 30, 30);
				}*/
			}
			catch(NumberFormatException numberFormatException) {
				JOptionPane.showConfirmDialog(null, "請確定所有欄位都有填入數字", "訊息",JOptionPane.DEFAULT_OPTION,JOptionPane.INFORMATION_MESSAGE);
			}
		}
	}
	
	// class DeleteButtonHandler //
	private class DeleteButtonHandler implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent event) {
			System.out.println("click delete btn");
		}
	}
	
	private boolean checkPositiveInt(JTextField field) {
		if(field.getText()!=null && Integer.parseInt(field.getText())>0) {
			return true;
		}
		else
			return false;
	}
	
	/*private getPos() {
		
	}*/
}
