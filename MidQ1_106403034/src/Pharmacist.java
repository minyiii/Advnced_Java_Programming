import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import java.io.ObjectOutputStream;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class Pharmacist extends JFrame{
	private final Color blackBoardColor = new Color(127, 161, 110);	//背景色
	private PostSerializable postSerializable;
	private JPanel upperPanel;			//最上方區域
	private JPanel titlePanel;			//最上方區域裡的標題區(含文字及JComboBox)
	private JLabel showTitle;			//最上方的Posts文字
	private JComboBox chooseTitle;		//選擇藥名
	private String postName;			//藥名(+.ser)
	private List<String> namesList;	
	private String[] titleNames;		//藥名
	private JLabel showTime;			//時間
	private String timeString;			//時間的string型態
	private JTextArea showText;			//中間的文字區域
	private String textString;			//存postSerializable中的content
	private final JPanel lowerPanel; 	//(正常)下方的JPanel
	private final JPanel ElowerPanel; 	//(編輯時)下方的JPanel
	private final JButton newButton;
	private final JButton saveButton;
	private final JButton saveAsButton;
	private final JButton importButton;
	private final JButton cancelButton;	//編輯時的panel	
	public Pharmacist() {	//constructor
		super("Pharmacist");
		titleNames = ReadTitle.returnTiles();
		for(String n:titleNames) {
			System.out.println(n);
		}
		namesList = Arrays.asList(titleNames);
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
		
		// 下方區域 //
		lowerPanel = new JPanel();
		newButton = new JButton("New");
		newButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent event){
				String newName = JOptionPane.showInputDialog("Enter filename");
				System.out.println(newName);
				//if(newName!=null) {
					namesList.add(newName);
					titleNames = (String[]) namesList.toArray();
					chooseTitle = new JComboBox<String>(titleNames);
					chooseTitle.setSelectedIndex(namesList.size());
					chooseTitle.setEditable(false);
					showText.setText(null);
					
					frameChange();
					if(showText.getText()==null){
					showText.setText(postSerializable.getContent());
					//}
				}	
			}
		});
		lowerPanel.add(newButton);
		add(lowerPanel, BorderLayout.SOUTH);
		
		// 編輯時的下方區域 //
		ElowerPanel = new JPanel();
		saveButton = new JButton("Save");
		ElowerPanel.add(saveButton);
		saveAsButton = new JButton("Save As");
		ElowerPanel.add(saveAsButton);
		importButton = new JButton("Import");
		importButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent event){
				JFileChooser fileChooser = new JFileChooser(new File("."));
				fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
				int IsOpen = fileChooser.showOpenDialog(Pharmacist.this);
				if(IsOpen == JFileChooser.APPROVE_OPTION){	//確認匯入
					showText.setText(ReadContent.readFromFile(fileChooser.getSelectedFile().toPath()));
				}
			}
		});
		ElowerPanel.add(importButton);
		
		cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent event){
				showText.setText(postSerializable.getContent());	//沒儲存所以文字就算有更動也沒用
				frameDefault();
			}
		});
		ElowerPanel.add(cancelButton);
		
		ElowerPanel.setBackground(Color.ORANGE);
	}
	
	
	// method //
	private void frameDefault(){
		showText.setEditable(false);				//這樣才不能編輯
		showText.setBackground(blackBoardColor);	//編輯貼文時的背景為白
		showText.setForeground(Color.YELLOW);		//編輯貼文時的字為黑
				
		//切換下方按鈕欄回原本的樣子
		lowerPanel.setVisible(true);
		ElowerPanel.setVisible(false);
		add(lowerPanel, BorderLayout.SOUTH);
		validate();
		repaint();
	}
	
	//下方按鈕欄切換成編輯狀態的樣子
	private void frameChange(){
		showText.setEditable(true);				//這樣才能編輯
		showText.setBackground(Color.WHITE);	//編輯貼文時的背景為白
		showText.setForeground(Color.BLACK);	//編輯貼文時的字為黑
			
		lowerPanel.setVisible(false);
		ElowerPanel.setVisible(true);
		add(ElowerPanel, BorderLayout.SOUTH);
		repaint();
	}
}
