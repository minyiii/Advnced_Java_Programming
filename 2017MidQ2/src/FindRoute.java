import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.JFileChooser;

public class FindRoute {
	private static List<Position> blocksList;
	private int[][] maze;
	
	public FindRoute(){	//constructor
		openMap();
		int[][] maze = new int[12][12];
		Arrays.fill(maze, 0);	//先皆可走
		
		for(Position p:blocksList) {
			maze[p.x][p.y] = 1;
		}
		
		Position[] blockArray = blocksList.toArray(new Position[blocksList.size()]);
		for(Position p:blockArray) {
			System.out.printf("(" + p.x + ", "+ p.y+")\n");
		}
		
		/*int count = 0;
		for(int i=0; i<12; i++) {
			for(int j=0; j<12; j++) {
				Position p = blocksList.get(count);
				if(i==0 || i==11 || j==0 || j==11) {
					maze[i][j] = true;
				}
				else if(p.x==i && p.y==j) {	//1不可走(true)
					maze[i][j] = true;
					count++;
				}
				else{
					maze[i][j]=false;
				}
			}
		}*/
		System.out.println("show maze:");
		for(int i=0;i<12;i++) {
			for(int j=0;j<12;j++) {
				System.out.print(maze[i][j]);
			}
			System.out.println();
		}
	}
	
	private static void openMap() {
		JFileChooser fileChooser = new JFileChooser(new File("."));
		fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		int IsOpen = fileChooser.showOpenDialog(null);
		if(IsOpen == JFileChooser.APPROVE_OPTION){	//確認匯入
			blocksList = new ArrayList(ReadMap.readMap(fileChooser.getSelectedFile().toPath()));
		}
		else
			System.exit(1);
	}
}
