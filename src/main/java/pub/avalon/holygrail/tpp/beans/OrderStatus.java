package pub.avalon.holygrail.tpp.beans;

/**
 * 订单状态
 *
 * @author 白超
 * @date 2018/9/2
 */
public enum OrderStatus {
    /**
     * 预先创建
     * 此时订单还未生效
     */
    PRE_CREATED,
    /**
     * 等待买方付款中
     * 此时已经
     */
    WAIT_BUYER_PAY,
    /**
     * 支付成功
     */
    SUCCESS,
    /**
     * 订单关闭
     */
    CLOSED,
    /**
     * 订单完成
     */
    FINISHED

}
