public class Mouse {
    private int startI, startJ; 
    private int endI, endJ; 
 
    public static void main(String[] args) {
    	//���U�O�ۤv�s�y���g�c�A�D�n�O���F����W���Ĳv�A�٦���֭p��W���t��C
        int maze[][] = {{2, 2, 2, 2, 2, 2, 2, 2, 2},
                        {2, 0, 0, 0, 0, 0, 0, 0, 2},
                        {2, 0, 2, 2, 0, 2, 2, 0, 2},
                        {2, 0, 2, 0, 0, 2, 0, 0, 2},
                        {2, 0, 2, 0, 2, 0, 2, 0, 2},
                        {2, 0, 0, 0, 0, 0, 2, 0, 2},
                        {2, 2, 0, 2, 2, 0, 2, 2, 2},
                        {2, 0, 0, 0, 0, 0, 0, 0, 2},
                        {2, 2, 2, 2, 2, 2, 2, 2, 2}};
       
        System.out.println("��ܰg�c�G");
        for(int i = 0; i < maze.length; i++) {
            for(int j = 0; j < maze[0].length; j++)
                if(maze[i][j] == 2)
                    System.out.print("�i");
                else
                    System.out.print("  ");
            System.out.println();
        }

        Mouse mouse = new Mouse();
        
        //�{�b�g�c���Цn�F�A�A�ӴN�O�n�N�ѹ�����W��m�A�_�l�I��(1,1)�A���I��(7,7)�C
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


    //���U�A�ǥѤW�����쪺�t��k�A�}�l���g�c�A�u���W�U���k�|�Ӥ�V�i�H���A�򥻤W���L�������A���A���F���������p�U�A�j��h�^�ÿ�ܨ�L�i�H������V�A�p���U�h�N�|��F�X�f�C
    public void go(int[][] maze) {
        visit(maze, startI, startJ);
    }
   


    private void visit(int[][] maze, int i, int j) {
        maze[i][j] = 1;

        if(i == endI && j == endJ) {
            System.out.println("n���X�f�I");
            //���U�O�C�L�o�Ӱg�c���ˤl
            for(int m = 0; m < maze.length; m++) {
                for(int n = 0; n < maze[0].length; n++) {
                	//�o��O�C�L���
                    if(maze[m][n] == 2)
                        System.out.print("�i");

                    //�o��O�N���L�����]��1�A�M��C�L�X�ϧ�
                    else if(maze[m][n] == 1)
                        System.out.print("��");
                    else
                        System.out.print("  ");
                }
                System.out.println();
            }
        }

 

        //���U�O�M�w�樫��V�A�W���w�g���L�N���A�h��
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