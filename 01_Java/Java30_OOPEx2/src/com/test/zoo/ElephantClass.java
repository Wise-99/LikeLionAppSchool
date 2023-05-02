package com.test.zoo;

public class ElephantClass extends AnimalClass {

	// 생성자
	public ElephantClass(String name) {
		this.type = "코끼리";
		this.name = name;
		this.numberLegs = 4;
		this.lengthNose = "길다";
		this.bodySize = "크다";
	}

	// 메서드 오버라이딩
	@Override
	public void howEat() {
		System.out.println("코를 이용해 먹습니다.");
	}
}
