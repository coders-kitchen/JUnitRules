package com.coderskitchen.junit.rule.injection;

import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

public class AutoInjectRule implements TestRule {

  private final Object objectUnderTest;
  private final Object test;

  /**
   * Constructor that sets the current testclass
   *
   * @param objectUnderTest
   */
  public AutoInjectRule(Object objectUnderTest, Object test) {
    this.objectUnderTest = objectUnderTest;
    this.test = test;
  }

  @Override
  public Statement apply(final Statement base, final Description description) {
    AutoInjectStatement autoInjectStatement = new AutoInjectStatement(new InjectionMatcher(), base);
    autoInjectStatement.atTestClass(test);
    autoInjectStatement.forClassUnderTest(objectUnderTest);
    return autoInjectStatement;
  }
}
