
import com.xinyunlian.jinfu.bank.dto.BankCardBinDto;
import com.xinyunlian.jinfu.bank.dto.BankCardDto;
import com.xinyunlian.jinfu.bank.service.BankService;
import com.xinyunlian.jinfu.common.util.JsonUtil;
import com.xinyunlian.jinfu.contract.dto.ContractBestSignCfgDto;
import com.xinyunlian.jinfu.contract.enums.EBsSignType;
import com.xinyunlian.jinfu.contract.enums.ECntrctTmpltType;
import com.xinyunlian.jinfu.contract.service.BestSignService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

import java.util.ArrayList;
import java.util.List;

/** 
* BankServiceImpl Tester. 
* 
* @author <Authors name> 
* @since <pre>���� 23, 2016</pre> 
* @version 1.0 
*/
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:jinfu.spring.xml"})
@TransactionConfiguration(defaultRollback = false)
public class BankServiceImplTest {
    @Autowired
    private BankService bankService;

    @Autowired
    private BestSignService bestSignService;
/** 
* 
* Method: createBankCar(BankCardDto bankCardDto) 
* 
*/ 
@Test
public void testCreateBankCar() throws Exception {
    BankCardDto bankCardDto = new BankCardDto();
    bankCardDto.setBankCardName("中国银行");
    bankCardDto.setBankCardNo("6216261000020011118");
    bankCardDto.setMobileNo("1333133");
    bankCardDto.setBankName("");
    bankCardDto.setIdCardNo("341126197709218366");
    bankCardDto.setUserId("UC0000000401");
    bankCardDto.setBankId(14L);
    bankService.saveBankCard(bankCardDto);
} 

/** 
* 
* Method: deleteBankCar(Long bankCardId) 
* 
*/ 
@Test
public void testDeleteBankCar() throws Exception {
    bankService.deleteBankCard(1L);
} 

/** 
* 
* Method: findByUserId(String userId) 
* 
*/ 
@Test
public void testFindByUserId() throws Exception {
    bankService.findByUserId("16082300000004");
}

    @Test
    public void testFindByNumLengthAndBin() throws Exception {
        BankCardBinDto bankCardBinDto = bankService.findByNumLengthAndBin("6216611400007078487");
        System.out.print(bankCardBinDto.getBankId());
    }

    /**
* 
* Method: findAll() 
* 
*/ 
@Test
public void testFindAll() throws Exception {
    bankService.findBankInfAll();
}

    @Test
    public void testFindByStoreId() throws Exception {
        List<Long> param = new ArrayList<>();
        param.add(1L);
        bankService.findByStoreIds(param);
    }

    @Test
    public void testBestSignCfg(){
        List<ContractBestSignCfgDto> dtoList = bestSignService.getCntrctBsCfg(ECntrctTmpltType.YDL01001, EBsSignType.ENTERPRISE);
        System.out.println(JsonUtil.toJson(dtoList));
    }


    @Test
    public void testCountByUserIdSupport(){
        Long count = bankService.countByUserIdSupport("UC00000007011");
        System.out.println(count);
    }

} 
