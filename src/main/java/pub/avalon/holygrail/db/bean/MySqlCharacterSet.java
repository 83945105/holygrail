package pub.avalon.holygrail.db.bean;

import pub.avalon.holygrail.db.norm.CharacterSet;

/**
 * MySql数据库字符集
 *
 * @author 白超
 * @date 2018/2/8
 */
public enum MySqlCharacterSet implements CharacterSet {
    /**
     *
     */
    armscii8("ARMSCII-8 Armenian"),
    ascii("US ASCII"),
    big5("Big5 Traditional Chinese"),
    binary("Binary pseudo charset"),
    cp1250("Windows Central European"),
    cp1251("Windows Cyrillic"),
    cp1256("Windows Arabic"),
    cp1257("Windows Baltic"),
    cp850("DOS West European"),
    cp852("DOS Central European"),
    cp866("DOS Russian"),
    cp932("SJIS for Windows Japanese"),
    dec8("DEC West European"),
    eucjpms("UJIS for Windows Japanese"),
    euckr("EUC-KR Korean"),
    gb2312("GB2312 Simplified Chinese"),
    gbk("GBK Simplified Chinese"),
    geostd8("GEOSTD8 Georgian"),
    greek("ISO 8859-7 Greek"),
    hebrew("ISO 8859-8 Hebrew"),
    hp8("HP West European"),
    keybcs2("DOS Kamenicky Czech-Slovak"),
    koi8r("KOI8-R Relcom Russian"),
    koi8u("KOI8-U Ukrainian"),
    latin1("cp1252 West European"),
    latin2("ISO 8859-2 Central European"),
    latin5("IOS 8859-9 Turkish"),
    latin7("ISO 8859-13 Baltic"),
    macce("Mac Central European"),
    macroman("Mac West European"),
    sjis("Shift-JIS Japanese"),
    swe7("7bit Swedish"),
    tis620("TIS620 Thai"),
    ucs2("UCS-2 Unicode"),
    ujis("EUC-JP Japanese"),
    utf16("UTF-16 Unicode"),
    utf32("UTF-32 Unicode"),
    utf8("UTF-8 Unicode"),
    utf8mb4("UTF-8 Unicode");

    public String comment;

    MySqlCharacterSet(String comment) {
        this.comment = comment;
    }
}
