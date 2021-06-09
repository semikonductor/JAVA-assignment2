package StudentHomeWork.Third;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class Student implements Observer, Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	String name;
	ArrayList<String> course = new ArrayList<String>();
	Map<String, Double> grade = new HashMap<String, Double>();
	String knowledge;
	
	Subject teacher;

	public Student(String name, Subject teacher) {
		this.teacher = teacher;
		teacher.registerObserver(this);
		for(String tc : ((Teacher)teacher).courses) {
			this.course.add(tc);
		}
		this.name = name;
	}
	
	@SuppressWarnings("static-access")
	public String toString() {
		return this.name+"  �γ̣�"+this.grade;
	}
	
	public boolean deTeacher(Subject teacher) {
		if(teacher.containObserver(this)) {
			teacher.removeObserver(this);
			return true;
		}
		else {
			return false;
		}
	}
	
	@Override
	public void update(String knowledge) {
		// TODO Auto-generated method stub
		// ѧ���յ� knowledge �����һ�� �Լ��� knowledge������ study() ѧϰһ�� 
		this.knowledge = knowledge;
		study();
	}
	
	public boolean study() {
		// ����ʦ���ⷢ�͵� knowledge �洢��ѧ������Ӧ���ı��ļ���
		try {
			RandomAccessFile studentTxt = new RandomAccessFile(this.name+".txt","rwd");
			try {
				long sTLength = studentTxt.length();
				studentTxt.seek(sTLength);
				Calendar calendar = Calendar.getInstance();
				SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				byte[] b = new String(formatter.format(calendar.getTime())+" "+knowledge+"\n").getBytes("utf-8");
				studentTxt.write(b);
				studentTxt.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
}
