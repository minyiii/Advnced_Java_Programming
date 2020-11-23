import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;



public class InputFrame extends JFrame{
	private final JLabel inputJLabel;		//��J�ؤW���r�ԭz
	private final JTextField inputJField;	//��J����
	private final JPanel buttonJPanel; 		//���s��
	private final JButton cancelJButton;	//�������s
	private final JButton sureJButton;		//�T�{���s
	
	public InputFrame(){	//constructor
		super("�C�X�Ҧ���3�άO3�����ƪ��Ʀr");
		setLayout(new BorderLayout());
		
		
		// ��J����r //
		inputJLabel = new JLabel("�п�J�@�ӥ����");
		add(inputJLabel, BorderLayout.NORTH);
		
		// ��J���خ� //
		inputJField = new JTextField(5);
		add(inputJField, BorderLayout.CENTER);
		
		// ���s�� //
		buttonJPanel = new JPanel();
		// �����s //
		cancelJButton = new JButton("����");
		cancelJButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent event){
				dispose();	//��������
			}
		});
		buttonJPanel.add(cancelJButton);
		// �T�{�� //
		sureJButton = new JButton("�T�{");
		sureJButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent event){
				calculate(Integer.parseInt(inputJField.getText()));
			}
		});
		buttonJPanel.add(sureJButton);
		add(buttonJPanel, BorderLayout.SOUTH);
	}
	
	private static boolean checkPositive(int num){	//�ݬO�_�������
		if(num>0)
			return true;
		else{
			JOptionPane.showMessageDialog(null, "�п�J����ơA���s��J", "���s��J", JOptionPane.PLAIN_MESSAGE);
			return false;
		}
	}
	
	private static boolean hasThree(String str){	//�ݼƦr���S��3
		int i=0;
		boolean result = false;
		
		while(i<str.length()){
			if(str.charAt(i)=='3'){
				result = true;
				break;
			}
			else
				i++;
		}
		return result;
	}
	
	private static void showCalResult(int num, Collection<Integer> set){
		String show = "";
		
		if(set.isEmpty()){
			show = "�ŪŦp�]QQ";
		}
		else{
			Set<Integer> ordered = new TreeSet<Integer>(set);
			Iterator<Integer> iterator = ordered.iterator();
			while(iterator.hasNext()){
				show += iterator.next();
				if(iterator.hasNext()){
					show += ", ";
				}
			}
		}
		JOptionPane.showMessageDialog(null, show, Integer.toString(num), JOptionPane.PLAIN_MESSAGE);
	}
	
	private static void calculate(int num){	//�M��㰣3�μƦr���t3���A�ëإߤ@��arrayList
		if(checkPositive(num)){
			List<Integer> list = new ArrayList<Integer>();
			for(int i=1; i<=num ;i++){
				//�Y�㰣3
				if(i%3==0)			
					list.add(i);
				//�Y�Ʀr����3
				else if(hasThree(Integer.toString(i))){
					list.add(i);
				}
			}
		Set<Integer> set = new HashSet<Integer>(list);
		showCalResult(num, set);
		}
	}
}
