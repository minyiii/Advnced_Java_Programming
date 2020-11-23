import java.util.Comparator;

public class NameComparator implements Comparator<Invoice>{
	@Override
	public int compare(Invoice invoice1, Invoice invoice2) {
		int difference = invoice1.getPartDescription().charAt(0)-invoice2.getPartDescription().charAt(0);
		return difference;
	}
}
