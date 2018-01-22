package com.xinyunlian.jinfu.executer;

import com.xinyunlian.jinfu.common.converter.ConverterService;
import com.xinyunlian.jinfu.common.util.AppConfigUtil;
import com.xinyunlian.jinfu.common.util.JsonUtil;
import com.xinyunlian.jinfu.common.util.ReflectionUtil;
import com.xinyunlian.jinfu.domain.CommandRequest;
import com.xinyunlian.jinfu.domain.CommandResponse;
import com.xinyunlian.jinfu.domain.req.PayRecvRequest;
import com.xinyunlian.jinfu.enums.PayRecvResult;
import com.xinyunlian.jinfu.service.BankLogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by cong on 2016/6/2.
 */
public abstract class AbstractCommandExecuter implements CommandExecuter {

    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractCommandExecuter.class);

    @Autowired
    private BankLogService bankLogService;

    @Override
    public CommandResponse execute(CommandRequest<?> request) {
        Class<?> reqClass = request.getClass();

        if (isSupportedRequestType(reqClass)) {
            String requestMsg = marshal(request);
            bankLogService.save(request, requestMsg);

            String responseMsg = "";

            if (AppConfigUtil.isUnProdEnv() && PayRecvRequest.class.isAssignableFrom(reqClass)) {
                PayRecvRequest payRecvRequest = (PayRecvRequest)request;
                if(payRecvRequest.getAccNo().endsWith("0000")){
                    responseMsg = "{\"result_code\":\"0000\",\"result_msg\":\"查询\",\"charset\":\"1\",\"sign_type\":\"1\",\"sign_msg\":\"9df4aee4a739b489b20fcf99f4c3ced4bbea7fa8ec8bd046be13003d875a11b5aa67aa0adeab7d7b159cfc37c519d35774a1ec448ed511be369f3876a2af3f09b0a87b472407db4ecc4cee93668bfdbf48ffe8c4fc6c7846e2e70a0b1e5f28df008ed838bc47eeb610776ff0a87fd8f04d5e292ec04183d3110f231c7b90d188f5662d0adb4c840dc9d5bf5efa90dbd0c78d6c521eb738332a24ae5cd58ced69521525fe21c37da77b73155f16f8d134435b64140bdb0d97beecf962b5f28d3ee92a1d3cbc3745c7d900f6214f9c1d94c8008bdd4493e7d89f9657e0263f68fc6f97e140f41471f31aa1873ebed0604244b033e9e541fd1ba3201cdde4e11790\",\"tran_amt\":1000,\"tran_no\":\"L16093000000001\",\"xyl_tran_no\":\"1120160930010001\",\"tran_status\":\"00\"}";
                }else if(payRecvRequest.getAccNo().endsWith("0001")){
                    responseMsg = "{\"result_code\":\"0000\",\"result_msg\":null,\"charset\":\"1\",\"sign_type\":\"1\",\"sign_msg\":\"2791a5553266c8950f46d0781feb3d071a4c9d6d49ba3f6b83fb8a2ffc7289dc80b5b579c252465057f7878c37f7816ca449e1f94f1f7e308141b8085addaf8d9613465ee6b5f718a9ca9bee88a9fd390b30bdc1d07804c30785e41ba98d6f50468b42f5ed478606fd7a65a4a2636b73bbfc5c83e36767c736060a80896f73fbdf01387c9b2310605e9ccd4cd2e7981c58fbca558fb2b28e9e2f428bbb29e7399063393be6d527bf154059cbd3016fe93fd07378799d15b1fab413fc5da8b968d1e429cbb4e03c93dd1d066e3a9cc49b60eb70b7d10209f62cea10857edeb11b8662e58bd815419ac9ce43ad0794a28c0a44df09818cfe3ad6cb9165a1c63a2f\",\"tran_amt\":\"1000\",\"tran_no\":\"L16093000000001\",\"xyl_tran_no\":\"1120160930010001\",\"tran_date\":\"20160930011424\",\"settle_date\":\"20160930\",\"tran_status\":\"02\"}";
                }else if(payRecvRequest.getAccNo().endsWith("0002")){
                    responseMsg = "{\"result_code\":\"C6\",\"result_msg\":\"卡上的余额不足[1000051]\",\"charset\":\"1\",\"sign_type\":\"1\",\"sign_msg\":\"009277579e3707dfdd9dfd19fa517b7a83f2726945623149e6ab348a35c72a1176b68cd6ee0d86a2f1c95242602c6384ff123c97e45ae62d00a1aa2e89f4b889205f45dd7ef5692e091ca492bfd9766d64c28e63f1591cce7dbfba517c127708f9b43d4138981f8c0b71cdfb7e436798c6d9b3bfdca0c79a4877f4ac633c7d4d5df09d286a1ecc14c6b86bc76e5ba63d46fb2195260abcb6ad046bf5a6eb2a6dc2e9329f5ff7fddeede21b6e2b590d26f8aa52c1741ba2c8e176887154004c4b56e7e22018e347635d76967726fb810cf4c57517b945e36d4720b75589c537d13002995e71d14d6dda6e009cab8381cd2b864728261c69a4dca95418d11e0f71\",\"tran_amt\":\"458400\",\"tran_no\":\"PR16112000000002\",\"xyl_tran_no\":\"0000000000000000000000001120161120170001\",\"tran_date\":\"20161120170001\",\"settle_date\":\"20161120\",\"tran_status\":\"01\"}";
                }else{
                    responseMsg = send(request);
                }
            }else{
                responseMsg = send(request);
            }

            LOGGER.info("receive response:{}", responseMsg);
            Class<?> respClass = ReflectionUtil.getGenericParamClass(reqClass);
            CommandResponse response = unmarshal(respClass, responseMsg);
            bankLogService.save(response, responseMsg);
            return response;
        } else {
            throw new IllegalArgumentException("request type invalid");
        }
    }

    protected abstract String marshal(CommandRequest<?> request);

    protected abstract CommandResponse unmarshal(Class<?> respClass, String responseMsg);

    protected abstract String send(CommandRequest<?> request);
}
