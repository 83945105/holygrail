package pub.avalon.holygrail.response.beans;

/**
 * @author 白超
 * @date 2018/9/25
 */
public class ResultDetailRealization implements ResultDetail {

    private ResultInfo resultInfo;

    public ResultDetailRealization(ResultInfo resultInfo) {
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
