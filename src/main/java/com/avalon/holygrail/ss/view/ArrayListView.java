package com.avalon.holygrail.ss.view;

import com.avalon.holygrail.ss.norm.ResultInfo;
import com.avalon.holygrail.ss.util.ResultUtil;

import java.util.ArrayList;
import java.util.Collection;

/**
 * 集合视图
 * @param <E>
 */
public class ArrayListView<E> extends ArrayList<E> implements DataView {

	public ArrayListView() {
		super();
	}

	public ArrayListView(Collection<? extends E> c) {
		super(c);
	}

	public ArrayListView(int initialCapacity) {
		super(initialCapacity);
	}

	@Override
	public ResultInfo getResultInfo() {
		return ResultUtil.createSuccess("ArrayListView is success");
	}
}
