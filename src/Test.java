import helper.DBFReader;
import helper.DBFWriter;
import helper.JDBField;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.jar.Attributes.Name;

import model.Student;
import model.Academy;


public class Test {

	ArrayList<Student> lists = new ArrayList<Student>();
	ArrayList<String> keys = new ArrayList<String>();
	
	@SuppressWarnings("finally")
	public void readDBF()
	{
	     try
	     {
		      System.out.println("正在读取文件！");
		      
		      File file = new File("dbf/test.dbf");
		      String pathStr = file.getAbsolutePath();
		      System.out.println(file.getAbsolutePath());
		      DBFReader dbfreader = new DBFReader(pathStr);
		      for(int b = 0;b<dbfreader.getFieldCount();b++)//读取字段名称
		      {
		    	  if(b>0)
		    		  System.out.print(",");
		    	  String temp = dbfreader.getField(b).getName().trim().toUpperCase();
		    	  System.out.print(temp);
		    	  keys.add(temp);
		    	  if(b == (dbfreader.getFieldCount()-1))
		    		  System.out.print("\n");
		    
		      } 
		      
		      for(int i = 0;dbfreader.hasNextRecord();i++)//读取数据
		      {
		    	  String aobj[] = dbfreader.nextRecordString();
		    	  Student student = new Student();
		    	  
		    	  for(int b = 0;b<dbfreader.getFieldCount();b++)
				  {
					    
					    String value = new String(aobj[b].trim());
					    if(b == 0){
					    	student.LQYXSM = value;
					    }else if(b == 1){
					    	student.LQZYMC = value;
					    }
					    
				  } 
		    	  lists.add(student);//添加进List
		      }
		      System.out.println("读取文件成功！");
	     }catch(Exception e){
	    	 e.printStackTrace();
	     }

	}

	
	
	public void displayStudent(){
		for (Student student : lists) {
			System.out.println(student.LQYXSM + "," + student.LQZYMC);
		}
	}
	
	public void displayAcademy(){
		try {
			File file2 = new File("./test.txt");
			FileOutputStream stream = new FileOutputStream(file2);
			for (Academy academy : acList) {
				String temp = academy.LQYXSM + "," + academy.LQZYMC + "," + academy.num;
				System.out.println(temp);
				stream.write((temp+"\n").getBytes());
			}
			stream.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	      
	}
	
	
	public void calulate(){
		
		for (Student student : lists) {
			int index = find(student);
			if (index == -1) {
				Academy academy = new Academy(student);
				acList.add(academy);
			}else{
				acList.get(index).num ++;
			}
		}
	}
	
	private ArrayList<Academy> acList = new ArrayList<Academy>();
	public int find(Student student){
		int len = acList.size();
		for (int i=0; i<len; i++) {
			Academy academy = acList.get(i);
			if (academy.LQYXSM.equals(student.LQYXSM)) {
				return i;
			}
		}
		return -1;
	}
	
	

	public static void main(String[] args) 
	{
		Test test = new Test();
		test.readDBF();
//		test.displayStudent();
		test.calulate();
		test.displayAcademy();
		
	}


}