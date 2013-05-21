package de.age.util.duck;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

class FrankenDuckProxy implements InvocationHandler {

	private final MethodMappingInfo mapping;
	
	private FrankenDuckProxy(MethodMappingInfo mapping) {
		this.mapping = mapping;
	}
	
	@Override
	public Object invoke(Object proxy, Method method, Object[] args)
			throws Throwable {
		CallTarget target = mapping.getTarget(method);
		Method targetMethod = target.getTargetMethod();
		return targetMethod.invoke(target.getTargetObject(), args);
	}

	@SuppressWarnings("unchecked")
	public static <D> D createProxy(Class<D> ducktypeClass, Object ... targetObjects) {
		Class<?>[] interfaces = new Class<?>[] { ducktypeClass };
		ClassLoader classLoader = ducktypeClass.getClassLoader();
		FrankenDuckProxy handler = new FrankenDuckProxy(MethodMappingInfo.determineMapping(ducktypeClass, targetObjects));
		return (D) Proxy.newProxyInstance(classLoader, interfaces, handler); 
	}
	
}