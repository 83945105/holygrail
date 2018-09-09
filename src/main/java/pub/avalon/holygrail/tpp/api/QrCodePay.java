package pub.avalon.holygrail.tpp.api;

import pub.avalon.holygrail.tpp.beans.OrderStatus;

import java.util.Map;

/**
 * 第三方支付对接
 * 二维码支付
 *
 * @author 白超
 * @date 2018/9/2
 */
public interface QrCodePay {

    /**
     * 获取支付信息
     *
     * @param orderId 订单ID
     * @return
     */
    Map<String, Object> getPayInfo(String orderId);

    /**
     * 轮询获取订单状态
     * 用于页面轮询
     *
     * @param orderId 订单ID
     * @return
     */
    OrderStatus pollingGetOrderStatus(String orderId);

}
