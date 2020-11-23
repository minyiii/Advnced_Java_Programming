import java.util.Comparator;

public class PriceComparator implements Comparator<Invoice>{
	@Override
	public int compare(Invoice invoice1, Invoice invoice2) {
		int difference = (int)(invoice1.getPrice()-invoice2.getPrice());
		return difference;
	}
}
