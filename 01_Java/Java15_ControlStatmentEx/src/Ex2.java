
public class Ex2 {

	public static void main(String[] args) {
		// 문제1)
		// 1 2 3
		// 4 5 6
		// 7 8 9
		for(int i=1; i<=9; i++) {
			System.out.print(i + " ");
			if(i % 3 == 0) {
				System.out.println();
			}
		}
		
//		for(int k  = 0 ; k < 3 ; k++) {
//            int a1 = 1 + (3 * k);
//
//            for(int i = 0 ; i < 3 ; i++) {
//                System.out.printf("%d ", a1 + i);
//            }
//            System.out.println();
//        }
		
		System.out.println("---------------------------");

		// 문제2)
		// 3 2 1
		// 6 5 4
		// 9 8 7
		for(int i=3; i<=9; i+=3) {
			System.out.println(i + " " + (i-1) + " " + (i-2));
			
		}
		
//		for(int k = 0 ; k < 3 ; k++) {
//
//            int a2 = 3 + (3 * k);
//
//            for(int i = 0 ; i < 3 ; i++) {
//                int a1 = a2 - i;
//                System.out.printf("%d ", a1);
//            }
//
//            System.out.println();
//        }
		
		System.out.println("---------------------------");

		// 문제3)
		// 1 4 7
		// 2 5 8
		// 3 6 9
		for(int i=1; i<=3; i++) {
			System.out.print(i + " ");
			
			for(int j=3; j<=6; j+=3) {
				System.out.print((i+j) + " ");
			}
			
			System.out.println();
		}
		
//		for(int k = 0 ; k < 3 ; k++) {
//
//            int a2 = 1 + k;
//
//            for(int i = 0 ; i < 3 ; i++) {
//                int a1 = a2 + (3 * i);
//                System.out.printf("%d ", a1);
//            }
//            System.out.println();
//        }
		
		System.out.println("---------------------------");

		// 문제4)
		// *
		// * *
		// * * *
		for(int i=1; i<=3; i++) {
			
			for(int j=1; j<=i; j++) {
				System.out.print("* ");
			}
			
			System.out.println();
		}
		
//		for(int k = 0 ; k < 3 ; k++) {
//
//            int a1 = 1 + k;
//
//            for(int i = 0 ; i < a1 ; i++) {
//                System.out.print("* ");
//            }
//
//            System.out.println();
//        }
		
		System.out.println("---------------------------");

		// 문제5)
		//     *
		//   * *
		// * * *
//		for(int i=1;i<=3;i++){
//			for(int j=3;j>0;j--){
//				if(i<j){
//					System.out.print(" ");
//				}else{
//					System.out.print("*");
//				}
//			}
//			System.out.println("");
//		}
		
		for(int k = 0 ; k < 3 ; k++) {

            int a1 = 2 - k;

            for(int i = 0 ; i < 3 ; i++) {
                if(i < a1) {
                    System.out.print("  ");
                } else {
                    System.out.print("* ");
                }
            }
            System.out.println();
        }
		
		System.out.println("---------------------------");

		// 문제6)
		// * *
		//  *
		// * *
//		for(int i=1; i<=9; i++) {
//			if(i % 2 != 0) {
//				System.out.print("*");
//			} else {
//				System.out.print(" ");
//			}
//			if(i%3 == 0) {
//				System.out.println();
//			}
//		}
		
		for(int k = 0 ; k < 3 ; k++) {

            int a1 = k % 2;

            for(int i = 0 ; i < 3 ; i++) {
                if(i % 2 == a1) {
                    System.out.print("* ");
                } else {
                    System.out.print("  ");
                }
            }
            System.out.println();
        }
	}

}
