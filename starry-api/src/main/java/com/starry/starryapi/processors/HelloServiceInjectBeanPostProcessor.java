package com.starry.starryapi.processors;

import com.starry.starryapi.customannotation.RoutingInjected;
import com.starry.starryapi.processors.tests.HelloService;
import com.starry.starryapi.processors.tests.UnitService;
import com.starry.starryapi.proxys.RoutingBeanProxyFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 首先自定义了两个注解：RoutingInjected、RoutingSwitch，
 * 前者的作用类似于我们常用的Autowired，声明了该注解的属性将会被注入一个路由代理类实例；后者的作用则是一个配置开关，声明了控制路由的开关属性
 * 在RoutingBeanPostProcessor类中，我们在postProcessAfterInitialization方法中通过检查bean中是否存在声明了RoutingInjected注解的属性，
 * 如果发现存在该注解则给该属性注入一个动态代理类实例
 * RoutingBeanProxyFactory类功能就是生成一个代理类实例，代理类的逻辑也比较简单。
 * 版本路由支持到方法级别，即优先检查方法是否存在路由配置RoutingSwitch，方法不存在配置时才默认使用类路由配置
 * <p>
 * 作者：圆圆仙人球
 * 链接：https://www.jianshu.com/p/1417eefd2ab1
 * 来源：简书
 * 著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。
 */
@Component
public class HelloServiceInjectBeanPostProcessor implements BeanPostProcessor, Ordered {

    private Map map = new ConcurrentHashMap(100);

    @Autowired
    private ApplicationContext applicationContext;

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        System.out.println("processor before执行....." + beanName);
        return BeanPostProcessor.super.postProcessBeforeInitialization(bean, beanName);
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        System.out.println("processor After执行....." + beanName);
        String beanNameUpper = beanName.toUpperCase();

        if (beanNameUpper.contains("DB") || beanNameUpper.contains("PROXY") || beanNameUpper.contains("TRANSACTIONTEMPLATE")) {
            // log.info(beanName + "特定服务不进行再次代理!该类为:" + bean);
            return bean;
        }

        if (!beanNameUpper.contains("HELLO") && !beanNameUpper.contains("UNITSERVICE")) {
            return bean;
        }

        if (map.get(beanName) != null) {
            // log.info(beanName + "已经代理过,不进行再次代理!");
            return map.get(beanName);
        }

        Class<?> beanClazz = bean.getClass();
        /**
         * 主要对 postProcessAfterInitialization() 方法进行了实现，用于增强 bean 对象的能力，
         * 这里我们使用了一下小例子，就是将当前 bean 对象返回的结果全部改为大写，因此这里我们使用到了一个代理对象
         * 方法进行拦截。在 invoke() 方法中，对结果进行大写转换，
         */
        if (beanClazz == UnitService.class) {
            Object proxy = Proxy.newProxyInstance(bean.getClass().getClassLoader(),
                    bean.getClass().getInterfaces(),
                    new InvocationHandler() {
                        /**
                         * @param proxy 代理监控对象
                         * @param method doSome()方法
                         * @param args doSome()方法执行时接收的实参
                         * @return
                         * @throws Throwable
                         */
                        @Override
                        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                            System.out.println("UnitService 被拦截了···");
                            String result = (String) method.invoke(bean, args);
                            return result;
                        }
                    });
            map.put(beanName, proxy);
            // proxy 暂时处理有问题beans.factory.NoSuchBeanDefinitionException，以后懂了再来看吧
            return bean;

        }

        // getFields()：获得某个类的所有的公共（public）的字段，包括父类中的字段。
        // getDeclaredFields()：获得某个类的所有声明的字段，即包括public、private和proteced，但是不包括父类的申明字段。
        Field[] declaredFields = beanClazz.getFields();
        for (Field f : declaredFields) {
            if (f.isAnnotationPresent(RoutingInjected.class)) {
                if (!f.getType().isInterface()) {
                    throw new BeanCreationException("RoutingInjected must be declared as an interface:" + f.getName()
                            + " @Class " + beanClazz.getName());
                }
                try {
                    this.handleRoutingInjected(f, bean, f.getType());
                } catch (IllegalAccessException e) {
                    throw new BeanCreationException("Exception thrown when handleAutowiredRouting", e);
                }
            }
        }

        return BeanPostProcessor.super.postProcessAfterInitialization(bean, beanName);
    }

    private void handleRoutingInjected(Field field, Object bean, Class type) throws IllegalAccessException {
        Map<String, Object> candidates = this.applicationContext.getBeansOfType(type);
        field.setAccessible(true);
        if (candidates.size() == 1) {
            field.set(bean, candidates.values().iterator().next());
        } else if (candidates.size() == 2) {
            String val = field.getAnnotation(RoutingInjected.class).value();
            Object proxy = RoutingBeanProxyFactory.createProxy(val, type, candidates);
            field.set(bean, proxy);
        } else {
            throw new IllegalArgumentException("Find more than 2 beans for type: " + type);
        }
    }

    /**
     * 默认值为 0，优先级最高，值越大优先级越低
     *
     * @return
     */
    @Override
    public int getOrder() {
        return 1;
    }
}
