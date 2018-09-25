package pub.avalon.holygrail.response.utils;

import pub.avalon.holygrail.response.beans.ResultDetail;
import pub.avalon.holygrail.response.beans.ResultDetailRealization;

import java.util.ArrayList;
import java.util.Collection;

/**
 * @author 白超
 * @date 2018/9/25
 */
public class ResultDetailUtil {

    private final static ThreadLocal<Collection<ResultDetail>> THREAD_LOCAL = new ThreadLocal<>();

    /**
     * 添加结果集明细
     *
     * @param resultDetail 结果明细
     */
    public static void addDetail(ResultDetail resultDetail) {
        Collection<ResultDetail> resultDetails = THREAD_LOCAL.get();
        if (resultDetails == null) {
            resultDetails = new ArrayList<>();
            THREAD_LOCAL.set(resultDetails);
        }
        resultDetails.add(resultDetail);
    }

    /**
     * 获取所有结果集明细
     *
     * @return
     */
    public static Collection<ResultDetail> getResultDetails() {
        return THREAD_LOCAL.get();
    }

    /**
     * 移除
     */
    public static void remove() {
        THREAD_LOCAL.remove();
    }

    public static ResultDetail createSuccess(String message) {
        return new ResultDetailRealization(ResultUtil.createSuccess(message));
    }

    public static ResultDetail createSuccess(int messageCode, Object[] params) {
        return new ResultDetailRealization(ResultUtil.createSuccess(messageCode, params));
    }

    public static ResultDetail createFail(String message) {
        return new ResultDetailRealization(ResultUtil.createFail(message));
    }

    public static ResultDetail createFail(int messageCode, Object[] params) {
        return new ResultDetailRealization(ResultUtil.createFail(messageCode, params));
    }

}
