package de.age.util.duck;


public class Ducktype {
	
	private Ducktype() {
	}
	
	public static boolean check(Class<?> ducktypeClass, Object ... implementingObjects) {
		checkObjectList(implementingObjects);
		try {
			MethodMappingInfo.determineMapping(ducktypeClass, implementingObjects);
		} catch (MissingMethodException | AmbiguousMethodException exc) {
			return false;
		}
		return true;
	}

	@SuppressWarnings("unchecked")
	public static <D> D adapt(Class<D> ducktypeClass, Object ... implementingObjects) {
		check(ducktypeClass, implementingObjects);
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
	
}
