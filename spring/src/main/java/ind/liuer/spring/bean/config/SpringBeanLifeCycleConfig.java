package ind.liuer.spring.bean.config;

import ind.liuer.spring.bean.lifecycle.SpringBeanLifeCycle;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Spring Bean 生命周期配置
 *
 * @author Mingの
 * @since 1.0
 */
@Configuration
public class SpringBeanLifeCycleConfig {

    @Bean(initMethod = "initMethod", destroyMethod = "destroyMethod")
    public SpringBeanLifeCycle springBeanLifeCycle() {
        return new SpringBeanLifeCycle();
    }
}
