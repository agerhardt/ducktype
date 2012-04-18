package de.age.util.duck.example.aspectoriented.withoutDucktyping;


public class RealDuckWaddler implements DuckWaddleIF {

	@Override
	public void waddleTo(Object target) {
		System.out.println("Really compositely waddling towards: " + target.toString());
	}

}
