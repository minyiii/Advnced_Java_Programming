package com.fishroad;
import java.util.Stack;

class Step{
	int x,y,d;
	public Step(int x,int y,int d) {
		this.x = x;//��y�� 
		this.y = y;//�a�y�� 
		this.d = d;//��V 
	}
}

public class MazeTest {
	public static void main(String[] args) { 
		/* �g�c�w�q int[][] maze = {{1,1,1,1,1,1,1,1,1,1}, 
		{1,0,0,1,1,0,1,1,1,1}, {1,1,0,0,0,1,1,1,1,1}, 
		{1,0,1,0,0,0,0,0,1,1}, {1,0,1,1,1,0,0,1,1,1}, 
		{1,1,0,0,1,1,0,0,0,1}, {1,0,1,1,0,0,1,1,0,1}, 
		{1,1,1,1,1,1,1,1,1,1}}; */
		//�樫����V�F�n��_�|�Ӥ�V int[][] move = {{0,1},{1,0},{0,-1},{-1,0}}; 
		//�C�L�g�c 
		for(int i=0;i<=7;i++){ 
			for(int j=0;j<=9;j++){ 
				if(maze[i][j] == 1){ 
					System.out.print("�i"); }
				else{ System.out.print("*"); } 
			} System.out.print("/n"); } 
		Stack s = new Stack(); 
		Stack s1 = new Stack(); 
		int a = path(maze, move, s); 
		Step aa = null; //������|�̭������� 
		Stack s2 = new Stack(); 
		/*�Ns���|�̭��������X��,�åB�N��������s2�����|�̭�,
		�o�˴N��O�ҬO���樫�����ǿ�X�y��,�_�h�O�˧ǿ�X���y�� */
		while(!s.isEmpty()){
			Step step = (Step) s.pop(); s2.push(step); 
		} //���L�X�̫᪺�y�ж��� 
		StringBuffer aaa = new StringBuffer() ; 
		while(!s2.isEmpty()){ 
			Step step = (Step) s2.pop(); 
			aaa.append("("+step.x+","+step.y+")"+"��"); 
		} 
		String str = aaa.toString(); 
		System.out.print(str.substring(0, str.length()-1));
		System.out.println("/r"); System.out.println("���L�����|��:"); 
		//���L�X���L���Ҧ��y�Ц�m 
		for(int i=0;i<=7;i++){ 
			for(int j=0;j<=9;j++){ 
				if(maze[i][j] == 1){ 
					System.out.print("�i"); 
				}
				else if(maze[i][j] == -1){ 
					System.out.print("#"); 
				}
				else{ System.out.print("*"); } } 
			System.out.print("/n"); 
		} 
		}
	//�p����| 
	public static int path(int[][] maze,int[][] move,Stack s){ 
		Step temp = new Step(1,1,-1); //�_�I 
		s.push(temp); 
		while(!s.isEmpty()){ 
			temp = (Step) s.pop(); 
			int x = temp.x;
			int y = temp.y; 
			int d = temp.d+1; 
			//�p�Gd�j��4�]�N�O����e�y�Ъ��L���i��,�u���h�@�B,���@�y�Ъ��U�@�Ӥ�V,�b�~��M��y�� 
			while(d<4){ 
				int i = x + move[d][0]; 
				int j = y + move[d][1]; 
				if(maze[i][j] == 0){
					//���I�i�F 
					temp = new Step(i,j,d); 
					//��F�s�I 
					s.push(temp);
					x = i;
					y = j; 
					maze[x][y] = -1; 
					//��F�s�I,�лx�w�g��F 
					if(x == 6 &;&; y == 8){ 
						return 1; //��F�X�f
					}
				}
			}
		}

},�g�c����,��^1 
	}
else{ 
	d = 0; //���s��l�Ƥ�V
	} 
}
else{ 
	d++; //���ܤ�V } 
	} 
} 
	return 0; }}
}