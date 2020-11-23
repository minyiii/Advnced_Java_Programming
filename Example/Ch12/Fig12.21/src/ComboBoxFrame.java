import java.awt.FlowLayout;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

public class ComboBoxFrame extends JFrame{
	private final JLabel label;
	private final JComboBox<String> imagesJComboBox;	//下拉式選單
	private static final String[] names = 	//字串陣列
		{ "rocket3.png","rocket4.png","moon1.png","moon2.png" };
	private final Icon[] icons = {	//icon陣列
			new ImageIcon(getClass().getResource(names[0])),
			new ImageIcon(getClass().getResource(names[1])),
			new ImageIcon(getClass().getResource(names[2])),
			new ImageIcon(getClass().getResource(names[3]))
	};
	
	public ComboBoxFrame(){
		super("Testing ComboBox");
		setLayout(new FlowLayout());
		
		imagesJComboBox = new JComboBox<String>(names);
		imagesJComboBox.setMaximumRowCount(3);
		
		imagesJComboBox.addItemListener(
			new ItemListener(){	//anonymous inner class
				String current = names[0];
				@Override
				public void itemStateChanged(ItemEvent event){
					JOptionPane.showMessageDialog(ComboBoxFrame.this, "Enter itemStateChanged()");
					if(event.getStateChange()==ItemEvent.SELECTED){
						label.setIcon(icons[imagesJComboBox.getSelectedIndex()]);
						current = (String)imagesJComboBox.getSelectedItem();
						JOptionPane.showMessageDialog(ComboBoxFrame.this, "ItemEvent(" +current+ ")selected");
					}
					else
						JOptionPane.showMessageDialog(ComboBoxFrame.this, "ItemEvent(" + current + ")de-selected");
				}
			}
				
		);
		
		add(imagesJComboBox);
		label = new JLabel(icons[0]);	//display 1st icon
		add(label);	//add label to JFrame
	}
}
