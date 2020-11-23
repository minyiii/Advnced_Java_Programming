
public class Position {
	//若元素為static，用arrayList add會被覆蓋
	/*讓修飾的屬性變為static，意味著該類的所有物件共享同一個屬性，
	 * 所以儘管集合裡存的是不同的物件，但是物件的屬性還是同一個值，
	 * 修改其中任何一個，其他的也會被修改
	 * */
	public int x;
	public int y;
	public Position(int xPos, int yPos) {
		x = xPos;
		y = yPos;
	}
	
	/*public static void printPos() {
		System.out.print("("+ x +", " + y + ")");
	}
	
	public static int getX() {
		return x;
	}
	
	public static int getY() {
		return y;
	}*/
}
