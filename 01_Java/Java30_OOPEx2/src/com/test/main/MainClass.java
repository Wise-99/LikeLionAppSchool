package com.test.main;

import java.util.ArrayList;
import java.util.Scanner;
import com.test.zoo.*;

//동물원
// 모든 클래스는 com.test.zoo 라는 패키지에 만들어준다.
// 동물들의 특징은 다음과 같다.
// 코끼리
// 다리 : 4개, 코 : 길다, 몸 : 크다, 식사방법 : 코를 이용해 먹는다.
// 사막여우
// 다리 : 4개, 코 : 짧다, 몸 : 작다, 식사방법 : 손을 이용해 먹는다.
// 캥거루
// 다리 : 2개, 코 : 짧다, 몸 : 크다, 식사방법 : 나뭇잎을 뜯어 먹는다.
// 다리, 코, 몸은 변수로 정의한다.
// 식사 방법은 메서드로 정의한다.
// 각 동물은 자신의 정보를 출력하는 메서드를 가지고 있다.

// 예시
// 동물의 종류를 입력해주세요
// 1. 코끼리, 2. 사막여우, 3. 캥거루, 0. 입력끝 : 1
// 동물의 이름을 입력해주세요 : 맘모스
// 위의 입력을 0을 입력할 때 까지 반복한다....
// 0을 눌러 입력이 끝나면 모든 동물의 정보를 출력한다.
// 타입 : 코끼리
// 이름 : 맘모스
// 다리 : 4개
// 코 : 길다
// 몸 : 크다
// 식사방법 : 손을 이용해 먹는다.
// .......

public class MainClass {

	public static void main(String[] args) {

		/*
		 * Scanner scan = new Scanner(System.in); int select = 1; String name = "";
		 * 
		 * ArrayList<Animal> arraylist = new ArrayList<>();
		 * 
		 * while(true) { System.out.println("동물의 종류를 입력해주세요");
		 * System.out.print("1. 코끼리, 2. 사막여우, 3. 캥거루, 0. 입력끝 : "); select =
		 * scan.nextInt();
		 * 
		 * if(select == 0) { // 0 입력 시 종료 System.out.println("------- 입력 종료 -------");
		 * break; } System.out.print("이름을 입력해주세요 : "); name = scan.next();
		 * 
		 * 
		 * if (select == 1) { Elephant elephant = new Elephant(name);
		 * arraylist.add(elephant); } else if (select == 2) { Fox fox = new Fox(name);
		 * arraylist.add(fox); } else if (select == 3) { Kangaroo kang = new
		 * Kangaroo(name); arraylist.add(kang); }
		 * 
		 * }
		 * 
		 * for (Animal arr : arraylist) { arr.showInfo(); }
		 */

		Scanner scanner = new Scanner(System.in);

		// 동물원 객체를 생성한다.
		ZooClass zooClass = new ZooClass(scanner);

		// 입력받은 동물의 타입 번호
		int typeNumber = 0;

		// 입력 받는 동물 번호가 0이 아닌 동안 반복한다.
		do {
			// 동물 타입 번호를 입력받는다.
			System.out.println("동물의 타입을 입력해주세요.");
			System.out.print("1.코끼리, 2.사막여우, 3.캥거루, 0.종료 : ");
			typeNumber = scanner.nextInt();

			// 동물 타입 번호가 0이 아니라면...
			if (typeNumber != 0) {
				// 동물 이름을 입력 받는다.
				System.out.print("동물의 이름을 입력해주세요 : ");
				String name = scanner.next();

				// 동물 객체를 생성한다.
				AnimalClass animal = null;

				switch (typeNumber) {
				case 1:
					animal = new ElephantClass(name);
					break;

				case 2:
					animal = new DesertFoxClass(name);
					break;

				case 3:
					animal = new KangarooClass(name);
					break;
				}
				// 동물 객체를 담아준다.
				zooClass.addAnimal(animal);
			}

		} while (typeNumber != 0);

		// 입력한 동물들의 정보를 출력한다.
		zooClass.printAnimalInfo();

		scanner.close();

	}

}