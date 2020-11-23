import javax.swing.JFrame;


public class Main {
	public static void main(String[] args) {
		JFrame frame = new JFrame("Using colors");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		ColorPanel colorPanel = new ColorPanel();
		frame.add(colorPanel);
		frame.setSize(400, 180);
		frame.setVisible(true);
	}
}
