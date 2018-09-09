package pub.avalon.holygrail.tpp.beans;

import pub.avalon.holygrail.tpp.api.Pay;
import pub.avalon.holygrail.tpp.exception.ThirdPartyPayException;

import java.util.*;

/**
 * 第三方管理
 *
 * @author 白超
 * @date 2018/9/2
 */
public class PayManager<O> {

    private Map<String, Pay<O>> payMap = new LinkedHashMap<>();

    public void addPay(Pay<O> pay) {
        if (pay != null) {
            Pay p = this.payMap.get(pay.getName());
            if (p != null) {
                throw new ThirdPartyPayException("第三方支付名称重复:" + pay.getName());
            }
            this.payMap.put(pay.getName(), pay);
        }
    }

    /**
     * 申请交易二维码
     *
     * @param order   订单数据
     * @param payName 支付名称
     * @return
     */
    protected String applyTradeQrCode(O order, String payName) {
        Pay<O> pay = this.payMap.get(payName);
        if (pay == null) {
            return null;
        }
        return pay.applyTradeQrCode(order);
    }

    /**
     * 查询交易状态
     *
     * @param orderId 订单ID
     * @param payName 交易名称
     * @return
     */
    protected TradeStatus queryTradeStatus(String orderId, String payName) {
        Pay<O> pay = this.payMap.get(payName);
        if (pay == null) {
            return null;
        }
        return pay.queryTradeStatus(orderId);
    }

    /**
     * 获取所有交易名称
     *
     * @return
     */
    protected Set<String> getPayNames() {
        return this.payMap.keySet();
    }

}
