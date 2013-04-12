/**
 * Copyright (c) 2013 Peter Daum (coderskitchen.com)
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction,
 * including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software,
 * and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT,
 * TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package com.coderskitchen.junit.rule;

import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import java.lang.reflect.Field;
import java.util.ArrayList;

/**
 * @author peter
 */
public class SetupEntityManagerRule implements TestRule {
	private final Object testClass;
	private final ArrayList<EntityManager> entityManagers;

	public SetupEntityManagerRule(Object testClass) {
		this.testClass = testClass;
		ArrayList<Field> fields = findFieldsRequestingEntityManager();
		entityManagers = createAndInjectEntityManagers(fields);

	}

	public Statement apply(Statement base, Description description) {
		return new EntityManagerStatement(base, entityManagers);
	}

	private ArrayList<Field> findFieldsRequestingEntityManager() {
		Field[] declaredFields = testClass.getClass().getDeclaredFields();
		ArrayList<Field> annotatedFields = new ArrayList<Field>();
		if (declaredFields == null)
			return annotatedFields;

		Boolean faultFound = false;
		for (Field field : declaredFields) {
			PersistenceContext annotation = field.getAnnotation(PersistenceContext.class);
			if (annotation == null)
				continue;
			final String unitName = annotation.unitName();
			if (unitName.isEmpty()) {
				System.err.println("Field [" + field.getName() + "] annotated with PersistenceContext must specify unitName");
				faultFound = true;
			}

			annotatedFields.add(field);
			System.out.println("Field [" + field.getName() + "] requests PU [" + unitName + "]");

		}

		if (faultFound)
			throw new RuntimeException("PersistenceContext annotated field(s) found with errors");

		return annotatedFields;
	}

	private ArrayList<EntityManager> createAndInjectEntityManagers(ArrayList<Field> fields) {
		ArrayList<EntityManager> managers = new ArrayList<EntityManager>();
		for (Field field : fields) {
			try {
				PersistenceContext annotation = field.getAnnotation(PersistenceContext.class);
				EntityManager entityManager = Persistence.createEntityManagerFactory(annotation.unitName()).createEntityManager();
				boolean accessible = field.isAccessible();
				field.setAccessible(true);
				field.set(testClass, entityManager);
				field.setAccessible(accessible);
			} catch (Exception ex) {
				throw new RuntimeException(ex);
			}
		}

		return managers;
	}

}
