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
	private final Color blackBoardColor = new Color(127, 161, 110);	//�I����
	private PostSerializable postSerializable;
	private JPanel upperPanel;			//�̤W��ϰ�
	private JPanel titlePanel;			//�̤W��ϰ�̪����D��(�t��r��JComboBox)
	private JLabel showTitle;			//�̤W�誺Posts��r
	private JComboBox chooseTitle;		//����ĦW
	private String postName;			//�ĦW(+.ser)
	private List<String> namesList;	
	private String[] titleNames;		//�ĦW
	private JLabel showTime;			//�ɶ�
	private String timeString;			//�ɶ���string���A
	private JTextArea showText;			//��������r�ϰ�
	private String textString;			//�spostSerializable����content
	private final JPanel lowerPanel; 	//(���`)�U�誺JPanel
	private final JPanel ElowerPanel; 	//(�s���)�U�誺JPanel
	private final JButton newButton;
	private final JButton saveButton;
	private final JButton saveAsButton;
	private final JButton importButton;
	private final JButton cancelButton;	//�s��ɪ�panel	
	public Pharmacist() {	//constructor
		super("Pharmacist");
		titleNames = ReadTitle.returnTiles();
		for(String n:titleNames) {
			System.out.println(n);
		}
		namesList = Arrays.asList(titleNames);
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
		
		// �U��ϰ� //
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
		
		// �s��ɪ��U��ϰ� //
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
				if(IsOpen == JFileChooser.APPROVE_OPTION){	//�T�{�פJ
					showText.setText(ReadContent.readFromFile(fileChooser.getSelectedFile().toPath()));
				}
			}
		});
		ElowerPanel.add(importButton);
		
		cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent event){
				showText.setText(postSerializable.getContent());	//�S�x�s�ҥH��r�N�⦳��ʤ]�S��
				frameDefault();
			}
		});
		ElowerPanel.add(cancelButton);
		
		ElowerPanel.setBackground(Color.ORANGE);
	}
	
	
	// method //
	private void frameDefault(){
		showText.setEditable(false);				//�o�ˤ~����s��
		showText.setBackground(blackBoardColor);	//�s��K��ɪ��I������
		showText.setForeground(Color.YELLOW);		//�s��K��ɪ��r����
				
		//�����U����s��^�쥻���ˤl
		lowerPanel.setVisible(true);
		ElowerPanel.setVisible(false);
		add(lowerPanel, BorderLayout.SOUTH);
		validate();
		repaint();
	}
	
	//�U����s��������s�説�A���ˤl
	private void frameChange(){
		showText.setEditable(true);				//�o�ˤ~��s��
		showText.setBackground(Color.WHITE);	//�s��K��ɪ��I������
		showText.setForeground(Color.BLACK);	//�s��K��ɪ��r����
			
		lowerPanel.setVisible(false);
		ElowerPanel.setVisible(true);
		add(ElowerPanel, BorderLayout.SOUTH);
		repaint();
	}
}
