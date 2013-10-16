package model;

import java.util.Comparator;

public class Academy implements Comparator<Academy>{
	public String LQYXSM;
	public String LQZYMC;
	public int num = 0;
	
	public Academy() {
		
	}
	
	public Academy(Student student){
		LQYXSM = student.LQYXSM;
		LQZYMC = student.LQZYMC;
	}

	@Override
	public int compare(Academy o1, Academy o2) {
		return o1.LQYXSM.compareTo(o2.LQYXSM);
	}
}