package pub.avalon.holygrail.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

/**
 * Json工具
 *
 * @author 白超
 * @date 2018/9/21
 */
public class JsonUtil {

    private JsonUtil() {
    }

    private final static ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    /**
     * 将对象转换为Json字符串
     *
     * @param object
     * @return
     */
    public static String toJsonString(Object object) {
        try {
            return OBJECT_MAPPER.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 将Json字符串解析为对象
     *
     * @param json
     * @param valueType
     * @param <T>
     * @return
     */
    public static <T> T parseObject(String json, Class<T> valueType) {
        try {
            return OBJECT_MAPPER.readValue(json, valueType);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 将Json字符串解析为对象
     *
     * @param json
     * @param valueTypeRef
     * @param <T>
     * @return
     */
    public static <T> T parseObject(String json, TypeReference<T> valueTypeRef) {
        try {
            return OBJECT_MAPPER.readValue(json, valueTypeRef);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 解析json
     *
     * @param json
     * @return
     */
    public static TreeNode readTree(String json) {
        try {
            return OBJECT_MAPPER.readTree(json);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
