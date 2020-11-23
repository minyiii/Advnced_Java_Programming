import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;


public class WordTypeCount {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Map<String, Integer> myMap = new HashMap<String, Integer>();
		createMap(myMap);
		displayMap(myMap);
	}
	
	private static void createMap(Map<String, Integer> map){
		Scanner scanner = new Scanner(System.in);
		System.out.println("Enter a string: ");
		String input = scanner.nextLine();
		String[] tokens = input.split(" ");	//切割字串成array
		
		for(String token:tokens){
			String word = token.toLowerCase();
			
			if(map.containsKey(word)){	//若已存在於map中
				int count = map.get(word);
				map.put(word, count+1);
			}
			else
				map.put(token, 1);
		}
	}
	
	private static void displayMap(Map<String, Integer> map){
		Set<String> keys = map.keySet();
		
		TreeSet<String> sortedKeys = new TreeSet<String>(keys);
		System.out.printf("\nMap contains:\nKey\tValue\n");
		
		for(String key:sortedKeys){
			System.out.printf("%-10s%10S\n", key, map.get(key));
		}
		System.out.printf("\nsize: %d\nisEmpty: %b\n", map.size(), map.isEmpty());
	}
}
