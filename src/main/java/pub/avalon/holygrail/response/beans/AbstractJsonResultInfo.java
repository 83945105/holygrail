package pub.avalon.holygrail.response.beans;

import java.util.Collection;

/**
 * @author 白超
 * @date 2018/6/22
 */
public abstract class AbstractJsonResultInfo implements ResultInfo {

    protected ResultCode resultCode;
    protected Boolean success;
    protected Boolean fail;
    protected Boolean error;
    protected Integer type;
    protected String message;
    protected Integer messageCode;
    protected String exceptionMessage;
    protected Collection<ResultDetail> resultDetails;

}
