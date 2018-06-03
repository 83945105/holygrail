package com.avalon.holygrail.ss.view;

import java.util.Map;

/**
 * Created by 白超 on 2018/6/3.
 */
public class JacksonView extends HashMapView {

    public JacksonView(int initialCapacity, float loadFactor) {
        super(initialCapacity, loadFactor);
    }

    public JacksonView(int initialCapacity) {
        super(initialCapacity);
    }

    public JacksonView() {
    }

    public JacksonView(Map m) {
        super(m);
    }
}
