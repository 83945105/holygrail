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
    public interface JsonViewParseSuccess<K, V> {

        void accept(JsonView<K, V> jsonView);

    }

    /**
     * JsonView 成功
     *
     * @param dataView
     * @param success
     * @param <K>
     * @param <V>
     * @throws JsonViewParseException
     */
    public static <K, V> void success(DataView dataView, JsonViewParseSuccess<K, V> success) throws JsonViewParseException {
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
     * @param <K>
     * @param <V>
     * @throws JsonViewParseException
     */
    public static <K, V> JsonView<K, V> success(DataView dataView) throws JsonViewParseException {
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
