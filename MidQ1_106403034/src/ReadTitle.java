import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

//讀取post.txt檔的title名稱，放進頁面的JComboBox中
public class ReadTitle {
	public static String[] returnTiles() {
		List<String> names = new ArrayList<String>();
		//String[] namesArray = new String[15];
		int count = 0;
		try{
			Scanner s = new Scanner(new File("posts.txt"));
			while(s.hasNextLine()) {
				names.add(s.nextLine());
			}
			return names.toArray(new String[names.size()]);
			//return (String[])names.toArray();
		}
		catch(FileNotFoundException ileNotFoundException){
			System.out.println("Error scanning the file. Terminating.");
			return null;
		}
	}
}
