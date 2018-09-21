package pub.avalon.holygrail.response.beans;

/**
 * 结果集信息
 *
 * @author 白超
 */
public interface ResultInfo {

    /**
     * 获取结果值
     *
     * @return
     */
    ResultCode getResultCode();

    /**
     * 是否成功
     *
     * @return
     */
    boolean isSuccess();

    /**
     * 是否失败
     *
     * @return
     */
    boolean isFail();

    /**
     * 是否错误
     *
     * @return
     */
    boolean isError();

    /**
     * 结果集类型
     *
     * @return
     */
    int getType();

    /**
     * 获取信息提示
     *
     * @return
     */
    String getMessage();

    /**
     * 获取消息代号
     *
     * @return
     */
    int getMessageCode();

    /**
     * 获取异常信息
     *
     * @return
     */
    String getExceptionMessage();

}
