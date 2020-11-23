import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ReadPost {
	private static ObjectInputStream input;
	
	//根據JComboBox點的名稱回傳該檔案的整個序列化物件
	public static PostSerializable returnPost(String medicine) {
		PostSerializable postSerializable;
		openFile(Paths.get(medicine));	//ex:"百保能 ACE AX"
		postSerializable = readPost();
		closeFile();
		return postSerializable;
	}
	
	public static void openFile(Path path) {
		try{
			input = new ObjectInputStream(Files.newInputStream(path));
		}
		catch(IOException ioException){
			System.out.println("Error opening file.");
		}
	}
	
	public static PostSerializable readPost(){	//回傳內容(字串)
		try{
			PostSerializable ps = (PostSerializable)input.readObject();
			//System.out.println(postSerializable.getEditTime().toString());
			return ps;
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
