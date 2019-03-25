package pub.avalon.holygrail.response.beans;

import pub.avalon.holygrail.utils.JsonUtil;

/**
 * @author 白超
 * @date 2019/3/25
 */
public class JsonResultDetail extends AbstractJsonResultDetail {

    private ResultDetail resultDetail;

    public JsonResultDetail(String json) {
        if (json == null) {
            throw new RuntimeException("JsonResultDetail Constructor arg json is null.");
        }
        this.resultDetail = new JacksonResultDetail(JsonUtil.readTree(json));
    }

    @Override
    public ResultInfo getResultInfo() {
        return this.resultDetail.getResultInfo();
    }
}
