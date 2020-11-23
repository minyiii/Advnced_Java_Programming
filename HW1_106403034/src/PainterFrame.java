//�m�W:Ĭ�өy �Ǹ�:106403034 �t��:��ޤTB
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
	private final JPanel toolPanel;	//�u���
	private final JPanel drawPanel;	//�e���� 
	private final JLabel statusBar;	//���A�C
	private String details;	//���A�C��r
	int x1,y1,x2,y2;	//�e�ϧΥΪ��_�I�y��(x1,y1)�A���I�y��(x2,y2)
	private int currentX, currentY;	//�ƹ�drag��U���y��
	private int currentColor = 0;	//��U���C��A�w�]���¦�(�U��colors�}�C���ޭ�0)
	private final Color[] colors = {Color.BLACK, Color.BLUE, Color.RED, Color.YELLOW};
	
	private final JPanel comboBoxPanel;
	private final JPanel radioButtonPanel;
	private final JLabel toolLabel;	//"ø�Ϥu��"
	private final JComboBox<String> comboBox;	//ø�Ϥu��comboBox
	private static final String[] drawTools = {"����","���u","����","�x��","�ꨤ�x��"};	//comboBox�ﶵ
	private int selectedTool;	//�u�����
	private final JLabel brushLabel;	//"����j�p"
	private final JRadioButton smallSize;	//����j�p�ﶵ(�p)
	private final JRadioButton mediumSize;	//����j�p�ﶵ(��)
	private final JRadioButton bigSize;	//����j�p�ﶵ(�j)
	private final ButtonGroup brushSize; //����j�p��RadioButton�զX
	private int brushSizeInt=5;	//default(�p����)
	private final JCheckBox fillCheckBox;	//��
	private final JButton colorButton;	//�����C��button
	private final JButton clearButton;	//�M���e��button
	
	public PainterFrame(){	//constructor
		super("�p�e�a");
		JOptionPane.showConfirmDialog(null, "Welcome", "�T��",JOptionPane.DEFAULT_OPTION,JOptionPane.INFORMATION_MESSAGE);	//�ﻫ�T��
		
		///// �u��C /////
		toolPanel = new JPanel();	//�u��C
		toolPanel.setLayout(new FlowLayout());
		//�u��C-�u����
		comboBoxPanel = new JPanel();
		toolLabel = new JLabel("ø�Ϥu��");
		comboBoxPanel.add(toolLabel,BorderLayout.NORTH);
		comboBox = new JComboBox<String>(drawTools);
		comboBox.setMaximumRowCount(3);	//�@���̦h��ܤT�ӿﶵ
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
				
		//�u��C-���C��button
		colorButton = new JButton("�����C��");
		colorButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent event){
				String showButton = event.getActionCommand();
				System.out.println("�I�� " + showButton);
				//�C��1�U���ޭȷ|+1�ܴ��C��A��̫�@�ӫ�|�^����ޭ�0�~��
				if(currentColor >= colors.length-1)
					currentColor=0;
				else
					currentColor ++;
			}
		});
		toolPanel.add(colorButton);
		
		//�u��C-�M�e��button
		clearButton = new JButton("�M���e��");
		clearButton.addActionListener(
			new ActionListener(){
				@Override
				public void actionPerformed(ActionEvent event){
					Graphics g = drawPanel.getGraphics();
					String showButton = event.getActionCommand();
					System.out.println("�I�� " + showButton);
					repaint();	//�M�e��
				}
			}
		);
		toolPanel.add(clearButton);
		add(toolPanel, BorderLayout.NORTH);
		
		//�e����
		drawPanel = new JPanel();
		drawPanel.setBackground(Color.WHITE);	//�e�����խI��
		drawPanel.addMouseListener(new MouseHandler());
		drawPanel.addMouseMotionListener(new MouseMoveHandler());
		add(drawPanel, BorderLayout.CENTER);
		
		//���A�CJPanel
		statusBar = new JLabel("���ЫD�d��");
		add(statusBar, BorderLayout.SOUTH);
	}
	
	// class RadioButtonHandler //
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
	
	// class MouseHandler //
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
	
	// class MouseMoveHandler //
	private class MouseMoveHandler extends MouseMotionAdapter{
		@Override
		public void mouseMoved(MouseEvent event){	//�ƹ����ʷ|��ܮy��
			int xPos = event.getX();
			int yPos = event.getY();
			details = String.format("���Ц�m:(%d, %d)", xPos, yPos);
			statusBar.setText(details);
		}
		@Override
		public void mouseDragged(MouseEvent event){	//�e����ΡA�䲾����O��U��m�õe���(drawBrush())
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
