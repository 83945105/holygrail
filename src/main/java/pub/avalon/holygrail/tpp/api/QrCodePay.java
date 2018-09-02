package pub.avalon.holygrail.tpp.api;

import pub.avalon.beans.Time;
import pub.avalon.holygrail.tpp.beans.OrderStatus;
import pub.avalon.holygrail.tpp.beans.PayManager;
import pub.avalon.holygrail.tpp.beans.TradeStatus;

import java.util.HashMap;
import java.util.Map;

/**
 * 第三方支付对接
 * 二维码支付
 *
 * @author 白超
 * @date 2018/9/2
 */
public interface QrCodePay<O> {

    O getOrderDetails(String orderId);

    OrderStatus getLocalOrderStatus(O order);

    Map<String, Object> process(String orderId);

    boolean saveTradeQrCode(String orderId, String payName, String qrCode);

    boolean saveTradeQrCodeAndOrderStatusToWaitBuyerPay(String orderId, String payName, String qrCode);

    boolean saveOrderStatusToSuccess(String orderId, String payName);

    void register(PayManager<O> register);

    default void doResultsForQrCode(String payName, String qrCode, O order, final Map<String, Object> results) {
        results.put(payName, qrCode);
        results.put("orderStatus", OrderStatus.PRE_CREATED.name());
        results.put("currentTime", Time.localDateTimeNow());
        results.put("currentTimeStamp", Time.timeStamp());
    }

    default Map<String, Object> doResultsForLocalOrderWaitBuyerPayStatus(O order, Map<String, Object> results) {
        results.put("orderStatus", OrderStatus.WAIT_BUYER_PAY.name());
        results.put("currentTime", Time.localDateTimeNow());
        results.put("currentTimeStamp", Time.timeStamp());
        return results;
    }

    default Map<String, Object> doResultsForLocalOrderSuccessStatus(O order, Map<String, Object> results) {
        results.put("orderStatus", OrderStatus.SUCCESS.name());
        results.put("currentTime", Time.localDateTimeNow());
        results.put("currentTimeStamp", Time.timeStamp());
        return results;
    }

    default Map<String, Object> doResultsForLocalOrderClosedStatus(O order, Map<String, Object> results) {
        results.put("orderStatus", OrderStatus.CLOSED.name());
        results.put("currentTime", Time.localDateTimeNow());
        results.put("currentTimeStamp", Time.timeStamp());
        return results;
    }

    default Map<String, Object> doResultsForLocalOrderFinishedStatus(O order, Map<String, Object> results) {
        results.put("orderStatus", OrderStatus.FINISHED.name());
        results.put("currentTime", Time.localDateTimeNow());
        results.put("currentTimeStamp", Time.timeStamp());
        return results;
    }

}
