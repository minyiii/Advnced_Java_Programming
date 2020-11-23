package com.fishroad;
import java.util.Stack;

class Step{
	int x,y,d;
	public Step(int x,int y,int d) {
		this.x = x;//橫座標 
		this.y = y;//縱座標 
		this.d = d;//方向 
	}
}

public class MazeTest {
	public static void main(String[] args) { 
		/* 迷宮定義 int[][] maze = {{1,1,1,1,1,1,1,1,1,1}, 
		{1,0,0,1,1,0,1,1,1,1}, {1,1,0,0,0,1,1,1,1,1}, 
		{1,0,1,0,0,0,0,0,1,1}, {1,0,1,1,1,0,0,1,1,1}, 
		{1,1,0,0,1,1,0,0,0,1}, {1,0,1,1,0,0,1,1,0,1}, 
		{1,1,1,1,1,1,1,1,1,1}}; */
		//行走的方向東南西北四個方向 int[][] move = {{0,1},{1,0},{0,-1},{-1,0}}; 
		//列印迷宮 
		for(int i=0;i<=7;i++){ 
			for(int j=0;j<=9;j++){ 
				if(maze[i][j] == 1){ 
					System.out.print("█"); }
				else{ System.out.print("*"); } 
			} System.out.print("/n"); } 
		Stack s = new Stack(); 
		Stack s1 = new Stack(); 
		int a = path(maze, move, s); 
		Step aa = null; //反轉堆疊裡面的元素 
		Stack s2 = new Stack(); 
		/*將s堆疊裡面的元素出棧,並且將元素壓到s2的堆疊裡面,
		這樣就能保證是按行走的順序輸出座標,否則是倒序輸出的座標 */
		while(!s.isEmpty()){
			Step step = (Step) s.pop(); s2.push(step); 
		} //打印出最後的座標順序 
		StringBuffer aaa = new StringBuffer() ; 
		while(!s2.isEmpty()){ 
			Step step = (Step) s2.pop(); 
			aaa.append("("+step.x+","+step.y+")"+"→"); 
		} 
		String str = aaa.toString(); 
		System.out.print(str.substring(0, str.length()-1));
		System.out.println("/r"); System.out.println("走過的路徑為:"); 
		//打印出走過的所有座標位置 
		for(int i=0;i<=7;i++){ 
			for(int j=0;j<=9;j++){ 
				if(maze[i][j] == 1){ 
					System.out.print("█"); 
				}
				else if(maze[i][j] == -1){ 
					System.out.print("#"); 
				}
				else{ System.out.print("*"); } } 
			System.out.print("/n"); 
		} 
		}
	//計算路徑 
	public static int path(int[][] maze,int[][] move,Stack s){ 
		Step temp = new Step(1,1,-1); //起點 
		s.push(temp); 
		while(!s.isEmpty()){ 
			temp = (Step) s.pop(); 
			int x = temp.x;
			int y = temp.y; 
			int d = temp.d+1; 
			//如果d大於4也就是說當前座標的無路可走,只能後退一步,找後一座標的下一個方向,在繼續尋找座標 
			while(d<4){ 
				int i = x + move[d][0]; 
				int j = y + move[d][1]; 
				if(maze[i][j] == 0){
					//該點可達 
					temp = new Step(i,j,d); 
					//到達新點 
					s.push(temp);
					x = i;
					y = j; 
					maze[x][y] = -1; 
					//到達新點,標誌已經到達 
					if(x == 6 &;&; y == 8){ 
						return 1; //到達出口
					}
				}
			}
		}

},迷宮有路,返回1 
	}
else{ 
	d = 0; //重新初始化方向
	} 
}
else{ 
	d++; //改變方向 } 
	} 
} 
	return 0; }}
}