package pub.avalon.holygrail.response.plugins;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import pub.avalon.holygrail.bean.User;
import pub.avalon.holygrail.response.beans.ResultInfo;
import pub.avalon.holygrail.response.utils.DataViewUtil;
import pub.avalon.holygrail.response.utils.ResultUtil;
import pub.avalon.holygrail.response.views.DataView;
import pub.avalon.holygrail.response.views.JsonView;
import pub.avalon.holygrail.response.views.ModelView;
import pub.avalon.holygrail.utils.JsonUtil;

import java.io.IOException;
import java.util.*;

/**
 * Created by 白超 on 2018/9/21.
 */
public class DataViewDeserializerTest {

    @Test
    void test() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();

        List<Map<String, Object>> list = new ArrayList<>();
        list.add(Collections.singletonMap("name", "张三"));
        list.add(Collections.singletonMap("name", "张三"));
        list.add(Collections.singletonMap("name", "张三"));
        list.add(Collections.singletonMap("name", "张三"));
        list.add(Collections.singletonMap("name", "张三"));

        DataView dataView = DataViewUtil.getModelViewSuccess(list);

        String json = JsonUtil.toJsonString(dataView);

        JsonView jsonView = JsonUtil.parseObject(json, JsonView.class);
        ResultInfo resultInfo = jsonView.getResultInfo();
        System.out.println(resultInfo.isSuccess());
        System.out.println(resultInfo.getMessageCode());
        System.out.println(resultInfo.getMessage());
        System.out.println(jsonView.size());

    }

    @Test
    void test02() {
        ObjectMapper objectMapper = new ObjectMapper();

        Map<String, Object> map = new HashMap<>();

        User user = new User();
        user.setName("张三");
        user.setA1(1024);

        map.put("user", user);

        List<Object> list = new ArrayList<>();
        list.add(user);
        list.add(Collections.singletonMap("name", "张三"));
        list.add(Collections.singletonMap("name", "张三"));
        list.add(Collections.singletonMap("name", "张三"));
        list.add(Collections.singletonMap("name", "张三"));

        ModelView modelView = new ModelView(ResultUtil.createSuccess("成功"));
//        modelView.setRecords(map);
        modelView.setRecord(user);
        modelView.setRows(list);

        String json = JsonUtil.toJsonString(modelView);

        System.out.println(json);

        JsonView jsonView = JsonUtil.parseObject(json, JsonView.class);

        Map records = jsonView.getRecords();

        System.out.println(records.size());
    }

    void testJsonView() throws Exception {

        ObjectMapper objectMapper = new ObjectMapper();

        List<Map<String, Object>> list = new ArrayList<>();
        list.add(Collections.singletonMap("name", "张三"));
        list.add(Collections.singletonMap("name", "张三"));
        list.add(Collections.singletonMap("name", "张三"));
        list.add(Collections.singletonMap("name", "张三"));
        list.add(Collections.singletonMap("name", "张三"));

        DataView dataView = DataViewUtil.getModelViewSuccess(list);

        String json = JsonUtil.toJsonString(dataView);
        String json1 = JSONObject.toJSONString(dataView);

        System.out.println(json);
        System.out.println(json1);

        JsonView jsonView = objectMapper.readValue(json, JsonView.class);

        JsonView jsonView1 = JSONObject.parseObject(json, JsonView.class);

        int count = 1;
        long tt;

        long minTT = Long.MAX_VALUE;
        long maxTT = Long.MIN_VALUE;

        for (int j = 0; j < 10000000; j++) {
            tt = 0;
            for (int i = 0; i < count; i++) {
                long st = System.nanoTime();
                JSONObject.parseObject(json, JsonView.class);
                long et = System.nanoTime() - st;
                tt += et;
            }
            if (tt < minTT) {
                minTT = tt;
            }
            if (tt > maxTT) {
                maxTT = tt;
            }
        }


        System.out.println("平均最小耗时：" + minTT / count);
        System.out.println("平均最大耗时：" + maxTT / count);
        System.out.println("累计最小耗时：" + minTT);
        System.out.println("累计最大耗时：" + maxTT);
    }

}
