package pub.avalon.holygrail.response.exception;

import pub.avalon.holygrail.response.beans.ResultInfo;

/**
 * 404异常
 *
 * @author 白超
 */
public class NotFoundException extends ResultException {

    public NotFoundException(Integer code, ResultInfo resultInfo) {
        super(code, resultInfo);
    }
}
