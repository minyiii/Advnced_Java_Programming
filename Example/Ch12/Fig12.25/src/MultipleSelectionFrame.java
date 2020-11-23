import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

public class MultipleSelectionFrame extends JFrame{
	private final JList<String> colorJList;	//�ŧi
	private final JList<String> copyJList;	//�ŧi
	private JButton copyButton;
	private static final String[] colorNames = {"Black", "Blue", "Cyan",
		"Dark Gray", "Gray", "Green", "Light Gray", "Magenta", "Orange",
		"Pink", "Red", "White", "Yellow"};
	
	public MultipleSelectionFrame(){	//constructor
		super("Multiple Selection Lists");
		setLayout(new FlowLayout());
		
		colorJList = new JList<String>(colorNames);	//��@
		colorJList.setVisibleRowCount(5);	//�@���i�ݨ�5�ӿﶵ
		colorJList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		add(new JScrollPane(colorJList));
		
		copyButton = new JButton("Copy>>>");
		copyButton.addActionListener(
			new ActionListener(){
				@Override
				public void actionPerformed(ActionEvent e){
					copyJList.setListData(
						colorJList.getSelectedValuesList().toArray(new String[0]));
				}
			}
		);
		add(copyButton);	//add button to JFrame
		
		copyJList = new JList<String>();
		copyJList.setVisibleRowCount(5);
		copyJList.setFixedCellWidth(100);
		copyJList.setFixedCellHeight(15);
		copyJList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		add(new JScrollPane(copyJList));
	}
}
