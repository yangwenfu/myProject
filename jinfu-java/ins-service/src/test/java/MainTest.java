import com.xinyunlian.jinfu.common.util.RC4Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by dongfangchao on 2017/3/20/0020.
 */
public class MainTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(InsOrderServiceTest.class);

    public static void main(String[] args) {
        try {
            String biz = "a5f4b4b217285f4c720878c91c2c15f1c45a32100878bb820a96c938b9e26d4e976ee087cc91249fdb590f1b07b5f19c817215c86f852de210840577299c4793c3f7dfcec3c1f68a4be1a1cfecf3cee828f90d78fabd10cc6e98d6ef532c57b1f0ee84fe6ddc0db7e38161adb510dc51df451468c63e394bbe4b7fb28196b6e991a2b65cd62e8e9cd5ec1e50fbb790224bca47c04f9e563fbde0";
            String dec = RC4Util.decryRC4(biz, "open20160501");
            System.out.println(dec);
        } catch (Exception e) {
            LOGGER.error("error",e);
            e.printStackTrace();
        }
    }

    /*public static void main(String[] args) {
        String dd = "XMQo2L58J0&#43;wbLduxENuq&#43;SjuJLoaST3ltaU3N0XDXdGqO6F3krDWeEHsKJZnQ20bT&#43;gz4r9tdbFBpuCsyEe3ybOc5x&#43;QfI1M7u18NMhHcCBLIkd8BRnr&#43;eVrH24CGtt/BV/XJmNtWMNuCQ2Zr7tB4jiRpj&#43;zwFQwHXbFNMLOYmBvV4xfAVOuD06JXKapGe2fMImkhrz2lzmswjmY5SBQBLYB4Ld78/AYSAuHcfPgzUluEMhoKR&#43;LtYcMgbiavBz&#43;p37jsF6VmTkDHGvurQWID/f&#43;sKci2dIOYFpeCAy6JioVwfA780ou&#43;OVgKZe3Id6NB0/HtLeI4Hsp87ppY27/HZBpQCMuZXv7ZPEXGGkXc296A9XPZfm9mNW0kyihHhyqJGyLq32vPU9INUzQg50bHJ4mGnV4Xmmxln6SAzAz6Gko0rtoHFlgUMSLtPcnX9f/P6iq3SRX/zSKOeGcgbcPtE/iaRpMULoXrcoRN9GBkkuvF&#43;SICzlqNwLN/QomAXGq2AybbaRkMKflMB4K4PtuyybA3Laen7GNbpgzrtclAER29MkwOlLGg87Sym9jFSVY2Vyh/AjlP2fNzISNqsdpthB0w5QFgIYD0CTiPiylzrcsGAgRfdHfyGYM3rkWSiWleuYjC5bn/DWysmn5NjeHVwGzA4zhUUs4ef&#43;szh6sf2Wg3CW0lTm6EOpXRKQvIg9&#43;YfotkTd85XKImrk20ZDHi&#43;lHH1mi4/WRDmgtbkujm6mAunWXQiwJ0aJVfyGOm/hBFQpdyMX9hCL8dslPcnx1PKkmzEEthYtnyTHyvxYaQF6nWnLpNv&#43;wJ9JVEiWHtCdrXjEA2cjW6L06obhJweuAw&#61;&#61;";
        String unescapeHtml = StringEscapeUtils.unescapeHtml(dd);
        System.out.println(unescapeHtml);
    }*/

}
