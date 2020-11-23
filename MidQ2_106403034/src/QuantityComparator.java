import java.util.Comparator;

public class QuantityComparator implements Comparator<Invoice>{
	@Override
	public int compare(Invoice invoice1, Invoice invoice2) {
		int difference = invoice1.getQuantity()-invoice2.getQuantity();
		return difference;
	}
}
