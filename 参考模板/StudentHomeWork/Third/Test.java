package StudentHomeWork.Third;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.RandomAccessFile;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Scanner;

public class Test {
	
	static Scanner input = new Scanner(System.in);
	ArrayList<Teacher> teachers = new ArrayList<Teacher>();
	ArrayList<Student> students = new ArrayList<Student>();
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
	
		Test test = new Test();
		int choose;
		do{
			System.out.print("\n操作菜单：\n1.创建老师\n2.创建学生\n3.教师列表\n4.学生列表\n5.学生选课\n6.学生退课\n7.上课\n8.课堂提问\n9.考试\n10.存档\n11.读档\n12.退出\n请选择操作：");
			choose = input.nextInt();
			switch(choose) {
				case 1:
					test.createTeacher();
					break;
				case 2:
					test.createStudent();
					break;
				case 3:
					test.teacherList();
					break;
				case 4:
					test.studentList();
					break;
				case 5:
					test.registerCourse();
					break;
				case 6:
					test.dropCourse();
					break;
				case 7:
					test.classTime();
					break;
				case 8:
					test.classroomQuestioning();
					break;
				case 9:
					test.examine(0.9, 0.95);
					break;
				case 10:
					test.saveO();
					break;
				case 11:
					test.readO();
					break;
				case 12:
					System.out.print("\n您已退出！\n");
				default:
			}
		}while(choose != 12);
	}



	private void createTeacher() {
		// TODO Auto-generated method stub
		System.out.print("请输入新建老师的姓名：");
		String name = input.next();
		System.out.print("请输入老师讲授的课程:");
		String course = input.next();
		Teacher newTeacher = new Teacher(name, course);
		this.teachers.add(newTeacher);
	}

	private boolean createStudent() {
		// TODO Auto-generated method stub
		System.out.print("请输入新建学生的姓名：");
		String sName = input.next();
		System.out.print("请输入老师名:");
		String tName = input.next();
		Subject chooseTeacher = null;
		boolean flag = false;
		for(Teacher t: this.teachers) {
			if(t.name.equals(tName)) {
				chooseTeacher = t;
				flag = true;
			}
		}
		if(flag) {
			Student newStudent = new Student(sName, chooseTeacher);
			newStudent.course = ((Teacher)chooseTeacher).courses;
			this.students.add(newStudent);
			return true;
		}
		else {
			System.out.print("创建学生出错！老师不存在");
			return false;
		}
	}

	private void teacherList() {
		// TODO Auto-generated method stub
		for(Teacher te : teachers) {
			System.out.println(te.toString());
		}
	}
	
	private void studentList() {
		// TODO Auto-generated method stub
		for(Student st : students) {
			System.out.println(st.toString());
		}
	}

	private void registerCourse() {
		// TODO Auto-generated method stub		
		System.out.print("请输入学生的姓名：");
		String sName = input.next();
		System.out.print("请输入老师名:");
		String tName = input.next();
		Subject chooseTeacher = null;
		Observer chooseStudent = null;
		boolean flagt = false, flags=false;
		for(Teacher t: this.teachers) {
			if(t.name.equals(tName)) {
				chooseTeacher = t;
				flagt = true;
			}
		}
		for(Student s: this.students) {
			if(s.name.equals(sName)) {
				chooseStudent = s;
				flags = true;
			}
		}
		if(flagt && flags) {
			if(chooseTeacher.containObserver(chooseStudent)) {
				System.out.print("该学生已选过该老师！请勿重复选课！！！\n");
			}
			else {
				for(String tc : ((Teacher)chooseTeacher).courses) {
					((Student)chooseStudent).course.add(tc);
				}
				chooseTeacher.registerObserver(chooseStudent);
				System.out.println("选课成功!!!\n");
			}
		}
	}

	private void dropCourse() {
		// TODO Auto-generated method stub
		System.out.print("请输入学生的姓名：");
		String sName = input.next();
		System.out.print("请输入老师名:");
		String tName = input.next();
		Subject chooseTeacher = null;
		Observer chooseStudent = null;
		boolean flagt = false, flags=false;
		for(Teacher t: this.teachers) {
			if(t.name.equals(tName)) {
				chooseTeacher = t;
				flagt = true;
			}
		}
		for(Student s: this.students) {
			if(s.name.equals(sName)) {
				chooseStudent = s;
				flags = true;
			}
		}
		if(flagt && flags) {
			if(!chooseTeacher.containObserver(chooseStudent)) {
				System.out.print("该学生未选过该老师的课程！无法退课！！！\n");
			}
			else {
				//chooseTeacher.removeObserver(chooseStudent);
				((Student)chooseStudent).deTeacher(chooseTeacher);
				System.out.println("选课成功!!!\n");
			}
		}
	}

	private void classTime() {
		// TODO Auto-generated method stub
		System.out.print("请输入老师名:");
		String tName = input.next();
		Subject chooseTeacher = null;
		boolean flagt = false;
		for(Teacher t: this.teachers) {
			if(t.name.equals(tName)) {
				chooseTeacher = t;
				flagt = true;
			}
		}
		if(flagt) {
			try {
				RandomAccessFile  teachingPlan= new RandomAccessFile( ((Teacher)chooseTeacher).name +".txt","rwd");
				try {
					long tPLength = teachingPlan.length();
					byte[] b = new byte[(int) tPLength];
					teachingPlan.read(b);
					String tPText = new String(b, "utf-8");
					System.out.println(tPText);
					((Teacher)chooseTeacher).readKnowledge(tPText);
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
		else {
			System.out.println("\n没有这个老师！！！");
		}
	}

	private void classroomQuestioning() {
		// TODO Auto-generated method stub
		System.out.print("请输入提问学生的姓名：");
		String sName = input.next();
		Observer chooseStudent = null;
		boolean flags = false;
		for(Student s: this.students) {
			if(s.name.equals(sName)) {
				chooseStudent = s;
				flags = true;
				break;
			}
		}
		if(flags) {
			System.out.print("请输入问题的关键字：");
			String questionKey = input.next();
			System.out.println("答：");
			try {
				RandomAccessFile  studentLearned= new RandomAccessFile( ((Student)chooseStudent).name +".txt","rwd");
				try {
					long tPLength = studentLearned.length();
					byte[] b = new byte[(int) tPLength];
					studentLearned.read(b);
					studentLearned.close();
					String tPText = new String(b, "utf-8");
					BufferedReader textIn = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(tPText.getBytes(Charset.forName("utf8"))), Charset.forName("utf8"))); 
					String line;  
					try {
						while ( (line = textIn.readLine()) != null ) {  
						    if(!line.equals("")){
						    	if(line.contentEquals(questionKey)) {
						    		System.out.println(line);
						    	}
						    }
						}
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} 

				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else {
			System.out.println(sName+"不存在");
		}
	}

	private void examine(double learningEfficiency, double examineStatus) {
		// TODO Auto-generated method stub
		System.out.print("请输入考试学生的姓名：");
		String sName = input.next();
		System.out.print("请输入老师名:");
		String tName = input.next();
		Subject chooseTeacher = null;
		Observer chooseStudent = null;
		boolean flagt = false, flags=false;
		for(Teacher t: this.teachers) {
			if(t.name.equals(tName)) {
				chooseTeacher = t;
				flagt = true;
			}
		}
		for(Student s: this.students) {
			if(s.name.equals(sName)) {
				chooseStudent = s;
				flags = true;
			}
		}
		if(flags && flagt) {
			try {
				RandomAccessFile  studentLearned= new RandomAccessFile( ((Student)chooseStudent).name +".txt","rwd");
				try {
					long tPLength = studentLearned.length();
					byte[] b = new byte[(int) tPLength];
					studentLearned.read(b);
					studentLearned.close();
					String tPText = new String(b, "utf-8");
					BufferedReader textIn = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(tPText.getBytes(Charset.forName("utf8"))), Charset.forName("utf8"))); 
					String line;  
					try {
						while ( (line = textIn.readLine()) != null ) {  
						    if(!line.equals("")){
						    	System.out.println(line);
						    }
						}
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} 

				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			String course = null;
			for(String sc : ((Student)chooseStudent).course) {
				for(String tc : ((Teacher)chooseTeacher).courses) {
					if(sc.equals(tc)) {
						course = sc;
					}
				}
			}
			((Student)chooseStudent).grade.put(course, (Math.random()*5 + 100 * learningEfficiency * examineStatus) % 100 );
		}
		else {
			System.out.println(sName+"或"+tName+"不存在");
		}
	}

	private void saveO() {
		// TODO Auto-generated method stub
		
		try {
			FileOutputStream f = new FileOutputStream("Student_date.ser");
			try {
				ObjectOutputStream s = new ObjectOutputStream(f);
				for(Student st: students) {
					s.writeObject(st);
				}
				f.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
		e.printStackTrace();
		}
		
		try {
			FileOutputStream f = new FileOutputStream("Teacher_date.ser");
			try {
				ObjectOutputStream s = new ObjectOutputStream(f);
				for(Teacher te: teachers) {
					s.writeObject(te);
				}
				f.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void readO() {
		// TODO Auto-generated method stub
		try {
			FileInputStream f = new FileInputStream("Student_date.ser");
			try {
				ObjectInputStream s = new ObjectInputStream(f);
				while(f.available()>0) {
					Object obj = null;
					try {
						obj = s.readObject();
					} catch (ClassNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					students.add((Student)obj);
				}
				s.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			FileInputStream f = new FileInputStream("Teacher_date.ser");
			try {
				ObjectInputStream s = new ObjectInputStream(f);
				while(f.available()>0) {
					Object obj = null;
					try {
						obj = s.readObject();
					} catch (ClassNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					teachers.add((Teacher)obj);
				}
				s.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
}
