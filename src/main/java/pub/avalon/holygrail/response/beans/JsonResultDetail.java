package pub.avalon.holygrail.response.beans;

/**
 * @author 白超
 * @date 2019/3/25
 */
public class JsonResultDetail extends AbstractJsonResultDetail {

    private String json;

    public JsonResultDetail(String json) {
        if(json == null) {
            throw new RuntimeException("JsonResultDetail Constructor arg json is null.");
        }
        this.json = json;
    }

    @Override
    public ResultInfo getResultInfo() {
        if (this.resultInfo != null) {
            return this.resultInfo;
        }
        this.resultInfo = new JsonResultInfo(this.json);
        return this.resultInfo;
    }
}
