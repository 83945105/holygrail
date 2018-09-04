package pub.avalon.holygrail.tpp.beans;

import pub.avalon.holygrail.response.utils.ExceptionUtil;
import pub.avalon.holygrail.tpp.api.QrCodePay;

import java.util.HashMap;
import java.util.Map;

/**
 * @author 白超
 * @date 2018/9/2
 */
public abstract class AbstractQrCodePay<O> implements QrCodePay<O> {

    private PayManager<O> payManager = new PayManager<>(this);

    public AbstractQrCodePay() {
        this.register(this.payManager);
    }

    @Override
    public Map<String, Object> process(String orderId) {
        Map<String, Object> results = new HashMap<>(16);
        //先获取订单本地状态
        O order = this.getOrderDetails(orderId);
        this.initResultsForLocalOrder(order, results);
        OrderStatus orderStatus = this.getLocalOrderStatus(order);
        switch (orderStatus) {
            //订单预创建
            //客户第一次进入付款页面触发
            case PRE_CREATED:
                //获取所有注册的第三方支付交易状态
                Map<String, TradeStatus> tradeStatusMap = this.payManager.getTradeStatus(orderId);
                String payName;
                TradeStatus tradeStatus;
                for (Map.Entry<String, TradeStatus> entry : tradeStatusMap.entrySet()) {
                    payName = entry.getKey();
                    tradeStatus = entry.getValue();
                    if (tradeStatus == TradeStatus.TRADE_NOT_EXIST) {
                        //状态是交易未创建  就去获取二维码
                        //无法保证每个第三方支付二维码都能申请成功
                        //只要申请成功一个,就把订单改为等待买家付款状态
                        String qrCode = this.payManager.getQrCode(payName, order);
                        //保存二维码并把订单置为等待买家付款状态
                        boolean s = this.saveTradeQrCodeAndOrderStatusToWaitBuyerPay(orderId, payName, qrCode);
                        if (s) {
                            this.doResultsForQrCode(payName, qrCode, order, results);
                        }
                    } else {
                        //交易不是未创建
                        //正常来说 客户只要能看到二维码 就不该再出现这个状态了
                        //暂时想象不出这种情况的出现场景
                        //除非 申请二维码更新订单状态出错或者后面某个步骤又错误的将状态改为预创建状态了
                        ExceptionUtil.throwErrorException("不应该出现的支付情况");
                    }
                }
                results.put("orderStatus", OrderStatus.WAIT_BUYER_PAY.name());
                return results;
            //等待买家付款
            //在准确的获取到客户支付结果之前  客户刷新了浏览器触发
            case WAIT_BUYER_PAY:
                //再次获取所有注册的第三方支付交易状态
                tradeStatusMap = this.payManager.getTradeStatus(orderId);
                for (Map.Entry<String, TradeStatus> entry : tradeStatusMap.entrySet()) {
                    payName = entry.getKey();
                    tradeStatus = entry.getValue();
                    if (tradeStatus == TradeStatus.TRADE_NOT_EXIST) {
                        //状态是交易未创建  就去获取二维码
                        String qrCode = this.payManager.getQrCode(payName, order);
                        //保存二维码
                        boolean s = this.saveTradeQrCode(orderId, payName, qrCode);
                        if (s) {
                            this.doResultsForQrCode(payName, qrCode, order, results);
                        }
                    } else if (tradeStatus == TradeStatus.WAIT_BUYER_PAY) {
                        //出现这种情况说明客户扫码了
                        //正常状态,不处理
                        //目前可以确认的是支付宝只有在用户扫码成功一瞬间才创建交易订单,才能进入该状态,其它支付待确认
                        //取出二维码
                        this.doResultsForQrCode(payName, this.getQrCode(order, payName), order, results);
                    } else if (tradeStatus == TradeStatus.TRADE_CLOSED) {
                        //出现这种情况说明客户扫码后长时间未支付,然后刷新了浏览器
                        //此时应该重新申请二维码
                        String qrCode = this.payManager.getQrCode(payName, order);
                        //保存二维码
                        boolean s = this.saveTradeQrCode(orderId, payName, qrCode);
                        if (s) {
                            this.doResultsForQrCode(payName, qrCode, order, results);
                        }
                    } else if (tradeStatus == TradeStatus.TRADE_SUCCESS || tradeStatus == TradeStatus.TRADE_FINISHED) {
                        //出现这俩种情况,说明订单支付成功,立刻将订单状态改为完成状态
                        //如果客户同时支付多个渠道成功并成功保存到我们后台,人工处理,自动发起撤销订单有风险
                        boolean s = this.saveOrderStatusToSuccess(orderId, payName);
                        if (!s) {
                            ExceptionUtil.throwErrorException("保存客户付款成功状态失败,订单ID：" + orderId);
                        }
                        results.put("orderStatus", OrderStatus.SUCCESS.name());
                        return results;
                    } else {
                        //出现这种情况,说明未获取到第三方支付结果
                        //暂不处理
                    }
                }
                results.put("orderStatus", OrderStatus.WAIT_BUYER_PAY.name());
                return results;
            //订单完成
            case SUCCESS:
                results.put("orderStatus", OrderStatus.SUCCESS.name());
                return results;
            //订单已关闭
            case CLOSED:
                results.put("orderStatus", OrderStatus.CLOSED.name());
                return results;
            //订单结束
            case FINISHED:
                results.put("orderStatus", OrderStatus.FINISHED.name());
                return results;
            default:
                ExceptionUtil.throwErrorException("订单状态不正确");

        }
        return null;
    }

    @Override
    public void register(PayManager<O> register) {
    }
}
