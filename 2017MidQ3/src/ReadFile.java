import java.io.EOFException;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

import org.xml.sax.InputSource;

public class ReadFile {
	//private static ObjectInputStream input;
	private static Scanner input;
	private static Grade[] grade;
	private static ArrayList<Grade> gradeAL;
	
	public static ArrayList<Grade> returnGrade() {
		openFile(Paths.get("grade.txt"));
		readGrade();
		closeFile();
		return gradeAL;
	}
	
	private static void openFile(Path path) {
		try{
			input = new Scanner(path);
			//input = new Scanner(new File("grade.txt"));
			//input = new ObjectInputStream(Files.newInputStream(path));
		}
		catch(IOException ioException){
			System.out.println("Error opening file.");
		}
	}
	
	private static void readGrade() {
		//try {
			
			gradeAL = new ArrayList<Grade>();
			while(true) {
				int id, score;
				if(input.hasNextLine())
				{
					id = input.nextInt();
					score = input.nextInt();
					Grade g = new Grade(id, score);
					gradeAL.add(g);
				}
				/*Grade grade = (Grade)input.readObject();
				gradeAL.add(grade);*/
				//System.out.printf("id:%d score:%d\n", g.getId(), g.getScore());
			}
			
		//}
		/*catch(EOFException endoOfFileException){
			System.out.printf("%nNo more records%n");
			//return  null;
		}
		catch(ClassNotFoundException classNotFoundException){
			System.out.println("Invalid object type. Terminating.");
			//return  null;
		}
		catch(IOException ioException){
			System.out.println("Error reading from file. Terminating.");
			//return  null;
		}*/
	}
	
	private static void closeFile() {
		if(input!=null){
				input.close();
		}
		/*catch(IOException ioException){
			System.out.println("Error closing file. Terminating.");
		}*/
	}
}
