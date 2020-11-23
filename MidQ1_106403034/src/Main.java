//姓名:蘇敏宜 學號:106403034 系級:資管三B

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;

import javax.swing.JFrame;
import javax.swing.JOptionPane;



public class Main {
	
	public static void main(String[] args) {
		int reply = JOptionPane.showConfirmDialog(null,"Are you pharmacist?" , 
				"Login", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
		//reply 0:是, 1:否, 2:取消
		switch(reply) {
		case 0:
			Pharmacist pharmacist = new Pharmacist();
			pharmacist.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			pharmacist.setSize(650,450);
			pharmacist.setVisible(true);
			pharmacist.setLocationRelativeTo(null);	//置中
			break;
		case 1:
			NotPharmacist notPharmacist = new NotPharmacist();
			//EXIT_ON_CLOSE：直接終止程式
			notPharmacist.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			notPharmacist.setSize(650,450);
			notPharmacist.setVisible(true);
			notPharmacist.setLocationRelativeTo(null);	//置中
			break;
		case 2:
			break;
		}
	}

}
