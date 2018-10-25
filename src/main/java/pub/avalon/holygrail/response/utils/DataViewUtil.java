package pub.avalon.holygrail.response.utils;

import pub.avalon.beans.Limit;
import pub.avalon.holygrail.response.views.*;

import java.util.Collection;
import java.util.Map;

/**
 * @author 白超
 */
public class DataViewUtil {

    private DataViewUtil() {
    }

    public static MessageView getMessageViewSuccess(String message) {
        return new MessageView(0, ResultUtil.createSuccess(message));
    }

    public static MessageView getMessageViewSuccess(Integer code, String message) {
        return new MessageView(code, ResultUtil.createSuccess(message));
    }


    public static ModelView getModelViewSuccess(Object record) {
        return new ModelView(0, ResultUtil.createSuccess("success"), record);
    }

    public static ModelView getModelViewSuccess(String message, Object record) {
        return new ModelView(0, ResultUtil.createSuccess(message), record);
    }

    public static ModelView getModelViewSuccess(Integer code, String message, Object record) {
        return new ModelView(code, ResultUtil.createSuccess(message), record);
    }


    public static ModelView getModelViewSuccess(Map<?, ?> records) {
        return new ModelView(0, ResultUtil.createSuccess("success"), records);
    }

    public static ModelView getModelViewSuccess(String message, Map<?, ?> records) {
        return new ModelView(0, ResultUtil.createSuccess(message), records);
    }

    public static ModelView getModelViewSuccess(Integer code, String message, Map<?, ?> records) {
        return new ModelView(code, ResultUtil.createSuccess(message), records);
    }

    public static ModelView getModelViewSuccess(Collection<?> rows) {
        return new ModelView(0, ResultUtil.createSuccess("success"), rows);
    }

    public static ModelView getModelViewSuccess(String message, Collection<?> rows) {
        return new ModelView(0, ResultUtil.createSuccess(message), rows);
    }

    public static ModelView getModelViewSuccess(Integer code, String message, Collection<?> rows) {
        return new ModelView(code, ResultUtil.createSuccess(message), rows);
    }


    public static ModelView getModelViewSuccess(Collection<?> rows, Limit limit) {
        return new ModelView(0, ResultUtil.createSuccess("success"), limit, rows);
    }

    public static ModelView getModelViewSuccess(String message, Collection<?> rows, Limit limit) {
        return new ModelView(0, ResultUtil.createSuccess(message), limit, rows);
    }

    public static ModelView getModelViewSuccess(Integer code, String message, Collection<?> rows, Limit limit) {
        return new ModelView(code, ResultUtil.createSuccess(message), limit, rows);
    }


    public static ModelView getModelViewSuccess(Collection<?> rows, Map<?, ?> records) {
        return new ModelView(0, ResultUtil.createSuccess("success"), rows, records);
    }

    public static ModelView getModelViewSuccess(String message, Collection<?> rows, Map<?, ?> records) {
        return new ModelView(0, ResultUtil.createSuccess(message), rows, records);
    }

    public static ModelView getModelViewSuccess(Integer code, String message, Collection<?> rows, Map<?, ?> records) {
        return new ModelView(code, ResultUtil.createSuccess(message), rows, records);
    }


    public static ModelView getModelViewSuccess(Collection<?> rows, Map<?, ?> records, Limit limit) {
        return new ModelView(0, ResultUtil.createSuccess("success"), limit, rows, records);
    }

    public static ModelView getModelViewSuccess(String message, Collection<?> rows, Map<?, ?> records, Limit limit) {
        return new ModelView(0, ResultUtil.createSuccess(message), limit, rows, records);
    }

    public static ModelView getModelViewSuccess(Integer code, String message, Collection<?> rows, Map<?, ?> records, Limit limit) {
        return new ModelView(code, ResultUtil.createSuccess(message), limit, rows, records);
    }


    public static ModelView getModelViewSuccess(Collection<?> rows, Object record) {
        return new ModelView(0, ResultUtil.createSuccess("success"), rows, record);
    }

    public static ModelView getModelViewSuccess(String message, Collection<?> rows, Object record) {
        return new ModelView(0, ResultUtil.createSuccess(message), rows, record);
    }

    public static ModelView getModelViewSuccess(Integer code, String message, Collection<?> rows, Object record) {
        return new ModelView(code, ResultUtil.createSuccess(message), rows, record);
    }

    public static ExceptionView getExceptionViewFail(String message) {
        return new ExceptionView(0, ResultUtil.createFail(message));
    }

    public static ExceptionView getExceptionViewFail(Integer code, String message) {
        return new ExceptionView(code, ResultUtil.createFail(message));
    }

    public static ExceptionView getExceptionViewError(String message) {
        return new ExceptionView(0, ResultUtil.createError(message));
    }

    public static ExceptionView getExceptionViewError(Integer code, String message) {
        return new ExceptionView(code, ResultUtil.createError(message));
    }

}
