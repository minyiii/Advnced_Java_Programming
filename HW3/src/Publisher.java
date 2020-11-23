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
	private final Color blackBoardColor = new Color(127, 161, 110);	//背景色
	private JPanel upperPanel;				//上方的JPanel(放標題、時間)
	private final JLabel showTitle;			//標題
	private JLabel showTime;				//時間
	private String timeString;				//時間的string型態
	//private JPanel midPanel;				//中間的JPanel
	private JTextArea showText;				//中間的文字區域
	private String textString;				//存postSerializable中的content
	private JLabel likeLabel;				//有愛心時的JLabel(含圖片"like.png"、文字"like")
	private JLabel unlikeLabel;				//沒愛心時的JLabel(含圖片"unlike.png"、文字"unlike")
	//private JPanel likePanel;				//放愛心JLabel的JPanel
	private boolean isLike;					//看有沒有按愛心
	private final JPanel lowerPanel; 		//(正常)下方的JPanel
	private final JButton editJButton;		//編輯按鈕
	private final JButton newPostJButton;	//新增貼文按鈕
	private JPanel ELowerPanel;				//(編輯貼文時)下方的JPanel
	private final JButton saveJButton;		//儲存 按鈕
	private final JButton saveAsJButton;	//另存新檔 按鈕
	private final JButton importJButton;	//匯入內容 按鈕
	private final JButton cancelJButton;	//取消 按鈕
	private boolean fromEditButton;			//1為從edit進入，0為從全新貼文進入
	private PostSerializable postSerializable;
	
	public Publisher(){
		super("公告系統");
		//final PostSerializable postSerializable = ReadSequentialFile.returnPost();
		postSerializable = ReadSequentialFile.returnPost();
		
		//最上面的區域，包含標題、和時間
		upperPanel = new JPanel();
		//Color blackBoardColor = new Color(127, 161, 110);	//設定背景色的RGB代碼
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
		//midPanel = new JPanel();
		textString = postSerializable.getContent();
		//System.out.println(textString);
		showText = new JTextArea(textString);
		showText.setEditable(false);	//這樣才不能編輯
		showText.setFont(new Font(null, Font.PLAIN, 15));
		showText.setForeground(Color.YELLOW);
		showText.setBackground(blackBoardColor);
		
		add(showText);
		/*midPanel.add(showText, SwingConstants.LEFT);
		midPanel.setBackground(blackBoardColor);
		add(midPanel, BorderLayout.CENTER);*/
		
		/// 下方的區域 ///
		// 正常 //
		lowerPanel = new JPanel();
		lowerPanel.setLayout(new BorderLayout());
		
		// 下-愛心 //
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
			likeLabel.setEnabled(false);	//設成不能按，會反灰
			lowerPanel.add(likeLabel);
		}
		else{
			likeLabel.setEnabled(false);	//設成不能按，會反灰
			lowerPanel.add(unlikeLabel);
		}*/
		
		//lowerPanel.add(likePanel);
		
		// 下-編輯按鈕 //
		EButtonHandler editHandler = new EButtonHandler();
		editJButton = new JButton("編輯");
		editJButton.addActionListener(editHandler);
		
		lowerPanel.add(editJButton, BorderLayout.CENTER);
		
		// 下-新貼文 //
		newPostJButton = new JButton("全新貼文");
		newPostJButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent event){
				fromEditButton=false;
				showText.setText(null);
				frameChange();
				if(showText.getText()!=null){	//有改的話
					/*postSerializable.setIsLike(false);
					likeChange(postSerializable.getIsLike());*/
					
					System.out.println("全新貼文 有改");
				}
				else{
					showText.setText(postSerializable.getContent());
				}
				
			}
		});
		lowerPanel.add(newPostJButton, BorderLayout.EAST);
		
		// 下-其餘設定(背景、加進JFrame) //
		lowerPanel.setBackground(Color.ORANGE);		
		add(lowerPanel, BorderLayout.SOUTH);
		
		// 編輯貼文時 //
		ELowerPanel = new JPanel();
		
		// 儲存 //
		saveJButton = new JButton("儲存");
		saveJButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent event){
				//取得更改後的字串及當下時間，並設定回去
				String newText = showText.getText();
				postSerializable.setContent(newText);
				postSerializable.setEditTime(new Date());
				showTime.setText(postSerializable.getEditTime().toString());
				
				if(!fromEditButton){	//不是從編輯按鈕來，代表是從全新貼文按入
					postSerializable.setIsLike(false);			//全新貼文沒按過愛心
					likeChange(postSerializable.getIsLike());	//重新設定愛心狀態
				}
				CreateSequentialFile.UpdatePost(postSerializable);
				
				frameDefault();
			}
		});
		ELowerPanel.add(saveJButton);
		
		// 另存新檔 //
		saveAsJButton = new JButton("另存新檔");
		saveAsJButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent event){
				//System.out.println("另存");
				JFileChooser fileChooser = new JFileChooser(new File("."));	//預設為當前位置
				fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);	//另存新檔只能存文件
				int IsSaveAs = fileChooser.showSaveDialog(Publisher.this);	//顯示儲存的視窗
				if(IsSaveAs==JFileChooser.APPROVE_OPTION){		//若點了儲存
					File file = fileChooser.getSelectedFile();
					/*下方不可直接用postSerializable當變數傳入，因此時的postSerializable為按下編輯或全新貼文
					 * "之前"的狀況。若直接把postSerializable傳入，進入編輯或全新貼文狀態後所做的更動將不會被記錄進去，
					 *	故我宣告另一個型態一樣是PostSerializable的變數savaAsPost，來記錄"當下"的文字內容、
					 *  有無按愛心(愛心進入編輯狀態依舊不能改，故用postSerializable取)、編輯時間(當下存檔的時間)，
					 *  最後把它當參數傳進CreateSequentialFile.SavaAs就好。
					 */
					PostSerializable savaAsPost = new PostSerializable(
						showText.getText(), postSerializable.getIsLike(), new Date());
					CreateSequentialFile.SavaAs(file.toPath(), savaAsPost);
				}
			}
		});
		ELowerPanel.add(saveAsJButton);
		
		// 匯入 //
		importJButton = new JButton("匯入");
		importJButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent event){
				JFileChooser fileChooser = new JFileChooser(new File("."));
				fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
				int IsOpen = fileChooser.showOpenDialog(Publisher.this);
				if(IsOpen == JFileChooser.APPROVE_OPTION){	//確認匯入
					/* 此處跟另存新檔一樣不能直接用外面的postSerializable，因匯入後，使用者也有可能按取消，
					 * 貼文內容將會變回原本匯入前的樣子，若直接改到外面就沒辦回到上一步。
					 */
					PostSerializable importPost = ReadSequentialFile.importOther(fileChooser.getSelectedFile().toPath());
					showTime.setText(importPost.getEditTime().toString());
					showText.setText(importPost.getContent());
					likeChange(importPost.getIsLike());
				}
			}
		});
		ELowerPanel.add(importJButton);
		
		// 取消 //
		cancelJButton = new JButton("取消");
		cancelJButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent event){
				showText.setText(postSerializable.getContent());	//沒儲存所以文字就算有更動也沒用
				frameDefault();
			}
		});
		ELowerPanel.add(cancelJButton);
		
		// 其餘設定(顏色) //
		ELowerPanel.setBackground(Color.ORANGE);
		
	}
	// method //
	private void frameDefault(){
			showText.setEditable(false);				//這樣才不能編輯
			showText.setBackground(blackBoardColor);	//編輯貼文時的背景為白
			showText.setForeground(Color.YELLOW);		//編輯貼文時的字為黑
			
			lowerPanel.setVisible(true);
			ELowerPanel.setVisible(false);
			add(lowerPanel, BorderLayout.SOUTH);
			validate();
			repaint();
	}
	
	private void frameChange(){
		showText.setEditable(true);				//這樣才能編輯
		showText.setBackground(Color.WHITE);	//編輯貼文時的背景為白
		showText.setForeground(Color.BLACK);	//編輯貼文時的字為黑
		
		lowerPanel.setVisible(false);
		ELowerPanel.setVisible(true);
		add(ELowerPanel, BorderLayout.SOUTH);
		repaint();
	}
	
	private void likeChange(boolean like){
		if(like){
			/*likeLabel.setEnabled(false);	//設成不能按，會反灰
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
	
	// 編輯按鈕的ActionListener //
	private class EButtonHandler implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent event){
			fromEditButton=true;
			frameChange();
		}
	}
	
}
