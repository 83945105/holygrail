package com.avalon.holygrail.ss.plugins;

import com.avalon.holygrail.ss.filter.ExceptionFilterChain;
import com.avalon.holygrail.ss.util.ResultUtil;
import com.avalon.holygrail.ss.view.ExceptionView;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.DefaultHandlerExceptionResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;

/**
 * 统一异常处理器
 */
public class ExceptionResolver extends DefaultHandlerExceptionResolver {

    private ExceptionFilterChain filterChain;

    private String defaultErrorMessage = "未知错误";

    //json转换器
    private HttpMessageConverter<ExceptionView> jsonMessageConverter;

    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler,
                                         Exception ex) {

        if (!(handler instanceof HandlerMethod)) {
            return super.resolveException(request, response, handler, ex);
        }

        ExceptionView exceptionView = new ExceptionView(ResultUtil.createError(this.defaultErrorMessage));

        if (this.filterChain != null) {
            try {
                this.filterChain.doFilter(ex, exceptionView);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        Method method = ((HandlerMethod) handler).getMethod();

        ResponseBody responseBody = AnnotationUtils.findAnnotation(method, ResponseBody.class);

        if (responseBody != null) {
            return this.resolveJsonException(response, exceptionView);
        }

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName(exceptionView.getJumpUrl());
        modelAndView.addObject("error", exceptionView);
        return modelAndView;
    }

    private ModelAndView resolveJsonException(HttpServletResponse response, ExceptionView exceptionView) {
        try {
            this.jsonMessageConverter.write(exceptionView, MediaType.APPLICATION_JSON, new ServletServerHttpResponse(response));
        } catch (HttpMessageNotWritableException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ModelAndView();
    }

    public HttpMessageConverter<ExceptionView> getJsonMessageConverter() {
        return jsonMessageConverter;
    }

    public void setJsonMessageConverter(HttpMessageConverter<ExceptionView> jsonMessageConverter) {
        this.jsonMessageConverter = jsonMessageConverter;
    }

}
