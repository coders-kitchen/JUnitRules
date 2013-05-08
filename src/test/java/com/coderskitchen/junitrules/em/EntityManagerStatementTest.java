package com.coderskitchen.junitrules.em;

import org.junit.Before;
import org.junit.Test;
import org.junit.runners.model.Statement;
import org.mockito.Mock;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;

import javax.persistence.EntityManager;
import java.util.ArrayList;

public class EntityManagerStatementTest {

  EntityManagerStatement cut;

  @Mock
  Statement statementMock;

  @Before
  public void setup() {
    MockitoAnnotations.initMocks(this);
  }
  @Test(expected = Exception.class)
  public void failingTransactionStopsExecution() throws Throwable {
    ArrayList<EntityManager> managers = new ArrayList<EntityManager>();
    EntityManager emMock = mock(EntityManager.class);
    managers.add(emMock);
    cut = new EntityManagerStatement(statementMock, managers);
    when(emMock.getTransaction()).thenThrow(new Exception("Mock Exception"));
    cut.evaluate();

  }
}
