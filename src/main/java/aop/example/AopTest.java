package aop.example;

import aop.JdkAOPInvocationHandler;
import aop.MethodInvocation;
import aop.advice.After;
import aop.advice.AfterReturn;
import aop.advice.AfterThrow;
import aop.advice.Around;
import aop.advice.Before;

import java.lang.reflect.Proxy;

public class AopTest {
    public static void main(String[] args) {
        StudentService ss = (StudentService) Proxy.newProxyInstance(
                AopTest.class.getClassLoader(),
                new Class[]{StudentService.class},
                new JdkAOPInvocationHandler(new StudentServiceImpl(), new StudentAspect())
        );
        ss.get();
    }
}

/**
 * List = [after1 func, before1 func, before2 func, after2 func]
 *
 *      before1, before2, original instance, after1 / 2, after1 /2
 */
class StudentAspect {

    @After
    public void after1() {
        System.out.println("after one");
    }

    @Before
    public void before1() {
        System.out.println("before one");
    }

    @Before
    public void before2() {
        System.out.println("before two");
    }

    @After
    public void after2() {
        System.out.println("after two");
    }

    @AfterThrow
    public void afterThrow1() {
        System.out.println("get an exception from original method");
    }

    @AfterReturn
    public void afterReturn1() {
        System.out.println("get a result from original method");
    }

    @Around
    public Object around1(MethodInvocation mi) throws Throwable{
        System.out.println("before around one");
        Object res = mi.proceed();
        System.out.println("after around one");
        return res;
    }


}


/**
 *  one @before function1 - m methods / locations
 *  one @before function2 - m methods / locations
 */


/**
 *   List = [after1 func, before1 func, before2 func, after2 func]
 *   ProxyMethodInvocation mi: [after1Interceptor, before1Interceptor, before2Interceptor, after2Interceptor]
 *    index = 0,after1Interceptor.invoke(ProxyMethodInvocation mi) {
 *        res = mi.proceed() {
 *            index = 1, before1Interceptor.invoke(ProxyMethodInvocation mi) {
 *                execute before1 function : print before1
 *                res = mi.proceed() {
 *                    index = 2, before2Interceptor.invoke(ProxyMethodInvocation mi) {
 *                          execute before2 function : print before2
 *                          res = mi.proceed() {
 *                              index = 3, after2Interceptor.invoke(ProxyMethodInvocation mi) {
 *                                  res = mi.proceed() {
 *                                      index = 4, execute method from student service
 *                                      return the result
 *                                  }
 *                                  execute after2 logic : print after2
 *                                  return res
 *                              }
 *                          }
 *                          return res
 *                    }
 *                }
 *                return res
 *            }
 *        }
 *        execute after1 function / method / logic : print after 1
 *        return res
 *    }
 */

/**
 *  homework:
 *      provide two annotations in this aop library
 *      1. @AfterThrow : trigger aspect logic only if original method / function throws exception
 *      2. @AfterReturn : trigger aspect logic after the original method returns data
 *
 *  2pm cdt:office hour
 */