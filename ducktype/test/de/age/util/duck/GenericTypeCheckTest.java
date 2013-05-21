package de.age.util.duck;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.math.BigInteger;

import org.junit.Test;

public class GenericTypeCheckTest {
	
	private static interface SimpleDuckIF {
		Number quack(Number numberOfQuacks);
	}
	
	private static class GenericDuck<T, U> {
		@SuppressWarnings("unused")
		public T quack(U someArgument) {return null;}
	}
	
	private static class IllegalGenericDuck<T extends Exception> {
		@SuppressWarnings("unused")
		public T quack(Number numberOfQuacks) {return null;}
	}
	
	private static class SubtypedGenericDuck<T extends BigInteger> {
		@SuppressWarnings("unused")
		public T quack(Number numberOfQuacks) {return null;}
	}
	
	@Test
	public void exactMatchingTypesMatch() {
		assertThat(Ducktype.check(SimpleDuckIF.class, new GenericDuck<Number, Number>()), is(true));
	}
	
	@Test
	public void illegalGenericReturnTypeNotAllowed() {
		assertThat(Ducktype.check(SimpleDuckIF.class, new IllegalGenericDuck<Exception>()), is(false));
	}
	
	@Test
	public void subtypedGenericReturnTypeMatch() {
		assertThat(Ducktype.check(SimpleDuckIF.class, new SubtypedGenericDuck<BigInteger>()), is(true));
	}
}
