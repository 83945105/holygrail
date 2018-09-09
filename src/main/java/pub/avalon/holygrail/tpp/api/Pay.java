package pub.avalon.holygrail.tpp.api;

import pub.avalon.holygrail.tpp.beans.TradeStatus;

/**
 * 支付
 *
 * @author 白超
 * @date 2018/9/2
 */
public interface Pay<O> {

    /**
     * 获取支付名称
     *
     * @return
     */
    String getName();

    /**
     * 查询交易状态
     *
     * @param orderId 订单ID
     * @return
     */
    TradeStatus queryTradeStatus(String orderId);

    /**
     * 申请二维码
     *
     * @param order 订单信息
     * @return
     */
    String applyTradeQrCode(O order);

}
