package pub.avalon.holygrail.response.views;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import pub.avalon.holygrail.response.beans.ResultInfo;
import pub.avalon.holygrail.response.plugins.DataViewDeserializer;

/**
 * 数据视图
 * 所有SpringMVC的Controller AJAX返回此接口
 *
 * @author 白超
 */
@JsonDeserialize(using = DataViewDeserializer.class)
public interface DataView {

    /**
     * 获取结果集
     *
     * @return
     */
    ResultInfo getResultInfo();


}
