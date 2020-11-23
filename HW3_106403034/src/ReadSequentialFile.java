import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

//讀整個序列化物件(整個物件都讀出來，包括時間、內容、愛心)
public class ReadSequentialFile {
	private static ObjectInputStream input;
	
	public static PostSerializable returnPost(){	//開啟Post檔
		PostSerializable postSerializable;
		openFile(Paths.get("post"));
		postSerializable = readRecords();
		closeFile();
		return postSerializable;
	}
	
	/*public static PostSerializable importOther(Path path){	//匯入檔案
		PostSerializable postSerializable;
		openFile(path);
		postSerializable = readRecords();
		closeFile();
		return postSerializable;
	}*/
	
	public static String importContent(Path path){	//匯入內容
		String string;
		openFile(path);
		string = readContent();
		closeFile();
		return string;
	}
	
	public static void openFile(Path path){
		try{
			input = new ObjectInputStream(Files.newInputStream(path));
		}
		catch(IOException ioException){
			System.out.println("Error opening file.");
		}
	}
	
	public static PostSerializable readRecords(){
		try{
			PostSerializable record = (PostSerializable)input.readObject();
			return record;
		}
		catch(EOFException endoOfFileException){
			System.out.printf("%nNo more records%n");
			return  null;
		}
		catch(ClassNotFoundException classNotFoundException){
			System.out.println("Invalid object type. Terminating.");
			return  null;
		}
		catch(IOException ioException){
			System.out.println("Error reading from file. Terminating.");
			return  null;
		}
	}
	
	public static String readContent(){	//回傳內容(字串)
		try{
			String content = (String)input.readObject();
			return content;
		}
		catch(EOFException endoOfFileException){
			System.out.printf("%nNo more records%n");
			return  null;
		}
		catch(ClassNotFoundException classNotFoundException){
			System.out.println("Invalid object type. Terminating.");
			return  null;
		}
		catch(IOException ioException){
			System.out.println("Error reading from file. Terminating.");
			return  null;
		}
	}
	
	public static void closeFile(){
		try{
			if(input!=null){
				input.close();
			}
		}
		catch(IOException ioException){
			System.out.println("Error closing file. Terminating.");
		}
	}
}
