package ind.liuer.spring.bean.processor.autowired;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.AutowiredAnnotationBeanPostProcessor;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.context.annotation.CommonAnnotationBeanPostProcessor;
import org.springframework.context.annotation.ContextAnnotationAutowireCandidateResolver;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.core.env.StandardEnvironment;

/**
 * @author Mingの
 * @since 1.0
 */
public class MingAutowired {

    public static final Logger log = LoggerFactory.getLogger(MingAutowired.class);

    public static void main(String[] args) {
        GenericApplicationContext context = new GenericApplicationContext();
        context.registerBean(XxxBean.class);
        context.registerBean(YyyBean.class);
        context.registerBean(ZzzBean.class);

        AbstractBeanDefinition testBeanBd = BeanDefinitionBuilder.genericBeanDefinition(TestBean.class).getBeanDefinition();
        context.registerBeanDefinition(TestBean.class.getName(), testBeanBd);

        // 解析@Value
        context.getDefaultListableBeanFactory().setAutowireCandidateResolver(new ContextAnnotationAutowireCandidateResolver());
        // 解析${}
        context.getDefaultListableBeanFactory().addEmbeddedValueResolver(new StandardEnvironment()::resolvePlaceholders);

        // 向容器中添加后置处理器Bean: AutowiredAnnotationBeanPostProcessor、CommonAnnotationBeanPostProcessor
        // 解析@Autowired @Value
        context.registerBean(AutowiredAnnotationBeanPostProcessor.class);
        // 解析@Resource、@PostConstruct、@PreDestroy
        context.registerBean(CommonAnnotationBeanPostProcessor.class);

        context.refresh();
        log.info("=====> {}", context.getBean(TestBean.class.getName()));

        context.close();
    }
}
