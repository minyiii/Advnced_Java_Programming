import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;

//姓名:蘇敏宜 學號:106403034 系級:資管三B


public class Main {

	public class Total{
		public String description;
		public double amount;
		
		public Total(String s, double d) {
			description = s;
			amount = d;
		}
	}

	public static void main(String[] args) {
		String input="";
		String showChoice = "\nFunctions: Report/Select/Sort\n"
				+ "Choice(-1 to exit): ";
		Invoice[] invoices = {
			new Invoice(83, "Electric sander", 7, 57.98),
			new Invoice(24, "Power saw", 18, 99.99),
			new Invoice(7, "Sledge hammer", 11, 21.50),
			new Invoice(77, "Hammer", 76, 11.99),
			new Invoice(39, "Lawn mower", 3, 79.50),  
			new Invoice(68, "Screwdriver", 106, 6.99), 
			new Invoice(56, "Jig saw", 21, 11.00),  
			new Invoice(3, "Wrench", 34, 7.50), 
			new Invoice(45, "Wrench", 13, 7.50),  
			new Invoice(22, "Hammer", 47, 11.99)  };
		List<Invoice> totalList = Arrays.asList(invoices);
		List<Invoice> sortList = Arrays.asList(invoices);
		Map<String, Double> invoiceMap = new HashMap<>();
		
		System.out.println("Welcome to invoice management system.\n" + showChoice);
		Scanner scanner = new Scanner(System.in);
		
		input = scanner.next();
		while(input.compareTo("-1")!=0) {	//若輸入不為-1
			//第一種
			if(input.compareTo("report")==0) {
				createMap(totalList, invoiceMap);
				printMap(invoiceMap);
			}
			//第二種
			else if(input.compareTo("select")==0) {
				System.out.printf("Input the range to show (min, max):");
				String s = scanner.nextLine();
				
				printMap(invoiceMap);
			}
			//第三種
			else if(input.compareTo("sort")==0) {
				System.out.print("Input the attribute to sort.\n"
						+ "ID/Name/Price/Quantity: ");
				String sortBy = scanner.next();
				if(sortBy.compareTo("id")==0) {
					Collections.sort(sortList, new IdComparator());
					printSort("id", sortList);
				}
				else if(sortBy.compareTo("name")==0) {
					Collections.sort(sortList, new NameComparator());
					printSort("name", sortList);
				}
				else if(sortBy.compareTo("price")==0) {
					Collections.sort(sortList, new PriceComparator());
					printSort("price",sortList);
				}
				else if(sortBy.compareTo("quantity")==0) {
					Collections.sort(sortList, new QuantityComparator());
					printSort("quantity",sortList);
				}
			}
			else
				System.out.println("Wrong Input. Enter again!!");
			System.out.println(showChoice);
			input = scanner.next();
		}
		
		System.exit(1);
	}
	
	private static void createMap(List<Invoice> list, Map<String, Double> map) {
		for(Invoice invoice:list) {
			double amount = invoice.getPrice()*invoice.getQuantity();
			String description = invoice.getPartDescription();
			if(map.containsKey(description)) {	//若已有就先取出其數量，再加入
				double count = map.get(description);
				map.put(description, count+amount);
			}
			else
				map.put(description, amount);
		}
	}
	
	private static void printMap(Map<String, Double> map) {
		System.out.printf("\nInvoice group bydescription:\n");
		Set<String> keys = map.keySet();
		List<Total> sortByAmount = new ArrayList<Total>();
		/*Map<String, Double> sortMap;
		List<Double> values = (List<Double>) map.values();
		for(int i=0;i<map.size();i++) {
			double amount = Collections.min(values);
			
		}
		Set<Double> amount = (Set<Double>) map.values();*/
		
		TreeSet<String> sortedKeys = new TreeSet<>(keys);	//依名字排序
		//TreeSet<Double> sortByAmount = new TreeSet<>(amount);	//依名字排序
		
		for(String key: sortedKeys) {
			double min=0;
			if(map.get(key)<min) {
				min = map.get(key);
				
			}
			System.out.printf("Description: %-15s Invoice amount: $%,10.2f\n", key, map.get(key));
		}
	}
	
	private static void printSort(String s, List<Invoice> list) {
		System.out.printf("\nResult of Invoices sort by part nuber(%s):\n",s);
		for(int i=0;i<list.size();i++) {
			System.out.printf(list.get(i)+"\n");
		}
	}
}
