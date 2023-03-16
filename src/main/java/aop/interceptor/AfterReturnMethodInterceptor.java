package aop.interceptor;

import aop.MethodInvocation;

import java.lang.reflect.Method;

public class AfterReturnMethodInterceptor implements MethodInterceptor {
    private Object aspectObj;
    private Method aspectMethod;

    public AfterReturnMethodInterceptor(Object aspectObj, Method aspectMethod) {
        this.aspectObj = aspectObj;
        this.aspectMethod = aspectMethod;
    }

    @Override
    public Object invoke(MethodInvocation mi) throws Throwable {
        Object result = mi.proceed();
        if (result != null) {
            aspectMethod.setAccessible(true);
            aspectMethod.invoke(aspectObj);
        }
        return result;
    }
}
