package com.coderskitchen.junitrules.injection;

import com.coderskitchen.junitrules.injection.util.cut.EmptyClass;
import com.coderskitchen.junitrules.injection.util.cut.NonEmptyClass;
import com.coderskitchen.junitrules.injection.util.test.TestClassWithInjectingFields;
import com.coderskitchen.junitrules.injection.util.test.NothingToInject;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.nullValue;
import static org.junit.Assert.assertThat;
import org.junit.Before;
import org.junit.Test;
import org.junit.runners.model.Statement;
import org.mockito.Mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;

import java.util.HashSet;

public class AutoInjectStatementTest {

  AutoInjectStatement cut;

  @Mock
  Statement statementMock;

  @Mock
  InjectionMatcher matcherMock;

  @Before
  public void setup() {
    MockitoAnnotations.initMocks(this);
  }

  @Test
  public void evaluateIsCalled() throws Throwable {
    cut = new AutoInjectStatement(matcherMock, statementMock);
    cut.evaluate();
    cut.forClassUnderTest(new EmptyClass());
    cut.atTestClass(new NothingToInject());
    verify(statementMock, times(1)).evaluate();
  }

  @Test
  public void propertiesOfclassUnderTestAreSet() throws Throwable {
    cut = new AutoInjectStatement(matcherMock, statementMock);

    NonEmptyClass classUnderTest = new NonEmptyClass();
    TestClassWithInjectingFields testClassWithInjectingFields = new TestClassWithInjectingFields();

    HashSet<InjectionMatch> value = new HashSet<InjectionMatch>();
    value.add(new InjectionMatch(testClassWithInjectingFields.getClass().getDeclaredField("myTestPropertyMock"), classUnderTest.getClass().getDeclaredField("myTestProperty")));

    when(matcherMock.getMatches()).thenReturn(value);
    cut.atTestClass(testClassWithInjectingFields);
    cut.forClassUnderTest(classUnderTest);

    testClassWithInjectingFields.myTestPropertyMock = "TEST";
    cut.evaluate();

    assertThat(classUnderTest.myTestProperty, is("TEST"));
  }

  @Test
  public void notMockedPropertiesOfClassUnderTestAreNotSet() throws Throwable {
    cut = new AutoInjectStatement(matcherMock, statementMock);

    NonEmptyClass classUnderTest = new NonEmptyClass();
    TestClassWithInjectingFields testClassWithInjectingFields = new TestClassWithInjectingFields();

    HashSet<InjectionMatch> value = new HashSet<InjectionMatch>();
    value.add(new InjectionMatch(testClassWithInjectingFields.getClass().getDeclaredField("myTestPropertyMock"), classUnderTest.getClass().getDeclaredField("myTestProperty")));

    when(matcherMock.getMatches()).thenReturn(value);
    cut.atTestClass(testClassWithInjectingFields);
    cut.forClassUnderTest(classUnderTest);

    testClassWithInjectingFields.myTestPropertyMock = "TEST";
    cut.evaluate();

    assertThat(classUnderTest.mySecondTestProperty, nullValue());
  }

  @Test
  public void notMatchedPropertiesAreNotTriedToBeAssigned() throws Throwable {
    cut = new AutoInjectStatement(matcherMock, statementMock);

    NonEmptyClass classUnderTest = new NonEmptyClass();
    TestClassWithInjectingFields testClassWithInjectingFields = new TestClassWithInjectingFields();

    HashSet<InjectionMatch> value = new HashSet<InjectionMatch>();
    value.add(new InjectionMatch(testClassWithInjectingFields.getClass().getDeclaredField("mySecondTestPropertyMock"), null));

    when(matcherMock.getMatches()).thenReturn(value);
    cut.atTestClass(testClassWithInjectingFields);
    cut.forClassUnderTest(classUnderTest);

    testClassWithInjectingFields.myTestPropertyMock = "TEST";
    cut.evaluate();

    assertThat(classUnderTest.mySecondTestProperty, nullValue());
  }

}
