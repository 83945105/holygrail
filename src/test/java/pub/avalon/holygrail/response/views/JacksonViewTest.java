package pub.avalon.holygrail.response.views;

import com.fasterxml.jackson.core.type.TypeReference;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pub.avalon.holygrail.response.beans.User;
import pub.avalon.holygrail.response.utils.DataViewUtil;
import pub.avalon.holygrail.utils.JsonUtil;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by 白超 on 2019/5/3.
 */
public class JacksonViewTest {

//    @Test
    void TestRecord() {
        Object record = null;
        DataView dataView = DataViewUtil.getModelViewSuccess(0, "", record);
        String json = JsonUtil.toJsonString(dataView);
        dataView = JsonUtil.parseObject(json, DataView.class);
        Integer integerValue = DataViewUtil.getRecord(dataView, new TypeReference<Integer>() {
        });
        Assertions.assertNull(integerValue);
        String stringValue = DataViewUtil.getRecord(dataView, new TypeReference<String>() {
        });
        Assertions.assertNull(stringValue);
        Object objectValue = DataViewUtil.getRecord(dataView, new TypeReference<Object>() {
        });
        Assertions.assertNull(objectValue);


        record = 1;
        dataView = DataViewUtil.getModelViewSuccess(0, "", record);
        json = JsonUtil.toJsonString(dataView);
        dataView = JsonUtil.parseObject(json, DataView.class);
        integerValue = DataViewUtil.getRecord(dataView, new TypeReference<Integer>() {
        });
        Assertions.assertEquals(new Integer(1), integerValue);
        stringValue = DataViewUtil.getRecord(dataView, new TypeReference<String>() {
        });
        Assertions.assertEquals("1", stringValue);
        objectValue = DataViewUtil.getRecord(dataView, new TypeReference<Object>() {
        });
        Assertions.assertEquals(1, objectValue);


        record = new User();
        ((User) record).setId("1");
        ((User) record).setAge(2);
        dataView = DataViewUtil.getModelViewSuccess(0, "", record);
        json = JsonUtil.toJsonString(dataView);
        dataView = JsonUtil.parseObject(json, DataView.class);
        stringValue = DataViewUtil.getRecord(dataView, new TypeReference<String>() {
        });
        Assertions.assertEquals("{\"id\":\"1\",\"username\":null,\"password\":null,\"age\":2}", stringValue);
        Map<String, Object> mapValue = DataViewUtil.getRecord(dataView, new TypeReference<Map<String, Object>>() {
        });
        assert mapValue != null;
        Assertions.assertEquals(4, mapValue.size());
        Assertions.assertEquals("1", mapValue.get("id"));
        Assertions.assertEquals(2, mapValue.get("age"));
        User beanValue = DataViewUtil.getRecord(dataView, new TypeReference<User>() {
        });
        assert beanValue != null;
        Assertions.assertEquals("1", beanValue.getId());
        Assertions.assertEquals(new Integer(2), beanValue.getAge());
        Assertions.assertNull(beanValue.getUsername());
        Assertions.assertNull(beanValue.getPassword());
        objectValue = DataViewUtil.getRecord(dataView, new TypeReference<Object>() {
        });
        assert objectValue != null;
        Assertions.assertEquals(LinkedHashMap.class, objectValue.getClass());


        record = new HashMap<>();
        ((HashMap) record).put("id", 2);
        ((HashMap) record).put("age", "3");
        ((HashMap) record).put("username", "张三");
        ((HashMap) record).put("password", 123);
        dataView = DataViewUtil.getModelViewSuccess(0, "", record);
        json = JsonUtil.toJsonString(dataView);
        dataView = JsonUtil.parseObject(json, DataView.class);
        stringValue = DataViewUtil.getRecord(dataView, new TypeReference<String>() {
        });
        Assertions.assertEquals("{\"password\":123,\"id\":2,\"age\":\"3\",\"username\":\"张三\"}", stringValue);
        mapValue = DataViewUtil.getRecord(dataView, new TypeReference<Map<String, Object>>() {
        });
        assert mapValue != null;
        Assertions.assertEquals(4, mapValue.size());
        Assertions.assertEquals(2, mapValue.get("id"));
        Assertions.assertEquals("3", mapValue.get("age"));
        beanValue = DataViewUtil.getRecord(dataView, new TypeReference<User>() {
        });
        assert beanValue != null;
        Assertions.assertEquals("2", beanValue.getId());
        Assertions.assertEquals(new Integer(3), beanValue.getAge());
        Assertions.assertEquals("张三", beanValue.getUsername());
        Assertions.assertEquals("123", beanValue.getPassword());
        objectValue = DataViewUtil.getRecord(dataView, new TypeReference<Object>() {
        });
        assert objectValue != null;
        Assertions.assertEquals(LinkedHashMap.class, objectValue.getClass());


        record = "";
        dataView = DataViewUtil.getModelViewSuccess(0, "", record);
        json = JsonUtil.toJsonString(dataView);
        dataView = JsonUtil.parseObject(json, DataView.class);
        stringValue = DataViewUtil.getRecord(dataView, new TypeReference<String>() {
        });
        Assertions.assertEquals("", stringValue);
        objectValue = DataViewUtil.getRecord(dataView, new TypeReference<Object>() {
        });
        assert objectValue != null;
        Assertions.assertEquals(LinkedHashMap.class, objectValue.getClass());
        objectValue = DataViewUtil.getRecord(dataView, new TypeReference<Object>() {
        });
        assert objectValue != null;
        Assertions.assertEquals("", objectValue);

        record = new BigDecimal(1);
        dataView = DataViewUtil.getModelViewSuccess(0, "", record);
        json = JsonUtil.toJsonString(dataView);
        dataView = JsonUtil.parseObject(json, DataView.class);
        stringValue = DataViewUtil.getRecord(dataView, new TypeReference<String>() {
        });
        Assertions.assertEquals("1", stringValue);

        System.out.println("结束");
    }

}
