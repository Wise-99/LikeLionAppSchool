import java.util.Scanner;

public class Ex4 {

	public static void main(String[] args) {
		// 사용자에게 숫자를 입력 받아 그 숫자 까지의 총합을 구한다.
		
		// 사용자에게 숫자를 입력 받는다.
		Scanner scan = new Scanner(System.in);

		// 입력받은 숫자까지 반복한다.
		System.out.print("숫자 입력 : ");
		int num = scan.nextInt();
		int total = 0;
		
		for(int i=1; i<=num; i++) {
			total += i; // 누적한다.
		}
		System.out.println("결과 : " + total);
		
		scan.close();
	}

}
