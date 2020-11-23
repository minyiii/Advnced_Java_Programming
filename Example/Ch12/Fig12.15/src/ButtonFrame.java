import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

public class ButtonFrame extends JFrame{
	private final JButton plainButton;
	private final JButton fancyButton;
	
	public ButtonFrame(){	//constructor
		super("Testing Buttons");
		setLayout(new FlowLayout());
		
		plainButton = new JButton("Plain Button");
		add(plainButton);	//add to JFrame
		
		Icon rocket1 = new ImageIcon(getClass().getResource("rocket1.png"));
		Icon rocket2 = new ImageIcon(getClass().getResource("rocket2.png"));
		
		fancyButton = new JButton("Fancy Button", rocket1);
		fancyButton.setRolloverIcon(rocket2);
		add(fancyButton);
		
		ButtonHandler handler = new ButtonHandler();
		plainButton.addActionListener(handler);
		fancyButton.addActionListener(handler);
	}
	
	private class ButtonHandler implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent event){
			JOptionPane.showMessageDialog(ButtonFrame.this, String.format(
					"You press: %s", event.getActionCommand()));
		}
	}
}
