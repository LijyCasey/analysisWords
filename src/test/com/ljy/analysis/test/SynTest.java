package com.ljy.analysis.test;

public class SynTest {
	private static int a;

	static{
		a=10;
		System.out.println("static "+a);
	}
	public static int getA(){
		return a;
	}
	public static void main(String[] args) {
		SynTest.getA();
	}
}
