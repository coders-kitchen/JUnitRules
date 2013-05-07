package com.coderskitchen.junit.rule.injection;

import org.junit.runners.model.Statement;

import java.lang.reflect.Field;
import java.util.Set;

public class AutoInjectStatement extends Statement {

  private final InjectionMatcher matcher;
  private final Statement surrounded;
  private Object classUnderTest;
  private Object testClass;

  public AutoInjectStatement(InjectionMatcher matcher, Statement surrounded) {
    this.surrounded = surrounded;
    this.matcher = matcher;
  }

  public AutoInjectStatement forClassUnderTest(Object classUnderTest) {
    this.classUnderTest = classUnderTest;
    matcher.forClassUnderTest(classUnderTest.getClass());
    return this;
  }

  public AutoInjectStatement atTestClass(Object testClass) {
    this.testClass = testClass;
    matcher.atTestClass(testClass.getClass());
    return this;
  }

  @Override
  public void evaluate() throws Throwable {
    matcher.calculateMatches();
    Set<InjectionMatch> matches = matcher.getMatches();
    for (InjectionMatch match : matches) {
      Field classField = match.classField;
      if (classField == null)
        continue;
      Field mockField = match.mockField;
      boolean accessible = classField.isAccessible();
      classField.setAccessible(true);
      Object value = mockField.get(testClass);
      classField.set(classUnderTest, value);
      classField.setAccessible(accessible);
    }
    surrounded.evaluate();
  }
}
