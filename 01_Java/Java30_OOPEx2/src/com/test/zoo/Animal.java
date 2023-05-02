package com.test.zoo;

public class Animal {
	String type;
	String name;
	int legs = 4;
	String nose = "짧다";
	String body = "크다";
	String eat;
	
	public void showInfo() {
        System.out.printf("타입 : %s \n", type);
        System.out.printf("이름 : %s \n", name);
        System.out.printf("다리 : %d개 \n", legs);
        System.out.printf("코 : %s \n", nose);
        System.out.printf("몸 : %s \n", body);
        System.out.printf("식사방법 : %s \n", eat);
        System.out.printf("\n");
    }
}