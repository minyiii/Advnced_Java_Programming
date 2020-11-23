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
	private final JLabel inputJLabel;		//輸入框上方文字敘述
	private final JTextField inputJField;	//輸入的框
	private final JPanel buttonJPanel; 		//按鈕區
	private final JButton cancelJButton;	//取消按鈕
	private final JButton sureJButton;		//確認按鈕
	
	public InputFrame(){	//constructor
		super("列出所有有3或是3的倍數的數字");
		setLayout(new BorderLayout());
		
		
		// 輸入的文字 //
		inputJLabel = new JLabel("請輸入一個正整數");
		add(inputJLabel, BorderLayout.NORTH);
		
		// 輸入的框框 //
		inputJField = new JTextField(5);
		add(inputJField, BorderLayout.CENTER);
		
		// 按鈕區 //
		buttonJPanel = new JPanel();
		// 取消鈕 //
		cancelJButton = new JButton("取消");
		cancelJButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent event){
				dispose();	//關閉視窗
			}
		});
		buttonJPanel.add(cancelJButton);
		// 確認紐 //
		sureJButton = new JButton("確認");
		sureJButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent event){
				calculate(Integer.parseInt(inputJField.getText()));
			}
		});
		buttonJPanel.add(sureJButton);
		add(buttonJPanel, BorderLayout.SOUTH);
	}
	
	private static boolean checkPositive(int num){	//看是否為正整數
		if(num>0)
			return true;
		else{
			JOptionPane.showMessageDialog(null, "請輸入正整數，重新輸入", "重新輸入", JOptionPane.PLAIN_MESSAGE);
			return false;
		}
	}
	
	private static boolean hasThree(String str){	//看數字有沒有3
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
			show = "空空如也QQ";
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
	
	private static void calculate(int num){	//尋找整除3或數字中含3的，並建立一個arrayList
		if(checkPositive(num)){
			List<Integer> list = new ArrayList<Integer>();
			for(int i=1; i<=num ;i++){
				//若整除3
				if(i%3==0)			
					list.add(i);
				//若數字中有3
				else if(hasThree(Integer.toString(i))){
					list.add(i);
				}
			}
		Set<Integer> set = new HashSet<Integer>(list);
		showCalResult(num, set);
		}
	}
}
