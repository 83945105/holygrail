package pub.avalon.holygrail.cache;

import java.lang.reflect.Field;

/**
 * @author 白超
 * @date 2018/8/13
 */
public class PropertyInfo {

    private String name;

    private Field field;

    private boolean isBoolean;

    private Class type;

    private String getterMethodName;

    private String setterMethodName;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Field getField() {
        return field;
    }

    public void setField(Field field) {
        this.field = field;
    }

    public boolean isBoolean() {
        return isBoolean;
    }

    public void setBoolean(boolean aBoolean) {
        isBoolean = aBoolean;
    }

    public Class getType() {
        return type;
    }

    public void setType(Class type) {
        this.type = type;
    }

    public String getGetterMethodName() {
        return getterMethodName;
    }

    public void setGetterMethodName(String getterMethodName) {
        this.getterMethodName = getterMethodName;
    }

    public String getSetterMethodName() {
        return setterMethodName;
    }

    public void setSetterMethodName(String setterMethodName) {
        this.setterMethodName = setterMethodName;
    }

}
