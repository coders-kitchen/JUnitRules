package com.coderskitchen.junit.rule.injection;

import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

public class AutoInjectRule implements TestRule {

  private final Class testClass;

  /**
   * Constructor that sets the current testclass
   *
   * @param testClass
   */
  public AutoInjectRule(Class testClass) {
    this.testClass = testClass;
  }

  @Override
  public Statement apply(final Statement base, final Description description) {

    return null;
  }
}
