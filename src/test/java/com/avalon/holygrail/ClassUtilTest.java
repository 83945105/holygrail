package com.avalon.holygrail;

import com.avalon.holygrail.bean.EUser;
import com.avalon.holygrail.bean.User;
import com.avalon.holygrail.utils.ClassUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Created by 白超 on 2018/8/13.
 */
public class ClassUtilTest {

    @Test
    void setProperty() {

        for (int i = 0; i < 1; i++) {
            Long startTime = System.nanoTime();
            EUser user = new EUser();
            User u = new User();
            u.setName("666");

            ClassUtil.setProperty(user, "uuid", "1");
            ClassUtil.setProperty(user, "c1", "1");
            ClassUtil.setProperty(user, "user", u);

            Assertions.assertEquals(1L, (long) user.getUuid());
            Assertions.assertEquals(true, user.isC1());
            Assertions.assertEquals(u, user.getUser());

            Long time = System.nanoTime() - startTime;
            System.out.println("耗时:" + time + "纳秒 " + time / 1000000 + "毫秒");
        }

    }
}
