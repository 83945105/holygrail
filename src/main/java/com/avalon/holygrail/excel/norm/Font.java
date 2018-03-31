package com.avalon.holygrail.excel.norm;

/**
 * 字体
 * Created by 白超 on 2018/3/27.
 */
public interface Font {

    /**
     * 字体颜色
     */
    enum FontColor {

        BLACK((short) 0x8), BROWN((short) 0x3c), OLIVE_GREEN((short) 0x3b), DARK_GREEN((short) 0x3a),

        DARK_TEAL((short) 0x38), DARK_BLUE((short) 0x12), INDIGO((short) 0x3e), GREY_80_PERCENT((short) 0x3f),

        DARK_BLUE2((short) 0x20),

        ORANGE((short) 0x35), DARK_YELLOW((short) 0x13), GREEN((short) 0x11), TEAL((short) 0x15), BLUE((short) 0xc),

        TEAL2((short) 0x26), BLUE2((short) 0x27),

        BLUE_GREY((short) 0x36), GREY_50_PERCENT((short) 0x17), RED((short) 0xa), LIGHT_ORANGE((short) 0x34), LIME((short) 0x32),

        SEA_GREEN((short) 0x39), AQUA((short) 0x31), LIGHT_BLUE((short) 0x30), VIOLET((short) 0x14), GREY_40_PERCENT((short) 0x37),

        VIOLET2((short) 0x24),

        PINK((short) 0xe), GOLD((short) 0x33), YELLOW((short) 0xd), BRIGHT_GREEN((short) 0xb), TURQUOISE((short) 0xf),

        PINK2((short) 0x21), YELLOW2((short) 0x22), BRIGHT_GREEN2((short) 0x23), TURQUOISE2((short) 0x23),

        DARK_RED((short) 0x10), SKY_BLUE((short) 0x28), PLUM((short) 0x3d), GREY_25_PERCENT((short) 0x16), ROSE((short) 0x2d),

        DARK_RED2((short) 0x25), PLUM2((short) 0x19),

        LIGHT_YELLOW((short) 0x2b), LIGHT_GREEN((short) 0x2a), LIGHT_TURQUOISE((short) 0x29), PALE_BLUE((short) 0x2c),

        LIGHT_GREEN2((short) 0x1b),

        LAVENDER((short) 0x2e), WHITE((short) 0x9), CORNFLOWER_BLUE((short) 0x18), LEMON_CHIFFON((short) 0x1a),

        MAROON((short) 0x19), ORCHID((short) 0x1c), CORAL((short) 0x1d), ROYAL_BLUE((short) 0x1e),

        LIGHT_CORNFLOWER_BLUE((short) 0x1f), TAN((short) 0x2f);

        public short value;

        FontColor(short value) {
            this.value = value;
        }
    }

    /**
     * 设置字体颜色
     *
     * @param color
     */
    void setColor(short color);

    /**
     * 设置字体颜色
     * @param fontColor
     */
    default void setColor(FontColor fontColor) {
        this.setColor(fontColor.value);
    }

    /**
     * 获取字体颜色
     *
     * @return
     */
    short getColor();

    /**
     * 设置是否有删除线
     *
     * @param strikeout
     */
    void setStrikeout(boolean strikeout);

    /**
     * 获取是否有删除线
     *
     * @return
     */
    boolean getStrikeout();

    /**
     * 设置是否斜体
     *
     * @param italic
     */
    void setItalic(boolean italic);

    /**
     * 获取是否斜体
     *
     * @return
     */
    boolean getItalic();

    /**
     * 设置字体大小
     *
     * @param size
     */
    void setFontHeightInPoints(short size);

    /**
     * 获取字体大小
     *
     * @return
     */
    short getFontHeightInPoints();

    /**
     * 设置字体名称
     *
     * @param name
     */
    void setFontName(String name);

    /**
     * 获取字体名称
     *
     * @return
     */
    String getFontName();

    /**
     * 设置是否粗体
     *
     * @param boldWeight
     */
    void setBoldWeight(boolean boldWeight);

    /**
     * 获取是否粗体
     *
     * @return
     */
    boolean getBoldWeight();

    /**
     * 下划线
     */
    enum UnderLine {
        SINGLE(1),
        DOUBLE(2),
        SINGLE_ACCOUNTING(3),
        DOUBLE_ACCOUNTING(4),
        NONE(5);

        public int value;

        UnderLine(int value) {
            this.value = value;
        }
    }

    /**
     * 设置下划线
     *
     * @param underLine
     */
    void setUnderLine(UnderLine underLine);

    /**
     * 获取下划线
     *
     * @return
     */
    UnderLine getUnderLine();

    /**
     * 获取字体属性
     *
     * @param source 数据源
     */
    default void getFontByName(Font source) {
        this.setColor(source.getColor());
        this.setStrikeout(source.getStrikeout());
        this.setItalic(source.getItalic());
        this.setFontHeightInPoints(source.getFontHeightInPoints());
        this.setFontName(source.getFontName());
        this.setBoldWeight(source.getBoldWeight());
        this.setUnderLine(source.getUnderLine());
    }
}
