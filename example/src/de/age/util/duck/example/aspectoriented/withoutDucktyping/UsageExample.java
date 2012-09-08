package de.age.util.duck.example.aspectoriented.withoutDucktyping;

import de.age.util.duck.example.DuckIF;

public class UsageExample {

	public static void main(String[] args) {
		DuckIF duck = new RealCompositeDuck(); // this is really a duck
		duck.quack();
	}
}
