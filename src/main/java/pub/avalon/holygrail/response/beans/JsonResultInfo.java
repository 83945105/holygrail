package pub.avalon.holygrail.response.beans;

/**
 * @author 白超
 * @date 2018/6/22
 */
public class JsonResultInfo extends ResultInfoRealization {

    public void setType(int type) {
        for (ResultCode code : ResultCode.values()) {
            if (code.getCode() == type) {
                super.setType(code);
                return;
            }
        }
    }
}
