package de.age.util.duck;

import java.lang.reflect.Method;

class CallTarget {
	
	private final Object targetObject;
	private final Method targetMethod;
	
	public CallTarget(Object targetObject, Method targetMethod) {
		super();
		this.targetObject = targetObject;
		this.targetMethod = targetMethod;
	}

	Object getTargetObject() {
		return targetObject;
	}
	
	Method getTargetMethod() {
		return targetMethod;
	}

}
