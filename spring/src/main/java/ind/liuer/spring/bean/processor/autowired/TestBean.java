package ind.liuer.spring.bean.processor.autowired;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import javax.annotation.Resource;

/**
 * @author Mingの
 * @since 1.0
 */
public class TestBean {

    @Autowired
    private XxxBean xxxBean;

    private YyyBean yyyBean;

    @Autowired
    public void setYyyBean(YyyBean yyyBean) {
        this.yyyBean = yyyBean;
    }

    @Resource
    private ZzzBean zzzBean;

    private String javaHome;

    @Autowired
    public void setJavaHome(@Value("${JAVA_HOME}") String javaHome) {
        this.javaHome = javaHome;
    }

    @Override
    public String toString() {
        return "TestBean{" +
            "xxxBean=" + xxxBean +
            ", yyyBean=" + yyyBean +
            ", zzzBean=" + zzzBean +
            ", javaHome='" + javaHome + '\'' +
            '}';
    }
}
