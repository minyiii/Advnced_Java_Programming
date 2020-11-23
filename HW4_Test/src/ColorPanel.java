import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;


public class ColorPanel extends JPanel{
	
	private int bloodValue=100;
	@Override
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		
		g.setColor(Color.BLUE);
		g.drawRect(20, 20, 300, 10);
		g.fillRect(20, 20, bloodValue*3, 10);
	}
}
