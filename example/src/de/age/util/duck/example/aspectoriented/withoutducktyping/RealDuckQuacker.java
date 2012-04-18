package de.age.util.duck.example.aspectoriented.withoutducktyping;


public class RealDuckQuacker implements DuckQuackIF {

	@Override
	public void quack() {
		System.out.println("Real composite quack");
	}

}
