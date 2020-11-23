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
	private Color blackBoardColor;	//背景色
	private JPanel upperPanel;	//上方的JPanel(放標題、時間)
	private final JLabel showTitle;	//標題
	private JLabel showTime;	//時間
	private String timeString;	//時間的string型態
	private JTextArea showText;	//中間的文字區域
	private String textString;	//存postSerializable中的content
	private JLabel likeLabel;	//愛心
	private JLabel unlikeLabel;
	private JPanel likePanel;
	private boolean isLike;
	private PostSerializable postSerializable;
	
	public NotPublisher(){	//constructor
		super("公告系統");
			
		postSerializable = ReadSequentialFile.returnPost();
		
		//最上面的區域，包含標題、和時間
		upperPanel = new JPanel();
		Color blackBoardColor = new Color(127, 161, 110);	//設定背景色的RGB代碼
		upperPanel.setBackground(blackBoardColor);
		upperPanel.setLayout(new GridLayout(2,1));
		
		// 上方的標題區 //
		showTitle = new JLabel("進Ja助教");
		showTitle.setFont(new Font(null, Font.BOLD, 24));	////設定字的大小
		showTitle.setForeground(Color.WHITE);	//設定字的顏色
		upperPanel.add(showTitle);
		
		// 上方的時間區域 //
		timeString = postSerializable.getEditTime().toString();
		showTime = new JLabel(timeString);
		showTime.setForeground(Color.WHITE);
		upperPanel.add(showTime);
		
		// 將最上方區域放進JFrame的北 //
		add(upperPanel, BorderLayout.NORTH);
		
		// 中間的文字區域 //
		textString = postSerializable.getContent();
		//System.out.println(textString);
		showText = new JTextArea(textString);
		showText.setEditable(false);	//這樣才不能編輯
		showText.setBackground(blackBoardColor);
		showText.setFont(new Font(null, Font.PLAIN, 15));
		showText.setForeground(Color.YELLOW);
		add(showText, BorderLayout.CENTER);
		
		// 下方的區域 //
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
					CreateSequentialFile.UpdatePost(postSerializable);	//為了更新愛心
				}
			}
		);
		likePanel.setBackground(Color.ORANGE);
		
		add(likePanel, BorderLayout.SOUTH);
		//System.out.println(postSerializable);
	}
}