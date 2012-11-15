package de.age.util.duck;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ DucktypeCheckTest.class, SimpleDucktypeAdaptTest.class,
		FrankenDucktypeAdaptTest.class, PolymorphTypeCheckTest.class,
		GenericTypeCheckTest.class })
public class AllTests {

}
