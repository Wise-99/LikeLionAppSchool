import java.util.Scanner;

public class Ex5 {

	public static void main(String[] args) {
		// 사용자에게 숫자를 입력 받아 그 숫자의 배수 100개를 출력한다.
		
		
		Scanner sc = new Scanner(System.in);
		System.out.print("숫자 입력 : ");
		int num = sc.nextInt();
		
		for(int i=1; i<=100; i++) {
			System.out.println(i + "번째 배수 : " + i*num);
		}
		
		sc.close();
		
//		// 사용자에게 숫자를 입력 받는다.
//		Scanner scan = new Scanner(System.in);
//        System.out.print("숫자 입력 : ");
//        int number = scan.nextInt();
//        
//        // 100번을 반복한다.
//
//        for(int i = 0 ; i < 100 ; i++) {
//            // 입력한 숫자를 곱하면서 출력한다.
//            System.out.printf("%d ", number * (i + 1));
//        }
//
//        scan.close();
	}
}
