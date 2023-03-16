package aop;


import aop.advice.After;
import aop.advice.AfterReturn;
import aop.advice.AfterThrow;
import aop.advice.Around;
import aop.advice.Before;
import aop.interceptor.AfterMethodInterceptor;
import aop.interceptor.AfterReturnMethodInterceptor;
import aop.interceptor.AfterThrowMethodInterceptor;
import aop.interceptor.AroundMethodInterceptor;
import aop.interceptor.BeforeMethodInterceptor;
import aop.interceptor.MethodInterceptor;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class JdkAOPInvocationHandler implements InvocationHandler {

    private Object originObj;
    private Object aspectObj;

    public JdkAOPInvocationHandler(Object originObj, Object aspectObj) {
        this.originObj = originObj;
        this.aspectObj = aspectObj;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Class<?> aspectClass = aspectObj.getClass();
        List<MethodInterceptor> interceptors = new ArrayList<>();
        for(Method aspectMethod: aspectClass.getDeclaredMethods()) {
            for(Annotation ano: aspectMethod.getDeclaredAnnotations()) {
                MethodInterceptor methodInterceptor = null;
                if(ano.annotationType() == Before.class) {
                    methodInterceptor = new BeforeMethodInterceptor(aspectObj, aspectMethod);
                } else if(ano.annotationType() == After.class) {
                    methodInterceptor = new AfterMethodInterceptor(aspectObj, aspectMethod);
                } else if(ano.annotationType() == Around.class) {
                    methodInterceptor = new AroundMethodInterceptor(aspectObj, aspectMethod);
                } else if (ano.annotationType() == AfterThrow.class) {
                    methodInterceptor = new AfterThrowMethodInterceptor(aspectObj, aspectMethod);
                } else if (ano.annotationType() == AfterReturn.class) {
                    methodInterceptor = new AfterReturnMethodInterceptor(aspectObj, aspectMethod);
                }
                interceptors.add(methodInterceptor);
            }
        }
        MethodInvocation mi = new ProxyMethodInvocation(interceptors, originObj, method, args);
        return mi.proceed();
    }
}