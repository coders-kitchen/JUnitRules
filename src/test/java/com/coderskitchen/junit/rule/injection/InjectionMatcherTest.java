package com.coderskitchen.junit.rule.injection;

import com.coderskitchen.junit.rule.injection.util.cut.EmptyClass;
import com.coderskitchen.junit.rule.injection.util.cut.NonEmptyClass;
import com.coderskitchen.junit.rule.injection.util.test.MocksToInject;
import com.coderskitchen.junit.rule.injection.util.test.NothingMatchingToInject;
import com.coderskitchen.junit.rule.injection.util.test.NothingToInject;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsCollectionContaining.hasItem;
import static org.hamcrest.core.IsCollectionContaining.hasItems;
import org.junit.Test;

import java.util.Set;

public class InjectionMatcherTest {

  InjectionMatcher cut = new InjectionMatcher();

  @Test
  public void nothingToInject() {
    Class cutter = EmptyClass.class;
    Class cutTest = NothingToInject.class;
    cut.forClassUnderTest(cutter);
    cut.atTestClass(cutTest);
    cut.calculateMatches();
    Set<InjectionMatch> injectionMatches = cut.getMatches();
    assertThat(injectionMatches.isEmpty(), is(true));
  }

  @Test
  public void noMatchesFound() {
    Class cutter = EmptyClass.class;
    Class cutTest = NothingMatchingToInject.class;
    cut.forClassUnderTest(cutter);
    cut.atTestClass(cutTest);
    cut.calculateMatches();
    Set<InjectionMatch> matches = cut.getMatches();

    assertThat(matches, hasItem(new InjectionMatch("injectionString", null)));
  }

  @Test
  public void matchesFound() {
    Class cutter = NonEmptyClass.class;
    Class<MocksToInject> cutTest = MocksToInject.class;
    cut.forClassUnderTest(cutter);
    cut.atTestClass(cutTest);
    cut.calculateMatches();
    Set<InjectionMatch> matches = cut.getMatches();
    assertThat(matches.isEmpty(), is(false));
  }

  @Test
  public void matchesAreCorrect() {
    Class cutter = NonEmptyClass.class;
    Class<MocksToInject> cutTest = MocksToInject.class;
    cut.forClassUnderTest(cutter);
    cut.atTestClass(cutTest);
    cut.calculateMatches();
    Set<InjectionMatch> matches = cut.getMatches();
    InjectionMatch[] expected = new InjectionMatch[3];
    expected[0] = (new InjectionMatch("myTestPropertyMock", "myTestProperty"));
    expected[1] = (new InjectionMatch("myStringMock", null));
    expected[2] = (new InjectionMatch("mySecondTestPropertyMock", "mySecondTestProperty"));
    assertThat(matches, hasItems(expected));
  }
}
