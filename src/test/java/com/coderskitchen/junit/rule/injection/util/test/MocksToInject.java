package com.coderskitchen.junit.rule.injection.util.test;

import org.mockito.Mock;

public class MocksToInject {

  @Mock
  String myStringMock;

  @Mock
  String myTestPropertyMock;

  String mySecondTestPropertyMock;

  Long myThirdTestPropertyMock;
}
