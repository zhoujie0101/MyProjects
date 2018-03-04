package com.jay.java7demo;

import com.sun.btrace.annotations.BTrace;
import com.sun.btrace.annotations.Kind;
import com.sun.btrace.annotations.Location;
import com.sun.btrace.annotations.OnMethod;
import com.sun.btrace.annotations.ProbeClassName;
import com.sun.btrace.annotations.ProbeMethodName;
import com.sun.btrace.annotations.TLS;

import static com.sun.btrace.BTraceUtils.jstack;
import static com.sun.btrace.BTraceUtils.println;
import static com.sun.btrace.BTraceUtils.str;
import static com.sun.btrace.BTraceUtils.strcat;
import static com.sun.btrace.BTraceUtils.timeMillis;

/**
 * Created by jay on 2017/4/8.
 */
@BTrace
public class Test {

    @TLS
    private long startTime = 0;

    @OnMethod(clazz = "com.jay.java7demo.RuntimeConstantPoolOOM", method = "execute")
    public void start() {
        startTime = timeMillis();
    }

    @OnMethod(clazz = "com.jay.java7demo.RuntimeConstantPoolOOM", method = "execute",
        location = @Location(Kind.RETURN))
    public void end() {
        println(strcat("the class method execute time=>", str(timeMillis()-startTime)));
        println("-------------------------------------------");
    }

    @OnMethod(clazz = "com.jay.java7demo.RuntimeConstantPoolOOM", method = "execute",
            location = @Location(Kind.RETURN))
    public void fun(@ProbeClassName String className, @ProbeMethodName String methodName,
                    int sleepTime) {
        jstack();
        println(strcat("the class name=>", className));
        println(strcat("the class method=>", methodName));
        println(strcat("the class method params=>", str(sleepTime)));
    }

}
