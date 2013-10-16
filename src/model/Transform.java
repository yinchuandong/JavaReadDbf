package model;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.jar.Attributes.Name;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.linuxense.javadbf.DBFReader;

import jxl.Workbook;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.Label;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

import model.Student;
import model.Academy;


public class Transform {

	private ArrayList<Student> lists = new ArrayList<Student>(); //原始数据列表
	private ArrayList<String> keys = new ArrayList<String>(); //字段的集合
	private ArrayList<Academy> acList = new ArrayList<Academy>(); //结果列表
	
	@SuppressWarnings("finally")
	public void readDBF(File file)
	{
	     try
	     {
		      System.out.println("正在读取文件！");
		      
		      System.out.println(file.getAbsolutePath());
		      DBFReader dbfreader = new DBFReader(new FileInputStream(file));
		      dbfreader.setCharactersetName("gb2312");
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
		      
		      Object[] rowValues = null;
		      while((rowValues = dbfreader.nextRecord()) != null){
		    	  
		    	  Student student = new Student();
		    	  for(int i = 0; i<dbfreader.getFieldCount();i++)
				  {
					    String value = rowValues[i].toString().trim();
					    if(i == 0){
					    	student.LQYXSM = value;
					    }else if(i == 1){
					    	student.LQZYMC = value;
					    }
					    
				  } 
		    	  lists.add(student);//添加进List
		      }
		      System.out.println("读取文件成功！");
	     }catch(Exception e){
	    	 JOptionPane.showMessageDialog(null, "文件格式不正确，\n 或者数据不合法");
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
	
	/**
	 * 计算每个学院的人数
	 */
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
	
	
	/**
	 * 查找是否存在特定学生
	 * @param student
	 * @return
	 */
	public int find(Student student){
		int len = acList.size();
		for (int i=0; i<len; i++) {
			Academy academy = acList.get(i);
			if (academy.LQZYMC.equals(student.LQZYMC)) {
				return i;
			}
		}
		return -1;
	}
	
	public void printExcel(File file){
		try {
			WritableWorkbook book = Workbook.createWorkbook(file);
			WritableSheet sheet = book.createSheet("Sheet_1", 0);
			Label labelA = new Label(0, 0, "LQYXSM");
			Label labelB = new Label(1, 0, "LQZYMC");
			Label labelC = new Label(2, 0, "人数");
			sheet.addCell(labelA);
			sheet.addCell(labelB);
			sheet.addCell(labelC);
			
			Collections.sort(acList, new Academy());
			
			int len = acList.size();
			for(int i=0; i<len; i++){
				Academy academy = acList.get(i);
				Label tempA = new Label(0, i+1, academy.LQYXSM);
				Label tempB = new Label(1, i+1, academy.LQZYMC);
				Label tempC = new Label(2, i+1, String.valueOf(academy.num));
				sheet.addCell(tempA);
				sheet.addCell(tempB);
				sheet.addCell(tempC);
			}
			
			book.write();
			book.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	

//	public static void main(String[] args) 
//	{
//		File file = new File("./dbf/lixia.dbf");
//		File fileOut = new File("output.xls");
//		Transform mainFrame = new Transform();
//		mainFrame.readDBF(file);
////		mainFrame.displayStudent();
//		mainFrame.calulate();
//		mainFrame.displayAcademy();
//		mainFrame.printExcel(fileOut);
//		
//	}


}