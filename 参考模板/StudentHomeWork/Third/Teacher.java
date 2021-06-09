package StudentHomeWork.Third;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;
import java.io.Serializable;
import java.nio.charset.Charset;
import java.util.ArrayList;

//import java.util.HashMap;
//import java.util.Map;

public class Teacher implements Subject, Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String name;
	ArrayList<String> courses = new ArrayList<String>();
	private ArrayList<Observer> observers;

	//	Map<String, ArrayList<Observer>> courseStudents = new HashMap<String, ArrayList<Observer>>();
	
	private String knowledge;
	
	public Teacher(String name, String course) {
		observers = new ArrayList<Observer>();
		this.name = name;
		this.courses.add(course);
		try {
			RandomAccessFile  teachingPlan= new RandomAccessFile( name +".txt","rwd");
			try {
				teachingPlan.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public String toString() {
		return this.name+"    "+this.courses;
	}
	@Override
	public void registerObserver(Observer o) {
		// TODO Auto-generated method stub
		observers.add(o);
	}

	@Override
	public void removeObserver(Observer o) {
		// TODO Auto-generated method stub
		int i = observers.indexOf(o);
		if(i >= 0) {
			observers.remove(i);
		}
	}
	
	public boolean containObserver(Observer o) {
		for(Observer x: observers) {
			if(x.equals(o)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public void notifyObservers() {
		// TODO Auto-generated method stub
		for(int i = 0; i < observers.size(); i ++) {
			Observer observer = (Observer)observers.get(i);
			observer.update(knowledge);
		}
	}
	
	public void knowledgeUpdate() {
		notifyObservers();
	}
	
	public void readKnowledge(String text) {
		// 传入String 类型的文本
		// 一每行作为一句话(一个知识点)发送给注册课程的学生
		 BufferedReader textIn = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(text.getBytes(Charset.forName("utf8"))), Charset.forName("utf8"))); 
		 String line;  
		 try {
			while ( (line = textIn.readLine()) != null ) {  
			     if(!line.equals("")){
			    	 knowledge = line;
			    	 System.out.println(line);
			    	 knowledgeUpdate();
			     }
			 }
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
}
