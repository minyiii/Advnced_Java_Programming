import java.util.List;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;


public class ReadMapFile {
	private static File file;
	private static List<Integer> list = new ArrayList<Integer>();
	public static List ReadFromFile(){
		file = new File("map.txt");
		read();
		return list;
	}
	
	private static void read(){
		try{
			Scanner scanner = new Scanner(file);
			while(scanner.hasNextInt()){
				list.add(scanner.nextInt());
			}
		}
		catch(FileNotFoundException fileNotFoundException){
			 System.out.println("File not found.");
		}
	}
}
