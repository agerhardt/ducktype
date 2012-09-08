package de.age.util.duck.example.aspectoriented.withDucktyping;

import de.age.util.duck.Ducktype;
import de.age.util.duck.example.DuckIF;

public class UsageExample {

	public static void main(String[] args) {
		FakeDuckQuacker fakeDuckQuacker = new FakeDuckQuacker(); // not a duck
		FakeDuckWaddler fakeDuckWaddler = new FakeDuckWaddler(); // not a duck
		DuckIF frankenduck = Ducktype.adapt(DuckIF.class, fakeDuckQuacker, fakeDuckWaddler); // can form a duck without changing the original interface
		frankenduck.quack();
	}
}
