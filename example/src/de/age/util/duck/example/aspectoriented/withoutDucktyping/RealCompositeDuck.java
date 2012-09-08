package de.age.util.duck.example.aspectoriented.withoutDucktyping;

import de.age.util.duck.example.DuckIF;

/**
 * Adapter to create a composite duck through subinterfaces.
 */
public class RealCompositeDuck implements DuckIF {

	private final DuckQuackIF quacker = new RealDuckQuacker();
	private final DuckWaddleIF waddler = new RealDuckWaddler();
	
	@Override
	public void quack() {
		quacker.quack();
	}

	@Override
	public void waddleTo(Object target) {
		waddler.waddleTo(target);
	}
	
	

}
