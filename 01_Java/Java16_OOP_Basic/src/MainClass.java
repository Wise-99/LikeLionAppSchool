
public class MainClass {
	public static void main(String[] args) {
		// 객체를 만들면 메모리에 저장
		// JVM은 객체의 고유 ID 값을 부여
		// 변수에 객체를 넣으면 주소 값이 아닌 객체의 ID 값(숫자 값)이 저장됨
		// ID를 가지고 객체에 접근하여 객체가 가지고 있는 요소를 사용하게 됨
		// 메모리 조각 모음을 하더라도 ID는 변하지 않음

		// 클래스를 통해 객체를 생성한다.
		TestClass1 t1 = new TestClass1();
		System.out.printf("t1 : %s\n", t1);

		// t1에 저장되어 있는 객체의 ID를 t2에 저장한다.
		TestClass1 t2 = t1;
		System.out.printf("t2 : %s\n", t2);

		// 새로운 객체를 생성한다.
		TestClass1 t3 = new TestClass1();
		System.out.printf("t3 : %s\n", t3);

	}
}

// 클래스를 정의한다.
class TestClass1 {

}