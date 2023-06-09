package com.test.main;

public class MainClass {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		// StringBuffer : 관리하는 문자열에 대한 직접적인 조작을 할 수 있다.
		// String이 관리하는 문자열은 변경이 불가능하다.
		// 이에, 추가 반환 삭제 등의 작업을 하게 되면 새로운 문자열이 계속 생성된다.
		// 메모리를 효율적으로 사용하기 위해 자주 변화되는 문자열은 StringBuffer로 관리한다.
		
		// StringBuffer 생성
		StringBuffer buffer1 = new StringBuffer();
		StringBuffer buffer2 = new StringBuffer("문자열");
		
		// 문자열 추가
		buffer1.append(100);
		buffer1.append(11.11);
		buffer1.append("문자열"); // 10011.11문자열
		
		System.out.println(buffer1);
		
		// StringBuffer -> String
		String str1 = buffer1.toString();
        System.out.println(str1); // 10011.11문자열
        
        // 삽입
        buffer1.insert(2, 555);
        buffer1.insert(8, "문자열");
        System.out.println(buffer1); // 10555011문자열.11문자열
        
        // 변경
        // 2 ~ 5 - 1까지를 동해물과 백두산으로 바꾼다.
        buffer1.replace(2, 5, "동해물과 백두산");
        System.out.println(buffer1); // 10동해물과 백두산011문자열.11문자열
        
        // 삭제
        // 2 ~ 10 - 1까지 삭제한다.
        buffer1.delete(2, 10);
        System.out.println(buffer1); // 10011문자열.11문자열
	}

}
