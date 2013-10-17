package com.coderskitchen.junitrules.injection;

import com.coderskitchen.junitrules.injection.util.cut.NonEmptyClass;
import com.coderskitchen.junitrules.injection.util.test.TestClassWithInjectingFields;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import org.junit.Test;
import org.junit.runners.model.Statement;

public class InjectionMatchingTest {



	@Test
	public void matchesAreCorrect() throws Throwable {

		NonEmptyClass nonEmptyClass = new NonEmptyClass();

		TestClassWithInjectingFields testClassWithInjectingFields = new TestClassWithInjectingFields();
		testClassWithInjectingFields.myTestPropertyMock = "TESTVALUE1";
		testClassWithInjectingFields.setMySecondTestPropertyMock("SECOND");
		testClassWithInjectingFields.setMyStringMock("STRINGMOCK");
		testClassWithInjectingFields.setMyThirdTestPropertyMock(-1L);

		InjectionMatcher matcher = new InjectionMatcher();
		AutoInjectStatement cut = new AutoInjectStatement(matcher, new Statement() {
			@Override
			public void evaluate() throws Throwable {
			}
		});

		cut.atTestClass(testClassWithInjectingFields);
		cut.forClassUnderTest(nonEmptyClass);

		cut.evaluate();

		assertThat(nonEmptyClass.myTestProperty, is("TESTVALUE1"));
		testClassWithInjectingFields.myTestPropertyMock = "TESTVALUE2";
	}
}
