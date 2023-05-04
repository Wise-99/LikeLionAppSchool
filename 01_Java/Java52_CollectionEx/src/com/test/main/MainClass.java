package com.test.main;

public class MainClass {

	public static void main(String[] args) {

		// Factory 객체를 생성한다.
		Factory factory = new Factory();

		// 자동차 종류
		int carType = 0;

		do {
			// 자동차 종류를 입력받는다.
			carType = factory.inputCarType();
			
			if(carType != 0) {
                // 자동차 정보를 입력받는다.
                factory.inputCarInfo(carType);
            }

		} while (carType != 0);

		// 입력한 자동차들의 정보를 출력한다.
		factory.printCarInfo();

	}

}

