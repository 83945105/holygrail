package pub.avalon.holygrail.response.utils;

import com.alibaba.fastjson.parser.ParserConfig;
import com.alibaba.fastjson.util.TypeUtils;
import com.fasterxml.jackson.core.type.TypeReference;
import pub.avalon.beans.Limit;
import pub.avalon.holygrail.response.beans.ResultInfo;
import pub.avalon.holygrail.response.views.*;
import pub.avalon.holygrail.utils.JsonUtil;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * @author 白超
 */
public class DataViewUtil {

    private DataViewUtil() {
    }

    /**
     * 是否成功
     *
     * @param dataView 数据视图
     * @return
     */
    public static boolean isSuccess(DataView dataView) {
        if (dataView instanceof MessageView) {
            return dataView.getResultInfo().isSuccess();
        }
        if (dataView instanceof JsonView) {
            return dataView.getResultInfo().isSuccess();
        }
        ExceptionUtil.throwErrorException("不支持的DataView类型");
        return false;
    }

    /**
     * 是否失败
     *
     * @param dataView 数据视图
     * @return
     */
    public static boolean isFail(DataView dataView) {
        if (dataView instanceof MessageView) {
            return dataView.getResultInfo().isFail();
        }
        if (dataView instanceof JsonView) {
            return dataView.getResultInfo().isFail();
        }
        ExceptionUtil.throwErrorException("不支持的DataView类型");
        return false;
    }

    /**
     * 是否错误
     *
     * @param dataView 数据视图
     * @return
     */
    public static boolean isError(DataView dataView) {
        if (dataView instanceof MessageView) {
            return dataView.getResultInfo().isError();
        }
        if (dataView instanceof JsonView) {
            return dataView.getResultInfo().isError();
        }
        ExceptionUtil.throwErrorException("不支持的DataView类型");
        return false;
    }

    /**
     * 获取消息结果集
     *
     * @param dataView 数据视图
     * @return
     */
    public static ResultInfo getResultInfo(DataView dataView) {
        if (dataView instanceof MessageView) {
            return dataView.getResultInfo();
        }
        if (dataView instanceof JsonView) {
            return dataView.getResultInfo();
        }
        ExceptionUtil.throwErrorException("不支持的DataView类型");
        return null;
    }

    /**
     * 获取存储于record的对象
     * 一般存储者放入的是非Map对象
     *
     * @param dataView 数据视图
     * @return
     */
    public static Object getRecord(DataView dataView) {
        if (dataView instanceof ModelView) {
            return ((ModelView) dataView).getRecord();
        }
        if (dataView instanceof JsonView) {
            return ((JsonView) dataView).getRecord();
        }
        ExceptionUtil.throwErrorException("不支持的DataView类型");
        return null;
    }

    /**
     * 获取存储于record的对象
     * 一般存储者放入的是非Map对象
     *
     * @param dataView      数据视图
     * @param typeReference
     * @param <T>
     * @return
     */
    public static <T> T getRecord(DataView dataView, TypeReference<T> typeReference) {
        if (dataView instanceof ModelView) {
            Object record = ((ModelView) dataView).getRecord();
            if (record == null) {
                return null;
            }
            return JsonUtil.parseObject(JsonUtil.toJsonString(record), typeReference);
        }
        if (dataView instanceof JsonView) {
            return ((JsonView) dataView).getRecord(typeReference);
        }
        ExceptionUtil.throwErrorException("不支持的DataView类型");
        return null;
    }

    /**
     * 获取存储于record的对象并转为指定的类型
     * 一般存储者放入的是非Map对象
     *
     * @param dataView   数据视图
     * @param returnType
     * @param <T>
     * @return
     */
    public static <T> T getRecord(DataView dataView, Class<T> returnType) {
        if (dataView instanceof ModelView) {
            return TypeUtils.cast(((ModelView) dataView).getRecord(), returnType, ParserConfig.getGlobalInstance());
        }
        if (dataView instanceof JsonView) {
            return ((JsonView) dataView).getRecord(returnType);
        }
        ExceptionUtil.throwErrorException("不支持的DataView类型");
        return null;
    }

    /**
     * 获取存储于records的对象
     * 一般存储者放入的是Map对象
     *
     * @param dataView 数据视图
     * @return
     */
    public static Map<?, ?> getRecords(DataView dataView) {
        if (dataView instanceof ModelView) {
            return ((ModelView) dataView).getRecords();
        }
        if (dataView instanceof JsonView) {
            return ((JsonView) dataView).getRecords();
        }
        ExceptionUtil.throwErrorException("不支持的DataView类型");
        return null;
    }

    /**
     * 获取存储于records的对象并转为指定的类型
     * 一般存储者放入的是Map对象
     *
     * @param dataView      数据视图
     * @param typeReference
     * @param <T>
     * @return
     */
    public static <T> T getRecords(DataView dataView, TypeReference<T> typeReference) {
        if (dataView instanceof ModelView) {
            Map<?, ?> records = ((ModelView) dataView).getRecords();
            if (records == null) {
                return null;
            }
            return JsonUtil.parseObject(JsonUtil.toJsonString(records), typeReference);
        }
        if (dataView instanceof JsonView) {
            return ((JsonView) dataView).getRecords(typeReference);
        }
        ExceptionUtil.throwErrorException("不支持的DataView类型");
        return null;
    }

    /**
     * 获取存储于records的对象并转为指定的类型
     * 一般存储者放入的是Map对象
     *
     * @param dataView   数据视图
     * @param returnType
     * @param <T>
     * @return
     */
    public static <T> T getRecords(DataView dataView, Class<T> returnType) {
        if (dataView instanceof ModelView) {
            return TypeUtils.cast(((ModelView) dataView).getRecords(), returnType, ParserConfig.getGlobalInstance());
        }
        if (dataView instanceof JsonView) {
            return ((JsonView) dataView).getRecords(returnType);
        }
        ExceptionUtil.throwErrorException("不支持的DataView类型");
        return null;
    }

    /**
     * 获取存储于rows的对象
     * 一般存储者放入的是集合对象
     *
     * @param dataView 数据视图
     * @return
     */
    public static Collection<?> getRows(DataView dataView) {
        if (dataView instanceof LimitDataView) {
            return ((LimitDataView) dataView).getRows();
        }
        if (dataView instanceof JsonView) {
            return ((JsonView) dataView).getRows();
        }
        ExceptionUtil.throwErrorException("不支持的DataView类型");
        return null;
    }

    /**
     * 获取存储于rows的对象
     * 一般存储者放入的是集合对象
     *
     * @param dataView      数据视图
     * @param typeReference
     * @param <T>
     * @return
     */
    public static <T> T getRows(DataView dataView, TypeReference<T> typeReference) {
        if (dataView instanceof LimitDataView) {
            Collection<?> rows = ((LimitDataView) dataView).getRows();
            return JsonUtil.parseObject(JsonUtil.toJsonString(rows), typeReference);
        }
        if (dataView instanceof JsonView) {
            return ((JsonView) dataView).getRows(typeReference);
        }
        ExceptionUtil.throwErrorException("不支持的DataView类型");
        return null;
    }

    /**
     * 获取存储于rows的对象
     * 一般存储者放入的是集合对象
     *
     * @param dataView 数据视图
     * @param beanType
     * @param <T>
     * @return
     */
    public static <T> List<T> getRows(DataView dataView, Class<T> beanType) {
        if (dataView instanceof LimitDataView) {
            Collection<?> rows = ((LimitDataView) dataView).getRows();
            ArrayList<T> list = new ArrayList<>(rows.size());
            rows.forEach(row -> list.add(TypeUtils.cast(row, beanType, ParserConfig.getGlobalInstance())));
            return list;
        }
        if (dataView instanceof JsonView) {
            return ((JsonView) dataView).getRows(beanType);
        }
        ExceptionUtil.throwErrorException("不支持的DataView类型");
        return null;
    }

    /**
     * 获取存储于limit的对象
     * 一般存储者放入的是分页对象
     *
     * @param dataView 数据视图
     * @return
     */
    public static Limit getLimit(DataView dataView) {
        if (dataView instanceof LimitView) {
            return ((LimitView) dataView).getLimit();
        }
        if (dataView instanceof JsonView) {
            return ((JsonView) dataView).getLimit();
        }
        ExceptionUtil.throwErrorException("不支持的DataView类型");
        return null;
    }

    /**
     * 获取存储于limit的对象
     * 一般存储者放入的是分页对象
     *
     * @param dataView      数据视图
     * @param typeReference
     * @param <T>
     * @return
     */
    public static <T extends Limit> T getLimit(DataView dataView, TypeReference<T> typeReference) {
        if (dataView instanceof LimitView) {
            Limit limit = ((LimitView) dataView).getLimit();
            if (limit == null) {
                return null;
            }
            return JsonUtil.parseObject(JsonUtil.toJsonString(limit), typeReference);
        }
        if (dataView instanceof JsonView) {
            return ((JsonView) dataView).getLimit(typeReference);
        }
        ExceptionUtil.throwErrorException("不支持的DataView类型");
        return null;
    }

    /**
     * 获取存储于limit的对象
     * 一般存储者放入的是分页对象
     *
     * @param dataView   数据视图
     * @param returnType
     * @param <T>
     * @return
     */
    public static <T extends Limit> T getLimit(DataView dataView, Class<T> returnType) {
        if (dataView instanceof LimitView) {
            return TypeUtils.cast(((LimitView) dataView).getLimit(), returnType, ParserConfig.getGlobalInstance());
        }
        if (dataView instanceof JsonView) {
            return ((JsonView) dataView).getLimit(returnType);
        }
        ExceptionUtil.throwErrorException("不支持的DataView类型");
        return null;
    }

    public static MessageView getMessageViewSuccess(String message) {
        return new MessageView(0, ResultUtil.createSuccess(message));
    }

    public static MessageView getMessageViewSuccess(Integer code, String message) {
        return new MessageView(code, ResultUtil.createSuccess(message));
    }

    public static MessageView getMessageViewFail(String message) {
        return new MessageView(0, ResultUtil.createFail(message));
    }

    public static MessageView getMessageViewFail(Integer code, String message) {
        return new MessageView(code, ResultUtil.createFail(message));
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

    public static ModelView getModelViewSuccess(Map<?, ?> records, Limit limit) {
        return new ModelView(0, ResultUtil.createSuccess("success"), records, limit);
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

    public static ModelView getModelViewSuccess(Integer code, String message, Collection<?> rows, Map<?, ?>
            records) {
        return new ModelView(code, ResultUtil.createSuccess(message), rows, records);
    }


    public static ModelView getModelViewSuccess(Collection<?> rows, Map<?, ?> records, Limit limit) {
        return new ModelView(0, ResultUtil.createSuccess("success"), limit, rows, records);
    }

    public static ModelView getModelViewSuccess(String message, Collection<?> rows, Map<?, ?> records, Limit
            limit) {
        return new ModelView(0, ResultUtil.createSuccess(message), limit, rows, records);
    }

    public static ModelView getModelViewSuccess(Integer code, String message, Collection<?> rows, Map<?, ?>
            records, Limit limit) {
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
