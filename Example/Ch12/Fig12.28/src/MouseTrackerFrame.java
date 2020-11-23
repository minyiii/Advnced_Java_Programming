import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class MouseTrackerFrame extends JFrame{
	private final JPanel mousePanel;
	private final JLabel statusBar;
	
	public MouseTrackerFrame(){
		super("Demonstrating Mouse Events");
		
		mousePanel = new JPanel();
		mousePanel.setBackground(Color.WHITE);	//default為白色
		add(mousePanel, BorderLayout.CENTER);
		
		statusBar = new JLabel();
		add(statusBar, BorderLayout.SOUTH);
		
		MouseHandler handler = new MouseHandler();
		mousePanel.addMouseListener(handler);
		mousePanel.addMouseMotionListener(handler);
	}
	
	private class MouseHandler implements MouseListener, MouseMotionListener{
		@Override
		public void mouseClicked(MouseEvent event){	//按後的放開
			statusBar.setText(String.format("Clicked at [%d, %d]",event.getX(),event.getY()));
		}
		
		@Override
		public void mousePressed(MouseEvent event){	//按下去沒放開時
			statusBar.setText(String.format("Pressed at [%d, %d]",event.getX(),event.getY()));
		}
		
		@Override
		public void mouseReleased(MouseEvent event){
			statusBar.setText(String.format("Released at [%d, %d]", event.getX(),event.getY()));
		}
		
		@Override
		public void mouseEntered(MouseEvent event){
			statusBar.setText(String.format("Entered at [%d, %d]", event.getX(), event.getY()));
			mousePanel.setBackground(Color.GREEN);
		}
		
		@Override
		public void mouseExited(MouseEvent event){
			statusBar.setText(String.format("Exit at [%d, %d]", event.getX(), event.getY()));
			mousePanel.setBackground(Color.WHITE);
		}
		
		@Override
		public void mouseDragged(MouseEvent event){
			statusBar.setText(String.format("Dragged at [%d%, %d]", event.getX(), event.getY()));
		}
		
		@Override
		public void mouseMoved(MouseEvent event){
			statusBar.setText(String.format("Moved at [%d, %d]", event.getX(), event.getY()));
		}
	}
}
