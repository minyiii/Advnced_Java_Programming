import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.util.Scanner;



//單純讀檔案字串內容
public class ReadContent {			//匯入按鈕
	private static File file;
	
	public static String readFromFile(Path path){	//匯入內容(讀檔案)
		String content;
		openFile(path);
		content = read();
		//closeFile();
		return content;
	}
	
	public static void openFile(Path path){
		file = new File(path.toString());
	}
	
	public static String read(){
		try{
			Scanner scanner = new Scanner(file);
			String readContent = "";	//若沒先="",匯入空白檔案會發生錯誤
			String readNext;	//下一行
			while(scanner.hasNextLine()){
				readNext= scanner.nextLine();
				readContent += readNext + "\n"; 
			}
			return readContent;
		}
		catch(FileNotFoundException ileNotFoundException){
			System.out.println("Error scanning the file. Terminating.");
			return null;
		}
	}
}