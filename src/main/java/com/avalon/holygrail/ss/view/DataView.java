package com.avalon.holygrail.ss.view;

import com.avalon.holygrail.ss.plugins.JacksonDataViewDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.io.Serializable;

/**
 * 数据视图
 * 所有SpringMVC的Controller AJAX返回此接口
 */
@JsonDeserialize(using = JacksonDataViewDeserializer.class)
public interface DataView extends Serializable {

}
