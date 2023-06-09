
public class Ex1 {
	public static void main(String[] args) {

		// 문제1)
		// "안녕하세요"를 5번 출력한다.

		for (int i = 0; i < 5; i++) {
			System.out.println("안녕하세요");
		}

		System.out.println("---------------------------");

		// 문제2)
		// 0 ~ 100까지의 범위에서 3의 배수를 출력한다.
		for (int i = 3; i < 100; i += 3) {
			System.out.printf("%d ", i);
		}
		
		System.out.println();
		System.out.println("---------------------------");

		// 문제3)
		// 0 ~ 100까지의 범위에서 5의 배수가 아닌 것을 출력한다.
		for(int i = 0; i < 100; i++) {
			if(i % 5 != 0) {
				System.out.printf("%d ", i);
			}
		}
		
		System.out.println();
		System.out.println("---------------------------");
		
		// 문제4)
		// 0 ~ 100까지의 총합을 구한다.
		int sum = 0;
		for(int i=0; i<=100; i++) {
			sum += i;
		}
		System.out.printf("%d\n", sum);
		
		System.out.println("---------------------------");

		// 문제5)
		// 0 ~ 100까지의 범위에서 3의 배수이자 7의 배수인 것만 출력한다.
		for(int i=1; i<=100; i++) {
			if(i % 3 == 0 && i % 7 == 0) {
				System.out.printf("%d ", i);
			}
		}
	}
}
