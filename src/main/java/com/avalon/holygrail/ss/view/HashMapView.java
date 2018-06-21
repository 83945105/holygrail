package com.avalon.holygrail.ss.view;

import com.avalon.holygrail.ss.norm.ResultInfo;
import com.avalon.holygrail.ss.util.ResultUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * Map视图
 *
 * @param <K>
 * @param <V>
 */
public class HashMapView<K, V> extends HashMap<K, V> implements DataView {

    public HashMapView(int initialCapacity, float loadFactor) {
        super(initialCapacity, loadFactor);
    }

    public HashMapView(int initialCapacity) {
        super(initialCapacity);
    }

    public HashMapView() {
    }

    public HashMapView(Map<? extends K, ? extends V> m) {
        super(m);
    }

    @Override
    public ResultInfo getResultInfo() {
        Object resultInfo = this.get("resultInfo");
        if (resultInfo == null) {
            return null;
        }
        return ResultUtil.createSuccess("HashMapView is success");
    }
}
