package pub.avalon.holygrail.response.exception;

import pub.avalon.holygrail.response.beans.ResultInfo;

/**
 * @author 白超
 * @date 2018/6/25
 */
public class JsonViewParseException extends ResultException {

    public JsonViewParseException(ResultInfo resultInfo) {
        super(resultInfo);
    }

}
