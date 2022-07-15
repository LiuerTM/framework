package ind.liuer.spring.bean.lifecycle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * Spring Bean 生命周期
 *
 * @author Mingの
 * @since 1.0
 */
public class SpringBeanLifeCycle implements InitializingBean, DisposableBean {

    public static final Logger log = LoggerFactory.getLogger(SpringBeanLifeCycle.class);

    /**
     * 构造器初始化
     */
    public SpringBeanLifeCycle() {
        log.info("{} is initializing through the constructor", this.getClass().getName());
        xxxAware();
    }

    /**
     * 实现XxxAware接口 => 回调setXxx()方法
     */
    public void xxxAware() {

        log.info("{} is calling setBeanName() from the BeanNameAware interface", this.getClass().getName());
        log.info("{} is calling setBeanClassLoader() from the BeanClassLoaderAware interface", this.getClass().getName());
        log.info("{} is calling setBeanFactory() from the BeanFactoryAware interface", this.getClass().getName());

        /*
         * 通过BeanPostProcessor后置处理器实现类
         * ApplicationContextAwareProcessor
         *      #postProcessBeforeInitialization()
         *      #invokeAwareInterfaces()
         * 实现回调
         * - setEnvironment()
         * - setEmbeddedValueResolver()
         * - setResourceLoader()
         * - setApplicationEventPublisher()
         * - setMessageSource()
         * - setApplicationStartup()
         * - setApplicationContext()
         */
        log.info("{} is calling setEnvironment() from the EnvironmentAware interface", this.getClass().getName());
        log.info("{} is calling setEmbeddedValueResolver() from the EmbeddedValueResolverAware interface", this.getClass().getName());

        log.info("only applicable when running in an application context: ");
        log.info("{} is calling setResourceLoader() from the ResourceLoaderAware interface", this.getClass().getName());
        log.info("{} is calling setApplicationEventPublisher() from the ApplicationEventPublisherAware interface", this.getClass().getName());
        log.info("{} is calling setMessageSource() from the MessageSourceAware interface", this.getClass().getName());
        log.info("{} is calling setApplicationStartup() from the ApplicationStartupAware interface", this.getClass().getName());
        log.info("{} is calling setApplicationContext() from the ApplicationContextAware interface", this.getClass().getName());

        log.info("only applicable when running in a web application context: ");
        log.info("{} is calling setServletContext() from the ServletContextAware interface", this.getClass().getName());
    }

    @PostConstruct
    public void postConstruct() {
        log.info("{} is calling postConstruct() from the postProcessBeforeInitialization method " +
            "of BeanPostProcessor", this.getClass().getName());
    }

    /**
     * InitializingBean's afterPropertiesSet()
     */
    @Override
    public void afterPropertiesSet() {
        log.info("{} is calling afterPropertiesSet() from the InitializingBean interface", this.getClass().getName());
    }

    /**
     * 自定义初始化方法
     */
    public void initMethod() {
        log.info("{} is calling initMethod() from the custom init-method", this.getClass().getName());
    }

    @PreDestroy
    public void preDestroy() {
        log.info("{} is calling preDestroy() from the postProcessBeforeInitialization method " +
            "of BeanPostProcessor", this.getClass().getName());
    }

    /**
     * DisposableBean's destroy()
     */
    @Override
    public void destroy() {
        log.info("{} is calling destroy() from the DisposableBean interface", this.getClass().getName());
    }

    /**
     * 自定义销毁方法
     */
    public void destroyMethod() {
        log.info("{} is calling destroyMethod() from the custom destroy-method", this.getClass().getName());
    }
}
