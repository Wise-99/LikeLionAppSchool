package com.test.main;

import java.util.Scanner;

public class MainClass {

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);

		// 학교 객체를 생성한다.
		School school = new School();

		// 학생 수를 입력받는다.
		school.inputStudentCount(scanner);

		// 학생 데이터를 입력 받는다.
        school.inputStudentInfo(scanner);
        
        // 학생 정보를 출력한다.
        school.printStudentInfo();
        
        // 총점을 구하고 출력한다.
        school.printPointTotal();
        
        // 평균을 구하고 출력한다.
        school.printPointAvg();
        
        scanner.close();
	}

}

class School {

	int studentCount; // 학생 수
	int koreanTotal; // 국어 총점
	int englishTotal; // 영어 총점
	int mathTotal; // 수학 총점

	Student[] studentArray; // 학생 정보를 담을 배열의 변수

	// 학생 수를 입력받는 메서드
	public void inputStudentCount(Scanner scanner) {
		System.out.print("학생 수 : ");
		studentCount = scanner.nextInt();

		// 배열을 생성한다.
		studentArray = new Student[studentCount];
		// 학생의 수 만큼 객체를 생성한다.
		for (int i = 0; i < studentCount; i++) {
			studentArray[i] = new Student();
		}
		
	}

	// 학생의 정보를 입력받는 메서드
	public void inputStudentInfo(Scanner scanner) {
		// 학생 수 만큼 반복한다.
		// 해당 for문은 값을 가져다가 사용할 때만 사용하는게 좋음
		// 객체 생성 때 사용하면 null 오류 남
		// 객체 생성시 사용하면 s1에 id가 할당 된 후 초기화 되버림
		// 인덱스를 활용하여 할 경우에는 배열에 id값이 들어감
		for(Student s1 : studentArray) {
			s1.inputStudentInfo(scanner);
		}
	}
	
	// 학생들의 정보를 출력하는 메서드
	public void printStudentInfo() {
		for(Student s1 : studentArray) {
			s1.printStudentInfo();
		}
	}

	// 과목별 총점을 구하고 출력하는 메서드
	public void printPointTotal() {
		// 변수 초기화
		koreanTotal = 0; // 국어 총점
		englishTotal = 0; // 영어 총점
		mathTotal = 0;
		
		// 학생 수만큼 반복한다.
		for(Student s1 : studentArray) {
			// 총점을 구한다.
			koreanTotal = koreanTotal + s1.korean;
            englishTotal = englishTotal + s1.english;
            mathTotal = mathTotal + s1.math;
		}
		
		System.out.printf("국어 총점 : %d\n", koreanTotal);
		System.out.printf("영어 총점 : %d\n", englishTotal);
		System.out.printf("수학 총점 : %d\n", mathTotal);
	}

	// 과목별 평균을 구하고 출력하는 메서드
	public void printPointAvg() {
		int koreanAvg = koreanTotal / studentCount;
		int englishAvg = englishTotal / studentCount;
		int mathAvg = mathTotal / studentCount;

		System.out.printf("국어 평균 : %d\n", koreanAvg);
		System.out.printf("영어 평균 : %d\n", englishAvg);
		System.out.printf("수학 평균 : %d\n", mathAvg);
	}
}

// 학생 클래스
class Student {
	// 이름
	String name;

	// 학년
	int grade;

	// 국어
	int korean;

	// 영어
	int english;

	// 수학
	int math;

	// 학생의 정보를 출력하는 메서드
	public void printStudentInfo() {
		System.out.printf("이름 : %s\n", name);
		System.out.printf("학년 : %d\n", grade);
		System.out.printf("국어 : %d\n", korean);
		System.out.printf("영어 : %d\n", english);
		System.out.printf("수학 : %d\n", math);
	}

	// 학생의 정보를 입력받는 메서드
	public void inputStudentInfo(Scanner scanner) {
		// 입력 받는다
		System.out.print("이름 : ");
		name = scanner.next();
		System.out.print("학년 : ");
		grade = scanner.nextInt();
		System.out.print("국어 : ");
		korean = scanner.nextInt();
		System.out.print("영어 : ");
		english = scanner.nextInt();
		System.out.print("수학 : ");
		math = scanner.nextInt();
	}
}