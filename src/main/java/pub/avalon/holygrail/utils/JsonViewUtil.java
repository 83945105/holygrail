package pub.avalon.holygrail.utils;

import pub.avalon.holygrail.response.exception.JsonViewParseException;
import pub.avalon.holygrail.response.beans.ResultInfo;
import pub.avalon.holygrail.response.utils.ResultUtil;
import pub.avalon.holygrail.response.views.DataView;
import pub.avalon.holygrail.response.views.JsonView;

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
            throw new JsonViewParseException(ResultUtil.createError("the param is null."));
        }
        JsonView jsonView = (JsonView) dataView;
        ResultInfo resultInfo = jsonView.getResultInfo();
        if (resultInfo == null) {
            throw new JsonViewParseException(ResultUtil.createError("can not find resultInfo in dataView."));
        }
        if (resultInfo.isSuccess()) {
            return jsonView;
        }
        throw new JsonViewParseException(resultInfo);
    }

}
