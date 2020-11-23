import java.io.IOException;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.NoSuchElementException;
import java.util.Scanner;


public class CreateSequentialFile {
	private static ObjectOutputStream output;	//output data to file
	
	public static void main(String[] args) {
		openFile();
		addRecords();
		closeFile();
	}
	
	public static void openFile(){
		try{
			output = new ObjectOutputStream(
				Files.newOutputStream(Paths.get("clients.ser")));
		}
		catch(IOException ioException){
			System.out.println("Error opening file. Terminating.");
			System.exit(1);
		}
	}
	
	//add records to file
	public static void addRecords(){
		Scanner input = new Scanner(System.in);
		
		System.out.printf("%s%n%s%n?",
			"Enter account number, first name, last name and balance.",
			"End end-of-file indicator to end input.");
		
		while(input.hasNext()){
			try{
				//create a new record
				Account record = new Account(input.nextInt(),
					input.next(), input.next(), input.nextDouble());
				//把record寫進output這個ObjectOutputStream
				output.writeObject(record);
			}
			catch(NoSuchElementException noSuchElementException){
				System.out.println("Invalid input. Please try again.");
			}
			catch(IOException ioException){
				System.out.println("Error writing to file. Terminating.");
			}
			System.out.print("? ");
		}
	}
	
	public static void closeFile(){
		try{
			if(output!=null){
				output.close();
			}
		}
		catch(IOException ioException){
			System.out.println("Error closing file. Terminating.");
		}
	}

}
