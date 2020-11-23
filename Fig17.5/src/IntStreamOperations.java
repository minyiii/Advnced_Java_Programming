import java.util.function.Function;
import java.util.stream.IntStream;
import java.util.Arrays;


public class IntStreamOperations {

	public static void main(String[] args) {
		int[] values = {3, 10, 6, 1, 4, 8, 2, 5, 9, 7};
		System.out.print("Original values: ");
		
		//()->System.out.println("Zero parameter lambda");
		
		
		IntStream.of(values).forEach(value -> System.out.printf("%d ", value));
		
		System.out.println();
		System.out.printf("\nCount: %d\n", IntStream.of(values).count());
		System.out.printf("\nMin: %d\n", IntStream.of(values).min().getAsInt());
		System.out.printf("\nMax: %d\n", IntStream.of(values).max().getAsInt());
		System.out.printf("Sum: %d\n", IntStream.of(values).average().getAsDouble());
		
		System.out.printf("\nSum via reduce method: %d\n", 
			IntStream.of(values).reduce(0, (x,y) -> x+y));
		System.out.printf("\nSum of squares via reduce method: %d\n", 
				IntStream.of(values).reduce(0, (x,y) -> x+y*y));	//平方和
		System.out.printf("\nSum via reduce method: %d\n", 
				IntStream.of(values).reduce(1, (x,y) -> x*y));	//元素相乘
		//偶數
		System.out.printf("\nEven values diaplayed in sorted order: ");
		IntStream.of(values)
			.filter(value->value%2==0)
			.sorted()
			.forEach(value->System.out.printf("%d", value));
		System.out.println();
		
		//奇數*10
		System.out.printf("Odd values multipled by 10 displayed in sorted order: ");
		IntStream.of(values).filter(value->value%2!=0)
			.map(value->value*10)
			.sorted()
			.forEach(value->System.out.printf("%d ",value));
		System.out.println();
		
		System.out.printf("\nSum of integers from 1 to 9: %d\n",
			IntStream.range(1, 10).sum());
		System.out.printf("Sum of integers from 1 to 10: %d\n", 
			IntStream.rangeClosed(1, 10).sum());
	}

}