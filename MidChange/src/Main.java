import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Date;

public class Main {
	
	public static void main(String[] args) {
		try{
			FileOutputStream fos = new FileOutputStream("普拿疼.ser");
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			PostSerializable ps = new PostSerializable("普拿疼的文字內容~~~~~", new Date());
			oos.writeObject(ps);
			oos.close();
		}
		catch(IOException ioException){
			System.out.println("Error updating file. Terminating.");
		}
	}
}
