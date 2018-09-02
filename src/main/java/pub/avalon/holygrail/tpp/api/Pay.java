package pub.avalon.holygrail.tpp.api;

import pub.avalon.holygrail.tpp.beans.TradeStatus;

/**
 * 支付
 *
 * @author 白超
 * @date 2018/9/2
 */
public interface Pay<R, O> {

    String getName();

    R executeTradeQuery(String orderId);

    TradeStatus getTradeStatus(R response);

    String getTradeQrCode(O order);

}
