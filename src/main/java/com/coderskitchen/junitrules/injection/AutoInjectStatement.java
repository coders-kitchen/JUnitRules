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
    System.out.println("Evaluate surrounded");
    surrounded.evaluate();
    System.out.println("Evaluate AutoInjectStatement");
    matcher.calculateMatches();
    Set<InjectionMatch> matches = matcher.getMatches();
    for (InjectionMatch match : matches) {
      Field classField = match.classField;
      if (classField == null)
        continue;
      Field mockField = match.mockField;
      boolean cutAccessible = classField.isAccessible();
      boolean testAccessible = mockField.isAccessible();
      classField.setAccessible(true);
      mockField.setAccessible(true);
      Object value = mockField.get(testClass);
      System.out.println(value);
      classField.set(classUnderTest, value);
      classField.setAccessible(cutAccessible);
      mockField.setAccessible(testAccessible);
    }

  }
}
