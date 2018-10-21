package pub.avalon.holygrail.wx.beans;

import com.alibaba.fastjson.JSONObject;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 公众号模板消息
 *
 * @author 白超
 * @date 2018/10/12
 */
public class TemplateMessage extends LinkedHashMap<String, Object> {

    /**
     * 设置接收方
     *
     * @param touser 接收方 一般是openid
     * @return
     */
    public TemplateMessage setTouser(String touser) {
        return this;
    }

    /**
     * 设置模板ID
     *
     * @param template_id 模板ID
     * @return
     */
    public TemplateMessage setTemplate_id(String template_id) {
        this.put("template_id", template_id);
        return this;
    }

    /**
     * 设置跳转url
     *
     * @param url 跳转的url
     * @return
     */
    public TemplateMessage setUrl(String url) {
        this.put("url", url);
        return this;
    }

    /**
     * 设置小程序信息
     *
     * @param miniprogram 小程序配置
     * @return
     */
    public TemplateMessage setMiniprogram(TemplateMessageMiniProgram miniprogram) {
        this.put("miniprogram", miniprogram);
        return this;
    }

    /**
     * 添加数据
     *
     * @param key        参数名
     * @param value      参数值
     * @param valueColor 参数值颜色
     * @return
     */
    @SuppressWarnings("unchecked")
    public TemplateMessage addData(String key, String value, String valueColor) {
        Map<String, String> data = new LinkedHashMap<>(2);
        data.put("value", value);
        data.put("color", valueColor);
        ((Map) this.computeIfAbsent("data", str -> new LinkedHashMap<String, Map<String, String>>())).put(key, data);
        return this;
    }

    public JSONObject getJSONObject() {
        return new JSONObject(this);
    }

    public String toJsonString() {
        return this.getJSONObject().toJSONString();
    }

    @SuppressWarnings("unchecked")
    public Map<String, String> toMap() {
        Map<String, String> map = new LinkedHashMap<>(this.size());
        this.forEach((key, value) -> map.put(key, JSONObject.toJSONString(value)));
        return map;
    }

}
