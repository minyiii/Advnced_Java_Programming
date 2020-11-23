import java.awt.BorderLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class MouseDetailsFrame extends JFrame{
	private String details;	//下方顯示的字串
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
			
			if(event.isMetaDown())	//滑鼠右鍵
				details += "with right mouse button";
			else if(event.isAltDown())	//中間鍵
				details += "with center mouse button";
			else	//左鍵
				details += "with left mouse button";
			
			statusBar.setText(details);
		}
	}
}
