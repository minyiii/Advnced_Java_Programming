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

public class LogIn extends JFrame{		//�n�JJFrame(�u���o���̻ݭn)
	private final JPanel buttonPanel;	//JButton��
	private final JButton logInButton;
	private final JButton cancelButton;
	private final JPanel inputPanel;	//�]�tactPanel�MpwdPanel
	private final JPanel actPanel;		//�]�tactLabel�MactField
	private final JLabel actLabel;
	private final JTextField actField;
	private final JPanel pwdPanel;		//�]�tpwdLabel�MpwdField
	private final JLabel pwdLabel;
	private final JPasswordField pwdField;
	private boolean success;			//�n�J���\(�b�K���T)
	
	public LogIn(){	//constructor
		super("�o���̵n�J");
		setLayout(new BorderLayout());
		
		// ��J�� //
		inputPanel = new JPanel();
		inputPanel.setLayout(new BorderLayout());
		
		// �b���� //
		actPanel = new JPanel();
		actLabel = new JLabel("�b��");
		actPanel.add(actLabel);
		actField = new JTextField(15);
		actPanel.add(actField);
		inputPanel.add(actPanel, BorderLayout.NORTH);
		
		// �K�X�� //
		pwdPanel = new JPanel();
		pwdLabel = new JLabel("�K�X");
		pwdPanel.add(pwdLabel);
		pwdField = new JPasswordField(15);
		pwdPanel.add(pwdField);
		inputPanel.add(pwdPanel, BorderLayout.SOUTH);
		
		// inputPanel�� //
		add(inputPanel, BorderLayout.NORTH);
		
		// JButton�� //
		buttonPanel = new JPanel();
		
		// �T�{�n�J���s //
		logInButton = new JButton("�T�{");
		logInButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent event){
				String account = actField.getText();
				String password = String.valueOf(pwdField.getPassword());
				//pwdField.getPassword().toString
				//java���r�ꪺ����n��equals
				if(account.equals("login")&&(password.equals("loggg"))){	//��J���T
					JOptionPane.showMessageDialog(LogIn.this, "��J���T", "�n�J���\", JOptionPane.PLAIN_MESSAGE);
					success = true;
					dispose();	//����LogIn����
					//����Pulisher�o���̵����A�]�N�O�i�s��e��
					Publisher Publisher = new Publisher();
					Publisher.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
					Publisher.setSize(650,450);
					Publisher.setVisible(true);
					Publisher.setLocationRelativeTo(null);	//�m��
				}
				else{
					success = false;
					JOptionPane.showMessageDialog(LogIn.this, "��J���~", "�L�k�n�J", JOptionPane.ERROR_MESSAGE);
					dispose();	//����LogIn����
				}
			}
		});
		buttonPanel.add(logInButton);
		
		
		// �������s //
		cancelButton = new JButton("����");
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
