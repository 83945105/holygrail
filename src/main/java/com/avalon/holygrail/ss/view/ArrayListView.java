package com.avalon.holygrail.ss.view;

import java.util.ArrayList;
import java.util.Collection;

/**
 * 集合视图
 * @param <E>
 */
public class ArrayListView<E> extends ArrayList<E> implements DataView {

	/**  
	 * @Fields serialVersionUID : 序列化标识符
	 */ 
	private static final long serialVersionUID = -6311887064693523630L;

	public ArrayListView() {
		super();
	}

	public ArrayListView(Collection<? extends E> c) {
		super(c);
	}

	public ArrayListView(int initialCapacity) {
		super(initialCapacity);
	}

}
