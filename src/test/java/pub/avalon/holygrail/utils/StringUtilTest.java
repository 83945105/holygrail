package pub.avalon.holygrail.utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

/**
 * Created by 白超 on 2018/9/21.
 */
public class StringUtilTest {

    @Test
    void isEmptyTest() {
        Assertions.assertTrue(StringUtil.isEmpty(null));
        Assertions.assertTrue(StringUtil.isEmpty(""));
        Assertions.assertTrue(StringUtil.isEmpty(" "));
        Assertions.assertTrue(StringUtil.isEmpty(new ArrayList<>()));
        Assertions.assertTrue(StringUtil.isEmpty(new HashMap<>()));
        Assertions.assertTrue(StringUtil.isEmpty(new Object[]{}));

        Assertions.assertFalse(StringUtil.isEmpty(0));
        Assertions.assertFalse(StringUtil.isEmpty("0"));
        Assertions.assertFalse(StringUtil.isEmpty(true));
        Assertions.assertFalse(StringUtil.isEmpty(false));
        Assertions.assertFalse(StringUtil.isEmpty(Collections.singletonList(0)));
        Assertions.assertFalse(StringUtil.isEmpty(Collections.singletonMap(0, 0)));
        Assertions.assertFalse(StringUtil.isEmpty(new Integer[]{0}));
    }

}
