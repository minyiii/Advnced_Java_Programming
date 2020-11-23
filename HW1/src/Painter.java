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
	int x1,y1,x2,y2;	//�_�I�y�СA���I�y��
	private final ArrayList<Point> points = new ArrayList();	//drag�쪺�I����o
	private String details;	//���A�C��r
	
	private int currentX,currentY;
	
	//Tool
	private final JPanel comboBoxPanel;
	private final JPanel radioButtonPanel;
	private final JComboBox<String> comboBox;	//ø�Ϥu��comboBox
	private final JLabel toolLabel;	//"ø�Ϥu��"
	private static final String[] drawTools = {"����","���u","����","�x��","�ꨤ�x��"};	//comboBox�ﶵ
	private int selectedTool;	//�u�����
	private int brushSizeInt=5;	//default(�p����)
	private final JRadioButton smallSize;	//����j�p�ﶵ(�p)
	private final JRadioButton mediumSize;	//����j�p�ﶵ(��)
	private final JRadioButton bigSize;	//����j�p�ﶵ(�j)
	private final ButtonGroup brushSize; //����j�p��RadioButton�զX
	private final JCheckBox fillCheckBox;	//��
	private final JButton colorButton;	//�����C��button
	private int currentColor = 0;	//�ثe�C�⪺���ޭ�
	private final Color[] colors = {Color.BLACK, Color.BLUE, Color.RED, Color.YELLOW};
	private final JButton clearButton;	//�M���e��button	
	private final JLabel brushLabel;	//"����j�p"
	
	
	public Painter(){	//constructor
		super("�p�e�a");
		JOptionPane.showConfirmDialog(null, "Welcome", "�T��",JOptionPane.DEFAULT_OPTION,JOptionPane.INFORMATION_MESSAGE);	//�ﻫ�T��
		
		setLayout(new BorderLayout());
		
		///// �u��C /////
		toolPanel = new JPanel();
		add(toolPanel, BorderLayout.NORTH);
		
		// �u��C-�u����
		comboBoxPanel = new JPanel();
		toolLabel = new JLabel("ø�Ϥu��");
		comboBoxPanel.add(toolLabel,BorderLayout.NORTH);
		comboBox = new JComboBox<String>(drawTools);
		comboBox.setMaximumRowCount(3);
		comboBox.addItemListener(
			new ItemListener(){
				@Override
				public void itemStateChanged(ItemEvent event){
					selectedTool = comboBox.getSelectedIndex();	//������ﶵ�����ޭ�
					if(event.getStateChange()==ItemEvent.SELECTED){
						String showTool = (String)event.getItem();
						System.out.println("��� " + showTool);
					}
				}
			}	
		);
		comboBoxPanel.add(comboBox,BorderLayout.SOUTH);
		toolPanel.add(comboBoxPanel);
		
		//�u��C-����j�p
		radioButtonPanel = new JPanel();
		brushLabel = new JLabel("����j�p");
		radioButtonPanel.add(brushLabel,BorderLayout.NORTH);
						
		smallSize = new JRadioButton("�p",true);
		mediumSize = new JRadioButton("��",false);
		bigSize = new JRadioButton("�j",false);
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
				
		//�u��C-��(JCheckBox)
		fillCheckBox = new JCheckBox("��");
		fillCheckBox.addItemListener(new ItemListener(){
			@Override
			public void itemStateChanged(ItemEvent event){
				String showFill = fillCheckBox.getActionCommand();
				if(fillCheckBox.isSelected())
					System.out.println("��� "+showFill);
				else
					System.out.println("���� "+showFill);
			}
		});
		toolPanel.add(fillCheckBox);
				
		//�u��C-�C��button
		colorButton = new JButton("�����C��");
		colorButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent event){
				String showButton = event.getActionCommand();
				System.out.println("�I�� " + showButton);
				if(currentColor >= colors.length-1)
					currentColor=0;
				else
					currentColor ++;
			}
		});
		toolPanel.add(colorButton);
		//�u��C-�M��button
		clearButton = new JButton("�M���e��");
		clearButton.addActionListener(
			new ActionListener(){
				@Override
				public void actionPerformed(ActionEvent event){
					Graphics g = drawPanel.getGraphics();
					String showButton = event.getActionCommand();
					System.out.println("�I�� " + showButton);
					//g.clearRect(0,0,drawPanel.getWidth(),drawPanel.getHeight());
					//drawPanel.removeAll();
					repaint();
					//drawPanel.setBackground(Color.WHITE);
				}
			}
		);
		toolPanel.add(clearButton);
		add(toolPanel, BorderLayout.NORTH);
			
		//// �e���� /////
		drawPanel = new DrawPanel();
		drawPanel.addMouseListener(new MouseHandler());
		drawPanel.addMouseMotionListener(new MouseMoveHandler());
		//drawPanel.setSize(850, 400);
		
		add(drawPanel, BorderLayout.CENTER);
		
		///// ���A�C /////
		statusBar = new JLabel("���ЫD�d��");
		add(statusBar, BorderLayout.SOUTH);
	}
	
	///// RadioButtonHandler /////
	private class RadioButtonHandler implements ItemListener{
		@Override
		public void itemStateChanged(ItemEvent event){
			if(smallSize.isSelected()){
				brushSizeInt = 5;
				System.out.println("��� �p ����");
			}
			else if(mediumSize.isSelected()){
				brushSizeInt = 10;
				System.out.println("��� �� ����");
			}
			else if(bigSize.isSelected()){
				brushSizeInt = 15;
				System.out.println("��� �j ����");
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
	private class MouseMoveHandler extends MouseMotionAdapter{	//�ƹ����ʷ|��ܮy��
		@Override
		public void mouseMoved(MouseEvent event){
			int xPos = event.getX();
			int yPos = event.getY();
			details = String.format("���Ц�m:(%d, %d)", xPos, yPos);
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
		public void mousePressed(MouseEvent event){	//���U�h�S��}��=>�_�I
			x1 = event.getX();
			y1 = event.getY();
		}
		@Override
		public void mouseReleased(MouseEvent event){	//���U�é즲�A��}��
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
		/////�U
		/*
		for(Point point:points){
			g.fillOval(point.x, point.y, brushSizeInt, brushSizeInt);
		}*/
		////�W
		g.fillOval(currentX, currentY, brushSizeInt, brushSizeInt);
	}
	
	///// Function drawShape /////
	private void drawShape(){
		Graphics g = drawPanel.getGraphics();
		int width = Math.abs(x2-x1);
		int height = Math.abs(y2-y1);
		
		switch (selectedTool){
		case 0:	//����
			break;
		case 1:	//���u
			g.setColor(colors[currentColor]);
			g.drawLine(x1, y1, x2, y2);
			break;
		case 2:	//���
			g.setColor(colors[currentColor]);
			if(fillCheckBox.isSelected())	//�Y���Ŀ��
				g.fillOval(x1, y1, width, height);
			else	//�S�Ŀ�
				g.drawOval(x1, y1, width, height);
			break;
		case 3:	//�x��
			g.setColor(colors[currentColor]);
			if(fillCheckBox.isSelected())
				g.fillRect(x1, y1, width, height);
			else
				g.drawRect(x1, y1, width, height);
			break;
		case 4:	//�ꨤ�x��
			g.setColor(colors[currentColor]);
			if(fillCheckBox.isSelected())
				g.fillRoundRect(x1, y1, width, height, 30, 30);
			else
				g.drawRoundRect(x1, y1, width, height, 30, 30);
			break;
		}
	}
}


