import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;


public class WriteContent {		//另存內容按鈕
	private static File file;
	
	public static void writeToFile(Path path, String content){
		openFile(path);
		write(content);
		//closeFile();
	}
	
	
	public static void openFile(Path path){
		file = new File(path.toString());
	}
	
	public static void write(String string){
		try{
			file.createNewFile();
			FileWriter fileWriter = new FileWriter(file);
			fileWriter.write(string);
			fileWriter.flush();
			fileWriter.close();
		}
		catch(IOException ioException){
			System.out.println("Error writing file. Terminating.");
			System.exit(1);
		}
	}
}