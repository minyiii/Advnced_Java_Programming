import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class Sort3 {

	public static void main(String[] args) {
		List<Time2> list = new ArrayList<Time2>();
		
		list.add(new Time2(6,24,34));
		list.add(new Time2(18,14,58));
		list.add(new Time2(6,05,34));
		list.add(new Time2(12,14,58));
		list.add(new Time2(6,24,22));
		
		System.out.printf("Unsorted array elements:\n%s\n", list);	//·|call toString()
		
		Collections.sort(list, new TimeComparator());
		System.out.printf("Sorted list elements:\n%s\n", list);
	}

}
