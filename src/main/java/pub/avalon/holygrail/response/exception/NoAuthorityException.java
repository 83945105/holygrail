package pub.avalon.holygrail.response.exception;

import pub.avalon.holygrail.response.beans.ResultInfo;

/**
 * 无权异常
 *
 * @author 白超
 */
public class NoAuthorityException extends ResultException {

    public NoAuthorityException(ResultInfo resultInfo) {
        super(resultInfo);
    }
}
