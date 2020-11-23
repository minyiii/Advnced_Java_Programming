import java.awt.BorderLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class MouseDetailsFrame extends JFrame{
	private String details;	//�U����ܪ��r��
	private final JLabel statusBar;
	
	public MouseDetailsFrame(){
		super("Mouse click and buttons");
		statusBar = new JLabel("Click the bar");
		add(statusBar,BorderLayout.SOUTH);
		addMouseListener(new MouseClickHandler());
	}
	
	private class MouseClickHandler extends MouseAdapter{
		@Override
		public void mouseClicked(MouseEvent event){
			int xPos = event.getX();
			int yPos = event.getY();
			
			details = String.format("Click at [%d, %d] %d time(s)", xPos, yPos, event.getClickCount());
			
			if(event.isMetaDown())	//�ƹ��k��
				details += "with right mouse button";
			else if(event.isAltDown())	//������
				details += "with center mouse button";
			else	//����
				details += "with left mouse button";
			
			statusBar.setText(details);
		}
	}
}
