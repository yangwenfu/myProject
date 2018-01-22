/**
 * Created by dongfangchao on 2017/2/15/0015.
 */
public class MainTest {

    public static void main(String[] args) {
        String originalName = "aaa.apk";
        String suffix = originalName.substring(originalName.lastIndexOf("."), originalName.length());
        System.out.println(suffix);
    }

}
