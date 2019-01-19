package pub.avalon.holygrail.function.beans;

import pub.avalon.beans.EnumMethods;

/**
 * 放置类型
 *
 * @author 白超
 * @date 2019/1/19
 */
public enum DropType implements EnumMethods {
    /**
     * 放置到目标元素前面
     */
    BEFORE,
    /**
     * 放置到目标元素后面
     */
    AFTER,
    /**
     * 放置到目标元素内部
     */
    INNER;

    @Override
    public Object getValue() {
        return this.name();
    }
}
