# JUnitRules #

## About ##
JUnitRules is a collection of rules for JUnit. It decreases the amount of boilerplate code written in tests.

At the moment two rules are provided
 * [EntityManagerRule](https://github.com/coders-kitchen/JUnitRules/edit/master/README.md#entitymanagerrule)
 * [FilePrepareRule](https://github.com/coders-kitchen/JUnitRules/edit/master/README.md#filepreparerule)

## EntityManagerRule ##

This rule provides a cool solution when you have to setup entity managers during your tests.
Ecspecially when you are in the context of pure Java EE (6), you'll write often code like this

```java
EntityManagerFactory emFactory = Persistence.createEntityManagerFactory("YourPU");
EntityManager em = emFactory.createEntityManager();
Transaction trans = em.getTransaction();
trans.begin();

// ... Your code

trans.rollback();
em.close();
```

With this rule, this is compressed to this:

```java
@Rule
public EntityManagerRule emRule = new EntityManagerRule(this);

@PersistenceContext(unitName = "YourPU")
EntityManager em;

@Test
public void yourTest() {
    // ... your code
}
```

<b>Important</b> Please notice that you must provide the unitName. Otherwise the rule can't determine what to do.


## FilePrepareRule ##

