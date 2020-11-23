//姓名:蘇敏宜 學號:106403034 系級:資管三B
//帳號:login、密碼:loggg (只有發布者才需要登入)


import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class Main {

	public static void main(String[] args) {
		int reply = JOptionPane.showConfirmDialog(null,"是否為發布者?" , "登入", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
		LogIn loginFrame = new LogIn();
		
		//0:是, 1:否, 2:取消
		switch(reply){
		case 0:	//是發佈者，需先輸入帳號、密碼
			//HIDE_ON_CLOSE：只是隱藏視窗、沒有結束程式
			loginFrame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
			loginFrame.setSize(300,160);
			loginFrame.setVisible(true);
			loginFrame.setLocationRelativeTo(null);
			/*if(loginFrame.getSuccess()){
				Publisher Publisher = new Publisher();
				Publisher.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				Publisher.setSize(650,450);
				Publisher.setVisible(true);
				Publisher.setLocationRelativeTo(null);	//置中
			}*/
			break;
		case 1:	//不是發布者，可直接進入
			NotPublisher notPublisher = new NotPublisher();
			//EXIT_ON_CLOSE：直接終止程式
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