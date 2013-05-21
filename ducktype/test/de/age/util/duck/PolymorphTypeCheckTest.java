package de.age.util.duck;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.easymock.EasyMock;
import org.junit.Test;

public class PolymorphTypeCheckTest {

	private static interface SimpleDuckIF {
		Number quack(String quackedMessage);
	}
	
	private static interface ObjectDuckIF {
		Number quack(Object quackedObject);
	}
	
	private static class ObjectDuck {
		@SuppressWarnings("unused")
		public Number quack(Object quackedObject) {return null;};
	}
	
	private static interface NumberDuckIF {
		public Integer quack(String quackedMessage);
	}
	
	private static class NumberDuck {
		@SuppressWarnings("unused")
		public Integer quack(String quackedMessage) {return null;};
	}
	
	@Test
	public void moreGeneralArgumentTypeIsAllowed() {
		assertThat(Ducktype.check(SimpleDuckIF.class, new ObjectDuck()), is(true));
	}
	
	@Test
	public void moreSpecificReturnTypeIsAllowed() {
		assertThat(Ducktype.check(SimpleDuckIF.class, new NumberDuck()), is(true));
	}
	
	@Test
	public void moreGeneralArgumentTypeGetsCalled() {
		ObjectDuckIF mock = EasyMock.createMock(ObjectDuckIF.class);
		SimpleDuckIF duck = Ducktype.adapt(SimpleDuckIF.class, mock);
		EasyMock.expect(mock.quack("QUACK")).andReturn(null);
		EasyMock.replay(mock);
		duck.quack("QUACK");
		EasyMock.verify(mock);
	}
	
	@Test
	public void moreSpecificArgumentTypeGetsCalled() {
		NumberDuckIF mock = EasyMock.createMock(NumberDuckIF.class);
		SimpleDuckIF duck = Ducktype.adapt(SimpleDuckIF.class, mock);
		EasyMock.expect(mock.quack("QUACK")).andReturn(null);
		EasyMock.replay(mock);
		duck.quack("QUACK");
		EasyMock.verify(mock);
	}

}
