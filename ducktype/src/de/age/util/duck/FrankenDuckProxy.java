package de.age.util.duck;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

class FrankenDuckProxy implements InvocationHandler {

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