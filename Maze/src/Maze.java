package DSwJ.S14.apps;  
  
import DSwJ.S14.ALStack;  
  
public class Maze {  
    public static final int ExitX = 10;    
    public static final int ExitY = 8;  
    public int curX=1;  
    public int curY=1;  
    protected ALStack history;             
    private int[][] MAZE = {{1,1,1,1,1,1,1,1,1,1,1,1},    
                            {1,0,0,0,1,1,1,1,1,1,1,1},    
                            {1,1,1,0,1,1,0,0,0,0,1,1},    
                            {1,1,1,0,1,1,0,1,1,0,1,1},    
                            {1,1,1,0,0,0,0,1,1,0,1,1},    
                            {1,1,1,0,1,1,0,1,1,0,1,1},    
                            {1,1,1,0,1,1,0,1,1,0,1,1},    
                            {1,1,1,1,1,1,0,1,1,0,1,1},    
                            {1,1,0,0,0,0,0,0,1,0,0,1},    
                            {1,1,1,1,1,1,1,1,1,1,1,1}};  
      
    public Maze() {history = new ALStack();}  
      
    public boolean start(){  
        go(curX,curY);  
        while(true) {             
            if(north()==0){  
                goNorth();  
            } else if(south()==0) {  
                goSouth();  
            } else if(west()==0) {  
                goWest();  
            } else if(east()==0) {  
                goEast();  
            } else {  
                Pos backPos = back();  
                if(backPos==null){  
                    System.out.println("Exit is unreachable!!!");  
                    return false;                     
                } else {  
                    curX = backPos.getX();  
                    curY = backPos.getY();  
                }  
            }  
            if(isExit())return true;;  
        }  
    }  
      
    public int north(){return MAZE[curY-1][curX];}  
    public int south(){return MAZE[curY+1][curX];}  
    public int west(){return MAZE[curY][curX-1];}  
    public int east(){return MAZE[curY][curX+1];}  
      
    public void goNorth(){go(curX,--curY);}  
    public void goSouth(){go(curX,++curY);}  
    public void goEast(){go(++curX,curY);}  
    public void goWest(){go(--curX,curY);}  
    public void go(int x, int y) {  
        MAZE[y][x]=2;  
        history.push(new Pos(x,y));  
    }  
      
    public Pos back(){  
        return history.pop();  
    }  
      
    public boolean isExit(){if(curX==ExitX&&curY==ExitY)return true;return false;}  
      
    public void showMaze(){  
        for(int i=0; i
            for(int j=0; j
                System.out.print(MAZE[i][j]+" ");  
            System.out.println();  
        }  
    }  
      
    public static void main(String args[]) {  
        Maze maze = new Maze();  
        if(maze.start()) {  
            System.out.println("Conquer the maze ^^.");  
        } else {  
            System.out.println("I lost in the maze ==\".");  
        }  
        maze.showMaze();  
    }  
}  