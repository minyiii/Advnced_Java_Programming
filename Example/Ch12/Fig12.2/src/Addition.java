import javax.swing.JOptionPane;
public class Addition {

	public static void main(String[] args) {
		String firstNumber = JOptionPane.showInputDialog("Enter first number:");
		String secondNumber = JOptionPane.showInputDialog("Enter second number");
		
		int number1 = Integer.parseInt(firstNumber);
		int number2 = Integer.parseInt(secondNumber);
		
		int sum = number1 + number2;
		JOptionPane.showMessageDialog(null, "Th sum is "+sum, "Sum of two Integer", JOptionPane.PLAIN_MESSAGE);
	}

}
