package de.age.util.duck;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class Ducktype {
	
	private Ducktype() {
	}
	
	public static boolean check(Class<?> ducktypeClass, Object ... implementingObjects) {
		checkObjectList(implementingObjects);
		try {
			checkForMissingMethods(ducktypeClass, implementingObjects);
			checkForAmbiguousMethods(ducktypeClass, implementingObjects);
		} catch (MissingMethodException | AmbiguousMethodException exc) {
			return false;
		}
		return true;
	}

	private static void checkForMissingMethods(Class<?> ducktypeClass,
			Object ... implementingObjects) {
		for (Method method : ducktypeClass.getMethods()) {
			if (objectsAreLackingMethod(method, implementingObjects)) {
				throw new MissingMethodException(method);
			}
		}
	}
	
	private static void checkForAmbiguousMethods(Class<?> ducktypeClass,
			Object ... implementingObjects) {
		if (implementingObjects.length == 1) {
			return;
		}
		for (Method method : ducktypeClass.getMethods()) {
			if (methodIsAmbiguous(method, implementingObjects)) {
				throw new AmbiguousMethodException(method);
			}
		}
	}

	@SuppressWarnings("unchecked")
	public static <D> D adapt(Class<D> ducktypeClass, Object ... implementingObjects) {
		check(ducktypeClass, implementingObjects);
		checkForMissingMethods(ducktypeClass, implementingObjects);
		checkForAmbiguousMethods(ducktypeClass, implementingObjects);
		if (implementingObjects.length == 1 && ducktypeClass.isAssignableFrom(implementingObjects[0].getClass())) {
			return (D) implementingObjects[0];
		} else {
			return FrankenDuckProxy.createProxy(ducktypeClass, implementingObjects);
		}
	}
	
	private static void checkObjectList(Object ... implementingObjects) {
		if (implementingObjects == null) {
			throw new NullPointerException("List of objects must not be null");
		}
		if (implementingObjects.length == 0) {
			throw new IllegalArgumentException("List of objects must not be empty");
		}
		for (Object object : implementingObjects) {
			if (object == null) {
				throw new IllegalArgumentException("List of objects must not contain null");
			}
		}
	}
	
	private static boolean objectsAreLackingMethod(Method method, Object ... implementingObjects) {
		for (Object o : implementingObjects) {
			Class<? extends Object> objectClass = o.getClass();
			try {
				Method objectMethod = objectClass.getMethod(method.getName(), method.getParameterTypes());
				if (objectMethod.getReturnType().equals(method.getReturnType())) {
					return false;
				}
			} catch (SecurityException e) {
			} catch (NoSuchMethodException e) {
			}
		}
		return true;
	}

	private static boolean methodIsAmbiguous(Method method,	Object[] implementingObjects) {
		boolean methodFound = false;
		for (Object o : implementingObjects) {
			Class<? extends Object> objectClass = o.getClass();
			try {
				objectClass.getMethod(method.getName(), method.getParameterTypes());
				if (methodFound) {
					return true;
				} else {
					methodFound = true;
				}
			} catch (SecurityException e) {
			} catch (NoSuchMethodException e) {
			}
		}
		return false;
	}
	
	private static class FrankenDuckProxy implements InvocationHandler {

		private final Object[] targetObjects;
		
		private FrankenDuckProxy(Object ... targetObjects) {
			this.targetObjects = targetObjects;
		}
		
		@Override
		public Object invoke(Object proxy, Method method, Object[] args)
				throws Throwable {
			for (Object possibleTarget : targetObjects) {
				try {
					Method targetMethod = possibleTarget.getClass().getMethod(method.getName(), method.getParameterTypes());
					return targetMethod.invoke(possibleTarget, args);
				} catch (NoSuchMethodException exc) {
					continue;
				}
			}
			throw new MissingMethodException(method);
		}

		@SuppressWarnings("unchecked")
		public static <D> D createProxy(Class<D> ducktypeClass, Object ... targetObjects) {
			Class<?>[] interfaces = new Class<?>[] { ducktypeClass };
			ClassLoader classLoader = ducktypeClass.getClassLoader();
			FrankenDuckProxy handler = new FrankenDuckProxy(targetObjects);
			return (D) Proxy.newProxyInstance(classLoader, interfaces, handler); 
		}
		
	}
	
}
