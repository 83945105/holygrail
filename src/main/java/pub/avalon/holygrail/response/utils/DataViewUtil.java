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

    public static MessageView getMessageViewSuccess() {
        return new MessageView(ResultUtil.createSuccess("success"));
    }

    public static MessageView getMessageViewSuccess(String message) {
        return new MessageView(ResultUtil.createSuccess(message));
    }

    public static MessageView getMessageViewSuccess(int messageCode, String... params) {
        return new MessageView(ResultUtil.createSuccess(messageCode, params));
    }

    public static MessageView getMessageViewFail() {
        return new MessageView(ResultUtil.createFail("fail"));
    }

    public static MessageView getMessageViewFail(String message) {
        return new MessageView(ResultUtil.createFail(message));
    }

    public static MessageView getMessageViewFail(int messageCode, String... params) {
        return new MessageView(ResultUtil.createFail(messageCode, params));
    }

    public static MessageView getMessageViewError() {
        return new MessageView(ResultUtil.createError("error"));
    }

    public static MessageView getMessageViewError(String message) {
        return new MessageView(ResultUtil.createError(message));
    }

    public static MessageView getMessageViewError(int messageCode, String... params) {
        return new MessageView(ResultUtil.createError(messageCode, params));
    }

    public static MessageView getMessageViewWarn() {
        return new MessageView(ResultUtil.createWarn("warn"));
    }

    public static MessageView getMessageViewWarn(String message) {
        return new MessageView(ResultUtil.createWarn(message));
    }

    public static MessageView getMessageViewWarn(int messageCode, String... params) {
        return new MessageView(ResultUtil.createWarn(messageCode, params));
    }

    public static MessageView getMessageViewInfo() {
        return new MessageView(ResultUtil.createInfo("info"));
    }

    public static MessageView getMessageViewInfo(String message) {
        return new MessageView(ResultUtil.createInfo(message));
    }

    public static MessageView getMessageViewInfo(int messageCode, String... params) {
        return new MessageView(ResultUtil.createInfo(messageCode, params));
    }

    public static DataGridView getDataGridViewSuccess() {
        return new DataGridView(ResultUtil.createSuccess("success"));
    }

    public static DataGridView getDataGridViewSuccess(Collection<?> rows) {
        return new DataGridView(ResultUtil.createSuccess("success"), rows);
    }

    public static DataGridView getDataGridViewSuccess(Collection<?> rows, Collection<?> footer) {
        return new DataGridView(ResultUtil.createSuccess("success"), rows, footer);
    }

    public static DataGridView getDataGridViewSuccess(Collection<?> rows, int total, int currPage, int pageSize) {
        return new DataGridView(ResultUtil.createSuccess("success"), total, currPage, pageSize, rows);
    }

    public static DataGridView getDataGridViewSuccess(Collection<?> rows, Limit limit) {
        return new DataGridView(ResultUtil.createSuccess("success"), limit, rows);
    }

    public static DataGridView getDataGridViewFail() {
        return new DataGridView(ResultUtil.createFail("fail"));
    }

    public static DataGridView getDataGridViewFail(Collection<?> rows) {
        return new DataGridView(ResultUtil.createFail("fail"));
    }

    public static DataGridView getDataGridViewWarn(Collection<?> rows) {
        return new DataGridView(ResultUtil.createWarn("warn"));
    }

    public static DataGridView getDataGridViewInfo(Collection<?> rows) {
        return new DataGridView(ResultUtil.createInfo("info"));
    }

    /**
     * @param filePath 文件路径
     * @param result   参数顺序: 文件真实名称、文件后缀、文件保存名、文件保存全名、文件完成保存路径(不包含项目路径)
     */
    public static UploadResultView getUploadResultViewSuccess(String filePath, String... result) {
        return new UploadResultView(ResultUtil.createSuccess("success"), result[0], result[1], result[2], result[3], result[4], filePath);
    }

    /**
     * @param result   参数顺序: 文件真实名称、文件后缀、文件保存名、文件保存全名、文件完成保存路径(不包含项目路径)
     * @param filePath 文件路径
     */
    public static UploadResultView getUploadResultViewSuccess(String[] result, String filePath) {
        return new UploadResultView(ResultUtil.createSuccess("success"), result[0], result[1], result[2], result[3], result[4], filePath);
    }

    public static ValidateResultView getValidateViewSuccess(boolean success) {
        return new ValidateResultView(ResultUtil.createSuccess("success"), success);
    }

    public static ModelView getModelViewSuccess(String message, Object record) {
        return new ModelView(ResultUtil.createSuccess(message), record);
    }

    public static ModelView getModelViewSuccess(String message, Map<?, ?> records) {
        return new ModelView(ResultUtil.createSuccess(message), records);
    }

    public static ModelView getModelViewSuccess(Object record) {
        return new ModelView(ResultUtil.createSuccess("success"), record);
    }

    public static ModelView getModelViewSuccess(Collection<?> rows) {
        return new ModelView(ResultUtil.createSuccess("success"), rows);
    }

    public static ModelView getModelViewSuccess(Collection<?> rows, Map<?, ?> records) {
        return new ModelView(ResultUtil.createSuccess("success"), rows, records);
    }

    public static ModelView getModelViewSuccess(Collection<?> rows, Map<?, ?> records, Limit limit) {
        return new ModelView(ResultUtil.createSuccess("success"), limit, rows, records);
    }

    public static ModelView getModelViewSuccess(Collection<?> rows, Object record) {
        return new ModelView(ResultUtil.createSuccess("success"), rows, record);
    }

    public static ModelView getModelViewSuccess(Collection<?> rows, String message) {
        return new ModelView(ResultUtil.createSuccess(message), rows);
    }

    public static ModelView getModelViewSuccess(Collection<?> rows, int total, int currPage, int pageSize) {
        return new ModelView(ResultUtil.createSuccess("success"), total, currPage, pageSize, rows);
    }

    public static ModelView getModelViewSuccess(Collection<?> rows, Limit limit) {
        return new ModelView(ResultUtil.createSuccess("success"), limit, rows);
    }

    public static ModelView getModelViewSuccess(Collection<?> rows, String message, int total, int currPage, int pageSize) {
        return new ModelView(ResultUtil.createSuccess(message), total, currPage, pageSize, rows);
    }

    public static ModelView getModelViewSuccess(Map<?, ?> records) {
        return new ModelView(ResultUtil.createSuccess("success"), records);
    }

    public static ModelView getModelViewSuccess(Map<?, ?> records, Limit limit) {
        return new ModelView(ResultUtil.createSuccess("success"), records, limit);
    }

    public static ModelView getModelViewFail(String message, Object record) {
        return new ModelView(ResultUtil.createFail(message), record);
    }

    public static ModelView getModelViewFail(Object record) {
        return new ModelView(ResultUtil.createFail("fail"), record);
    }

}
