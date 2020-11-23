import javax.swing.JFrame;

//姓名:蘇敏宜  學號:106403034 系級:資管三B

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		MapFrame mapFrame = new MapFrame(ReadMap.readMap());
		mapFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mapFrame.setSize(550, 650);
		mapFrame.setVisible(true);
		mapFrame.setLocationRelativeTo(null);
	}

}
