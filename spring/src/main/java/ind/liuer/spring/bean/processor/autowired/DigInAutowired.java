package ind.liuer.spring.bean.processor.autowired;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.PropertyValues;
import org.springframework.beans.factory.annotation.AutowiredAnnotationBeanPostProcessor;
import org.springframework.beans.factory.annotation.InjectionMetadata;
import org.springframework.beans.factory.config.DependencyDescriptor;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.annotation.ContextAnnotationAutowireCandidateResolver;
import org.springframework.core.MethodParameter;
import org.springframework.core.env.StandardEnvironment;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * @author Mingの
 * @date 2022-07-21 11:57
 * @since 1.0
 */
public class DigInAutowired {

    public static final Logger log = LoggerFactory.getLogger(DigInAutowired.class);

    public static void main(String[] args) throws Throwable {
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        beanFactory.registerSingleton(XxxBean.class.getName(), new XxxBean());
        beanFactory.registerSingleton(YyyBean.class.getName(), new YyyBean());
        beanFactory.registerSingleton(ZzzBean.class.getName(), new ZzzBean());

        beanFactory.setAutowireCandidateResolver(new ContextAnnotationAutowireCandidateResolver());
        beanFactory.addEmbeddedValueResolver(new StandardEnvironment()::resolvePlaceholders);

        AutowiredAnnotationBeanPostProcessor postProcessor = new AutowiredAnnotationBeanPostProcessor();
        postProcessor.setBeanFactory(beanFactory);

        PropertyValues pvs = new MutablePropertyValues();

        // AutowiredAnnotationBeanPostProcessor#postProcessProperties()进行依赖注入
        TestBean testBean = new TestBean();
        log.info("Before PostProcessor =====> {}", testBean);
        postProcessor.postProcessProperties(pvs, testBean, testBean.getClass().getName());
        log.info("After  PostProcessor =====> {}", testBean);

        // InjectionMetadata#inject()进行依赖注入
        TestBean testBean1 = new TestBean();
        log.info("Before InjectionMetadata =====> {}", testBean1);
        String methodNameMetadata = "findAutowiringMetadata";
        Method method = AutowiredAnnotationBeanPostProcessor.class.getDeclaredMethod(methodNameMetadata, String.class, Class.class, PropertyValues.class);
        method.setAccessible(true);
        InjectionMetadata injectionMetadata = (InjectionMetadata) method.invoke(postProcessor, testBean.getClass().getName(), TestBean.class, pvs);
        injectionMetadata.inject(testBean1, testBean.getClass().getName(), pvs);
        log.info("After  InjectionMetadata =====> {}", testBean1);

        // 依赖查找
        String fieldName = "xxxBean";
        Field xxxBeanField = TestBean.class.getDeclaredField(fieldName);
        DependencyDescriptor xxxBeanDd = new DependencyDescriptor(xxxBeanField, true);
        Object xxxBeanObj = beanFactory.doResolveDependency(xxxBeanDd, null, null, null);
        log.info("=====> {}", xxxBeanObj);

        String methodName = "setYyyBean";
        Method setYyyBeanMethod = TestBean.class.getDeclaredMethod(methodName, YyyBean.class);
        DependencyDescriptor setYyyBeanDd = new DependencyDescriptor(new MethodParameter(setYyyBeanMethod, 0), true);
        Object yyyBeanObj = beanFactory.doResolveDependency(setYyyBeanDd, null, null, null);
        log.info("=====> {}", yyyBeanObj);

        String valueMethodName = "setJavaHome";
        Method setJavaHomeMethod = TestBean.class.getDeclaredMethod(valueMethodName, String.class);
        DependencyDescriptor setJavaHomeDd = new DependencyDescriptor(new MethodParameter(setJavaHomeMethod, 0), true);
        Object javaHomeString = beanFactory.doResolveDependency(setJavaHomeDd, null, null, null);
        log.info("=====> {}", javaHomeString);
    }
}
