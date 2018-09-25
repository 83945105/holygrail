package pub.avalon.holygrail.response.beans;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author 白超
 * @date 2018/9/25
 */
public class JsonResultDetail extends LinkedHashMap<String, Object> implements ResultDetail {

    @Override
    public ResultInfo getResultInfo() {
        Map<?, ?> resultInfo = (Map) get("resultInfo");
        JsonResultInfo jsonResultInfo = new JsonResultInfo();
        for (Map.Entry<?, ?> entry : resultInfo.entrySet()) {
            jsonResultInfo.put((String) entry.getKey(), entry.getValue());
        }
        return jsonResultInfo;
    }
}
