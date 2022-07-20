package ind.liuer.spring.bean.factory.processor.autowired;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

/**
 * @author Mingの
 * @since 1.0
 */
public class Bean0 {

    @Autowired
    private Bean1 bean1;

    private Bean2 bean2;

    @Autowired
    public void setBean2(Bean2 bean2) {
        this.bean2 = bean2;
    }

    private String javaHome;

    @Autowired
    public void setJavaHome(@Value("${JAVA_HOME}") String javaHome) {
        this.javaHome = javaHome;
    }

    @Override
    public String toString() {
        return "Bean0{" +
                "bean1=" + bean1 +
                ", bean2=" + bean2 +
                ", javaHome='" + javaHome + '\'' +
                '}';
    }
}
