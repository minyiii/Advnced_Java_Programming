//姓名:蘇敏宜 學號:106403034 系級:資管三B
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class Main {

	public static void main(String[] args) {
		int reply = JOptionPane.showConfirmDialog(null,"是否為發布者?" , "登入", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
		//0:是, 1:否, 2:取消
		switch(reply){
		case 0:
			Publisher Publisher = new Publisher();
			Publisher.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			Publisher.setSize(650,450);
			Publisher.setVisible(true);
			Publisher.setLocationRelativeTo(null);	//置中
			break;
		case 1:
			NotPublisher notPublisher = new NotPublisher();
			notPublisher.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			notPublisher.setSize(650,450);
			notPublisher.setVisible(true);
			notPublisher.setLocationRelativeTo(null);	//置中
			break;
		case 2:
			break;
		}
	}
}