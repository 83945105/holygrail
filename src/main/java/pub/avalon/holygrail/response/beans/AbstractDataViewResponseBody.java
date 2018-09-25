package pub.avalon.holygrail.response.beans;

import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;
import pub.avalon.holygrail.response.utils.ResultDetailUtil;
import pub.avalon.holygrail.response.views.DataView;

import java.util.Collection;
import java.util.Objects;

/**
 * @author 白超
 * @date 2018/9/25
 */
public abstract class AbstractDataViewResponseBody implements ResponseBodyAdvice<DataView> {

    @Override
    public boolean supports(MethodParameter returnType, Class converterType) {
        return Objects.requireNonNull(returnType.getMethod()).getReturnType().isAssignableFrom(DataView.class);
    }

    @Override
    public DataView beforeBodyWrite(DataView body, MethodParameter returnType, MediaType selectedContentType, Class selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        Collection<ResultDetail> resultDetails = ResultDetailUtil.getResultDetails();
        if (resultDetails != null) {
            ResultInfo resultInfo = body.getResultInfo();
            if (resultInfo instanceof ResultInfoRealization) {
                ((ResultInfoRealization) resultInfo).addAllResultDetails(resultDetails);
                ResultDetailUtil.remove();
            }
        }
        return body;
    }
}
