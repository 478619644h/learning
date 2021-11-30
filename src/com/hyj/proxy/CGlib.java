package com.hyj.proxy;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

public class CGlib {

    public static void main(String[] args) {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(CGlib.class);
        enhancer.setCallback(new MethodInterceptor() {
            @Override
            public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
                Object result = methodProxy.invokeSuper(o,objects);
                return result;
            }
        });

        CGlib cGlib =(CGlib) enhancer.create();
        cGlib.test();
    }

    public void test(){
        System.out.println("cglib testing");
    }

}
