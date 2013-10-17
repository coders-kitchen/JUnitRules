package com.coderskitchen.junitrules.injection.util.test;

public class TestClassWithInjectingFields {

	public String myTestPropertyMock;
	String myStringMock;
	String mySecondTestPropertyMock;
	Long myThirdTestPropertyMock;

	public void setMySecondTestPropertyMock(String mySecondTestPropertyMock) {
		this.mySecondTestPropertyMock = mySecondTestPropertyMock;
	}

	public void setMyStringMock(String myStringMock) {
		this.myStringMock = myStringMock;
	}

	public void setMyTestPropertyMock(String myTestPropertyMock) {
		this.myTestPropertyMock = myTestPropertyMock;
	}

	public void setMyThirdTestPropertyMock(Long myThirdTestPropertyMock) {
		this.myThirdTestPropertyMock = myThirdTestPropertyMock;
	}
}
