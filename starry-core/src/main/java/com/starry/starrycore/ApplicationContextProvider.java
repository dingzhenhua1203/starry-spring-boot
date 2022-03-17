package com.starry.starrycore;



import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * 获取spring中bean对象的工具类:
 * 用法1.解决多线程中bean的使用问题
 * 在多线程处理问题时，无法通过@Autowired注入bean，报空指针异常，
 * 在线程中为了线程安全，是防注入的，如果要用到这个类，只能从bean工厂里拿个实例
 * 用法2.spring默认是单例的， 为了获取多例对象，请求接口每一层都要改为多例，这样就不太方面
 * 那么可以选择不使用@Autowried注入 改用ApplicationContextProvider.getBean
 * 使用案例：
 * eg:Order order = ApplicationContextProvider.getBean(Order.class);
 */
@Component
public class ApplicationContextProvider implements ApplicationContextAware {
    /**
     * 上下文对象实例
     */
    private static ApplicationContext applicationContext;
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
    /**
     * 获取applicationContext
     *
     * @return
     */
    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }
    /**
     * 通过name获取 Bean.
     *
     * @param name
     * @return
     */
    public static Object getBean(String name) {
        return getApplicationContext().getBean(name);
    }
    /**
     * 通过class获取Bean.
     *
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T getBean(Class<T> clazz) {
        return getApplicationContext().getBean(clazz);
    }
    /**
     * 通过name,以及Clazz返回指定的Bean
     *
     * @param name
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T getBean(String name, Class<T> clazz) {
        return getApplicationContext().getBean(name, clazz);
    }
}
