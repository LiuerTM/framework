package ind.liuer.spring.bean.factory.processor.autowired;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.PropertyValues;
import org.springframework.beans.factory.annotation.AutowiredAnnotationBeanPostProcessor;
import org.springframework.beans.factory.annotation.InjectionMetadata;
import org.springframework.beans.factory.config.DependencyDescriptor;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.context.annotation.ContextAnnotationAutowireCandidateResolver;
import org.springframework.core.MethodParameter;
import org.springframework.core.env.StandardEnvironment;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * @author Mingの
 * @since 1.0
 */
public class DigInAutowired {

    private static final Logger log = LoggerFactory.getLogger(DigInAutowired.class);

    public static void main(String[] args) throws Throwable {
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        RootBeanDefinition bean1BeanDefinition = new RootBeanDefinition(Bean1.class);
        beanFactory.registerBeanDefinition(Bean1.class.getName(), bean1BeanDefinition);
        RootBeanDefinition bean2BeanDefinition = new RootBeanDefinition(Bean2.class);
        beanFactory.registerBeanDefinition(Bean2.class.getName(), bean2BeanDefinition);

        // @Value
        beanFactory.setAutowireCandidateResolver(new ContextAnnotationAutowireCandidateResolver());
        // ${}
        beanFactory.addEmbeddedValueResolver(new StandardEnvironment()::resolvePlaceholders);

        AutowiredAnnotationBeanPostProcessor beanPostProcessor = new AutowiredAnnotationBeanPostProcessor();
        beanPostProcessor.setBeanFactory(beanFactory);

        Bean0 bean00 = new Bean0();
        Bean0 bean01 = new Bean0();
        MutablePropertyValues propertyValues = new MutablePropertyValues();

        log.info("==============================");
        postProcessor(beanPostProcessor, bean00, propertyValues);

        log.info("==============================");
        postProcessDetails(beanPostProcessor, bean01, propertyValues);

        log.info("==============================");
        lookupDependency(beanFactory);
    }

    /**
     * 执行后置处理器方法实现依赖注入
     *
     * @param beanPostProcessor AutowiredAnnotationBeanPostProcessor
     * @param bean0             Bean
     * @param propertyValues    null
     */
    private static void postProcessor(AutowiredAnnotationBeanPostProcessor beanPostProcessor, Bean0 bean0, PropertyValues propertyValues) {
        log.info("=====> before AutowiredAnnotationBeanPostProcessor: {}", bean0);

        beanPostProcessor.postProcessProperties(propertyValues, bean0, bean0.getClass().getName());
        log.info("=====> after  AutowiredAnnotationBeanPostProcessor: {}", bean0);
    }

    /**
     * 执行后置处理器方法实现依赖注入详细过程
     *
     * @param beanPostProcessor AutowiredAnnotationBeanPostProcessor
     * @param bean0             Bean
     * @param propertyValues    null
     */
    private static void postProcessDetails(AutowiredAnnotationBeanPostProcessor beanPostProcessor, Bean0 bean0, PropertyValues propertyValues) throws Throwable {
        log.info("=====> before AutowiredAnnotationBeanPostProcessor: {}", bean0);

        String methodName = "findAutowiringMetadata";
        Method findAutowiringMetaDataMethod = AutowiredAnnotationBeanPostProcessor.class.getDeclaredMethod(methodName, String.class, Class.class, PropertyValues.class);
        findAutowiringMetaDataMethod.setAccessible(true);
        InjectionMetadata injectionMetadata = (InjectionMetadata) findAutowiringMetaDataMethod.invoke(beanPostProcessor, bean0.getClass().getName(), Bean0.class, propertyValues);
        log.info("=====> {}", injectionMetadata);

        injectionMetadata.inject(bean0, bean0.getClass().getName(), propertyValues);
        log.info("=====> after  AutowiredAnnotationBeanPostProcessor: {}", bean0);
    }

    /**
     * 依赖查找@Autowired属性
     *
     * @param beanFactory DefaultListableBeanFactory
     */
    private static void lookupDependency(DefaultListableBeanFactory beanFactory) throws Exception {
        String fieldName = "bean1";
        Field bean1Field = Bean0.class.getDeclaredField(fieldName);
        DependencyDescriptor fieldDependencyDescriptor = new DependencyDescriptor(bean1Field, true);
        Object bean1 = beanFactory.doResolveDependency(fieldDependencyDescriptor, null, null, null);
        log.info("=====> {}", bean1);

        String methodName = "setBean2";
        Method setBean2Method = Bean0.class.getDeclaredMethod(methodName, Bean2.class);
        DependencyDescriptor methodDependencyDescriptor = new DependencyDescriptor(new MethodParameter(setBean2Method, 0), true);
        Object bean2 = beanFactory.doResolveDependency(methodDependencyDescriptor, null, null, null);
        log.info("=====> {}", bean2);

        String valueMethodName = "setJavaHome";
        Method setJavaHomeMethod = Bean0.class.getDeclaredMethod(valueMethodName, String.class);
        DependencyDescriptor valueMethodDependencyDescriptor = new DependencyDescriptor(new MethodParameter(setJavaHomeMethod, 0), true);
        Object javaHome = beanFactory.doResolveDependency(valueMethodDependencyDescriptor, null, null, null);
        log.info("=====> {}", javaHome);
    }
}
