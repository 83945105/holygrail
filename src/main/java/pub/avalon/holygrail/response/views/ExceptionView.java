package pub.avalon.holygrail.response.views;

import pub.avalon.holygrail.response.beans.ResultInfo;

import java.util.Map;

/**
 * 异常视图
 *
 * @author 白超
 */
public class ExceptionView extends MessageView {

    protected Map<?, ?> data;

    public ExceptionView(Integer code, ResultInfo resultInfo) {
        super(code, resultInfo);
    }

    public ExceptionView(Integer code, ResultInfo resultInfo, Map<?, ?> data) {
        super(code, resultInfo);
        this.data = data;
    }

    public Map<?, ?> getData() {
        return data;
    }

    public void setData(Map<?, ?> data) {
        this.data = data;
    }

}
