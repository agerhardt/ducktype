package de.age.util.duck;

import java.lang.reflect.Method;

public class MissingMethodException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	private final Method method;
	
	public MissingMethodException(Method method) {
		this.method = method;
	}

	@Override
	public String toString() {
		return "MissingMethodException [method=" + method + "]";
	}
	
}
