import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;


//Åª¤J¦a¹Ï (functional programming)
public class ReadMapFile {
	
	public static List readFromFile() {
		try{
			Pattern pattern = Pattern.compile("\\s+");
			List<String> elements = Files.lines(Paths.get("map.txt"))
				.flatMap(line->pattern.splitAsStream(line))
				.collect(Collectors.toList());
			return elements;
		}
		catch(IOException iOException){
			 System.out.println("Erroro, IOException.");
			 return null;
		}
	}
}
