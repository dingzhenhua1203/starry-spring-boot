package com.starry.starryapi.aspects;

import com.starry.starryapi.annotations.LogRecord;
import com.starry.starrycore.JsonUtil;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 开启@EnableAspectJAutoProxy
 * <p>
 * 无异常：顺序
 *
 * @Around（proceed()之前的部分） → @Before → 方法执行 → @Around（proceed()之后的部分） → @After → @AfterReturning
 * <p>
 * 有异常：顺序
 * @Around（proceed(之前的部分)） → @Before → 扔异常ing → @After → @AfterThrowing
 * 有异常的话，@Around的proceed()之后的部分和@AfterReturning不能执行到
 */
@Aspect
@Order(2)  // 越小优先级越高
@Component
public class LogWebAspect {

    // LoggerFactory.getLogger(this.getClass());
    private final Logger logger = LoggerFactory.getLogger(LogWebAspect.class);
    // 用来收集方法执行耗时
    ThreadLocal<Long> startTime = new ThreadLocal<>();

    /*
    PointCut表达式和PointCut签名。
    表达式是拿来确定切入点的位置的，说白了就是通过一些规则来确定，哪些方法是要增强的，也就是要拦截哪些方法
    execution(方法修饰符(可选)  返回类型  类路径 方法名  参数  异常模式(可选))
    @within: 匹配使用指定注解的类
    @annotation:指定方法所应用的注解
     */
    @Pointcut("execution(public * com.starry.starryapi.controller..*.*(..))")
    public void controllerLog() {
    }

    //切入点描述，这个是uiController包的切入点
    @Pointcut("execution(public * com.starry.starryapi.service..*.*(..))")
    public void serviceLog() {
    }

    /**
     * 设置操作日志切入点 记录操作日志 在注解@LogRecord的位置切入代码
     */
    @Pointcut("@annotation(com.starry.starryapi.annotations.LogRecord)")
    public void logRecordPoint() {
    }

    // Pointcut定义时，还可以使用&&、||、! 这三个运算,表示组合使用
    @Pointcut("controllerLog()")
    private void logUnionPoint() {
    }

    // 在切入点方法执行之前执行我们定义的advice
    // 除了@Around外，都加这个JoinPoint作参数。@Around注解的参数一定要是ProceedingJoinPoint
    @Before("logUnionPoint()") //在切入点的方法run之前要干的
    public void logBeforeController(JoinPoint joinPoint) {
        startTime.set(System.currentTimeMillis());

        System.out.println(this.getClass());
        System.out.println(LogWebAspect.class);
        //这个RequestContextHolder是Springmvc提供来获得请求的东西
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();

        // 记录下请求内容
        logger.info("URL : " + request.getRequestURL().toString());
        logger.info("URI : " + request.getRequestURI());
        logger.info("HTTP_METHOD : " + request.getMethod());
        logger.info("IP : " + request.getRemoteAddr());
        // 下面这个getSignature().getDeclaringTypeName()是获取包+类名的   然后后面的joinPoint.getSignature.getName()获取了方法名
        logger.info("调用方法 : " + joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());
        logger.info("http参数：" + request.getQueryString());
        logger.info("参数： " + Arrays.toString(joinPoint.getArgs()));
        // 返回的是经过加强后的代理类的对象
        logger.info("THIS: " + joinPoint.getThis());

        // 请求的参数
        Map<String, String> rtnMap = converMap(request.getParameterMap());
        // 将参数所在的数组转换成json
        String params = JsonUtil.parseString(rtnMap);

    }

    // 在切入的方法运行完之后把我们的advice增强加进去
    @After("logUnionPoint()")
    public void afterLog(JoinPoint joinPoint) {
        System.out.println("after......aop");
    }

    @Around("logUnionPoint()")
    public void testAround(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        System.out.println("around-before......aop");
        // 执行实际方法
        proceedingJoinPoint.proceed();
        System.out.println("around-after......aop");
    }

    // 目标方法正常完成后把增强处理织入
    @AfterReturning(returning = "returnOb", pointcut = "logUnionPoint()")
    public void doAfterReturning(JoinPoint joinPoint, Object returnOb) {
        // 从切面织入点处通过反射机制获取织入点处的方法
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        // 获取切入点所在的方法
        Method method = signature.getMethod();
        // 获取操作
        LogRecord opLog = method.getAnnotation(LogRecord.class);
        if (opLog != null) {
            String operModul = opLog.methodName();
            String operType = opLog.logType();
        }
        // 获取请求的类名
        String className = joinPoint.getTarget().getClass().getName();
        // 获取请求的方法名
        String methodName = method.getName();
        methodName = className + "." + methodName;
        System.out.println("返回结果： " + JsonUtil.parseString(returnOb));
    }

    // 异常抛出增强，在异常抛出后织入的增强
    @AfterThrowing(pointcut = "logUnionPoint()", throwing = "ex")
    public void doAfterThrowing(JoinPoint joinPoint, Exception ex) {
        String methodName = joinPoint.getSignature().getName();
        List<Object> args = Arrays.asList(joinPoint.getArgs());
        System.out.println(ex.getClass().getName()); // 异常名称
        System.out.println(stackTraceToString(ex.getClass().getName(), ex.getMessage(), ex.getStackTrace())); // 异常信息
        System.out.println("连接点方法为：" + methodName + ",参数为：" + args + ",异常为：" + ex);

    }

    /**
     * 转换request 请求参数
     *
     * @param paramMap request获取的参数数组
     */
    private Map<String, String> converMap(Map<String, String[]> paramMap) {
        Map<String, String> rtnMap = new HashMap<String, String>();
        for (String key : paramMap.keySet()) {
            rtnMap.put(key, paramMap.get(key)[0]);
        }
        return rtnMap;
    }

    /**
     * 转换异常信息为字符串
     *
     * @param exceptionName    异常名称
     * @param exceptionMessage 异常信息
     * @param elements         堆栈信息
     */
    private String stackTraceToString(String exceptionName, String exceptionMessage, StackTraceElement[] elements) {
        StringBuffer strbuff = new StringBuffer();
        for (StackTraceElement stet : elements) {
            strbuff.append(stet + "\n");
        }
        String message = exceptionName + ":" + exceptionMessage + "\n\t" + strbuff.toString();
        return message;
    }
}
