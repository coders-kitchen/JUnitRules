package com.coderskitchen.junit.rule;

import static org.junit.Assert.assertTrue;
import org.junit.Rule;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;


/**
 * Unit test for simple App.
 */
public class AppTest {
    @Rule
    public SetupEntityManagerRule setupEntityManagerRule = new SetupEntityManagerRule(this);
    
    @PersistenceContext(unitName="RuleTutorialPU")
    EntityManager first;
    
    @PersistenceContext(unitName="SecondRuleTutorialPU")
    EntityManager second;
    
    @Test
    public void out() {
        assertTrue(true);
    }
}
