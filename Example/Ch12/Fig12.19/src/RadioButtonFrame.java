import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;

public class RadioButtonFrame extends JFrame{
	private final JTextField textField;
	private final JRadioButton plainJRadioButton;
	private final JRadioButton boldJRadioButton;
	private final JRadioButton italicJRadioButton;
	private final JRadioButton bolditalicJRadioButton;
	private final ButtonGroup radioGroup;
	private final Font plainFont;
	private final Font boldFont;
	private final Font italicFont;
	private final Font bolditalicFont;
	
	public RadioButtonFrame(){	//constructor
		super("RadioButton test");
		setLayout(new FlowLayout());
		
		textField = new JTextField("Watch the font change",25);
		add(textField);	//add to JFrame
		
		plainJRadioButton = new JRadioButton("Plain",true);
		boldJRadioButton = new JRadioButton("Bold",false);
		italicJRadioButton = new JRadioButton("Italic",false);
		bolditalicJRadioButton = new JRadioButton("Bold/Italic",false);
		add(plainJRadioButton);
		add(boldJRadioButton);
		add(italicJRadioButton);
		add(bolditalicJRadioButton);
		
		radioGroup = new ButtonGroup();
		radioGroup.add(plainJRadioButton);
		radioGroup.add(boldJRadioButton);
		radioGroup.add(italicJRadioButton);
		radioGroup.add(bolditalicJRadioButton);
		
		plainFont = new Font("Serif", Font.PLAIN, 14);
		boldFont = new Font("Serif", Font.BOLD, 14);
		italicFont = new Font("Serif", Font.ITALIC, 14);
		bolditalicFont = new Font("Serif", Font.BOLD +Font.ITALIC, 14);
		textField.setFont(plainFont);	//default
		
		//各有各的handler
		plainJRadioButton.addItemListener(new RadioButtonHandler(plainFont));
		boldJRadioButton.addItemListener(new RadioButtonHandler(boldFont));
		italicJRadioButton.addItemListener(new RadioButtonHandler(italicFont));
		bolditalicJRadioButton.addItemListener(new RadioButtonHandler(bolditalicFont));
	}
	private class RadioButtonHandler implements ItemListener{
		private Font font;
		public RadioButtonHandler(Font f){	//constructor
			font = f;
		}
		
		@Override
		public void itemStateChanged(ItemEvent event){	//event發生時會發生的action
			textField.setFont(font);
		}
		
	}
}
