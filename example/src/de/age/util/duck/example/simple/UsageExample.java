package de.age.util.duck.example.simple;

import de.age.util.duck.Ducktype;
import de.age.util.duck.example.DuckIF;
import de.age.util.duck.example.FakeDuckImpl;
import de.age.util.duck.example.RealDuckImpl;

public class UsageExample {

	public static void main(String[] args) {
		DuckIF[] ducks = new DuckIF[2];
		ducks[0] = new RealDuckImpl();
		ducks[1] = Ducktype.adapt(DuckIF.class, new FakeDuckImpl());
		for (DuckIF duck : ducks) {
			duck.quack();
		}
		/*
		 * On the other hand this will not work:
		 * 
		 * ducks[1] = new FakeDuckImpl();
		 */
	}
}
