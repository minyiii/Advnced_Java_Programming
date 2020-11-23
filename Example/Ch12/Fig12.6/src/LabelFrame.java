import java.awt.FlowLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.Icon;
import javax.swing.ImageIcon;

public class LabelFrame extends JFrame{
	private final JLabel label1;
	private final JLabel label2;
	private final JLabel label3;
	
	public LabelFrame(){	//constructor
		super("Testing JLabel");
		setLayout(new FlowLayout());
		
		label1 = new JLabel("Label with text");	//constructor
		label1.setToolTipText("This is Label1");	//游標經過會顯示
		add(label1);	//add label1 to JFrame
		
		Icon fix = new ImageIcon(getClass().getResource("fix.png"));
		label2 = new JLabel("label with text and icon", fix, SwingConstants.LEFT);	//constructor
		label2.setToolTipText("This is label2");
		add(label2);
		
		label3 = new JLabel();	//constructor
		label3.setText("Label with icon and text at bottom");
		label3.setIcon(fix);	//add icon to JLabel
		label3.setHorizontalTextPosition(SwingConstants.CENTER);
		label3.setVerticalTextPosition(SwingConstants.BOTTOM);
		label3.setToolTipText("This is label3");
		add(label3);
	}
}
