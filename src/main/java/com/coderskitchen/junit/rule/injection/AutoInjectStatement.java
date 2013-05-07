package com.coderskitchen.junit.rule.injection;

import org.junit.runners.model.Statement;

public class AutoInjectStatement extends Statement {

  private final InjectionMatcher matcher;
  private final Statement surrounded;

  public AutoInjectStatement(InjectionMatcher matcher, Statement surrounded) {
    this.surrounded = surrounded;
    this.matcher = matcher;
  }

  public AutoInjectStatement forClassUnderTest(Object classUnderTest) {
    matcher.forClassUnderTest(classUnderTest.getClass());
    return this;
  }

  public AutoInjectStatement atTestClass(Object testClass) {
    matcher.atTestClass(testClass.getClass());
    return this;
  }

  @Override
  public void evaluate() throws Throwable {

  }
}
