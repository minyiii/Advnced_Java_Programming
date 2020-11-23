public class Mouse {
    private int startI, startJ; 
    private int endI, endJ; 
 
    public static void main(String[] args) {
    	//底下是自己製造的迷宮，主要是為了執行上的效率，還有減少計算上的負擔。
        int maze[][] = {{2, 2, 2, 2, 2, 2, 2, 2, 2},
                        {2, 0, 0, 0, 0, 0, 0, 0, 2},
                        {2, 0, 2, 2, 0, 2, 2, 0, 2},
                        {2, 0, 2, 0, 0, 2, 0, 0, 2},
                        {2, 0, 2, 0, 2, 0, 2, 0, 2},
                        {2, 0, 0, 0, 0, 0, 2, 0, 2},
                        {2, 2, 0, 2, 2, 0, 2, 2, 2},
                        {2, 0, 0, 0, 0, 0, 0, 0, 2},
                        {2, 2, 2, 2, 2, 2, 2, 2, 2}};
       
        System.out.println("顯示迷宮：");
        for(int i = 0; i < maze.length; i++) {
            for(int j = 0; j < maze[0].length; j++)
                if(maze[i][j] == 2)
                    System.out.print("█");
                else
                    System.out.print("  ");
            System.out.println();
        }

        Mouse mouse = new Mouse();
        
        //現在迷宮都創好了，再來就是要將老鼠給放上位置，起始點為(1,1)，終點為(7,7)。
        mouse.setStart(1, 1);
        mouse.setEnd(7, 7);
       
        mouse.go(maze);
    }
   
    public void setStart(int i, int j) {
        this.startI = i;
        this.startJ = j;
    }
   
    public void setEnd(int i, int j) {
        this.endI = i;
        this.endJ = j;
    }


    //底下再藉由上面提到的演算法，開始走迷宮，只有上下左右四個方向可以走，基本上走過的路不再走，除了死路的情況下，強制退回並選擇其他可以走的方向，如此下去就會到達出口。
    public void go(int[][] maze) {
        visit(maze, startI, startJ);
    }
   


    private void visit(int[][] maze, int i, int j) {
        maze[i][j] = 1;

        if(i == endI && j == endJ) {
            System.out.println("n找到出口！");
            //底下是列印這個迷宮的樣子
            for(int m = 0; m < maze.length; m++) {
                for(int n = 0; n < maze[0].length; n++) {
                	//這邊是列印牆壁
                    if(maze[m][n] == 2)
                        System.out.print("█");

                    //這邊是將走過的路設為1，然後列印出圖形
                    else if(maze[m][n] == 1)
                        System.out.print("◇");
                    else
                        System.out.print("  ");
                }
                System.out.println();
            }
        }

 

        //底下是決定行走方向，上面已經提過就不再多提
        if(maze[i][j+1] == 0)
            visit(maze, i, j+1);
        if(maze[i+1][j] == 0)
            visit(maze, i+1, j);
        if(maze[i][j-1] == 0)
            visit(maze, i, j-1);
        if(maze[i-1][j] == 0)
            visit(maze, i-1, j);
      
        maze[i][j] = 0;
    }
}