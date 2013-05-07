package com.coderskitchen.junit.rule.injection;

import com.coderskitchen.junit.rule.injection.util.cut.NonEmptyClass;
import com.coderskitchen.junit.rule.injection.util.test.MocksToInject;
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
    MocksToInject mocksToInject = new MocksToInject();
    AutoInjectRule cut = new AutoInjectRule(classUnderTest, mocksToInject);
    Statement result = cut.apply(statementMock, descriptionMock);

  }
}
