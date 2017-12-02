package niuke.jingdo.javabase;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

//在业务开发中，为业务接口类中的方法处理前后添加相同的前置处理动作以及后置处理动作是很常见的场景。典型场景如：处理方法前添加日志记录，进行鉴权，记录方法处理时间等。试写出解决上述场景的具体代码，具体要求如下（20分）：
//        1) 可以对任意业务接口的方法进行增强，添加执行方法的前后置逻辑
//        2) 不修改原有业务接口以及其具体实现类
//        3) 前后置逻辑可以按需动态地进行选择，如：可以只添加日志记录和进行鉴权，也可以只记录方法处理时间等
//        4) 根据你的设计，写出上述三个典型场景中至少一个的具体实现
public class DynamicProxy {

    public static void main(String[] args) {
        List<Interceptor> interceptors = new LinkedList<>();
        interceptors.add(new LoggerInterceptor());
        interceptors.add(new RecordInterceptor());

        MyHandler factory = new MyHandler();
        factory.myInit(interceptors);

        MyBusiness myBusiness = (MyBusiness) factory.createProxyInstance(new MyBusinessImpl());
        myBusiness.xxx1("123");
    }
}

interface Interceptor {
    public Object intercept();
}

class LoggerInterceptor implements Interceptor {
    @Override
    public Object intercept() {
        return null;
    }
}

class RecordInterceptor implements Interceptor {
    @Override
    public Object intercept() {
        return null;
    }
}

interface MyBusiness {
    void xxx1(String msg);

    void xxx2(int value);
}

class MyBusinessImpl implements MyBusiness {
    List<Interceptor> interceptors;

    public void xxx1(String msg) {
        System.out.println(msg);
    }

    public void xxx2(int value) {
        System.out.println(value);
    }
}

class MyHandler implements InvocationHandler {
    List<Interceptor> processors;
    Object clientObject;

    public void myInit(List<Interceptor> processors) {
        this.processors = processors;
    }

    public Object createProxyInstance(Object clientObject) {
        this.clientObject = clientObject;
        return Proxy.newProxyInstance(this.clientObject.getClass().getClassLoader(), this.clientObject.getClass().getInterfaces(), this);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Iterator<Interceptor> interceptors = processors.iterator();
        while (interceptors.hasNext()) {
            Interceptor interceptor = interceptors.next();
            interceptor.intercept();
        }
        MyBusiness myBusiness = (MyBusiness) this.clientObject;
        return method.invoke(myBusiness, args);
    }
}