import java.io.FileNotFoundException;
import java.lang.SecurityException;
import java.util.Formatter;
import java.util.FormatterClosedException;
import java.util.NoSuchElementException;
import java.util.Scanner;


public class CreateTextFile {
	private static Formatter output;

	public static void main(String[] args) {
		openFile();
		addRecords();
		closeFile();
	}
	
	public static void openFile(){	//�@�w�n��Exception�B�z
		try{
			output = new Formatter("clients.txt");
		}
		catch(SecurityException securityException){	//�S���v��
			System.err.println("Write permission denied. Terminating.");
			System.exit(1);
		}
		catch(FileNotFoundException fileNotFoundException){	//�S��k�}���ɮ�
			System.err.println("Error opening file. Terminating.");
			System.exit(1);
		}
	}
	public static void addRecords(){
		Scanner input = new Scanner(System.in);
		System.out.printf("%s\n%s\n?",
				"Enter account number, first name, last name and balance.",
				"Enter end-of-file indicator to end input.");
		
		while(input.hasNext()){
			try{
				//output new record to file
				output.format("%d %s %s %.2f\n", 
						input.nextInt(), input.next(), input.next(), input.nextDouble());
				
			}
			catch(FormatterClosedException formatterClosedException ){	//formatter�w�Q����
				System.err.println("Error writing to file. Terminating.");
				break;
			}
			catch(NoSuchElementException noSuchElementException){	//if no more tokens are available
				System.err.println("Invalid input. Please try again.");
				input.nextLine();	//discard input so user can try again
			}
			System.out.print("? ");
		}
	}
	public static void closeFile(){
		if(output!=null)
			output.close();
	}

}
