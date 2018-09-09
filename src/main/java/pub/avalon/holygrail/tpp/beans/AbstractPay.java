package pub.avalon.holygrail.tpp.beans;

import pub.avalon.holygrail.tpp.api.Pay;
import pub.avalon.holygrail.tpp.exception.ThirdPartyPayException;

/**
 * 第三方支付
 *
 * @author 白超
 * @date 2018/9/2
 */
public abstract class AbstractPay<O> implements Pay<O> {

    protected String name;

    public AbstractPay(String name) {
        if (name == null) {
            throw new ThirdPartyPayException("第三方支付名称不能为空");
        }
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }
}
