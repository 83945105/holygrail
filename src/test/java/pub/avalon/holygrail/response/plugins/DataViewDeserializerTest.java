package pub.avalon.holygrail.response.plugins;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.ParserConfig;
import com.alibaba.fastjson.util.TypeUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import pub.avalon.holygrail.bean.User;
import pub.avalon.holygrail.response.beans.ResultDetail;
import pub.avalon.holygrail.response.beans.ResultInfo;
import pub.avalon.holygrail.response.utils.DataViewUtil;
import pub.avalon.holygrail.response.utils.ResultUtil;
import pub.avalon.holygrail.response.views.DataView;
import pub.avalon.holygrail.response.views.AbstractJsonView;
import pub.avalon.holygrail.response.views.ModelView;
import pub.avalon.holygrail.utils.JsonUtil;

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

        AbstractJsonView jsonView = JsonUtil.parseObject(json, AbstractJsonView.class);
        ResultInfo resultInfo = jsonView.getResultInfo();
        System.out.println(resultInfo.isSuccess());
        System.out.println(resultInfo.getMessageCode());
        System.out.println(resultInfo.getMessage());

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

        ModelView modelView = new ModelView(0, ResultUtil.createSuccess("成功"));
        modelView.setRecords(map);
        modelView.setRecord(user);
        modelView.setRows(list);

        String json = JsonUtil.toJsonString(modelView);

        System.out.println(json);

        AbstractJsonView jsonView = JsonUtil.parseObject(json, AbstractJsonView.class);

        Map records = jsonView.getRecords();

        System.out.println(records.size());

        jsonView.getRecord();

        jsonView.getRecord(User.class);

        jsonView.getRows();

        jsonView.getRows(row -> {

            return TypeUtils.cast(row, User.class, ParserConfig.getGlobalInstance());
        });

        System.out.println("----------");

    }

    @Test
    void test03() {

        String json = "{\"resultInfo\":{\"resultCode\":\"SUCCESS\",\"messageCode\":0,\"message\":\"success\",\"exceptionMessage\":null,\"resultDetails\":[{\"resultInfo\":{\"resultCode\":\"FAIL\",\"messageCode\":0,\"message\":\"失败\",\"exceptionMessage\":null,\"resultDetails\":null,\"error\":false,\"type\":0,\"success\":false,\"fail\":true}},{\"resultInfo\":{\"resultCode\":\"FAIL\",\"messageCode\":0,\"message\":\"失败\",\"exceptionMessage\":null,\"resultDetails\":null,\"error\":false,\"type\":0,\"success\":false,\"fail\":true}},{\"resultInfo\":{\"resultCode\":\"FAIL\",\"messageCode\":0,\"message\":\"失败\",\"exceptionMessage\":null,\"resultDetails\":null,\"error\":false,\"type\":0,\"success\":false,\"fail\":true}}],\"error\":false,\"type\":1,\"success\":true,\"fail\":false}}";

        AbstractJsonView jsonView = JsonUtil.parseObject(json, AbstractJsonView.class);

        Collection<ResultDetail> resultDetails = jsonView.getResultInfo().getResultDetails();

        for (ResultDetail resultDetail : resultDetails) {
            System.out.println(resultDetail.getResultInfo());
        }

        System.out.println(resultDetails.size());

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

        AbstractJsonView jsonView = objectMapper.readValue(json, AbstractJsonView.class);

        AbstractJsonView jsonView1 = JSONObject.parseObject(json, AbstractJsonView.class);

        int count = 1;
        long tt;

        long minTT = Long.MAX_VALUE;
        long maxTT = Long.MIN_VALUE;

        for (int j = 0; j < 10000000; j++) {
            tt = 0;
            for (int i = 0; i < count; i++) {
                long st = System.nanoTime();
                JSONObject.parseObject(json, AbstractJsonView.class);
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
