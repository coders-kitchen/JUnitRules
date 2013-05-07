/**
 * Copyright 2013 Peter Daum
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.coderskitchen.junitrules.injection;

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
