import javax.swing.JFrame;

public class Main {

	public static void main(String[] args) {
		PainterFrame painterFrame = new PainterFrame();
		painterFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		painterFrame.setSize(850,600);
		painterFrame.setVisible(true);
		painterFrame.setLocationRelativeTo(null);
	}

}
