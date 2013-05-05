package com.coderskitchen.junit.rule.injection;

import java.util.HashSet;
import java.util.Set;

public class InjectionMatcher {
  private Set<InjectionMatch> matches;

  public InjectionMatcher() {
    matches = new HashSet<InjectionMatch>();
  }

  public InjectionMatcher forClassUnderTest(final Class classUnderTest) {
    return this;
  }

  public InjectionMatcher atTestClass(final Class testClass) {
    return this;
  }

  public InjectionMatcher match() {
    return this;
  }

  public InjectionMatcher inject() {
    return this;
  }

  public Set<InjectionMatch> getMatches() {
    return matches;
  }
}
