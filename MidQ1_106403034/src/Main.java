//�m�W:Ĭ�өy �Ǹ�:106403034 �t��:��ޤTB

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;

import javax.swing.JFrame;
import javax.swing.JOptionPane;



public class Main {
	
	public static void main(String[] args) {
		int reply = JOptionPane.showConfirmDialog(null,"Are you pharmacist?" , 
				"Login", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
		//reply 0:�O, 1:�_, 2:����
		switch(reply) {
		case 0:
			Pharmacist pharmacist = new Pharmacist();
			pharmacist.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			pharmacist.setSize(650,450);
			pharmacist.setVisible(true);
			pharmacist.setLocationRelativeTo(null);	//�m��
			break;
		case 1:
			NotPharmacist notPharmacist = new NotPharmacist();
			//EXIT_ON_CLOSE�G�����פ�{��
			notPharmacist.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			notPharmacist.setSize(650,450);
			notPharmacist.setVisible(true);
			notPharmacist.setLocationRelativeTo(null);	//�m��
			break;
		case 2:
			break;
		}
	}

}
