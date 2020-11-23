import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

//�s��ηs�W��Ʈɼu�X�Ӫ�����
public class AddPersonFrame extends JFrame{
	private StudentQueries studentQueries = new StudentQueries();
	private Student studentNow = new Student();
	private ContactsFrame contactsFrame;
	
	private final JLabel nameLabel;
	private final JLabel typeLabel;
	private final JLabel phoneLabel;
	private JTextField nameTextField;
	private JComboBox typeComboBox;
	private static String[] typeArr= {"cell", "company", "home"}; 
	private JTextField phoneTextField;
	private JPanel namePanel;
	private JPanel typePanel;
	private JPanel phonePanel;
	private JButton submitBtn;
	
	private boolean isAdd = true;	//true���s�W�Bfalse���s��
	
	//constructor
	public AddPersonFrame(String title, ContactsFrame contactsFrame) {
		super(title);	//�s�W�B�s��
		
		this.contactsFrame = contactsFrame;
		setLayout(new FlowLayout(FlowLayout.LEFT, 15, 15));
		setSize(250, 200);
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setVisible(true);
		setLocationRelativeTo(null);
		
		//�m�W��
		namePanel = new JPanel();
		nameLabel = new JLabel("�m�W");
		nameTextField = new JTextField(10);
		namePanel.setLayout(new BoxLayout(namePanel, BoxLayout.X_AXIS));
		namePanel.add(nameLabel);
		namePanel.add(Box.createHorizontalStrut(30));
		namePanel.add(nameTextField);
		add(namePanel);
		
		//
		typePanel = new JPanel();
		typeLabel = new JLabel("����");
		typeComboBox = new JComboBox<String>(typeArr);
		typePanel.setLayout(new BoxLayout(typePanel, BoxLayout.X_AXIS));
		typePanel.add(typeLabel);
		typePanel.add(Box.createHorizontalStrut(40));
		typePanel.add(typeComboBox);
		add(typePanel);
		
		//
		phonePanel = new JPanel();
		phoneLabel = new JLabel("�q��");
		phoneTextField = new JTextField(10);
		phonePanel.setLayout(new BoxLayout(phonePanel, BoxLayout.X_AXIS));
		phonePanel.add(phoneLabel);
		phonePanel.add(Box.createHorizontalStrut(30));
		phonePanel.add(phoneTextField);
		add(phonePanel);
		
		//
		submitBtn = new JButton("����");
		submitBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent actionEvent) {
				String name = nameTextField.getText();
				String type = typeComboBox.getSelectedItem().toString();
				String phone = phoneTextField.getText();
				
				System.out.printf("name: %s, type: %s, phone: %s\n", name, type, phone);
				if(name.length()==0 || phone.length()==0) {
					JOptionPane.showMessageDialog(null, "�Ҧ����ҥ���!!!", "Error", JOptionPane.WARNING_MESSAGE);
				}
				else {
					if(checkPhone(type, phone)) {
						System.out.printf("good\n");
						//�g�iDB
						if(isAdd==false) {
							System.out.println(studentNow.getMemberID());
							int row = studentQueries.updateStudent(studentNow.getMemberID(), name, type, phone);
							if(row==1) {
								System.out.println("update success");
								AddPersonFrame.this.dispose();
								contactsFrame.displayDefault();
							}
							else {
								System.out.println("update fail");
							}
						}
						else {
							int row = studentQueries.addStudent(name, type, phone);
							if(row==1) {
								System.out.println("add success");
								AddPersonFrame.this.dispose();
								contactsFrame.displayDefault();
							}
							else {
								System.out.println("add fail");
							}
						}
						
					}
					else {	//�榡���~
						JOptionPane.showMessageDialog(null, "�q�ܮ榡���~!!!", "Error", JOptionPane.WARNING_MESSAGE);
					}
					
				}
			}
		});
		add(submitBtn);
	}
	
	//�Y���s�説�A�N�|�I�s��method
	public void setText(Student student) {
		isAdd = false;	//�]�w�{�b���A���s��
		studentNow = student;
		nameTextField.setText(student.getName());
		typeComboBox.setSelectedItem(student.getType());
		phoneTextField.setText(student.getPhone());
	}
	
	//���b
	public boolean checkPhone(String type, String phone) {
		boolean check = false;
		String cellRegex = "09[0-9]{8}$";
		String otherRegex = "0[0-9]{8,9}$";
		
		switch(type) {
		case "cell":
			return phone.matches(cellRegex);
		case "company":
		case "home":
			return phone.matches(otherRegex);
		}
		return check;
	}
}
