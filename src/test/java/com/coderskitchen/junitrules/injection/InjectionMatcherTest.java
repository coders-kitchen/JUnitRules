package com.coderskitchen.junitrules.injection;

import com.coderskitchen.junitrules.injection.util.cut.EmptyClass;
import com.coderskitchen.junitrules.injection.util.cut.NonEmptyClass;
import com.coderskitchen.junitrules.injection.util.test.MocksToInject;
import com.coderskitchen.junitrules.injection.util.test.NothingMatchingToInject;
import com.coderskitchen.junitrules.injection.util.test.NothingToInject;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsCollectionContaining.hasItem;
import org.junit.Test;

import java.lang.reflect.Field;
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
  public void noMatchesFound() throws NoSuchFieldException {
    Class cutter = EmptyClass.class;
    Class cutTest = NothingMatchingToInject.class;
    cut.forClassUnderTest(cutter);
    cut.atTestClass(cutTest);
    cut.calculateMatches();
    Set<InjectionMatch> matches = cut.getMatches();
    Field expectedField = cutTest.getDeclaredField("injectionString");
    assertThat(matches, hasItem(new InjectionMatch(expectedField, null)));
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

}
