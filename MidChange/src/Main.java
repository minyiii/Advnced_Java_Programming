import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Date;

public class Main {
	
	public static void main(String[] args) {
		try{
			FileOutputStream fos = new FileOutputStream("�����k.ser");
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			PostSerializable ps = new PostSerializable("�����k����r���e~~~~~", new Date());
			oos.writeObject(ps);
			oos.close();
		}
		catch(IOException ioException){
			System.out.println("Error updating file. Terminating.");
		}
	}
}
