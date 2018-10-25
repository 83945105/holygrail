package pub.avalon.holygrail.response.views;

import pub.avalon.holygrail.response.beans.ResultInfo;

/**
 * 信息视图
 *
 * @author 白超
 */
public class MessageView implements DataView {

    /**
     * 消息代号
     */
    public static final String CODE = "code";

    /**
     * ResultInfo属性名
     */
    public static final String RESULT_INFO_PARAM = "resultInfo";

    /**
     * 消息代号
     */
    protected Integer code;

    /**
     * 结果集信息
     */
    protected ResultInfo resultInfo;

    public MessageView(Integer code, ResultInfo resultInfo) {
        this.code = code;
        this.resultInfo = resultInfo;
    }

    @Override
    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    @Override
    public ResultInfo getResultInfo() {
        return resultInfo;
    }

    public void setResultInfo(ResultInfo resultInfo) {
        this.resultInfo = resultInfo;
    }

}
