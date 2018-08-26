package pub.avalon.holygrail.response.views;

import pub.avalon.holygrail.response.beans.ResultInfo;

/**
 * 信息视图
 *
 * @author 白超
 */
public class MessageView implements DataView {

    /**
     * ResultInfo属性名
     */
    public static final String RESULT_INFO_PARAM = "resultInfo";

    /**
     * 结果集信息
     */
    protected ResultInfo resultInfo;

    public MessageView(ResultInfo resultInfo) {
        this.resultInfo = resultInfo;
    }

    @Override
    public ResultInfo getResultInfo() {
        return resultInfo;
    }

    public void setResultInfo(ResultInfo resultInfo) {
        this.resultInfo = resultInfo;
    }

}
