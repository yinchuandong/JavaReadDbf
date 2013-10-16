package model;

public class Academy{
	public String LQYXSM;
	public String LQZYMC;
	public int num = 0;
	
	public Academy() {
		
	}
	
	public Academy(Student student){
		LQYXSM = student.LQYXSM;
		LQZYMC = student.LQZYMC;
	}
}