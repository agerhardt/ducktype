package de.age.util.duck.example;

public class FakeDuckImpl {

	public void quack() {
		System.out.println("Fake quack");
	}

	public void waddleTo(Object target) {
		System.out.println("Fakishly waddling towards: " + target.toString());
	}
}
