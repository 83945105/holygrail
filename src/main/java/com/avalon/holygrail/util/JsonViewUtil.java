package com.avalon.holygrail.util;

import com.avalon.holygrail.ss.exception.JsonViewParseException;
import com.avalon.holygrail.ss.norm.ResultInfo;
import com.avalon.holygrail.ss.view.DataView;
import com.avalon.holygrail.ss.view.JsonView;

/**
 * Created by 白超 on 2018/6/25.
 */
public class JsonViewUtil {

    @FunctionalInterface
    public interface JsonViewParseSuccess {

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
     * @param dataView
     * @throws JsonViewParseException
     */
    public static JsonView success(DataView dataView) throws JsonViewParseException {
        if (dataView == null) {
            throw new JsonViewParseException(null);
        }
        JsonView jsonView = (JsonView) dataView;
        ResultInfo resultInfo = jsonView.getResultInfo();
        if (resultInfo == null) {
            throw new JsonViewParseException(null);
        }
        if (resultInfo.isSuccess()) {
            return jsonView;
        }
        throw new JsonViewParseException(resultInfo);
    }

}
