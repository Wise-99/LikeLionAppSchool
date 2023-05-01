import java.util.Scanner;

public class MainClass {

	public static void main(String[] args) {
		StudentClass stu1 = new StudentClass();
		StudentClass stu2 = new StudentClass();
		StudentClass stu3 = new StudentClass();
		
		Scanner sc = new Scanner(System.in);
		
		System.out.print("첫번째 학생 이름 입력 : ");
		stu1.name = sc.next();
		
		System.out.print("첫번째 학생 학년 입력 : ");
		stu1.grade = sc.nextInt();
		
		System.out.print("첫번째 학생 국어 점수 입력 : ");
		stu1.korean = sc.nextInt();
		
		System.out.print("첫번째 학생 영어 점수 입력 : ");
		stu1.eng = sc.nextInt();
		
		System.out.print("첫번째 학생 수학 점수 입력 : ");
		stu1.math = sc.nextInt();
		
		System.out.printf("이름 : %s\n", stu1.name);
		System.out.printf("학년 : %s\n", stu1.grade);
		System.out.printf("국어 : %s\n", stu1.korean);
		System.out.printf("영어 : %s\n", stu1.eng);
		System.out.printf("수학 : %s\n", stu1.math);
		
		//-------------------------------------------------------
		
		System.out.print("두번째 학생 이름 입력 : ");
		stu2.name = sc.next();
		
		System.out.print("두번째 학생 학년 입력 : ");
		stu2.grade = sc.nextInt();
		
		System.out.print("두번째 학생 국어 점수 입력 : ");
		stu2.korean = sc.nextInt();
		
		System.out.print("두번째 학생 영어 점수 입력 : ");
		stu2.eng = sc.nextInt();
		
		System.out.print("두번째 학생 수학 점수 입력 : ");
		stu2.math = sc.nextInt();
		
		System.out.printf("이름 : %s\n", stu2.name);
		System.out.printf("학년 : %s\n", stu2.grade);
		System.out.printf("국어 : %s\n", stu2.korean);
		System.out.printf("영어 : %s\n", stu2.eng);
		System.out.printf("수학 : %s\n", stu2.math);
		
		//-------------------------------------------------------
		
		System.out.print("세번째 학생 이름 입력 : ");
		stu3.name = sc.next();
		
		System.out.print("세번째 학생 학년 입력 : ");
		stu3.grade = sc.nextInt();
		
		System.out.print("세번째 학생 국어 점수 입력 : ");
		stu3.korean = sc.nextInt();
		
		System.out.print("세번째 학생 영어 점수 입력 : ");
		stu3.eng = sc.nextInt();
		
		System.out.print("세번째 학생 수학 점수 입력 : ");
		stu3.math = sc.nextInt();
		
		System.out.printf("이름 : %s\n", stu3.name);
		System.out.printf("학년 : %s\n", stu3.grade);
		System.out.printf("국어 : %s\n", stu3.korean);
		System.out.printf("영어 : %s\n", stu3.eng);
		System.out.printf("수학 : %s\n", stu3.math);
		
		//-------------------------------------------------------
		
		sc.close();
		
		System.out.println("과목별 총점");
		System.out.printf("국어 : %d\n", stu1.total(stu1.korean, stu2.korean, stu3.korean));
		System.out.printf("영어 : %d\n", stu1.total(stu1.eng, stu2.eng, stu3.eng));
		System.out.printf("수학 : %d\n", stu1.total(stu1.math, stu2.math, stu3.math));
		
		System.out.println("과목별 평균");
		System.out.printf("국어 : %d\n", stu1.avg(stu1.korean, stu2.korean, stu3.korean));
		System.out.printf("영어 : %d\n", stu1.avg(stu1.eng, stu2.eng, stu3.eng));
		System.out.printf("수학 : %d\n", stu1.avg(stu1.math, stu2.math, stu3.math));
	}

}
