package pub.avalon.holygrail.tpp.beans;

import pub.avalon.beans.Time;
import pub.avalon.holygrail.tpp.api.QrCodePay;
import pub.avalon.holygrail.tpp.exception.ThirdPartyPayException;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author 白超
 * @date 2018/9/2
 */
public abstract class AbstractQrCodePay<O> implements QrCodePay {

    protected String orderStatusKey = "orderStatus";

    protected PayManager<O> payManager = new PayManager<>();

    public AbstractQrCodePay() {
        this.register(this.payManager);
    }

    /**
     * 获取本地订单详情
     *
     * @param orderId 订单ID
     * @return
     */
    public abstract O getLocalOrderDetails(String orderId);

    /**
     * 获取本地订单状态
     *
     * @param order 订单信息
     * @return
     */
    public abstract OrderStatus getLocalOrderStatus(O order);

    /**
     * 将订单状态更新为等待买家付款
     * 用户第一次执行 {@link #getPayInfo(String)} 出发本方法
     *
     * @param orderId 订单ID
     * @throws ThirdPartyPayException
     */
    public abstract void updateLocalOrderStatusToWaitBuyerPay(String orderId) throws ThirdPartyPayException;


    /**
     * 更新本地交易二维码
     *
     * @param orderId 订单ID
     * @param payName 支付名称
     * @param qrCode  二维码
     * @throws Exception
     */
    public abstract void updateLocalTradeQrCodeAndLocalTradeStatusToWaitBuyerPay(String orderId, String payName, String qrCode) throws Exception;

    /**
     * 取出本地第三方交易状态
     *
     * @param order   订单信息
     * @param payName 支付名称
     * @return
     */
    public abstract TradeStatus getLocalTradeStatus(O order, String payName);

    /**
     * 取出本地交易二维码
     *
     * @param order   订单信息
     * @param payName 支付名称
     * @return
     */
    public abstract String getLocalTradeQrCode(O order, String payName);

    /**
     * 更新本地交易状态为成功
     *
     * @param orderId 订单ID
     * @param payName 支付名称
     */
    public abstract void updateLocalTradeStatusToSuccess(String orderId, String payName);

    /**
     * 更新本地交易状态为结束
     *
     * @param orderId 订单ID
     * @param payName 支付名称
     */
    public abstract void updateLocalTradeStatusToFinished(String orderId, String payName);

    /**
     * 注册第三方支付
     *
     * @param register 支付管理器
     */
    public void register(PayManager<O> register) {

    }

    /**
     * 初始化结果集
     *
     * @param order   订单信息
     * @param results 结果集
     * @return
     */
    public Map<String, Object> initResultsByLocalOrder(O order, Map<String, Object> results) {
        results.put("currentTime", Time.localDateTimeNow());
        results.put("currentTimeStamp", Time.timeStamp());
        return results;
    }

    /**
     * 处理交易二维码
     *
     * @param payName 交易名称
     * @param qrCode  二维码
     * @param order   订单数据
     * @param results 结果集
     */
    public void doTradeQrCodeForResults(String payName, String qrCode, O order, final Map<String, Object> results) {
        results.put(payName, qrCode);
    }

    private void applyTradeQrCode(String orderId, O order, String payName, Map<String, Object> results) {
        //无法保证每个第三方支付二维码都能申请成功
        String tradeQrCode = null;
        try {
            tradeQrCode = this.payManager.applyTradeQrCode(order, payName);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (tradeQrCode != null) {
            //保存二维码,并将对应的第三方支付状态改为等待付款
            try {
                this.updateLocalTradeQrCodeAndLocalTradeStatusToWaitBuyerPay(orderId, payName, tradeQrCode);
                //处理二维码
                this.doTradeQrCodeForResults(payName, tradeQrCode, order, results);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public Map<String, Object> getPayInfo(String orderId) {
        Map<String, Object> results = new HashMap<>(16);
        //获取订单信息
        O order = this.getLocalOrderDetails(orderId);
        //初始化结果集
        this.initResultsByLocalOrder(order, results);
        //获取订单状态
        OrderStatus orderStatus = this.getLocalOrderStatus(order);
        switch (orderStatus) {
            //客户第一次进入付款页面触发
            case PRE_CREATED:
                //将订单状态改为等待付款
                this.updateLocalOrderStatusToWaitBuyerPay(orderId);
                //获取所有注册的第三方支付交易状态
                this.payManager.getPayNames().forEach(payName -> {
                    TradeStatus tradeStatus = this.payManager.queryTradeStatus(orderId, payName);
                    if (tradeStatus == TradeStatus.TRADE_NOT_EXIST) {
                        //状态是交易未创建  就去申请二维码
                        this.applyTradeQrCode(orderId, order, payName, results);
                    } else {
                        //交易不是未创建
                        //正常来说 客户只要能看到二维码 就不该再出现这个状态了
                        //暂时想象不出这种情况的出现场景
                        //除非 申请二维码更新订单状态出错或者后面某个步骤又错误的将状态改为预创建状态了
                        throw new ThirdPartyPayException("不应该出现的支付情况");
                    }
                });
                results.put(orderStatusKey, OrderStatus.WAIT_BUYER_PAY.name());
                return results;
            //等待买家付款
            //在准确的获取到客户支付结果之前  客户刷新了浏览器触发
            case WAIT_BUYER_PAY:
                //依次获取本地各个第三方交易状态
                Set<String> payNames = this.payManager.getPayNames();
                boolean complete = false;
                for (String payName : payNames) {
                    TradeStatus tradeStatus = this.getLocalTradeStatus(order, payName);
                    if (tradeStatus == null) {
                        //本地没有支付二维码,说明在订单为PRE_CREATED时,申请该第三方支付二维码失败,或者保存到本地失败,先查询下交易状态
                        tradeStatus = this.payManager.queryTradeStatus(orderId, payName);
                        if (tradeStatus == TradeStatus.TRADE_NOT_EXIST) {
                            //状态是交易未创建  重新申请二维码
                            this.applyTradeQrCode(orderId, order, payName, results);
                        } else {
                            //交易不是未创建
                            //正常来说 客户只要能看到二维码 就说明二维码和订单状态已经保存成功,tradeStatus就不可能为null,不应该进入这个分支
                            throw new ThirdPartyPayException("不应该出现的支付情况");
                        }
                    } else if (tradeStatus == TradeStatus.TRADE_NOT_EXIST) {
                        //正常来说本地记录的第三方交易状态不会出现TRADE_NOT_EXIST,除非流程一开始的时候将第三方交易状态默认为TRADE_NOT_EXIST,如果出现这种情况,就走申请流程
                        //申请二维码
                        this.applyTradeQrCode(orderId, order, payName, results);
                    } else if (tradeStatus == TradeStatus.WAIT_BUYER_PAY) {
                        //取出本地记录的二维码
                        String tradeQrCode = this.getLocalTradeQrCode(order, payName);
                        if (tradeQrCode == null) {
                            //二维码不存在,重新申请
                            this.applyTradeQrCode(orderId, order, payName, results);
                        } else {
                            //处理二维码
                            this.doTradeQrCodeForResults(payName, tradeQrCode, order, results);
                        }
                    } else if (tradeStatus == TradeStatus.TRADE_CLOSED) {
                        //交易关闭
                        this.applyTradeQrCode(orderId, order, payName, results);
                    } else if (tradeStatus == TradeStatus.TRADE_SUCCESS) {
                        this.updateLocalTradeStatusToSuccess(orderId, payName);
                        complete = true;
                    } else if (tradeStatus == TradeStatus.TRADE_FINISHED) {
                        this.updateLocalTradeStatusToFinished(orderId, payName);
                        complete = true;
                    } else {
                        throw new ThirdPartyPayException("本地第三方支付状态不正确,订单号：" + orderId + "支付名称" + payName);
                    }
                }
                results.put(orderStatusKey, complete ? OrderStatus.SUCCESS.name() : OrderStatus.WAIT_BUYER_PAY.name());
                return results;
            //订单完成
            case SUCCESS:
                results.put(orderStatusKey, OrderStatus.SUCCESS.name());
                return results;
            //订单已关闭
            case CLOSED:
                results.put(orderStatusKey, OrderStatus.CLOSED.name());
                return results;
            //订单结束
            case FINISHED:
                results.put(orderStatusKey, OrderStatus.FINISHED.name());
                return results;
            default:
                throw new ThirdPartyPayException("订单状态不正确");

        }
    }

    @Override
    public OrderStatus pollingGetOrderStatus(String orderId) {
        //获取订单信息
        O order = this.getLocalOrderDetails(orderId);
        //获取订单状态
        OrderStatus orderStatus = this.getLocalOrderStatus(order);
        if (orderStatus == OrderStatus.WAIT_BUYER_PAY) {
            //调用第三方获取状态
            Set<String> payNames = this.payManager.getPayNames();
            boolean complete = false;
            for (String payName : payNames) {
                TradeStatus tradeStatus = this.payManager.queryTradeStatus(orderId, payName);
                if (tradeStatus == TradeStatus.TRADE_SUCCESS) {
                    this.updateLocalTradeStatusToSuccess(orderId, payName);
                    complete = true;
                } else if (tradeStatus == TradeStatus.TRADE_FINISHED) {
                    this.updateLocalTradeStatusToFinished(orderId, payName);
                    complete = true;
                }
            }
            return complete ? OrderStatus.SUCCESS : OrderStatus.WAIT_BUYER_PAY;
        }
        return orderStatus;
    }

    public void setOrderStatusKey(String orderStatusKey) {
        this.orderStatusKey = orderStatusKey;
    }

    public void setPayManager(PayManager<O> payManager) {
        this.payManager = payManager;
    }
}
