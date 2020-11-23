import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.Date;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;



public class Publisher extends JFrame{
	private final Color blackBoardColor = new Color(127, 161, 110);	//�I����
	private JPanel upperPanel;				//�W�誺JPanel(����D�B�ɶ�)
	private final JLabel showTitle;			//���D
	private JLabel showTime;				//�ɶ�
	private String timeString;				//�ɶ���string���A
	//private JPanel midPanel;				//������JPanel
	private JTextArea showText;				//��������r�ϰ�
	private String textString;				//�spostSerializable����content
	private JLabel likeLabel;				//���R�߮ɪ�JLabel(�t�Ϥ�"like.png"�B��r"like")
	private JLabel unlikeLabel;				//�S�R�߮ɪ�JLabel(�t�Ϥ�"unlike.png"�B��r"unlike")
	//private JPanel likePanel;				//��R��JLabel��JPanel
	private boolean isLike;					//�ݦ��S�����R��
	private final JPanel lowerPanel; 		//(���`)�U�誺JPanel
	private final JButton editJButton;		//�s����s
	private final JButton newPostJButton;	//�s�W�K����s
	private JPanel ELowerPanel;				//(�s��K���)�U�誺JPanel
	private final JButton saveJButton;		//�x�s ���s
	private final JButton saveAsJButton;	//�t�s�s�� ���s
	private final JButton importJButton;	//�פJ���e ���s
	private final JButton cancelJButton;	//���� ���s
	private boolean fromEditButton;			//1���qedit�i�J�A0���q���s�K��i�J
	private PostSerializable postSerializable;
	
	public Publisher(){
		super("���i�t��");
		//final PostSerializable postSerializable = ReadSequentialFile.returnPost();
		postSerializable = ReadSequentialFile.returnPost();
		
		//�̤W�����ϰ�A�]�t���D�B�M�ɶ�
		upperPanel = new JPanel();
		//Color blackBoardColor = new Color(127, 161, 110);	//�]�w�I���⪺RGB�N�X
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
		//midPanel = new JPanel();
		textString = postSerializable.getContent();
		//System.out.println(textString);
		showText = new JTextArea(textString);
		showText.setEditable(false);	//�o�ˤ~����s��
		showText.setFont(new Font(null, Font.PLAIN, 15));
		showText.setForeground(Color.YELLOW);
		showText.setBackground(blackBoardColor);
		
		add(showText);
		/*midPanel.add(showText, SwingConstants.LEFT);
		midPanel.setBackground(blackBoardColor);
		add(midPanel, BorderLayout.CENTER);*/
		
		/// �U�誺�ϰ� ///
		// ���` //
		lowerPanel = new JPanel();
		lowerPanel.setLayout(new BorderLayout());
		
		// �U-�R�� //
		isLike = postSerializable.getIsLike();
		Icon unlikeIcon = new ImageIcon(getClass().getResource("unlike.png"));
		Icon likeIcon = new ImageIcon(getClass().getResource("like.png"));
		unlikeLabel = new JLabel("unlike", unlikeIcon, SwingConstants.LEFT);
		likeLabel = new JLabel("like", likeIcon, SwingConstants.LEFT);
		unlikeLabel.setEnabled(false);
		likeLabel.setEnabled(false);
		//likePanel = new JPanel();
		
		likeChange(isLike);
		
		/*if(isLike){
			likeLabel.setEnabled(false);	//�]��������A�|�Ϧ�
			lowerPanel.add(likeLabel);
		}
		else{
			likeLabel.setEnabled(false);	//�]��������A�|�Ϧ�
			lowerPanel.add(unlikeLabel);
		}*/
		
		//lowerPanel.add(likePanel);
		
		// �U-�s����s //
		EButtonHandler editHandler = new EButtonHandler();
		editJButton = new JButton("�s��");
		editJButton.addActionListener(editHandler);
		
		lowerPanel.add(editJButton, BorderLayout.CENTER);
		
		// �U-�s�K�� //
		newPostJButton = new JButton("���s�K��");
		newPostJButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent event){
				fromEditButton=false;
				showText.setText(null);
				frameChange();
				if(showText.getText()!=null){	//���諸��
					/*postSerializable.setIsLike(false);
					likeChange(postSerializable.getIsLike());*/
					
					System.out.println("���s�K�� ����");
				}
				else{
					showText.setText(postSerializable.getContent());
				}
				
			}
		});
		lowerPanel.add(newPostJButton, BorderLayout.EAST);
		
		// �U-��l�]�w(�I���B�[�iJFrame) //
		lowerPanel.setBackground(Color.ORANGE);		
		add(lowerPanel, BorderLayout.SOUTH);
		
		// �s��K��� //
		ELowerPanel = new JPanel();
		
		// �x�s //
		saveJButton = new JButton("�x�s");
		saveJButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent event){
				//���o���᪺�r��η�U�ɶ��A�ó]�w�^�h
				String newText = showText.getText();
				postSerializable.setContent(newText);
				postSerializable.setEditTime(new Date());
				showTime.setText(postSerializable.getEditTime().toString());
				
				if(!fromEditButton){	//���O�q�s����s�ӡA�N��O�q���s�K����J
					postSerializable.setIsLike(false);			//���s�K��S���L�R��
					likeChange(postSerializable.getIsLike());	//���s�]�w�R�ߪ��A
				}
				CreateSequentialFile.UpdatePost(postSerializable);
				
				frameDefault();
			}
		});
		ELowerPanel.add(saveJButton);
		
		// �t�s�s�� //
		saveAsJButton = new JButton("�t�s�s��");
		saveAsJButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent event){
				//System.out.println("�t�s");
				JFileChooser fileChooser = new JFileChooser(new File("."));	//�w�]����e��m
				fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);	//�t�s�s�ɥu��s���
				int IsSaveAs = fileChooser.showSaveDialog(Publisher.this);	//����x�s������
				if(IsSaveAs==JFileChooser.APPROVE_OPTION){		//�Y�I�F�x�s
					File file = fileChooser.getSelectedFile();
					/*�U�褣�i������postSerializable���ܼƶǤJ�A�]���ɪ�postSerializable�����U�s��Υ��s�K��
					 * "���e"�����p�C�Y������postSerializable�ǤJ�A�i�J�s��Υ��s�K�媬�A��Ұ�����ʱN���|�Q�O���i�h�A
					 *	�G�ګŧi�t�@�ӫ��A�@�ˬOPostSerializable���ܼ�savaAsPost�A�ӰO��"��U"����r���e�B
					 *  ���L���R��(�R�߶i�J�s�説�A���¤����A�G��postSerializable��)�B�s��ɶ�(��U�s�ɪ��ɶ�)�A
					 *  �̫�⥦��ѼƶǶiCreateSequentialFile.SavaAs�N�n�C
					 */
					PostSerializable savaAsPost = new PostSerializable(
						showText.getText(), postSerializable.getIsLike(), new Date());
					CreateSequentialFile.SavaAs(file.toPath(), savaAsPost);
				}
			}
		});
		ELowerPanel.add(saveAsJButton);
		
		// �פJ //
		importJButton = new JButton("�פJ");
		importJButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent event){
				JFileChooser fileChooser = new JFileChooser(new File("."));
				fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
				int IsOpen = fileChooser.showOpenDialog(Publisher.this);
				if(IsOpen == JFileChooser.APPROVE_OPTION){	//�T�{�פJ
					/* ���B��t�s�s�ɤ@�ˤ��ઽ���Υ~����postSerializable�A�]�פJ��A�ϥΪ̤]���i��������A
					 * �K�夺�e�N�|�ܦ^�쥻�פJ�e���ˤl�A�Y�������~���N�S��^��W�@�B�C
					 */
					PostSerializable importPost = ReadSequentialFile.importOther(fileChooser.getSelectedFile().toPath());
					showTime.setText(importPost.getEditTime().toString());
					showText.setText(importPost.getContent());
					likeChange(importPost.getIsLike());
				}
			}
		});
		ELowerPanel.add(importJButton);
		
		// ���� //
		cancelJButton = new JButton("����");
		cancelJButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent event){
				showText.setText(postSerializable.getContent());	//�S�x�s�ҥH��r�N�⦳��ʤ]�S��
				frameDefault();
			}
		});
		ELowerPanel.add(cancelJButton);
		
		// ��l�]�w(�C��) //
		ELowerPanel.setBackground(Color.ORANGE);
		
	}
	// method //
	private void frameDefault(){
			showText.setEditable(false);				//�o�ˤ~����s��
			showText.setBackground(blackBoardColor);	//�s��K��ɪ��I������
			showText.setForeground(Color.YELLOW);		//�s��K��ɪ��r����
			
			lowerPanel.setVisible(true);
			ELowerPanel.setVisible(false);
			add(lowerPanel, BorderLayout.SOUTH);
			validate();
			repaint();
	}
	
	private void frameChange(){
		showText.setEditable(true);				//�o�ˤ~��s��
		showText.setBackground(Color.WHITE);	//�s��K��ɪ��I������
		showText.setForeground(Color.BLACK);	//�s��K��ɪ��r����
		
		lowerPanel.setVisible(false);
		ELowerPanel.setVisible(true);
		add(ELowerPanel, BorderLayout.SOUTH);
		repaint();
	}
	
	private void likeChange(boolean like){
		if(like){
			/*likeLabel.setEnabled(false);	//�]��������A�|�Ϧ�
			lowerPanel.add(likeLabel);*/
			
			lowerPanel.remove(unlikeLabel);
			//lowerPanel.removeAll();
			lowerPanel.add(likeLabel, BorderLayout.WEST);
			
			validate();
			repaint();
		}
		else{	
			lowerPanel.remove(likeLabel);
			lowerPanel.add(unlikeLabel, BorderLayout.WEST);
			
			validate();
			repaint();
		}
	}
	
	// �s����s��ActionListener //
	private class EButtonHandler implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent event){
			fromEditButton=true;
			frameChange();
		}
	}
	
}
