package de.age.util.duck;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import org.easymock.EasyMock;
import org.junit.Test;

public class FrankenDucktypeAdaptTest {

	private static interface SimpleDuckIF {
		void quack();
		void waddleTo(Object target);
		String getName();
	}
	
	private static interface DuckQuackIF {
		void quack();
	}
	
	private static interface DuckWaddleIF {
		void waddleTo(Object target);
	}
	
	private static interface DuckNameIF {
		String getName();
	}
	
	@Test
	public void adaptToMultipleObjectsNotNull() {
		Object[] frankenMocks = new Object[3];
		frankenMocks[0] = EasyMock.createMock(DuckQuackIF.class);
		frankenMocks[1] = EasyMock.createMock(DuckWaddleIF.class);
		frankenMocks[2] = EasyMock.createMock(DuckNameIF.class);
		SimpleDuckIF duck = Ducktype.adapt(SimpleDuckIF.class, frankenMocks);
		assertThat(duck, is(notNullValue()));
	}
	
	@Test
	public void methodCallsGetPassedToMultipleTargets() {
		Object[] frankenMocks = new Object[3];
		frankenMocks[0] = EasyMock.createMock(DuckQuackIF.class);
		frankenMocks[1] = EasyMock.createMock(DuckWaddleIF.class);
		frankenMocks[2] = EasyMock.createMock(DuckNameIF.class);
		SimpleDuckIF duck = Ducktype.adapt(SimpleDuckIF.class, frankenMocks);
		((DuckQuackIF) frankenMocks[0]).quack();
		String target = "target";
		((DuckWaddleIF) frankenMocks[1]).waddleTo(target);
		EasyMock.expect(((DuckNameIF) frankenMocks[2]).getName()).andReturn("name");
		EasyMock.replay(frankenMocks);
		duck.quack();
		duck.waddleTo(target);
		duck.getName();
		EasyMock.verify(frankenMocks);
	}
	
	@Test(expected=MissingMethodException.class)
	public void adaptThrowsExceptionWhenMethodsAreMissing() {
		Object[] frankenMocks = new Object[2];
		frankenMocks[0] = EasyMock.createMock(DuckQuackIF.class);
		frankenMocks[1] = EasyMock.createMock(DuckWaddleIF.class);
		Ducktype.adapt(SimpleDuckIF.class, frankenMocks);
	}
	
	@Test(expected=AmbiguousMethodException.class)
	public void adaptThrowsExceptionWhenMethodsAreAmbiguous() {
		Object[] frankenMocks = new Object[4];
		frankenMocks[0] = EasyMock.createMock(DuckQuackIF.class);
		frankenMocks[1] = EasyMock.createMock(DuckWaddleIF.class);
		frankenMocks[2] = EasyMock.createMock(DuckNameIF.class);
		frankenMocks[3] = EasyMock.createMock(DuckNameIF.class);
		Ducktype.adapt(SimpleDuckIF.class, frankenMocks);
	}
	
}
