import java.io.Serializable;

/**
 * Created by dongfangchao on 2016/11/21.
 */
public class TestObject implements Serializable{

    private static final long serialVersionUID = 3706443278319935597L;
    private String name;
    private Integer age;
    private Boolean female;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Boolean getFemale() {
        return female;
    }

    public void setFemale(Boolean female) {
        this.female = female;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }
}
