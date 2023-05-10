package com.test.main;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Scanner;

// 2조 김민우 김예린 정주성 한상완

public class MainClass {

	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		
		ArrayList<Student> studentList = new ArrayList<>();
		School school = new School();
		int menu = 0;
		
		do {
			// 메뉴 번호 입력 받기
			menu = school.saveMenu(scan);
			
			switch (menu) {
			case 1:
				// 학생정보 입력 후 Student객체 반환
				Student student = school.InfoSaveInSchool(scan);
				
				studentList.add(student); // 리스트에 추가
				saveInfo(studentList); 	// 리스트를 파일에 저장
				break;
			case 2:// 학생정보 검색
				school.StudentSearch(scan, loadInfo());
				break;
			case 3:// 과목별 총점 출력
				school.printTotal(loadInfo());
				break;
			case 4:// 과목별 평균 출력
				school.printAvg(loadInfo());
				break;
			case 5:// 종료
				break;
				
			default :
				System.out.println("1~5까지만 입력 가능합니다. 다시 입력해주세요.");
				
			}
			System.out.println();
			
		} while (menu != 5);
		
		scan.close();
	}

	// 파일에 학생 정보 저장
	public static void saveInfo(ArrayList<Student> studentList) {
		try {
			// 기본 스트림
			FileOutputStream fos = new FileOutputStream("student.dat");
			// 객체 출력 스트림
			ObjectOutputStream oos = new ObjectOutputStream(fos);

			// 객체를 쓴다.
			oos.writeObject(studentList);

			oos.flush();
			oos.close();
			fos.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 파일에 있는 학생 정보 불러오기
	public static ArrayList<Student> loadInfo() {
		
		// 객체를 복원하기 위한 리스트
		ArrayList<Student> list = null;

		try {
			// 기본 스트림 생성
			FileInputStream fis = new FileInputStream("student.dat");
			// 객체 읽기 스트림
			ObjectInputStream ois = new ObjectInputStream(fis);

			// 객체를 복원한다.
			list = (ArrayList<Student>) ois.readObject();

			ois.close();
			fis.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}
}

// 학생 클래스
// 학생의 정보는 이름, 학년, 국어, 영어, 수학 으로 구성됩니다.
class Student implements Serializable {

	String name;
	int grade;
	int korean;
	int english;
	int math;

	public Student(String name, int grade, int korean, int english, int math) {
		this.name = name;
		this.grade = grade;
		this.korean = korean;
		this.english = english;
		this.math = math;
	}

	// 학생 정보 출력 메서드
	public void printInfo() {
		System.out.printf("이름: %s\n", name);
		System.out.printf("학년: %d\n", grade);
		System.out.printf("국어: %d\n", korean);
		System.out.printf("영어: %d\n", english);
		System.out.printf("수학: %d\n", math);
	}
}

class School {
	public School() {}
	
	// 메뉴 번호 입력 메서드
	public int saveMenu(Scanner scan) {
		System.out.printf("학생 정보 관리 프로그램 \n\n");
		System.out.printf("1. 학생정보 입력\n" + "2. 학생정보 검색\n" + "3. 과목별 총점 출력\n" + "4. 과목별 평균 출력\n" + "5. 종료\n");
		System.out.printf("번호 입력 : ");
		int menu = scan.nextInt();
		System.out.println("----------------------------------");
		
		return menu;
	}
	
	// 학생 정보 입력하여 Student객체로 반환하는 메서드
	public Student InfoSaveInSchool(Scanner scan) {
		System.out.print("이름을 입력해주세요: ");
		String name = scan.next();
		System.out.print("학년을 입력해주세요: ");
		int grade = scan.nextInt();
		System.out.print("국어 점수를 입력해주세요: ");
		int korean = scan.nextInt();
		System.out.print("영어 점수를 입력해주세요: ");
		int english = scan.nextInt();
		System.out.print("수학 점수를 입력해주세요: ");
		int math = scan.nextInt();
		
		Student student = new Student(name, grade, korean, english, math);
		
		return student;
	}
	
	public void StudentSearch(Scanner scan, ArrayList<Student> list) {
		System.out.print("학생의 이름을 입력해주세요 : ");
		String name = scan.next();
		
		if (name.equals("모두")) {
			for (Student student : list)
				student.printInfo();
		} else {
			for (Student student : list) {
				if (student.name.equals(name)) {
					student.printInfo();
				}
			}
		}
		System.out.println("----------------------------------");
	}
	
	// 과목별 총점 출력 메서드
	public void printTotal(ArrayList<Student> list) {
		int korsum = 0;
		int engsum = 0;
		int mathsum = 0;
		try {

			for (Student student : list) {
				korsum += student.korean;
				engsum += student.english;
				mathsum += student.math;
			}
			
			System.out.printf("국어 총점 : %d\n", korsum);
			System.out.printf("영어 총점 : %d\n", engsum);
			System.out.printf("수학 총점 : %d\n", mathsum);
			System.out.println("----------------------------------");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 과목별 평균 출력 메서드
	public void printAvg(ArrayList<Student> list) {
		int korsum = 0;
		int engsum = 0;
		int mathsum = 0;
		try {

			for (Student student : list) {
				korsum += student.korean;
				engsum += student.english;
				mathsum += student.math;
			}
			System.out.printf("국어 평균 : %d\n", korsum / list.size());
			System.out.printf("영어 평균 : %d\n", engsum / list.size());
			System.out.printf("수학 평균 : %d\n", mathsum / list.size());
			System.out.println("----------------------------------");			

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}