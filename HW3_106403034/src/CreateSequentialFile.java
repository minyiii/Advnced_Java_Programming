import java.io.IOException;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

//將整個序列化物件匯出成檔案
public class CreateSequentialFile {
	private static ObjectOutputStream output;
	
	public static void UpdatePost(PostSerializable postSerializable){	//更新Post檔的內容
		openFile(Paths.get("post"));
		updateRecords(postSerializable);
		closeFile();
	}
	
	/*public static void SavaAs(Path path, PostSerializable postSerializable){
		openFile(path);
		updateRecords(postSerializable);
		closeFile();
	}*/
	
	public static void SavaAs(Path path, String string){
		openFile(path);
		saveContent(string);
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
	
	private static void saveContent(String content){	//把編輯區的內容(字串)存檔
		try{
			output.writeObject(content);
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
