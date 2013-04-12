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

import org.junit.runners.model.Statement;

import javax.persistence.EntityManager;
import java.util.ArrayList;

/**
 * @author peter
 */
public class EntityManagerStatement extends Statement {
	private final Statement surrounded;
	private final ArrayList<EntityManager> entityManagers;

	public EntityManagerStatement(Statement surrounded, ArrayList<EntityManager> entityManagers) {
		this.surrounded = surrounded;
		this.entityManagers = entityManagers;
	}

	@Override
	public void evaluate() throws Throwable {
		try {
			for (EntityManager em : entityManagers) {
				em.getTransaction().begin();
			}
			surrounded.evaluate();
		} catch (Exception e) {
			for (EntityManager em : entityManagers) {
				try {
					em.getTransaction().rollback();
					em.close();

				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
			throw e;
		}
	}
}
