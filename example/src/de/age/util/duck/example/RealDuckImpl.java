package de.age.util.duck.example;

public class RealDuckImpl implements DuckIF {

	@Override
	public void quack() {
		System.out.println("Real quack");
	}

	@Override
	public void waddleTo(Object target) {
		System.out.println("Really waddling towards: " + target.toString());
	}

}
