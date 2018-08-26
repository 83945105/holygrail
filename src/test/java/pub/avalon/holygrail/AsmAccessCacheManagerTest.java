package pub.avalon.holygrail;

import pub.avalon.holygrail.cache.AsmAccessCacheManager;
import com.esotericsoftware.reflectasm.FieldAccess;
import com.esotericsoftware.reflectasm.MethodAccess;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Created by 白超 on 2018/8/13.
 */
public class AsmAccessCacheManagerTest {

    @Test
    void getFieldAccessTest() {
        FieldAccess fieldAccess = AsmAccessCacheManager.getFieldAccess(AsmAccessCacheManager.class);
        Thread t1 = new Thread(() -> {
            Assertions.assertEquals(fieldAccess, AsmAccessCacheManager.getFieldAccess(AsmAccessCacheManager.class));
        });
        t1.start();
    }

    @Test
    void getMethodAccessTest() {
        MethodAccess methodAccess = AsmAccessCacheManager.getMethodAccess(AsmAccessCacheManager.class);
        Thread t1 = new Thread(() -> {
            Assertions.assertEquals(methodAccess, AsmAccessCacheManager.getMethodAccess(AsmAccessCacheManager.class));
        });
        t1.start();
    }

}
