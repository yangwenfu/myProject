import com.google.common.base.CaseFormat;

/**
 * @author Sephy
 * @since: 2017-03-16
 */
public class CaseFormatTest {
    public static void main(String[] args) {
        System.out.println(CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_CAMEL, "NATIP"));
    }
}
