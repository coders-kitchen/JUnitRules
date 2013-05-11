package com.coderskitchen.junitrules.em;

import org.junit.Before;
import org.junit.Test;
import org.junit.runners.model.Statement;
import org.mockito.Mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.util.ArrayList;

public class EntityManagerStatementTest {

  EntityManagerStatement cut;
  @Mock
  Statement statementMock;
  @Mock
  EntityTransaction transactionMock;
  @Mock
  EntityManager emMock;
  private ArrayList<EntityManager> managers;

  @Before
  public void setup() {
    MockitoAnnotations.initMocks(this);
    managers = createAndSetupEntityManagersList();
  }

  @Test(expected = Exception.class)
  public void failingTransactionStopsExecution() throws Throwable {
    cut = new EntityManagerStatement(statementMock, managers);
    when(emMock.getTransaction()).thenThrow(new Exception("Mock Exception"));
    cut.evaluate();
  }

  private ArrayList<EntityManager> createAndSetupEntityManagersList() {
    ArrayList<EntityManager> managers = new ArrayList<EntityManager>();
    managers.add(emMock);
    return managers;
  }

  @Test
  public void assertThatTransactionGetStarted() throws Throwable {
    when(emMock.getTransaction()).thenReturn(transactionMock);
    cut = new EntityManagerStatement(statementMock, managers);
    cut.evaluate();
    verify(transactionMock, times(1)).begin();
  }

  @Test
  public void assertThatTransactionAreRollbacked() throws Throwable {
    when(emMock.getTransaction()).thenReturn(transactionMock);
    cut = new EntityManagerStatement(statementMock, managers);
    cut.evaluate();
    verify(transactionMock, times(1)).rollback();
  }

  @Test
  public void successfulTransactionStartEvaluatesGivenStatement() throws Throwable {
    cut = new EntityManagerStatement(statementMock, managers);

    when(emMock.getTransaction()).thenReturn(transactionMock);
    cut.evaluate();
    verify(statementMock, times(1)).evaluate();
  }
}
