package de.age.util.duck.example.aspectoriented.withDucktyping;

public class FakeDuckWaddler {
	
	public void waddleTo(Object target) {
		System.out.println("Fakishly compositely waddling towards: " + target.toString());
	}
}
