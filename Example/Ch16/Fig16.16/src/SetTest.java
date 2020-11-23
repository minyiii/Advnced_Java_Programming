import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;


public class SetTest {
	public static void main(String[] args){
		String[] colors = {"red", "white", "blue", "green", "gray",
			"orange", "tan", "white", "cyan", "peach", "gray", "orange"};
		List<String> list = Arrays.asList(colors);
		System.out.printf("List: %s\n", list);
		
		printNonDulicates(list);
	}
	
	private static void printNonDulicates(Collection<String> values){
		//Set<String> set = new HashSet<String>(values);
		Set<String> set = new TreeSet<String>(values);
		System.out.printf("\nNonDuplicates are: ");
		for(String value:set)
			System.out.printf("%s ", value);
		System.out.println();
	}
}