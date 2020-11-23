import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ListIterator;
import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.util.Scanner;

import javax.swing.JOptionPane;

public class ReadMap {
	private static File file;
	//private boolean maze[][];
	//private Position[] blocks;
	private static List<Position> blocksList = new ArrayList<Position>();
	
	public static List<Position> readMap (Path path){
		openFile(path);
		read();
		showMap();
		return blocksList;
	}
	
	private static void openFile(Path path) {
		file = new File(path.toString());
	}
	
	//java���Φr��G��r��.split("�n���Ϊ�����");
	private static void read() {
		try {
			Scanner s = new Scanner(file);
			
			int row = 1;
			/*while(s.hasNextLine()) {
				while(s.hasNextInt()) {
					int col = s.nextInt();
					blocksList.add(new Position(row, col));
					System.out.printf("add block: (%d, %d)\n", row, col);
				}
				row++;
			}*/
			//int count = 0;
			while(s.hasNextLine()) {
				String blockString = s.nextLine();
				String[] blockS = blockString.split(" ");	//���Φr��
				for(String b: blockS) {
					int col = Integer.parseInt(b);	//�r����int
					Position p = new Position(row, col);
					blocksList.add(p);
					//System.out.printf("add block: (%d, %d) %d\n", p.x, p.y, blocksList.size());
				}
				row++;
			}
		}
		catch(FileNotFoundException fileNotFoundException) {
			JOptionPane.showMessageDialog(null, file.getName()+" does not exit", "ERROR", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	private static void showMap() {
		//�_�I(0,0)->(1,1)�B���I(4,0)->(5,1)
		int row, col, count=0;
		for(row=1;row<11;row++) {
			for(col=1;col<11;col++) {
				//Position pCompare = new Position(row, col);
				Position pBlock = blocksList.get(count);
				//int index = blocksList.indexOf(pCompare);
				if(pBlock.x==row && pBlock.y==col) {	//�����N�O��ê��
					System.out.print("�p");
					count++;
				}
				else{	//�S���N���O��ê���A�L�ť�
					if(row==1 && col==1) {
						System.out.print("��");
					}
					else if(row==5 && col==1) {
						System.out.print("��");
					}
					else {
						System.out.print("�@");
					}
				}
			}
			System.out.println();
		}
	}
}
