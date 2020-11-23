import javax.swing.JFrame;

public class Main {

	public static void main(String[] args) {
		Painter painter = new Painter();
		painter.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		painter.setSize(850,600);
		painter.setVisible(true);
	}

}
