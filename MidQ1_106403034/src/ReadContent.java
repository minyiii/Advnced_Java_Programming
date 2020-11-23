import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.util.Scanner;



//���Ū�ɮצr�ꤺ�e
public class ReadContent {			//�פJ���s
	private static File file;
	
	public static String readFromFile(Path path){	//�פJ���e(Ū�ɮ�)
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
			String readContent = "";	//�Y�S��="",�פJ�ť��ɮ׷|�o�Ϳ��~
			String readNext;	//�U�@��
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