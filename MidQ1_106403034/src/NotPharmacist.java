import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class NotPharmacist extends JFrame{
	private final Color blackBoardColor = new Color(127, 161, 110);	//�I����
	private PostSerializable postSerializable;
	private JPanel upperPanel;			//�̤W��ϰ�
	private JPanel titlePanel;			//�̤W��ϰ�̪����D��(�t��r��JComboBox)
	private JLabel showTitle;			//�̤W�誺Posts��r
	private JComboBox chooseTitle;		//����ĦW
	private String postName;			//�ĦW(+.ser)
	private String[] titleNames;		//�ĦW
	private JLabel showTime;			//�ɶ�
	private String timeString;			//�ɶ���string���A
	private JTextArea showText;			//��������r�ϰ�
	private String textString;			//�spostSerializable����content
	private final JPanel lowerPanel; 	//(���`)�U�誺JPanel
	
	public NotPharmacist(){	//constructor
		super("Not Pharmacist");
		titleNames = ReadTitle.returnTiles();
		for(String n:titleNames) {
			System.out.println(n);
		}
		postName = titleNames[0]+".ser";
		postSerializable = ReadPost.returnPost(postName);
		
		//�̤W�����ϰ�A�]�t���D�B�M�ɶ�
		upperPanel = new JPanel();
		upperPanel.setBackground(blackBoardColor);
		upperPanel.setLayout(new GridLayout(2,1));
		
		// �W�誺���D�� //
		titlePanel = new JPanel();
		titlePanel.setBackground(blackBoardColor);
		titlePanel.setLayout(new FlowLayout());
		
		showTitle = new JLabel("Posts");
		//showTitle.setFont(new Font(null, Font.BOLD, ));	////�]�w�r���j�p
		showTitle.setForeground(Color.WHITE);	//�]�w�r���C��
		titlePanel.add(showTitle);
		
		chooseTitle = new JComboBox<String>(titleNames);
		chooseTitle.addItemListener(
			new ItemListener() 
			{
				@Override
				public void itemStateChanged(ItemEvent event) {
					if(event.getStateChange()==ItemEvent.SELECTED) {
						postName = String.format("%s.ser", titleNames[chooseTitle.getSelectedIndex()]);
						System.out.println(postName);
						postSerializable = ReadPost.returnPost(postName);
						timeString = postSerializable.getEditTime().toString();
						showTime.setText(timeString);
						textString = postSerializable.getContent();
						showText.setText(textString);
					}
				}
			});
		titlePanel.add(chooseTitle);
		upperPanel.add(titlePanel);
		
		// �W�誺�ɶ��ϰ� //
		timeString = postSerializable.getEditTime().toString();
		showTime = new JLabel(timeString);
		showTime.setForeground(Color.WHITE);
		upperPanel.add(showTime);
				
		// �N�̤W��ϰ��iJFrame���_ //
		add(upperPanel, BorderLayout.NORTH);
		
		// ��������r�ϰ� //
		textString = postSerializable.getContent();
		showText = new JTextArea(textString);
		showText.setEditable(false);	//�o�ˤ~����s��
		showText.setFont(new Font(null, Font.PLAIN, 15));
		showText.setForeground(Color.YELLOW);
		showText.setBackground(blackBoardColor);
				
		add(showText, BorderLayout.CENTER);
		
		lowerPanel = new JPanel();
		lowerPanel.setBackground(Color.ORANGE);
		
		add(lowerPanel, BorderLayout.SOUTH);
	}
}
