import javax.swing.JFrame;

//�m�W:Ĭ�өy  �Ǹ�:106403034 �t��:��ޤTB

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
