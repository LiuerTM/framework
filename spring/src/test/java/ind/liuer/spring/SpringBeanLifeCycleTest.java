package ind.liuer.spring;

import ind.liuer.spring.bean.config.SpringBeanLifeCycleConfig;
import ind.liuer.spring.bean.lifecycle.SpringBeanLifeCycle;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * Spring Bean 生命周期测试
 *
 * @author Mingの
 * @since 1.0
 */
public class SpringBeanLifeCycleTest {

    public static final Logger log = LoggerFactory.getLogger(SpringBeanLifeCycleTest.class);

    @Test
    public void testSpringBeanLifeCycle() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(SpringBeanLifeCycleConfig.class);
        SpringBeanLifeCycle springBeanLifeCycle = context.getBean(SpringBeanLifeCycle.class);
        log.info("SpringBeanLifeCycle = {}", springBeanLifeCycle);
        context.close();
    }
}
