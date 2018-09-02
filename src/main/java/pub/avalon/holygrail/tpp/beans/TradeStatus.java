package pub.avalon.holygrail.tpp.beans;

/**
 * 交易状态
 *
 * @author 白超
 * @date 2018/9/2
 */
public enum TradeStatus {
    /**
     * 交易不存在
     */
    TRADE_NOT_EXIST,
    /**
     * 交易创建，等待买家付款
     */
    WAIT_BUYER_PAY,
    /**
     * 未付款交易超时关闭，或支付完成后全额退款
     */
    TRADE_CLOSED,
    /**
     * 交易支付成功
     */
    TRADE_SUCCESS,
    /**
     * 交易结束，不可退款
     */
    TRADE_FINISHED

}
