package com.coderskitchen.junit.rule;

import com.coderskitchen.junit.rule.em.EntityManagerRule;
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
    public EntityManagerRule setupEntityManagerRule = new EntityManagerRule(this);
    
    @PersistenceContext(unitName="RuleTutorialPU")
    EntityManager first;
    
    @PersistenceContext(unitName="SecondRuleTutorialPU")
    EntityManager second;
    
    @Test
    public void out() {
        assertTrue(true);
    }
}
