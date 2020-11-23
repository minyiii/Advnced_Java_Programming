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
	
	private JPanel titlePanel;	//上方標題區域
	private JLabel titleLabel;	//contacts
	private Icon addIcon;		//加的圖片
	private JLabel iconLabel;	//放圖的JLabel
	private JComboBox groupComboBox;	//選群組的ComboBox
	private static String[] groupArr= {"群組分類","classmate", "family", "friend" ,"undefined"};
	private JPanel searchPanel;	//上方搜尋區域
	private JTextField searchField;	//搜尋欄位
	private JButton searchBtn;		//搜尋按鈕
	private JPanel tablePanel;		//表格區
	private ResultSetTableModel tableModel;
	private JTable table;			//表格本身
	private JTableHeader header;	//表格column名
	private final TableRowSorter<ResultSetTableModel> sorter;
	
	private String defaultQuery= "SELECT name, phone, Group1 FROM people";
	private String selecyByGroup = "SELECT * FROM people WHERE Group1 = ";
	
	//constructor
	public ContactsFrame() {
		super("contacts");
		
		setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		setResizable(false);
		
		studentQueries = new StudentQueries();
		
		//上方標題區域
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
					
					try {	//過濾
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
						
						//設定第二col消失(但filter功能正常)
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
		
		//上方搜尋區
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
		
		//下方JTable
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
	    table.setRowHeight(50);	//設定row高度
	    table.setPreferredScrollableViewportSize(new Dimension(250,430));	//設定table在可捲動狀態下的大小
	    
	    //設定第二col消失(但filter功能正常)
	    setGroup();
	    displayDefault();
	    
	    //這兩行要assign完tableModel和table的值才可打
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
	
	//更改圖片大小
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
	
	//顯示一開始的狀況(全部student)
	public void displayDefault() {
		try {
			tableModel.setQuery(defaultQuery);
			
			//設定第二col消失(但filter功能正常)
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
	
	//圖片Label的滑鼠點擊事件
	private class iconMA extends MouseAdapter {
		@Override
		public void mouseClicked(MouseEvent event) {
			System.out.printf("icon click\n");
			AddPersonFrame add = new AddPersonFrame("新增", ContactsFrame.this);
			displayDefault();
		}
	}
	
	//JTable的點擊事件
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
				
				Object[] options = {"Update", "Delete"};	//自訂button文字
				int choice = JOptionPane.showOptionDialog(null, s.getType()+" : "+s.getPhone(), s.getName()
						, JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
				System.out.printf("choice: %d\n", choice);	//update:0、delete:1、叉叉:-1
				switch(choice) {
				case 0:	//更新資料
					AddPersonFrame addPersonFrame = new AddPersonFrame("編輯", ContactsFrame.this);
					addPersonFrame.setText(s);
					displayDefault();
					break;
				case 1:	//刪除資料
					studentQueries.deleteStudent(s.getMemberID());
					displayDefault();
					break;
				}
			}
		}
	}
	
	//搜尋按鈕的點擊事件
	public class searchBtnAL implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent event) {
			System.out.printf("btn click\n");
			String text = searchField.getText();
			if(text.length()==0) {	//沒東西，不用過濾
				displayDefault();
				sorter.setRowFilter(null);
			}
			else {
				try {	//過濾
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
