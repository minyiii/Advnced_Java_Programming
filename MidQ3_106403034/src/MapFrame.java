import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class MapFrame extends JFrame{
	private List<Position> map;
	private JPanel inputPanel;
	private JLabel inputLabel;
	private JTextField inputX;
	private JTextField inputY;	
	private JPanel mapPanel;
	private JPanel block;
	private JPanel road;
	private JPanel other;
	private JButton runButton;
	private int[][] maze;
	
	public MapFrame(List<Position> map) {
		super("走迷宮");
		setLayout(new BorderLayout());
		this.map = map;
		maze = ReadMap.returnMaze();
		
		inputPanel = new JPanel();
		inputLabel = new JLabel("選擇終點(x, y):");
		inputPanel.add(inputLabel);
		inputX = new JTextField(5);
		inputY = new JTextField(5);
		inputPanel.add(inputX);
		inputPanel.add(inputY);
		
		add(inputPanel, BorderLayout.NORTH);
		
		mapPanel = new JPanel();
		mapPanel.setLayout(new GridLayout(10,10));
		
		block = new JPanel();
		block.setBackground(Color.BLACK);
		road = new JPanel();
		road.setBackground(Color.WHITE);
		other = new JPanel();
		other.setBackground(Color.BLUE);
		
		drawMap();
		
		add(mapPanel, BorderLayout.CENTER);
		
		runButton = new JButton("run");
		add(runButton, BorderLayout.SOUTH);
	}
	
	private void drawMap() {
		for(int i=1;i<11;i++) {
			for(int j=1;j<11;j++) {
				if(i==1&&j==1) {
					other = new JPanel();
					other.setBackground(Color.BLUE);
					mapPanel.add(other);
				}
				else if(maze[i][j]==1) {
					block = new JPanel();
					block.setBackground(Color.BLACK);
					mapPanel.add(block);
				}
				else if(maze[i][j]==0){
					road = new JPanel();
					road.setBackground(Color.WHITE);
					mapPanel.add(road);
				}
			}
		}
		
		
		/*int row, col, count=0;
		for(row=1;row<11;row++) {
			for(col=1;col<11;col++) {
				Position pBlock = map.get(count);
				if(pBlock.x==row && pBlock.y==col) {	//有找到就是障礙物
					mapPanel.add(block);
					count++;
				}
				else{	//沒找到代表不是障礙物，印空白
					if(row==1 && col==1) {
						mapPanel.add(other);
					}
					else if(row==5 && col==1) {
						mapPanel.add(other);
					}
					else {
						mapPanel.add(road);
					}
				}
			}
		}*/
		validate();
		repaint();
	}

}
