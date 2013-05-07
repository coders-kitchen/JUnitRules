package com.coderskitchen.junit.rule.injection;

import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Set;

public class InjectionMatcher {
  private Set<InjectionMatch> matches;
  private Field[] cutFields;
  private Field[] testFields;
  private Set<String> alreadyMatchedFields;

  public InjectionMatcher() {
    matches = new HashSet<InjectionMatch>();
    alreadyMatchedFields = new HashSet<String>();
  }

  public InjectionMatcher forClassUnderTest(final Class classUnderTest) {
    cutFields = classUnderTest.getDeclaredFields();
    return this;
  }

  public InjectionMatcher atTestClass(final Class testClass) {
    testFields = testClass.getDeclaredFields();
    return this;
  }

  public InjectionMatcher calculateMatches() {
    matchFields();
    addMissingMockFields();

    return this;
  }

  private void matchFields() {
    for (Field cutField : cutFields) {
      for (Field testField : testFields) {
        addMatchIfFieldsAreEqual(cutField, testField);
      }
    }
  }

  private void addMatchIfFieldsAreEqual(final Field cutField, final Field testField) {
    String testFieldName = testField.getName();
    String cutFieldName = cutField.getName();
    if (areNamesEqual(testFieldName, cutFieldName) && areTypesEqual(cutField, testField)) {
      alreadyMatchedFields.add(testFieldName);
      matches.add(new InjectionMatch(testField, cutField));
    }
  }

  private void addMissingMockFields() {
    for (Field testField : testFields) {
      if (alreadyMatchedFields.contains(testField.getName()))
        continue;
      matches.add(new InjectionMatch(testField, null));
    }
  }

  public Set<InjectionMatch> getMatches() {

    return matches;
  }

  private boolean areNamesEqual(final String testFieldName, final String cutFieldName) {
    return testFieldName.contains(cutFieldName);
  }

  private boolean areTypesEqual(final Field cutField, final Field testField) {
    return cutField.getType().equals(testField.getType());
  }
}
