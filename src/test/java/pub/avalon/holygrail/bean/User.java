package pub.avalon.holygrail.bean;

/**
 * Created by 白超 on 2018/8/13.
 */
public class User {

    private static String alias = "User";

    private String id;

    private String name;

    private int a1;

    private Integer a2;

    private boolean c1;

    private Boolean c2;

    private User user;

    public static String getAlias() {
        return alias;
    }

    public static void setAlias(String alias) {
        User.alias = alias;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getA1() {
        return a1;
    }

    public void setA1(int a1) {
        this.a1 = a1;
    }

    public Integer getA2() {
        return a2;
    }

    public void setA2(Integer a2) {
        this.a2 = a2;
    }

    public boolean isC1() {
        return c1;
    }

    public void setC1(boolean c1) {
        this.c1 = c1;
    }

    public Boolean getC2() {
        return c2;
    }

    public void setC2(Boolean c2) {
        this.c2 = c2;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
