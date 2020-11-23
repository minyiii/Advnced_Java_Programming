import java.util.concurrent.ArrayBlockingQueue;

public class BlockingBuffer implements Buffer{
	private final ArrayBlockingQueue<Integer> buffer;
	
	public BlockingBuffer() {
		buffer = new ArrayBlockingQueue<Integer>(1);	//()內表array大小
	}
	
	public void blockingPut(int value) throws InterruptedException{
		buffer.put(value);
		System.err.printf("%s%2d\t%s%d\n", "Producer writes ", value, "Buffer cells occupied: ",buffer.size());
	}
	
	public int blockingGet() throws InterruptedException {
		int readValue = buffer.take();
		System.err.printf("%s %2d\t%s%d\n", "Consumer reads ", readValue, "Buffer cells occupied: ", buffer.size());
		return readValue;
	}
}
