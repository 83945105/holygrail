package com.avalon.holygrail.excel.norm;

/**
 * 单元格样式
 * Created by 白超 on 2018/1/17.
 */
public interface CellStyle {

    /**
     * 水平对齐方式
     */
    enum H_AlignType {
        /**
         * 常规对齐
         */
        GENERAL((short) 0x0),
        /**
         * 靠左(缩进)
         */
        LEFT((short) 0x1),
        /**
         * 居中
         */
        CENTER((short) 0x2),
        /**
         * 靠右(缩进)
         */
        RIGHT((short) 0x3),
        /**
         * 填充
         */
        FILL((short) 0x4),
        /**
         * 两端对齐
         */
        JUSTIFY((short) 0x5),
        /**
         * 跨列居中
         */
        CENTER_SELECTION((short) 0x6),
        /**
         * 分散对齐
         */
        DISTRIBUTED((short) 0x7);

        public short value;

        H_AlignType(short value) {
            this.value = value;
        }

        /**
         * 根据name获取水平枚举类型
         */
        public static H_AlignType getHAlignByName(String name) {
            for (H_AlignType alignType : H_AlignType.values()) {
                if (alignType.name().equalsIgnoreCase(name)) {
                    return alignType;
                }
            }
            throw new RuntimeException("hAlign值不正确:" + name);
        }

        /**
         * 根据value获取水平枚举类型
         */
        public static H_AlignType getHAlignByValue(short value) {
            for (H_AlignType alignType : H_AlignType.values()) {
                if (alignType.value == value) {
                    return alignType;
                }
            }
            throw new RuntimeException("hAlign值不正确:" + value);
        }
    }

    /**
     * 垂直对齐方式
     */
    enum V_AlignType {
        /**
         * 靠上
         */
        TOP((short) 0x0),
        /**
         * 居中
         */
        CENTER((short) 0x1),
        /**
         * 靠下
         */
        BOTTOM((short) 0x2),
        /**
         * 两端对齐
         */
        JUSTIFY((short) 0x3),
        /**
         * 分散对齐
         */
        DISTRIBUTED((short) 0x4);

        public short value;

        V_AlignType(short value) {
            this.value = value;
        }

        /**
         * 根据name获取垂直枚举类型
         */
        public static V_AlignType getVAlignByName(String name) {
            for (V_AlignType alignType : V_AlignType.values()) {
                if (alignType.name().equalsIgnoreCase(name)) {
                    return alignType;
                }
            }
            throw new RuntimeException("vAlign值不正确:" + name);
        }

        /**
         * 根据value获取垂直枚举类型
         */
        public static V_AlignType getVAlignByValue(short value) {
            for (V_AlignType alignType : V_AlignType.values()) {
                if (alignType.value == value) {
                    return alignType;
                }
            }
            throw new RuntimeException("vAlign值不正确:" + value);
        }
    }

    /**
     * 获取单元格水平对齐方式
     */
    H_AlignType getHAlign();

    /**
     * 设置单元格水平对齐方式
     */
    void setHAlign(String HAlign);

    /**
     * 设置单元格水平对齐方式
     */
    default void setHAlign(H_AlignType hAlignType) {
        this.setHAlign(hAlignType.name());
    }

    /**
     * 设置单元格水平对齐方式
     */
    default void setHAlign(short value) {
        this.setHAlign(H_AlignType.getHAlignByValue(value));
    }

    /**
     * 获取单元格垂直对齐方式
     */
    V_AlignType getVAlign();

    /**
     * 设置单元格垂直对齐方式
     */
    void setVAlign(String VAlign);

    /**
     * 设置单元格垂直对齐方式
     */
    default void setVAlign(V_AlignType vAlignType) {
        this.setVAlign(vAlignType.name());
    }

    /**
     * 设置单元格垂直对齐方式
     */
    default void setVAlign(short value) {
        this.setVAlign(V_AlignType.getVAlignByValue(value));
    }

    /**
     * 边框样式
     */
    enum BorderStyle {
        NONE((short) 0),
        THIN((short) 1),
        MEDIUM((short) 2),
        DASHED((short) 3),
        DOTTED((short) 4),
        THICK((short) 5),
        DOUBLE((short) 6),
        HAIR((short) 7),
        MEDIUM_DASHED((short) 8),
        DASH_DOT((short) 9),
        MEDIUM_DASH_DOT((short) 10),
        DASH_DOT_DOT((short) 11),
        MEDIUM_DASH_DOT_DOTC((short) 12),
        SLANTED_DASH_DOT((short) 13);

        public short value;

        BorderStyle(short value) {
            this.value = value;
        }

        /**
         * 根据name获取边框样式
         */
        public static BorderStyle getBorderStyleByName(String name) {
            for (BorderStyle borderStyle : BorderStyle.values()) {
                if (borderStyle.name().equalsIgnoreCase(name)) {
                    return borderStyle;
                }
            }
            throw new RuntimeException("borderStyle值不正确:" + name);
        }

        /**
         * 根据value获取边框样式
         */
        public static BorderStyle getBorderStyleByValue(short value) {
            for (BorderStyle borderStyle : BorderStyle.values()) {
                if (borderStyle.value == value) {
                    return borderStyle;
                }
            }
            throw new RuntimeException("borderStyle值不正确:" + value);
        }
    }

    /**
     * 获取左边框样式
     */
    BorderStyle getBorderLeft();

    /**
     * 设置左边框样式
     */
    void setBorderLeft(String borderLeft);

    /**
     * 设置左边框样式
     */
    default void setBorderLeft(BorderStyle borderStyle) {
        this.setBorderLeft(borderStyle.name());
    }

    /**
     * 设置左边框样式
     */
    default void setBorderLeft(short value) {
        this.setBorderLeft(BorderStyle.getBorderStyleByValue(value));
    }

    /**
     * 获取上边框样式
     */
    BorderStyle getBorderTop();

    /**
     * 设置上边框样式
     */
    void setBorderTop(String borderTop);

    /**
     * 设置上边框样式
     */
    default void setBorderTop(BorderStyle borderStyle) {
        this.setBorderTop(borderStyle.name());
    }

    /**
     * 设置上边框样式
     */
    default void setBorderTop(short value) {
        this.setBorderTop(BorderStyle.getBorderStyleByValue(value));
    }

    /**
     * 获取右边框样式
     */
    BorderStyle getBorderRight();

    /**
     * 设置右边框样式
     */
    void setBorderRight(String borderRight);

    /**
     * 设置右边框样式
     */
    default void setBorderRight(BorderStyle borderStyle) {
        this.setBorderRight(borderStyle.name());
    }

    /**
     * 设置右边框样式
     */
    default void setBorderRight(short value) {
        this.setBorderRight(BorderStyle.getBorderStyleByValue(value));
    }

    /**
     * 获取下边框样式
     */
    BorderStyle getBorderBottom();

    /**
     * 设置下边框样式
     */
    void setBorderBottom(String borderBottom);

    /**
     * 设置下边框样式
     */
    default void setBorderBottom(BorderStyle borderStyle) {
        this.setBorderBottom(borderStyle.name());
    }

    /**
     * 设置下边框样式
     */
    default void setBorderBottom(short value) {
        this.setBorderBottom(BorderStyle.getBorderStyleByValue(value));
    }

    /**
     * 获取边框样式
     *
     * @return 左边框, 上边框, 右边框, 下边框
     */
    BorderStyle[] getBorder();

    /**
     * 设置四个边框样式,以","分隔,顺序为左上右下
     */
    default void setBorder(String border) {
        String[] borders = border.split(",");
        this.setBorderLeft(borders[0]);
        this.setBorderTop(borders[1]);
        this.setBorderRight(borders[2]);
        this.setBorderBottom(borders[3]);
    }

    /**
     * 设置四个边框样式,顺序为左上右下
     */
    default void setBorder(BorderStyle[] borderStyles) {
        StringBuilder sb = new StringBuilder();
        for (BorderStyle borderStyle : borderStyles) {
            sb.append(",").append(borderStyle.name());
        }
        this.setBorder(sb.substring(1));
    }

    /**
     * 设置四个边框样式,顺序为左上右下
     */
    default void setBorder(short[] values) {
        this.setBorderLeft(values[0]);
        this.setBorderTop(values[1]);
        this.setBorderRight(values[2]);
        this.setBorderBottom(values[3]);
    }

    /**
     * 拷贝样式
     *
     * @param target 目标单元格
     */
    default void copyCellStyleByName(CellStyle target) {
        target.setHAlign(getHAlign().name());
        target.setVAlign(getVAlign().name());
        target.setBorderLeft(getBorderLeft().name());
        target.setBorderTop(getBorderTop().name());
        target.setBorderRight(getBorderRight().name());
        target.setBorderBottom(getBorderBottom().name());
    }

    /**
     * 拷贝样式
     *
     * @param target 目标单元格
     */
    default void copyCellStyleByValue(CellStyle target) {
        target.setHAlign(getHAlign().value);
        target.setVAlign(getVAlign().value);
        target.setBorderLeft(getBorderLeft().value);
        target.setBorderTop(getBorderTop().value);
        target.setBorderRight(getBorderRight().value);
        target.setBorderBottom(getBorderBottom().value);
    }
}
