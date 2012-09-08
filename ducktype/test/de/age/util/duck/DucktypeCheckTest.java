package de.age.util.duck;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

public class DucktypeCheckTest {

	private static interface SimpleDuckIF {
		void quack();
	}
	
	private static class MatchingDuckClass {
		@SuppressWarnings("unused")
		public void quack() {}
	}
	
	private static class PrivateAccessDuckClass {
		@SuppressWarnings("unused")
		private void quack() {}
	}
	
	private static class WrongReturnDuckClass {
		@SuppressWarnings("unused")
		public String quack() {return null;}
	}
	
	private static class WrongParameterDuckClass {
		@SuppressWarnings("unused")
		public void quack(String s) {}
	}
	
	private static class WrongNameDuckClass {
		@SuppressWarnings("unused")
		public void quack_othername() {}
	}
	
	@Test(expected=NullPointerException.class)
	public void checkThrowsExceptionForNullListOfObjects() {
		Ducktype.check(Comparable.class, (Object[]) null);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void checkThrowsExceptionWhenListOfObjectsIsEmpty() {
		Ducktype.check(Comparable.class);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void checkThrowsExceptionWhenAnyObjectInListIsNull() {
		Ducktype.check(Comparable.class, new Object[] { "x", null, "y" });
	}
	
	@Test
	public void checkReturnsTrueForSimpleCase() {
		boolean checkResult = Ducktype.check(SimpleDuckIF.class, new MatchingDuckClass());
		assertThat(checkResult, is(true));
	}
	
	@Test
	public void checkReturnsFalseForPrivateAccessability() {
		boolean checkResult = Ducktype.check(SimpleDuckIF.class, new PrivateAccessDuckClass());
		assertThat(checkResult, is(false));
	}
	
	@Test
	public void checkReturnsFalseForWrongReturnType() {
		boolean checkResult = Ducktype.check(SimpleDuckIF.class, new WrongReturnDuckClass());
		assertThat(checkResult, is(false));
	}
	
	@Test
	public void checkReturnsFalseForWrongParameters() {
		boolean checkResult = Ducktype.check(SimpleDuckIF.class, new WrongParameterDuckClass());
		assertThat(checkResult, is(false));
	}
	
	@Test
	public void checkReturnsFalseForWrongName() {
		boolean checkResult = Ducktype.check(SimpleDuckIF.class, new WrongNameDuckClass());
		assertThat(checkResult, is(false));
	}
	
}
