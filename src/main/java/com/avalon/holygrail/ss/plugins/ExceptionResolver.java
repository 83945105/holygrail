package com.avalon.holygrail.ss.plugins;

import com.avalon.holygrail.ss.exception.ResultException;
import com.avalon.holygrail.ss.norm.ResultCode;
import com.avalon.holygrail.ss.norm.ResultInfo;
import com.avalon.holygrail.ss.util.ResultUtil;
import com.avalon.holygrail.ss.view.ExceptionView;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpOutputMessage;
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
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Method;
import java.lang.reflect.UndeclaredThrowableException;
import java.util.Map;

/**
 * 统一异常处理器
 */
public class ExceptionResolver extends DefaultHandlerExceptionResolver {

    private Map<String, String> viewMap;

    //json转换器
    private HttpMessageConverter<ExceptionView> jsonMessageConverter;

    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler,
                                         Exception ex) {

        if (!(handler instanceof HandlerMethod)) {
            return super.resolveException(request, response, handler, ex);
        }

        Method method = ((HandlerMethod) handler).getMethod();

        ResponseBody responseBody = AnnotationUtils.findAnnotation(method, ResponseBody.class);

        if (responseBody != null) {
            return this.resolveJsonException(request, response, handler, ex);
        }

        ModelAndView modelAndView = new ModelAndView();

        ExceptionView exceptionView = resolveException(ex);

        int type = exceptionView.getResultInfo().getType();
        if (type == ResultCode.NEED_LOGIN_CODE) {
            modelAndView.setViewName(viewMap.get("loginView"));//登录页面
            return modelAndView;
        } else if (type == ResultCode.NO_AUTHORITY_CODE) {//无权
            modelAndView.setViewName(viewMap.get("noAuthorityView"));
            modelAndView.addObject("resultInfo", exceptionView.getResultInfo());
            return modelAndView;
        } else if(type == ResultCode.NOT_FOUND) {//404
            modelAndView.setViewName(viewMap.get("404View"));
            modelAndView.addObject("resultInfo", exceptionView.getResultInfo());
            return modelAndView;
        }

        request.setAttribute("exceptionView", exceptionView.getResultInfo());
        modelAndView.addObject("exceptionView", exceptionView.getResultInfo());
        modelAndView.setViewName(viewMap.get("errorView"));

        return modelAndView;
    }

    private ExceptionView resolveException(Exception ex) {
        ResultInfo resultInfo;
        if (ex instanceof UndeclaredThrowableException) {
            ex = (Exception) ((UndeclaredThrowableException) ex).getUndeclaredThrowable();
        }
        if (ex instanceof ResultException) {
            resultInfo = ((ResultException) ex).getResultInfo();
            if (resultInfo.isError()) {
                ex.printStackTrace();
                StringWriter sw = new StringWriter();
                ex.printStackTrace(new PrintWriter(sw));
                resultInfo.setExceptionMessage(sw.toString());
            }
        } else if (ex instanceof DataIntegrityViolationException) {
            resultInfo = ResultUtil.createFail(800, null);
        } else {
            ex.printStackTrace();
            resultInfo = ResultUtil.createError("未知错误");
            StringWriter sw = new StringWriter();
            ex.printStackTrace(new PrintWriter(sw));
            resultInfo.setExceptionMessage(sw.toString());
        }
        return new ExceptionView(resultInfo);
    }

    private ModelAndView resolveJsonException(HttpServletRequest request,
                                              HttpServletResponse response, Object handler, Exception ex) {

        ExceptionView exceptionView = this.resolveException(ex);

        HttpOutputMessage outputMessage = new ServletServerHttpResponse(response);

        try {
            this.jsonMessageConverter.write(exceptionView, MediaType.APPLICATION_JSON, outputMessage);
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

    public Map<String, String> getViewMap() {
        return viewMap;
    }

    public void setViewMap(Map<String, String> viewMap) {
        this.viewMap = viewMap;
    }

}
