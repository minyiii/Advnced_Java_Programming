//�m�W:Ĭ�өy �Ǹ�:106403034 �t��:��ޤTB
//�b��:login�B�K�X:loggg (�u���o���̤~�ݭn�n�J)


import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class Main {

	public static void main(String[] args) {
		int reply = JOptionPane.showConfirmDialog(null,"�O�_���o����?" , "�n�J", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
		LogIn loginFrame = new LogIn();
		
		//0:�O, 1:�_, 2:����
		switch(reply){
		case 0:	//�O�o�G�̡A�ݥ���J�b���B�K�X
			//HIDE_ON_CLOSE�G�u�O���õ����B�S�������{��
			loginFrame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
			loginFrame.setSize(300,160);
			loginFrame.setVisible(true);
			loginFrame.setLocationRelativeTo(null);
			/*if(loginFrame.getSuccess()){
				Publisher Publisher = new Publisher();
				Publisher.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				Publisher.setSize(650,450);
				Publisher.setVisible(true);
				Publisher.setLocationRelativeTo(null);	//�m��
			}*/
			break;
		case 1:	//���O�o���̡A�i�����i�J
			NotPublisher notPublisher = new NotPublisher();
			//EXIT_ON_CLOSE�G�����פ�{��
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