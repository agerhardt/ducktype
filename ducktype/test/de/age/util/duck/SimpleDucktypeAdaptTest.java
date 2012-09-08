package de.age.util.duck;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.sameInstance;
import static org.junit.Assert.assertThat;
import static org.junit.Assume.assumeThat;

import org.easymock.EasyMock;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class SimpleDucktypeAdaptTest {

	private static interface SimpleDuckIF {
		void quack();
		void waddleTo(Object target);
		String getName();
	}
	
	private static interface MatchingProxyDuckIF {
		void quack();
		void waddleTo(Object target);
		String getName();
	}
	
	private SimpleDuckIF duck;
	private MatchingProxyDuckIF proxy;
	
	@Before
	public void setUp() {
		proxy = EasyMock.createMock(MatchingProxyDuckIF.class);
		duck = Ducktype.adapt(SimpleDuckIF.class, proxy);
	}
	
	@After
	public void tearDown() {
		proxy = null;
		duck = null;
	}

	@Test
	public void duckGetsCreated() {
		assertThat(duck, is(notNullValue()));
	}
	
	@Test
	public void methodGetsCalled() {
		assumeThat(duck, is(notNullValue()));
		proxy.quack();
		EasyMock.replay(proxy);
		duck.quack();
		EasyMock.verify(proxy);
	}
	
	@Test
	public void parametersGetPassed() {
		assumeThat(duck, is(notNullValue()));
		String target = "target";
		proxy.waddleTo(target);
		EasyMock.replay(proxy);
		duck.waddleTo(target);
		EasyMock.verify(proxy);
	}
	
	@Test
	public void returnValuesGetPassed() {
		assumeThat(duck, is(notNullValue()));
		String name = "name";
		EasyMock.expect(proxy.getName()).andReturn(name);
		EasyMock.replay(proxy);
		String resultName = duck.getName();
		assertThat(resultName, is(sameInstance(name)));
	}
	
	@Test
	public void adaptReturnsSameObjectWhenInterfaceIsAlreadyImplemented() {
		SimpleDuckIF identicalProxy = EasyMock.createMock(SimpleDuckIF.class);
		duck = Ducktype.adapt(SimpleDuckIF.class, identicalProxy);
		assertThat(duck, is(sameInstance(identicalProxy)));
	}

	@Test(expected=NullPointerException.class)
	public void adaptThrowsExceptionForNullListOfObjects() {
		Ducktype.adapt(Comparable.class, (Object[]) null);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void adaptThrowsExceptionWhenListOfObjectsIsEmpty() {
		Ducktype.adapt(Comparable.class);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void adaptThrowsExceptionWhenAnyObjectInListIsNull() {
		Ducktype.adapt(Comparable.class, new Object[] { "x", null, "y" });
	}
	
}
