//�m�W:Ĭ�өy �Ǹ�:106403034 �t��:��ޤTB
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class Main {

	public static void main(String[] args) {
		int reply = JOptionPane.showConfirmDialog(null,"�O�_���o����?" , "�n�J", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
		//0:�O, 1:�_, 2:����
		switch(reply){
		case 0:
			Publisher Publisher = new Publisher();
			Publisher.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			Publisher.setSize(650,450);
			Publisher.setVisible(true);
			Publisher.setLocationRelativeTo(null);	//�m��
			break;
		case 1:
			NotPublisher notPublisher = new NotPublisher();
			notPublisher.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			notPublisher.setSize(650,450);
			notPublisher.setVisible(true);
			notPublisher.setLocationRelativeTo(null);	//�m��
			break;
		case 2:
			break;
		}
	}
}