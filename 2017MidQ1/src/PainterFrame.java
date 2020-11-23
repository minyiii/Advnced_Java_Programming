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
	private final JPanel controlPanel;		//�W�豱���
	private final JLabel posLabel;			//���W�y�Ф�JLabel
	private final JTextField inputX;		//��Jx�y��
	private final JTextField inputY;		//��Jy�y��
	private final JButton getPosButton;		//���I��button
	private boolean isGetPos;				//�O�_�b���I��
	private boolean isSuccess;				//�O�_�b���I���\
	private final JLabel LWLabel;			//�ϧΪ��e��JLabel
	private final JTextField inputLength;	//��J��
	private final JTextField inputWidth;	//��J�e
	private final JButton drawButton;		//draw��button
	private final JLabel roundLWLabel;		//�ꨤ���eJLabel
	private final JTextField inputRLength;	//��J�ꨤ��
	private final JTextField inputRWidth;	//��J�ꨤ�e
	private final JButton deleteNewButton;	//�R�̷s�ϧΪ�button
	private final JPanel drawPanel;			//�����e�ϰ�
	private final JLabel mouseLabel;		//�U��ƹ��y�Ц�m��
	private String showPosition;			//�U����ܪ��ƹ��y�Ф��r��
	private int xPos;	//�ƹ����ʡGx�y��
	private int yPos;	//�ƹ����ʡGy�y��
	
	public PainterFrame(){	//constructor
		super("�p�e�a");
		setLayout(new BorderLayout());
		GridBagConstraints c;
		
		// �W�豱��� //
		controlPanel = new JPanel();
		controlPanel.setLayout(new GridBagLayout());
		
		// ����ϡG�y��
		posLabel = new JLabel("���W�y�СG");
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
		
		// ����ϡG���I
		isGetPos = false;		//�@�}�l�S���A�]��false�A���O�b���I
		isSuccess = false;
		getPosButton = new JButton("���I");
		getPosButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent event){
				//���������Bmouseevent�B������
				JOptionPane.showConfirmDialog(null, "�}�l���I", "�T��",JOptionPane.DEFAULT_OPTION,JOptionPane.INFORMATION_MESSAGE);
				isGetPos = true;	//�O���F��button��A�~���\��click�����G��@�y��
				/*if(isSuccess == true) {
					JOptionPane.showConfirmDialog(null, "���I���\", "�T��",JOptionPane.DEFAULT_OPTION,JOptionPane.INFORMATION_MESSAGE);
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
		
		// ����ϡG�ϧΪ��e
		LWLabel = new JLabel("�ϧΪ��e�G");
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
		
		// ����ϡG�ꨤ���e
		roundLWLabel = new JLabel("�ꨤ���e�G");
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
		
		// ����ϡGdraw button�B�R��button
		drawButton = new JButton("Draw");
		drawButton.addActionListener(new DrawButtonHandler());
		c.gridx = 2;
		c.gridy = 2;
		c.gridwidth = 4;
		c.gridheight = 1;
		controlPanel.add(drawButton, c);
		
		deleteNewButton = new JButton("�R���̷s�ϧ�");
		deleteNewButton.addActionListener(new DeleteButtonHandler());
		c.gridx = 8;
		c.gridy = 2;
		c.gridwidth = 4;
		c.gridheight = 1;
		controlPanel.add(deleteNewButton, c);
		
		
		add(controlPanel, BorderLayout.NORTH);
		
		//�����e�ϰ�
		drawPanel = new JPanel();
		drawPanel.setBackground(Color.WHITE);
		drawPanel.addMouseListener(new MouseHandler());
		drawPanel.addMouseMotionListener(new MouseMotionHandler());
		add(drawPanel, BorderLayout.CENTER);
		
		//�U��ƹ��y�Ц�m��
		mouseLabel = new JLabel();
		add(mouseLabel, BorderLayout.SOUTH);
	}
	
	// class MouseHandler //
	private class MouseHandler extends MouseAdapter{
		@Override
		public void mouseClicked(MouseEvent event) {
			//isGetPos���N�q�G�p�G�O���U���I��A�~�|���\click���y�з����G
			if(isGetPos==true) {
				inputX.setText(Integer.toString(event.getX()));	//int��String
				inputY.setText(Integer.toString(event.getY()));	//int��String
				//isSuccess = true;	//�u�������I�~���~����X�U�ӵ���
				JOptionPane.showConfirmDialog(null, "���I���\", "�T��",JOptionPane.DEFAULT_OPTION,JOptionPane.INFORMATION_MESSAGE);
				isGetPos = false;
			}
		}
	}
	
	// class MouseMotionHandler //
	private class MouseMotionHandler extends MouseMotionAdapter {
		@Override
		public void mouseMoved(MouseEvent event) {
			showPosition = String.format("��Ц�m�G (%d, %d)", event.getX(), event.getY());
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
				{	//�T�{�U���O�_��J���T
						System.out.println("input ok");
						Graphics2D g = (Graphics2D)drawPanel.getGraphics();
						g.drawRoundRect(, , width, height, 30, 30);
				}*/
			}
			catch(NumberFormatException numberFormatException) {
				JOptionPane.showConfirmDialog(null, "�нT�w�Ҧ���쳣����J�Ʀr", "�T��",JOptionPane.DEFAULT_OPTION,JOptionPane.INFORMATION_MESSAGE);
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
