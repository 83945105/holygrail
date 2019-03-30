package pub.avalon.holygrail.response.utils;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.ParserConfig;
import com.alibaba.fastjson.util.TypeUtils;
import com.fasterxml.jackson.core.type.TypeReference;
import pub.avalon.beans.DataBaseType;
import pub.avalon.beans.Limit;
import pub.avalon.beans.Pagination;
import pub.avalon.holygrail.response.beans.ResultInfoRealization;
import pub.avalon.holygrail.response.beans.User;
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
     * @param dataView 数据视图
     * @return true - 成功 / false - 不成功
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
     * 获取存储于record的对象
     * 一般存储者放入的是非Map对象
     *
     * @return
     */
    public static Map<String, Object> getRecord(DataView dataView) {
        if (dataView instanceof ModelView) {
            Object record = ((ModelView) dataView).getRecord();
            if (record == null) {
                return null;
            }
            return JsonUtil.parseObject(JsonUtil.toJsonString(record), new TypeReference<Map<String, Object>>() {
            });
        }
        if (dataView instanceof JsonView) {
            return ((JsonView) dataView).getRecord();
        }
        if (dataView instanceof MessageView) {
            ExceptionUtil.throwErrorException("无法从消息视图MessageView中获取record属性");
            return null;
        }
        ExceptionUtil.throwErrorException("不支持的DataView类型");
        return null;
    }

    /**
     * 获取存储于record的对象
     * 一般存储者放入的是非Map对象
     *
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
        if (dataView instanceof MessageView) {
            ExceptionUtil.throwErrorException("无法从消息视图MessageView中获取record属性");
            return null;
        }
        ExceptionUtil.throwErrorException("不支持的DataView类型");
        return null;
    }

    /**
     * 获取存储于record的对象并转为指定的类型
     * 一般存储者放入的是非Map对象
     *
     * @param returnType
     * @param <T>
     * @return
     */
    public static <T> T getRecord(DataView dataView, Class<T> returnType) {
        return null;
    }

    /**
     * 获取存储于records的对象
     * 一般存储者放入的是Map对象
     *
     * @return
     */
    public static Map<String, Object> getRecords() {
        return null;
    }

    /**
     * 获取存储于records的对象并转为指定的类型
     * 一般存储者放入的是Map对象
     *
     * @param typeReference
     * @param <T>
     * @return
     */
    public static <T> T getRecords(TypeReference<T> typeReference) {
        return null;
    }

    /**
     * 获取存储于records的对象并转为指定的类型
     * 一般存储者放入的是Map对象
     *
     * @param returnType
     * @param <T>
     * @return
     */
    public static <T> T getRecords(Class<T> returnType) {
        return null;
    }

    /**
     * 获取存储于rows的对象
     * 一般存储者放入的是集合对象
     *
     * @return
     */
    public static List<Map<String, Object>> getRows() {
        return null;
    }

    /**
     * 获取存储于rows的对象
     * 一般存储者放入的是集合对象
     *
     * @param typeReference
     * @param <T>
     * @return
     */
    public static <T> T getRows(TypeReference<T> typeReference) {
        return null;
    }

    /**
     * 获取存储于rows的对象
     * 一般存储者放入的是集合对象
     *
     * @param beanType
     * @param <T>
     * @return
     */
    public static <T> List<T> getRows(Class<T> beanType) {
        return null;
    }

    /**
     * 获取存储于limit的对象
     * 一般存储者放入的是分页对象
     *
     * @return
     */
    public static Limit getLimit() {
        return null;
    }

    /**
     * 获取存储于limit的对象
     * 一般存储者放入的是分页对象
     *
     * @param typeReference
     * @param <T>
     * @return
     */
    public static <T extends Limit> T getLimit(TypeReference<T> typeReference) {
        return null;
    }

    /**
     * 获取存储于limit的对象
     * 一般存储者放入的是分页对象
     *
     * @param returnType
     * @param <T>
     * @return
     */
    public static <T extends Limit> T getLimit(Class<T> returnType) {
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
//            return ((AbstractJsonView) dataView).getRows(returnType);
            return null;
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

    public static void main(String[] args) {
        User user = new User();
        user.setId("666");

        Map map = TypeUtils.cast(user, LinkedHashMap.class, ParserConfig.getGlobalInstance());
        System.out.println(map);

        if (false) {
            return;
        }

        List<User> list = new ArrayList<>();
        list.add(user);
        Pagination pagination = new Pagination(DataBaseType.MYSQL, 100, 1, 20);
        DataView dataView = DataViewUtil.getModelViewSuccess(10, "666", list, pagination);
        ResultInfoRealization rr = (ResultInfoRealization) dataView.getResultInfo();
        rr.addResultDetail(() -> ResultUtil.createError("666"));
        String json = JsonUtil.toJsonString(dataView);
        DataView dv = JsonUtil.parseObject(json, new TypeReference<DataView>() {
        });
        long begin = System.nanoTime();

        if (dv instanceof JacksonView) {


        }

        long use = System.nanoTime() - begin;
        System.out.println(use + " = " + use / (1000 * 1000));
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
