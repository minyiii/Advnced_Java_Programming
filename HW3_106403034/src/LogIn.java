import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class LogIn extends JFrame{		//登入JFrame(只有發布者需要)
	private final JPanel buttonPanel;	//JButton區
	private final JButton logInButton;
	private final JButton cancelButton;
	private final JPanel inputPanel;	//包含actPanel和pwdPanel
	private final JPanel actPanel;		//包含actLabel和actField
	private final JLabel actLabel;
	private final JTextField actField;
	private final JPanel pwdPanel;		//包含pwdLabel和pwdField
	private final JLabel pwdLabel;
	private final JPasswordField pwdField;
	private boolean success;			//登入成功(帳密正確)
	
	public LogIn(){	//constructor
		super("發布者登入");
		setLayout(new BorderLayout());
		
		// 輸入區 //
		inputPanel = new JPanel();
		inputPanel.setLayout(new BorderLayout());
		
		// 帳號區 //
		actPanel = new JPanel();
		actLabel = new JLabel("帳號");
		actPanel.add(actLabel);
		actField = new JTextField(15);
		actPanel.add(actField);
		inputPanel.add(actPanel, BorderLayout.NORTH);
		
		// 密碼區 //
		pwdPanel = new JPanel();
		pwdLabel = new JLabel("密碼");
		pwdPanel.add(pwdLabel);
		pwdField = new JPasswordField(15);
		pwdPanel.add(pwdField);
		inputPanel.add(pwdPanel, BorderLayout.SOUTH);
		
		// inputPanel區 //
		add(inputPanel, BorderLayout.NORTH);
		
		// JButton區 //
		buttonPanel = new JPanel();
		
		// 確認登入按鈕 //
		logInButton = new JButton("確認");
		logInButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent event){
				String account = actField.getText();
				String password = String.valueOf(pwdField.getPassword());
				//pwdField.getPassword().toString
				//java中字串的比較要用equals
				if(account.equals("login")&&(password.equals("loggg"))){	//輸入正確
					JOptionPane.showMessageDialog(LogIn.this, "輸入正確", "登入成功", JOptionPane.PLAIN_MESSAGE);
					success = true;
					dispose();	//關閉LogIn視窗
					//產生Pulisher發布者視窗，也就是可編輯畫面
					Publisher Publisher = new Publisher();
					Publisher.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
					Publisher.setSize(650,450);
					Publisher.setVisible(true);
					Publisher.setLocationRelativeTo(null);	//置中
				}
				else{
					success = false;
					JOptionPane.showMessageDialog(LogIn.this, "輸入錯誤", "無法登入", JOptionPane.ERROR_MESSAGE);
					dispose();	//關閉LogIn視窗
				}
			}
		});
		buttonPanel.add(logInButton);
		
		
		// 取消按鈕 //
		cancelButton = new JButton("取消");
		cancelButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent event){
				dispose();
			}
		});
		buttonPanel.add(cancelButton);
		
		add(buttonPanel, BorderLayout.SOUTH);
	}
	
	public boolean getSuccess(){
		return success;
	}
}
