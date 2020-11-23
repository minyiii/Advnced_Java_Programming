import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;


public class BloodPanel extends JPanel{	//¦å¶q¹Ï
	private int bloodValue=100;
	@Override
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		
		g.setColor(Color.BLUE);
		g.drawRect(15, 20, 500, 10);
		g.fillRect(15, 20, bloodValue*5, 10);
	}
	
	public void setValue(int value){
		bloodValue = value;
	}
}
