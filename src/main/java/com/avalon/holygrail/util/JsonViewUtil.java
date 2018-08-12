package com.avalon.holygrail.util;

import com.avalon.holygrail.ss.exception.JsonViewParseException;
import com.avalon.holygrail.ss.norm.ResultInfo;
import com.avalon.holygrail.ss.util.ResultUtil;
import com.avalon.holygrail.ss.view.DataView;
import com.avalon.holygrail.ss.view.JsonView;

/**
 * @author 白超
 * @date 2018/6/25
 */
public class JsonViewUtil {

    private JsonViewUtil() {
    }

    @FunctionalInterface
    public interface JsonViewParseSuccess {

        /**
         * 接收处理JsonView
         *
         * @param jsonView json视图
         */
        void accept(JsonView jsonView);

    }

    /**
     * JsonView 成功
     *
     * @param dataView
     * @param success
     * @throws JsonViewParseException
     */
    public static void success(DataView dataView, JsonViewParseSuccess success) throws JsonViewParseException {
        if (dataView == null) {
            throw new JsonViewParseException(null);
        }
        JsonView jsonView = (JsonView) dataView;
        ResultInfo resultInfo = jsonView.getResultInfo();
        if (resultInfo == null) {
            throw new JsonViewParseException(null);
        }
        if (resultInfo.isSuccess()) {
            success.accept(jsonView);
            return;
        }
        throw new JsonViewParseException(resultInfo);
    }

    /**
     * JsonView 成功
     *
     * @param dataView 数据视图
     * @throws JsonViewParseException
     */
    public static JsonView success(DataView dataView) throws JsonViewParseException {
        if (dataView == null) {
            throw new JsonViewParseException(ResultUtil.createError("the param dataView is null."));
        }
        JsonView jsonView = (JsonView) dataView;
        ResultInfo resultInfo = jsonView.getResultInfo();
        if (resultInfo == null) {
            throw new JsonViewParseException(ResultUtil.createError("can not find resultInfo in the param dataView."));
        }
        if (resultInfo.isSuccess()) {
            return jsonView;
        }
        throw new JsonViewParseException(resultInfo);
    }

}
