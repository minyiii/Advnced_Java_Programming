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
	private final Color blackBoardColor = new Color(127, 161, 110);	//背景色
	private PostSerializable postSerializable;
	private JPanel upperPanel;			//最上方區域
	private JPanel titlePanel;			//最上方區域裡的標題區(含文字及JComboBox)
	private JLabel showTitle;			//最上方的Posts文字
	private JComboBox chooseTitle;		//選擇藥名
	private String postName;			//藥名(+.ser)
	private String[] titleNames;		//藥名
	private JLabel showTime;			//時間
	private String timeString;			//時間的string型態
	private JTextArea showText;			//中間的文字區域
	private String textString;			//存postSerializable中的content
	private final JPanel lowerPanel; 	//(正常)下方的JPanel
	
	public NotPharmacist(){	//constructor
		super("Not Pharmacist");
		titleNames = ReadTitle.returnTiles();
		for(String n:titleNames) {
			System.out.println(n);
		}
		postName = titleNames[0]+".ser";
		postSerializable = ReadPost.returnPost(postName);
		
		//最上面的區域，包含標題、和時間
		upperPanel = new JPanel();
		upperPanel.setBackground(blackBoardColor);
		upperPanel.setLayout(new GridLayout(2,1));
		
		// 上方的標題區 //
		titlePanel = new JPanel();
		titlePanel.setBackground(blackBoardColor);
		titlePanel.setLayout(new FlowLayout());
		
		showTitle = new JLabel("Posts");
		//showTitle.setFont(new Font(null, Font.BOLD, ));	////設定字的大小
		showTitle.setForeground(Color.WHITE);	//設定字的顏色
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
		
		// 上方的時間區域 //
		timeString = postSerializable.getEditTime().toString();
		showTime = new JLabel(timeString);
		showTime.setForeground(Color.WHITE);
		upperPanel.add(showTime);
				
		// 將最上方區域放進JFrame的北 //
		add(upperPanel, BorderLayout.NORTH);
		
		// 中間的文字區域 //
		textString = postSerializable.getContent();
		showText = new JTextArea(textString);
		showText.setEditable(false);	//這樣才不能編輯
		showText.setFont(new Font(null, Font.PLAIN, 15));
		showText.setForeground(Color.YELLOW);
		showText.setBackground(blackBoardColor);
				
		add(showText, BorderLayout.CENTER);
		
		lowerPanel = new JPanel();
		lowerPanel.setBackground(Color.ORANGE);
		
		add(lowerPanel, BorderLayout.SOUTH);
	}
}
