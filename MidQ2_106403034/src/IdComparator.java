import java.util.Comparator;

public class IdComparator implements Comparator<Invoice>{
	@Override
	public int compare(Invoice invoice1, Invoice invoice2) {
		int difference = invoice1.getPartNumber()-invoice2.getPartNumber();
		return difference;
	}
}
