package pub.avalon.holygrail.tpp.beans;

import pub.avalon.holygrail.tpp.api.Pay;
import pub.avalon.holygrail.tpp.api.QrCodePay;
import pub.avalon.holygrail.tpp.exception.ThirdPartyPayException;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;

/**
 * 第三方管理
 *
 * @author 白超
 * @date 2018/9/2
 */
public class PayManager<O> {

    private List<Pay<Object, O>> payList = new ArrayList<>();
    private QrCodePay<O> qrCodePay;

    public PayManager(QrCodePay<O> qrCodePay) {
        this.qrCodePay = qrCodePay;
    }

    public PayManager addPay(Pay pay) {
        if (pay != null) {
            for (Pay p : payList) {
                if (p.getName().equals(pay.getName())) {
                    throw new ThirdPartyPayException("第三方支付名称重复:" + pay.getName());
                }
            }
            this.payList.add(pay);
        }
        return this;
    }

    protected String getQrCode(String payName, O order) {
        for (Pay<Object, O> pay : payList) {
            if (pay.getName().equals(payName)) {
                return pay.getTradeQrCode(order);
            }
        }
        return null;
    }

    protected void getTradeStatus(String orderId, BiConsumer<String, TradeStatus> consumer) {
        for (Pay<Object, O> pay : this.payList) {
            Object payInfo = pay.executeTradeQuery(orderId);
            if (payInfo == null) {
                continue;
            }
            TradeStatus tradeStatus = pay.getTradeStatus(payInfo);
            consumer.accept(pay.getName(), tradeStatus);
        }
    }
}
