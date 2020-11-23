import java.util.ArrayList;
import java.util.Arrays;

public class Main {

	public static void main(String[] args) {
		ArrayList<Grade> grades = ReadFile.returnGrade();
		int[] level;
		level = new int[11];	//arrayList.size()回傳元素個數
		Arrays.fill(level, 0);
		for(Grade g:grades) {
			int score = g.getScore();
			if(score<10) 
				level[0]++;
			else if(score>=10 && score<20)
				level[1]++;
			else if(score>=20 && score<30)
				level[2]++;
			else if(score>=30 && score<40)
				level[3]++;
			else if(score>=40 && score<50)
				level[4]++;
			else if(score>=50 && score<60)
				level[5]++;
			else if(score>=60 && score<70)
				level[6]++;
			else if(score>=70 && score<80)
				level[7]++;
			else if(score>=80 && score<90)
				level[8]++;
			else if(score>=90 && score<100)
				level[9]++;
			else if(score==100)
				level[10]++;
			System.out.printf("id:%d score:%d\n", g.getId(), g.getScore());
		}
		for(int i:level) {
			System.out.printf("i ", i);
		}
		
	}

}
