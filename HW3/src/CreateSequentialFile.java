import java.io.IOException;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


public class CreateSequentialFile {
	private static ObjectOutputStream output;
	
	public static void UpdatePost(PostSerializable postSerializable){	//更新Post檔的內容
		openFile(Paths.get("post"));
		updateRecords(postSerializable);
		closeFile();
	}
	
	public static void SavaAs(Path path, PostSerializable postSerializable){
		openFile(path);
		updateRecords(postSerializable);
		closeFile();
	}
	
	private static void openFile(Path path){
		try{
			output = new ObjectOutputStream(
				Files.newOutputStream(path));
		}
		catch(IOException ioException){
			System.out.println("Error opening file. Terminating.");
			System.exit(1);
		}
	}
	
	private static void updateRecords(PostSerializable postSerializable){
		try{
			//output.reset();
			output.writeObject(postSerializable);
		}
		catch(IOException ioException){
			System.out.println("Error updating file. Terminating.");
			System.exit(1);
		}
	}
	
	private static void closeFile(){
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
