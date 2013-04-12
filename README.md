PersistanceManagerRule
======================

This rule provides a JUnit @Rule based setup of EntityManagers for tests.

It's derived from TestDBRule tutorial.

Usage
=====

Using this rule can be done in this way:
```java
import static org.junit.Assert.assertTrue;
import org.junit.Rule;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;


/**
 * Unit test for simple App.
 */
public class Example {
    @Rule
    public SetupEntityManagerRule setupEntityManagerRule = new SetupEntityManagerRule(this);
    
    @PersistenceContext(unitName="PUFromPersistence.xml")
    EntityManager first;
    
    @Test
    public void out() {
        assertTrue(true);
    }
}
```
<b>Important</b> Please notice that you must provide the unitName. Otherwise the rule can't determine what to do.
