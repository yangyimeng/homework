package com.yangyimeng.homework.exam;


import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class AopTest {


    public static void main(String [] args) {
        AopTest aopTest = new AopTest();
        aopTest.invocationHandlerTest();
        aopTest.cglibTest();
    }


    public interface Service {

        public void add();

        public void  update();

    }


    public class AService implements Service {

        public AService() {

        }

        @Override
        public void add() {
            System.out.println("Aservice add ");
        }

        @Override
        public void update() {
            System.out.println("Aservice update");
        }
    }

    public class BService {

        public BService() {

        }

        public void add() {
            System.out.println("BService add ");
        }

        public void update() {
            System.out.println("BService update");
        }
    }

    public class MyInvocationHandler implements InvocationHandler {

        private Object target;

        MyInvocationHandler() {
            super();
        }

        MyInvocationHandler(Object target) {
            super();
            this.target = target;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            System.out.println("before----");
            Object result = method.invoke(target, args);
            System.out.println("after-----");
            return null;
        }
    }

    public class CglibProxy implements MethodInterceptor {

        @Override
        public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
            System.out.println("before----");
            Object result = methodProxy.invokeSuper(o, objects);
            System.out.println("after-----");
            return result;
        }
    }

    public void invocationHandlerTest() {
        System.out.println("invocation handler test");
        Service service = new AService();
        MyInvocationHandler handler = new MyInvocationHandler(service);
        Service proxy = (Service) Proxy.newProxyInstance(service.getClass().getClassLoader(),
                service.getClass().getInterfaces(), handler);
        proxy.add();
        System.out.println();
        proxy.update();
    }



    public void cglibTest() {
        System.out.println("cglib test");
        CglibProxy proxy = new CglibProxy();
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(BService.class);
        enhancer.setCallback(proxy);
        BService service = (BService) enhancer.create();
        service.add();
        System.out.println();
        service.update();
    }

}
