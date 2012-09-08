package de.age.util.duck;

import java.lang.reflect.Method;
import java.util.Arrays;

public class AmbiguousMethodException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	private final Method[] methods;
	
	public AmbiguousMethodException(Method ... methods) {
		this.methods = methods;
	}

	@Override
	public String toString() {
		return "AmbiguousMethodException [methods=" + Arrays.toString(methods) + "]";
	}

}
