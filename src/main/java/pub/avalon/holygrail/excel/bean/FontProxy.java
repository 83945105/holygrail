package pub.avalon.holygrail.excel.bean;

import org.apache.poi.ss.usermodel.Font;

/**
 * @author 白超
 * @date 2018/4/9
 */
public class FontProxy implements pub.avalon.holygrail.excel.norm.Font {

    private Font font;

    public FontProxy(Font font) {
        this.font = font;
    }

    @Override
    public void setColor(FontColor fontColor) {
        this.font.setColor(fontColor.value);
    }

    @Override
    public FontColor getColor() {
        return FontColor.getFontColorByValue(this.font.getColor());
    }

    @Override
    public void setStrikeout(boolean strikeout) {
        this.font.setStrikeout(strikeout);
    }

    @Override
    public boolean isStrikeout() {
        return this.font.getStrikeout();
    }

    @Override
    public void setItalic(boolean italic) {
        this.font.setItalic(italic);
    }

    @Override
    public boolean isItalic() {
        return this.font.getItalic();
    }

    @Override
    public void setFontHeightInPoints(short size) {
        this.font.setFontHeightInPoints(size);
    }

    @Override
    public short getFontHeightInPoints() {
        return this.font.getFontHeightInPoints();
    }

    @Override
    public void setFontName(String fontName) {
        this.font.setFontName(fontName);
    }

    @Override
    public String getFontName() {
        return this.font.getFontName();
    }

    @Override
    public void setBoldWeight(boolean boldWeight) {
        this.font.setBoldweight(boldWeight ? Font.BOLDWEIGHT_BOLD : Font.BOLDWEIGHT_NORMAL);
    }

    @Override
    public boolean isBoldWeight() {
        return this.font.getBoldweight() == Font.BOLDWEIGHT_BOLD;
    }

    @Override
    public void setUnderLine(UnderLine underLine) {
        this.font.setUnderline((byte) underLine.value);
    }

    @Override
    public UnderLine getUnderLine() {
        switch (this.font.getUnderline()) {
            case 1:
                return UnderLine.SINGLE;
            case 2:
                return UnderLine.DOUBLE;
            case 3:
                return UnderLine.SINGLE_ACCOUNTING;
            case 4:
                return UnderLine.DOUBLE_ACCOUNTING;
            case 5:
                return UnderLine.NONE;
            default:
                return null;
        }
    }

    public Font getFont() {
        return font;
    }

    public void setFont(Font font) {
        this.font = font;
    }
}
