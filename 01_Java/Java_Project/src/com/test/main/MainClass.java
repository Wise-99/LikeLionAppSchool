package com.test.main;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class MainClass {

	public static void main(String[] args) throws FileNotFoundException, ClassNotFoundException {

		// 2팀
		// 정주성, 김민우, 김예린, 한상완
		
		int menuType = 0; // 메뉴 번호를 저장할 변수

		Scanner scan = new Scanner(System.in); // 스캐너
		Student stu = new Student(); // 빈 객체 생성

		do {
			System.out.println("----- menu -----");
			System.out.println("1. 학생정보 입력");
			System.out.println("2. 학생정보 검색");
			System.out.println("3. 과목별 총점 출력");
			System.out.println("4. 과목별 평균 출력");
			System.out.println("5. 종료");

			// 메뉴 종류를 입력받는다.
			menuType = scan.nextInt();

			// 5가 아닐 때만 동작
			if (menuType != 5) {
				
				switch (menuType) {
				case 1:	
					Student s = stu.studentScan(scan); // 1번 화면 출력 및 객체 생성 후 리턴
					
					saveObject(s); // 객체와 객체의 갯수 리턴
					
					break;
					
				case 2:
					System.out.print("검색할 학생 이름 입력 : ");
					String sName = scan.next();
					
					if(sName.equals("모두")) {
						stu.allSutudentInfo(loadObject());
					} else {
						stu.studentSearch(loadObject(), sName);
					}
					break;
					
				case 3:
					stu.printTotal(loadObject());
					break;
					
				case 4:
					stu.printAvg(loadObject());
					break;
				}
			}

		} while (menuType != 5);

	}

	// 파일에 객체 저장하기
	public static void saveObject(Student s) throws FileNotFoundException, ClassNotFoundException {
		
		// 이전에 저장되어있는 객체들을 저장한다.
		ArrayList<Student> stuList = loadObject();

		try {

			// 기본 스트림
			FileOutputStream fos = new FileOutputStream("Student.dat");

			// 객체 출력 스트림
			ObjectOutputStream oos = new ObjectOutputStream(fos);

			// 객체를 쓴다.
			oos.writeObject(s);
			for(Student stu : stuList) {
				oos.writeObject(stu);
			}

			oos.flush();
			oos.close();
			fos.close();

			System.out.println("--- 학생 정보 입력 완료 ---");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 파일에 저장되어있는 객체 불러오기
	public static ArrayList<Student> loadObject() throws FileNotFoundException, ClassNotFoundException {
		// 객체를 복원할 리스트
		ArrayList<Student> stuList = new ArrayList<Student>();

		try {
			// 기본 스트림 생성
			FileInputStream fis = new FileInputStream("Student.dat");

			// 객체 읽기 스트림
			ObjectInputStream ois = new ObjectInputStream(fis);

			Student stu = null;
			while ((stu = (Student) ois.readObject()) != null) {
				stuList.add(stu);

			}

			ois.close();
			fis.close();
			
			throw new FileNotFoundException("빈 파일");
			
		} catch (EOFException e) {
            // 더 이상 읽을 객체가 없을 때 예외 처리

            
        } catch (IOException e) {
            
        }
		
		return stuList;
	}

	
}

class Student implements Serializable {

	String name; 	// 이름
	int grade; 		// 학년
	int korean; 	// 국어
	int english; 	// 영어
	int math; 		// 수학

	// 생성자
	public Student(String name, int grade, int korean, int english, int math) {
		this.name = name;
		this.grade = grade;
		this.korean = korean;
		this.english = english;
		this.math = math;
	}

	// 빈 생성자
	public Student() {
		
	}

	public Student studentScan(Scanner scan) {

		System.out.println("----- 학생 정보입력 -----");
		System.out.print("이름 : ");
		String name = scan.next();

		System.out.print("학년 : ");
		int grade = scan.nextInt();

		System.out.print("국어 : ");
		int korean = scan.nextInt();

		System.out.print("영어 : ");
		int english = scan.nextInt();

		System.out.print("수학 : ");
		int math = scan.nextInt();
		
		Student s = new Student(name, grade, korean, english, math);
		
		return s;

	}
	// 모든 학생의 정보를 출력하는 메서드
	public void allSutudentInfo(ArrayList<Student> stulist) {
		for (Student stu : stulist) {
			System.out.println("이름 : " + stu.name);
			System.out.println("학년 : " + stu.grade);
			System.out.println("국어 점수 : " + stu.korean);
			System.out.println("영어 점수 : " + stu.english);
			System.out.println("수학 점수 : " + stu.math);
		}
	}
	
	
	// 학생 정보를 출력하는 메서드
	public void studentSearch(ArrayList<Student> stulist, String name) {
		for (Student stu : stulist) {

			if (name.equals(stu.name)) {
				System.out.println("이름 : " + stu.name);
				System.out.println("학년 : " + stu.grade);
				System.out.println("국어 점수 : " + stu.korean);
				System.out.println("영어 점수 : " + stu.english);
				System.out.println("수학 점수 : " + stu.math);
			}
		}
	}

	// 과목별 총점을 구하는 메서드
	public void printTotal(ArrayList<Student> stuList) {
		int koTotal = 0;
		int engTotal = 0;
		int mathTotal = 0;
		
		for(Student s : stuList) {
			koTotal += s.korean;
			engTotal += s.english;
			mathTotal += s.math;
		}
		
		System.out.println("국어 총점 : " + koTotal);
		System.out.println("영어 총점 : " + engTotal);
		System.out.println("수학 총점 : " + mathTotal);
		
	}
	// 과목별 평균을 구하는 메서드
	public void printAvg(ArrayList<Student> stuList) {
		int koAvg = 0;
		int engAvg = 0;
		int mathAvg = 0;
		int len = stuList.size();
		
		for(Student s : stuList) {
			koAvg += s.korean;
			engAvg += s.english;
			mathAvg += s.math;
		}
		
		System.out.println("국어 평균 : " + koAvg/len);
		System.out.println("영어 평균 : " + engAvg/len);
		System.out.println("수학 평균 : " + mathAvg/len);
		
		
	}
}