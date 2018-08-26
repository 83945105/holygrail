package pub.avalon.holygrail.response.exception;

import pub.avalon.holygrail.response.beans.ResultInfo;

/**
 * 需要登录异常
 *
 * @author 白超
 */
public class NeedLoginException extends ResultException {

    public NeedLoginException(ResultInfo resultInfo) {
        super(resultInfo);
    }
}
