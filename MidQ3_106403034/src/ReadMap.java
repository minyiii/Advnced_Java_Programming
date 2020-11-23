import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ListIterator;
import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class ReadMap {
	private static int[][] maze;
	private static File file;
	private static List<Position> blocksList = new ArrayList<Position>();
	
	public static List<Position> readMap (){
		openFile();
		read();
		//showMap();
		return blocksList;
	}
	
	public static int[][] returnMaze() {
		return maze;
	}
	
	private static void openFile() {
		file = new File("map.txt");
	}
	
	//java切割字串：原字串.split("要切割的條件");
	private static void read(){
		try {
			Scanner s = new Scanner(file);
			maze = new int[12][12];
			int row = 1;
			
			while(s.hasNextLine()) {
				String blockString = s.nextLine();
				String[] blockS = blockString.split(" ");	//切割字串
				for(String b: blockS) {
					int col = Integer.parseInt(b);	//字串轉int
					Position p = new Position(row, col);	//1為牆壁
					blocksList.add(p);
					System.out.printf("block: (%d, %d) %d\n", p.x, p.y, blocksList.size());
				}
				row++;
			}
			
			int count =0;
			for(int i=1;i<11;i++) {
				for(int j=1;j<11;j++) {
					if(blocksList.get(count).x==i && blocksList.get(count).y==j) {
						maze[i][j] = 1;
					}
					else
						maze[i][j] = 0;
				}
			}
		}
		catch(FileNotFoundException fileNotFoundException) {
			JOptionPane.showMessageDialog(null, file.getName()+" does not exit", "ERROR", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	private static void showMap() {
		//起點(0,0)->(1,1)、終點(4,0)->(5,1)
		MapFrame mapFrame = new MapFrame(blocksList);
		mapFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mapFrame.setSize(550, 650);
		mapFrame.setVisible(true);
		mapFrame.setLocationRelativeTo(null);
	}
}
