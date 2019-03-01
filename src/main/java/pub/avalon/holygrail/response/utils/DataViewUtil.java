package pub.avalon.holygrail.response.utils;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.ParserConfig;
import com.alibaba.fastjson.util.TypeUtils;
import com.fasterxml.jackson.core.type.TypeReference;
import pub.avalon.beans.DataBaseType;
import pub.avalon.beans.Limit;
import pub.avalon.beans.Pagination;
import pub.avalon.holygrail.response.views.*;
import pub.avalon.holygrail.utils.JsonUtil;

import java.util.*;
import java.util.function.BiFunction;

/**
 * @author 白超
 */
public class DataViewUtil {

    private DataViewUtil() {
    }

    /**
     * 是否成功
     *
     * @param dataView
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
     * 获取单个对象
     *
     * @param dataView
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
     * 获取单个对象
     *
     * @param returnType 返回值类型
     * @param dataView
     * @param <T>
     * @return
     */
    public static <T> T getRecord(Class<T> returnType, DataView dataView) {
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
     * 获取Map对象
     *
     * @param dataView
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
     * 获取Map对象
     *
     * @param returnType 返回值类型
     * @param dataView
     * @param <T>
     * @return
     */
    public static <T> T getRecords(Class<T> returnType, DataView dataView) {
        if (dataView instanceof ModelView) {
            return TypeUtils.cast(((ModelView) dataView).getRecords(), returnType, ParserConfig.getGlobalInstance());
        }
        if (dataView instanceof JsonView) {
            return ((JsonView) dataView).getRecords(returnType);
        }
        ExceptionUtil.throwErrorException("不支持的DataView类型");
        return null;
    }

    public static void main(String[] args) {
        Map<String, Object> records = new HashMap<>();
        records.put("key", 123);
        DataView dataView = DataViewUtil.getModelViewSuccess(records);
        String json = JSONObject.toJSONString(dataView);
        System.out.println(json);


    }

    public static Collection getRows(DataView dataView) {
        if (dataView instanceof LimitDataView) {
            return ((LimitDataView) dataView).getRows();
        }
        if (dataView instanceof JsonView) {
            return ((JsonView) dataView).getRows();
        }
        ExceptionUtil.throwErrorException("不支持的DataView类型");
        return null;
    }

    public static <T> Collection<T> getRows(Class<T> returnType, DataView dataView) {
        if (dataView instanceof LimitDataView) {
            ArrayList<T> list = new ArrayList<>();
            Collection<?> rows = ((LimitDataView) dataView).getRows();
            if (rows == null) {
                return null;
            }
            rows.forEach(obj -> list.add(TypeUtils.cast(obj, returnType, ParserConfig.getGlobalInstance())));
            return list;
        }
        if (dataView instanceof JsonView) {
            return ((JsonView) dataView).getRows(returnType);
        }
        ExceptionUtil.throwErrorException("不支持的DataView类型");
        return null;
    }

    public static void getRows(DataView dataView, BiFunction<Object, Long, Boolean> callback) {
        Collection<?> rows = null;
        if (dataView instanceof LimitDataView) {
            rows = ((LimitDataView) dataView).getRows();
        } else if (dataView instanceof JsonView) {
            rows = ((JsonView) dataView).getRows();
        } else {
            ExceptionUtil.throwErrorException("不支持的DataView类型");
        }
        if (rows == null) {
            return;
        }
        Iterator iterator = rows.iterator();
        long i = 0;
        while (iterator.hasNext()) {
            if (!callback.apply(iterator.next(), i++)) {
                break;
            }
        }
    }

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

    public static <T extends Limit> T getLimit(Class<T> returnType, DataView dataView) {
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
