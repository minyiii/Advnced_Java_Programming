import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;


public class NotPublisher extends JFrame{
	private Color blackBoardColor;	//�I����
	private JPanel upperPanel;	//�W�誺JPanel(����D�B�ɶ�)
	private final JLabel showTitle;	//���D
	private JLabel showTime;	//�ɶ�
	private String timeString;	//�ɶ���string���A
	private JTextArea showText;	//��������r�ϰ�
	private String textString;	//�spostSerializable����content
	private JLabel likeLabel;	//�R��
	private JLabel unlikeLabel;
	private JPanel likePanel;
	private boolean isLike;
	private PostSerializable postSerializable;
	
	public NotPublisher(){	//constructor
		super("���i�t��");
			
		postSerializable = ReadSequentialFile.returnPost();
		
		//�̤W�����ϰ�A�]�t���D�B�M�ɶ�
		upperPanel = new JPanel();
		Color blackBoardColor = new Color(127, 161, 110);	//�]�w�I���⪺RGB�N�X
		upperPanel.setBackground(blackBoardColor);
		upperPanel.setLayout(new GridLayout(2,1));
		
		// �W�誺���D�� //
		showTitle = new JLabel("�iJa�U��");
		showTitle.setFont(new Font(null, Font.BOLD, 24));	////�]�w�r���j�p
		showTitle.setForeground(Color.WHITE);	//�]�w�r���C��
		upperPanel.add(showTitle);
		
		// �W�誺�ɶ��ϰ� //
		timeString = postSerializable.getEditTime().toString();
		showTime = new JLabel(timeString);
		showTime.setForeground(Color.WHITE);
		upperPanel.add(showTime);
		
		// �N�̤W��ϰ��iJFrame���_ //
		add(upperPanel, BorderLayout.NORTH);
		
		// ��������r�ϰ� //
		textString = postSerializable.getContent();
		//System.out.println(textString);
		showText = new JTextArea(textString);
		showText.setEditable(false);	//�o�ˤ~����s��
		showText.setBackground(blackBoardColor);
		showText.setFont(new Font(null, Font.PLAIN, 15));
		showText.setForeground(Color.YELLOW);
		add(showText, BorderLayout.CENTER);
		
		// �U�誺�ϰ� //
		isLike = postSerializable.getIsLike();
		Icon unlikeIcon = new ImageIcon(getClass().getResource("unlike.png"));
		Icon likeIcon = new ImageIcon(getClass().getResource("like.png"));
		unlikeLabel = new JLabel("unlike", unlikeIcon, SwingConstants.LEFT);
		likeLabel = new JLabel("like", likeIcon, SwingConstants.LEFT);
		
		likePanel = new JPanel();
		
		if(postSerializable.getIsLike()){
			likePanel.add(likeLabel);
		}
		else
			likePanel.add(unlikeLabel);
		
		likePanel.addMouseListener(
			new MouseAdapter(){
				@Override
				public void mouseClicked(MouseEvent event){
					if(postSerializable.getIsLike()){	//1:like
						//isLike = false;
						postSerializable.setIsLike(false);
						likePanel.removeAll();
						likePanel.add(unlikeLabel);
						validate();
						repaint();
					}
					else{
						//isLike = true;
						postSerializable.setIsLike(true);
						likePanel.removeAll();
						likePanel.add(likeLabel);
						validate();
						repaint();
					}
					CreateSequentialFile.UpdatePost(postSerializable);	//���F��s�R��
				}
			}
		);
		likePanel.setBackground(Color.ORANGE);
		
		add(likePanel, BorderLayout.SOUTH);
		//System.out.println(postSerializable);
	}
}