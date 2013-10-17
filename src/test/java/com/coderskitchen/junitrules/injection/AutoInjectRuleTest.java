package com.coderskitchen.junitrules.injection;

import com.coderskitchen.junitrules.injection.util.cut.NonEmptyClass;
import com.coderskitchen.junitrules.injection.util.test.TestClassWithInjectingFields;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class AutoInjectRuleTest {

  @Mock
  Statement statementMock;

  @Mock
  Description descriptionMock;

  @Before
  public void setup() {
    MockitoAnnotations.initMocks(this);
  }

  @Test
  public void testApply() throws Exception {
    NonEmptyClass classUnderTest = new NonEmptyClass();
    TestClassWithInjectingFields testClassWithInjectingFields = new TestClassWithInjectingFields();
    AutoInjectRule cut = new AutoInjectRule(classUnderTest, testClassWithInjectingFields);
    Statement result = cut.apply(statementMock, descriptionMock);


  }
}
