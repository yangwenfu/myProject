import com.xinyunlian.jinfu.common.util.ImageUtils;
import org.junit.Before;
import org.junit.Test;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.InputStream;

/**
 * Created by dongfangchao on 2017/1/12/0012.
 */
public class ImageTest {

    private String srcFileName;

    @Before
    public void before(){
        srcFileName = "E:\\云联金服\\项目\\20170118\\android001.png";
    }

    @Test
    public void getResolution(){
        try {
            InputStream is = new FileInputStream(srcFileName);
            BufferedImage src = ImageIO.read(is); //构造Image对象
            int srcWidth = src.getWidth(null); //得到源图宽
            int srcHeight = src.getHeight(null); //得到源图长
            System.out.println("分辨率：" + srcWidth + "*" + srcHeight);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
