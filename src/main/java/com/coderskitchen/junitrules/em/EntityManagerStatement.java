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
package com.coderskitchen.junitrules.em;

import org.junit.runners.model.Statement;

import javax.persistence.EntityManager;
import java.util.ArrayList;

/**
 * This class is the decorater that controls the creation and destruction of the transactions.
 * <p/>
 * It takes also care of exceptional behavior which may occur during usage of the entity managers.
 *
 * @author peter daum
 */
public class EntityManagerStatement extends Statement {
  private final Statement surrounded;
  private final ArrayList<EntityManager> entityManagers;

  /**
   * Constructor.
   * <p/>
   * Takes the original statement and a list of entity managers
   *
   * @param surrounded     original statement
   * @param entityManagers
   */
  public EntityManagerStatement(Statement surrounded, ArrayList<EntityManager> entityManagers) {
    this.surrounded = surrounded;
    this.entityManagers = entityManagers;
  }

  /**
   * Ensures that transactions are started before and rollbacked after the execution of the original statement.
   *
   * @throws Throwable
   */
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
