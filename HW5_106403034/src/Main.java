//姓名:蘇敏宜  學號:106403034 系級:資管三B

import java.io.IOException;

import javax.swing.JFrame;

public class Main {

	public static void main(String[] args) throws IOException {
		MapFrame mapFrame = new MapFrame();
		mapFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mapFrame.setSize(550, 650);
		mapFrame.setVisible(true);
		mapFrame.setLocationRelativeTo(null);
	}
}