package com.coderskitchen.junit.rule.injection;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import org.junit.Test;

import java.util.Set;

public class InjectionMatcherTest {

  InjectionMatcher cut = new InjectionMatcher();

  @Test
  public void nothingToInject() {
    Class cutter = EmptyClass.class;
    Class cutTest = NothingToInjectTestCase.class;
    cut.forClassUnderTest(cutter);
    cut.atTestClass(cutTest);
    cut.match();
    Set<InjectionMatch> injectionMatches = cut.getMatches();
    assertThat(injectionMatches.isEmpty(), is(true));
  }

  @Test
  public void noMatchesFound() {
    Class cutter = EmptyClass.class;
    Class cutTest = NothingMatchingToInject.class;
    cut.forClassUnderTest(cutter);
    cut.atTestClass(cutTest);
    cut.match();
    assertThat(cut.getMatches().isEmpty(), is(true));
  }
}
