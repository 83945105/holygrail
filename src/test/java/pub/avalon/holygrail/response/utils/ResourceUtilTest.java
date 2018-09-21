package pub.avalon.holygrail.response.utils;

import org.junit.jupiter.api.Test;
import pub.avalon.holygrail.response.config.ResultConf;

/**
 * Created by 白超 on 2018/9/20.
 */
public class ResourceUtilTest {

    @Test
    void test() {
        ResourceUtil.getKeyValues(ResultConf.MESSAGE, null).forEach((s, o) -> {
            System.out.println(s);
        });
    }

}
