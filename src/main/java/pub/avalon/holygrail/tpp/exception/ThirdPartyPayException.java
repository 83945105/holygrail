package pub.avalon.holygrail.tpp.exception;

/**
 * 第三方支付异常
 *
 * @author 白超
 * @date 2018/9/2
 */
public class ThirdPartyPayException extends RuntimeException {

    public ThirdPartyPayException() {
    }

    public ThirdPartyPayException(String message) {
        super(message);
    }

    public ThirdPartyPayException(String message, Throwable cause) {
        super(message, cause);
    }

    public ThirdPartyPayException(Throwable cause) {
        super(cause);
    }
}
