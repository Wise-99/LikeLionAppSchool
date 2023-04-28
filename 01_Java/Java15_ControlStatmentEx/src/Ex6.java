import java.util.Scanner;

public class Ex6 {

	public static void main(String[] args) {
		// 사용자에게 숫자를 입력 받아 2에서 부터 그 숫자까지의 범위 안에서
		// 소수(1과 자기 자신으로만 나눠지는 수)를 모두 출력한다.
		Scanner sc = new Scanner(System.in);
		System.out.print("숫자 입력 : ");
		int num = sc.nextInt();

		for (int i = 2; i <= num; i++) {
			int count = 0;
			for (int k = 2; k <= i; k++) {
				if (i % k == 0) {
					count++;
				}
			}

			if (count < 2) {
				System.out.println(i);
			}
		}
		sc.close();

//		// 숫자를 입력받는다.
//		Scanner scan = new Scanner(System.in);
//		System.out.print("숫자를 입력해주세요(3이상) : ");
//		int number = scan.nextInt();
//
//		// 2는 소수이므로 그냥 출력한다.
//		System.out.printf("%d ", 2);
//
//		// 3에서 부터 입력한 숫자까지 반복한다.
//		for (int i = 3; i <= number; i++) {
//			boolean chk = true;
//			// 2에서 부터 현재 숫자 - 1까지 반복한다.
//			for(int k=2; k <= i-1; k++) {
//				// 현재 숫자를 현재 숫자2로 나눈 나머지가 0인지 확인한다.
//				int a1 = i % k;
//				if(a1 == 0) {
//					chk = false;
//					break;
//				}
//			}
//
//			// 소수면 출력한다.
//            if(chk == true) {
//                System.out.printf("%d ", i);
//            }
//            
//            scan.close();
//		}
	}
}
