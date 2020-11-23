import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;
import java.util.regex.PatternSyntaxException;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.ScrollPaneConstants;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

public class ContactsFrame extends JFrame{
	private StudentQueries studentQueries;
	
	private JPanel titlePanel;	//�W����D�ϰ�
	private JLabel titleLabel;	//contacts
	private Icon addIcon;		//�[���Ϥ�
	private JLabel iconLabel;	//��Ϫ�JLabel
	private JComboBox groupComboBox;	//��s�ժ�ComboBox
	private static String[] groupArr= {"�s�դ���","classmate", "family", "friend" ,"undefined"};
	private JPanel searchPanel;	//�W��j�M�ϰ�
	private JTextField searchField;	//�j�M���
	private JButton searchBtn;		//�j�M���s
	private JPanel tablePanel;		//����
	private ResultSetTableModel tableModel;
	private JTable table;			//��楻��
	private JTableHeader header;	//���column�W
	private final TableRowSorter<ResultSetTableModel> sorter;
	
	private String defaultQuery= "SELECT name, phone, Group1 FROM people";
	private String selecyByGroup = "SELECT * FROM people WHERE Group1 = ";
	
	//constructor
	public ContactsFrame() {
		super("contacts");
		
		setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		setResizable(false);
		
		studentQueries = new StudentQueries();
		
		//�W����D�ϰ�
		titlePanel = new JPanel();
		titleLabel = new JLabel("Contacts");
		titleLabel.setFont(new Font("Calibri", 1, 25));
		
		groupComboBox = new JComboBox<String>(groupArr);
		groupComboBox.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent itemEvent) {
				if(itemEvent.getStateChange()==ItemEvent.SELECTED) {
					String groupStr = groupArr[groupComboBox.getSelectedIndex()];
					//studentQueries.getByGroup(groupStr);
					System.out.println(groupStr);
					
					try {	//�L�o
						if(groupStr.equals(groupArr[0])) {
							displayDefault();
							sorter.setRowFilter(null);
						}
						else {
							sorter.setRowFilter(RowFilter.regexFilter(groupStr));
						}
					}
					catch(PatternSyntaxException p) {
						JOptionPane.showMessageDialog(null, "Bad regex pattern", "Bad regex pattern", JOptionPane.ERROR_MESSAGE);
					}
					
					/*try {
						tableModel.setQuery(selecyByGroup+groupStr+";");
						
						//�]�w�ĤGcol����(��filter�\�ॿ�`)
					    /*TableColumn col = table.getColumnModel().getColumn(1);
					    table.removeColumn(col);
					}
					catch (SQLException sqlException){
						tableModel.disconnectFromDB();
						displayDefault();
				    }
					catch(IllegalStateException illegalStateException) {
						illegalStateException.printStackTrace();
						displayDefault();
					}
					*/
				}
			}
		});
		
		addIcon = changeSize("add-user.png");
		iconLabel = new JLabel(addIcon);
		iconLabel.addMouseListener(new iconMA());
		titlePanel.setLayout(new BoxLayout(titlePanel, BoxLayout.X_AXIS));
		titlePanel.add(titleLabel);
		titlePanel.add(Box.createHorizontalStrut(20));
		titlePanel.add(groupComboBox);
		titlePanel.add(Box.createHorizontalStrut(20));
		titlePanel.add(iconLabel);
		
		add(titlePanel);
		
		//�W��j�M��
		searchPanel = new JPanel();
		searchField = new JTextField(15);
		searchBtn = new JButton("Search");
		searchBtn.setBackground(Color.ORANGE);
		searchBtn.addActionListener(new searchBtnAL());
		searchPanel.setLayout(new BoxLayout(searchPanel, BoxLayout.X_AXIS));
		searchPanel.add(searchField);
		searchPanel.add(Box.createHorizontalStrut(20));
		searchPanel.add(searchBtn);
		
		add(searchPanel);
		
		//�U��JTable
		tablePanel = new JPanel();
		table = new JTable();
		
		tablePanel.setLayout(new BoxLayout(tablePanel, BoxLayout.Y_AXIS));
		try {
			tableModel = new ResultSetTableModel(defaultQuery);
			header = table.getTableHeader();
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		
		table.setModel(tableModel);
		table.addMouseListener(new tableMA());
	    table.setRowHeight(50);	//�]�wrow����
	    table.setPreferredScrollableViewportSize(new Dimension(250,430));	//�]�wtable�b�i���ʪ��A�U���j�p
	    
	    //�]�w�ĤGcol����(��filter�\�ॿ�`)
	    setGroup();
	    displayDefault();
	    
	    //�o���nassign��tableModel�Mtable���Ȥ~�i��
		sorter = new TableRowSorter<ResultSetTableModel>(tableModel);
	    table.setRowSorter(sorter);
	    
		JScrollPane scrollPane = new JScrollPane(table, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
	    tablePanel.add(header);
		tablePanel.add(scrollPane);
		add(tablePanel);
		
		addWindowListener(new WindowAdapter() {
			public void windowClosed(WindowEvent w) {
				tableModel.disconnectFromDB();
				System.exit(0);
			}
		});
		
	}
	
	//���Ϥ��j�p
	public Icon changeSize(String str){
		ImageIcon imgIcon = new ImageIcon(getClass().getResource(str));
		Image img = imgIcon.getImage().getScaledInstance(35, 35, java.awt.Image.SCALE_SMOOTH);
		Icon icon = new ImageIcon(img);
		return icon;
	}
	
	//
	public void setGroup() {
		studentQueries.getAllStudent().stream()
			.map(s->s.getMemberID())
			.forEach(id->studentQueries.setUndefined(id));
	}
	
	//��ܤ@�}�l�����p(����student)
	public void displayDefault() {
		try {
			tableModel.setQuery(defaultQuery);
			
			//�]�w�ĤGcol����(��filter�\�ॿ�`)
		    TableColumn col = table.getColumnModel().getColumn(1);
		    table.removeColumn(col);
		    col = table.getColumnModel().getColumn(1);
		    table.removeColumn(col);
		}
		catch (SQLException sqlException){
			tableModel.disconnectFromDB();
			System.exit(1);
	    }
	}
	
	//�Ϥ�Label���ƹ��I���ƥ�
	private class iconMA extends MouseAdapter {
		@Override
		public void mouseClicked(MouseEvent event) {
			System.out.printf("icon click\n");
			AddPersonFrame add = new AddPersonFrame("�s�W", ContactsFrame.this);
			displayDefault();
		}
	}
	
	//JTable���I���ƥ�
	private class tableMA extends MouseAdapter {
		@Override
		public void mouseClicked(MouseEvent e) {
			if (e.getClickCount() == 2) {	//double click
				JTable target = (JTable)e.getSource();
				int row = target.getSelectedRow();
				String selectedName = (String)target.getValueAt(row, 0);
				//String selectedName = (String)target.getValueAt(row, 1);
				System.out.println(selectedName);
				Student s = studentQueries.getByName(selectedName);
				
				Object[] options = {"Update", "Delete"};	//�ۭqbutton��r
				int choice = JOptionPane.showOptionDialog(null, s.getType()+" : "+s.getPhone(), s.getName()
						, JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
				System.out.printf("choice: %d\n", choice);	//update:0�Bdelete:1�B�e�e:-1
				switch(choice) {
				case 0:	//��s���
					AddPersonFrame addPersonFrame = new AddPersonFrame("�s��", ContactsFrame.this);
					addPersonFrame.setText(s);
					displayDefault();
					break;
				case 1:	//�R�����
					studentQueries.deleteStudent(s.getMemberID());
					displayDefault();
					break;
				}
			}
		}
	}
	
	//�j�M���s���I���ƥ�
	public class searchBtnAL implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent event) {
			System.out.printf("btn click\n");
			String text = searchField.getText();
			if(text.length()==0) {	//�S�F��A���ιL�o
				displayDefault();
				sorter.setRowFilter(null);
			}
			else {
				try {	//�L�o
					System.out.println(text);
					sorter.setRowFilter(RowFilter.regexFilter(text));
				}
				catch(PatternSyntaxException p) {
					JOptionPane.showMessageDialog(null, "Bad regex pattern", "Bad regex pattern", JOptionPane.ERROR_MESSAGE);
				}
			}
		}
	}
}
