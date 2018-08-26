package pub.avalon.holygrail;

import pub.avalon.holygrail.bean.EUser;
import pub.avalon.holygrail.cache.GetterSetterNameCacheManager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Created by 白超 on 2018/8/13.
 */
public class GetterSetterNameCacheManagerTest {

    @Test
    void getGetterMethodNameTest() {
        String[] propertyNames = {"id", "name", "a1", "a2", "c1", "c2", "uuid"};
        String[] results = {"getId", "getName", "getA1", "getA2", "isC1", "isC2", "getUuid"};
        for (int i = 0; i < propertyNames.length; i++) {
            Assertions.assertEquals(results[i], GetterSetterNameCacheManager.getGetterMethodName(EUser.class, propertyNames[i]));
        }
    }

}
