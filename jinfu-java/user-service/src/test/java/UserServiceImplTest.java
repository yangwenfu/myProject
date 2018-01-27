import com.xinyunlian.jinfu.center.dto.CenterUserDto;
import com.xinyunlian.jinfu.olduser.service.OldUserService;
import com.xinyunlian.jinfu.user.dto.UserExtDto;
import com.xinyunlian.jinfu.user.dto.UserInfoDto;
import com.xinyunlian.jinfu.user.dto.req.PasswordDto;
import com.xinyunlian.jinfu.user.dto.req.UserSearchDto;
import com.xinyunlian.jinfu.user.dto.resp.UserDetailDto;
import com.xinyunlian.jinfu.user.enums.ESource;
import com.xinyunlian.jinfu.user.service.UserExtService;
import com.xinyunlian.jinfu.user.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

import java.util.List;

/** 
* UserServiceImpl Tester. 
* 
* @author <Authors name> 
* @version 1.0 
*/
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:standard-code-dao-test.xml"})
@TransactionConfiguration(defaultRollback = false)
public class UserServiceImplTest extends AbstractTransactionalJUnit4SpringContextTests {
    @Autowired
    private UserService userService;

    @Autowired
    private OldUserService oldUserService;

    @Autowired
    private UserExtService userExtService;

    /**
     *
     * Method: findUserByMobile(String mobile)
     *
     */
    @Test
    public void testFindUserByMobile() throws Exception {
        UserInfoDto userInfoDto = userService.findUserByMobile("13805844950");
        System.out.println(userInfoDto.toString());
    }

    @Test
    public void testFindByUserNameLike() throws Exception {
        List<UserInfoDto> userInfoDtos = userService.findByUserNameLike("11");
        System.out.println(userInfoDtos.get(0).getUserName());
    }


    /**
     *
     * Method: createUser(PasswordDto passwordDto)
     *
     */
    @Test
    public void testCreateUser() throws Exception {
       // CertifyInfoDto userInfoDto = userService.findUserByMobile("13805844950");
      //  if(userInfoDto == null) {
        UserInfoDto passwordDto = new UserInfoDto();
        passwordDto.setMobile("13805844912");
        passwordDto.setLoginPassword("23213");
        passwordDto.setSource(ESource.REGISTER);
        userService.saveUser(passwordDto);
      //  }

    }

    /**
* 
* Method: findUserByUserId(String userId) 
* 
*/ 
@Test
public void testFindUserByUserId() throws Exception {
    UserInfoDto userInfoDto = userService.findUserByUserId("UC2000002000");
    System.out.println(userInfoDto.toString());
} 


/** 
* 
* Method: findUserDetailByUserId(String userId) 
* 
*/ 
@Test
public void testFindUserDetailByUserId() throws Exception {
    UserDetailDto userInfoDto = userService.findUserDetailByUserId("16082300000004");
    System.out.println(userInfoDto.toString());
} 


/** 
* 
* Method: updateUser(CertifyInfoDto userInfoDto)
* 
*/ 
@Test
public void testUpdateUser() throws Exception {
    UserInfoDto userInfoDto = userService.findUserByMobile("13805844912");
    System.out.println(userInfoDto.toString());
    userInfoDto.setEmail("123@163.com");
    userInfoDto.setIdCardNo("232498726378129387");
    userInfoDto.setUserName("测试人员");
    userService.updateUser(userInfoDto);
} 

/** 
* 
* Method: updatePassword(PasswordDto passwordDto) 
* 
*/ 
@Test
public void testUpdatePassword() throws Exception {
    UserInfoDto userInfoDto = userService.findUserByUserId("16082300000004");
    PasswordDto pass = new PasswordDto();
    pass.setOldPassword("321");
    pass.setNewPassword("000000");
    pass.setUserId(userInfoDto.getUserId());
    userService.updatePassword(pass);
}
    @Test
    public void testGetOldUserPage() throws Exception{
        UserSearchDto userSearchDto = new UserSearchDto();
        userSearchDto.setMobile("13805844950");
       // userSearchDto.setProvince("浙江省");
        userSearchDto.setUserName("测试人员");
       // userSearchDto.setArea("海曙区");
       // userSearchDto.setCity("宁波市");
        userSearchDto.setRegisterEndDate("2010-10-02");
        userSearchDto.setRegisterEndDate("2017-02-02");
        userSearchDto = oldUserService.getOldUserPage(userSearchDto);
        System.out.print(userSearchDto.getList().size());
    }

    @Test
    public void testGetUserPage()throws Exception{
        UserSearchDto userSearchDto = new UserSearchDto();
        userSearchDto.setMobile("13805844950");
        // userSearchDto.setProvince("浙江省");
        userSearchDto.setIdCardNo("232498726378129387");
        userSearchDto.setUserName("测试人员");
        // userSearchDto.setArea("海曙区");
        // userSearchDto.setCity("宁波市");
        userSearchDto.setEmail("123@163.com");
        userSearchDto.setRegisterEndDate("2010-10-02");
        userSearchDto.setRegisterEndDate("2017-02-02");
        userSearchDto = userService.getUserPage(userSearchDto);
        System.out.print(userSearchDto.getList().get(0).toString());
    }

    @Test
    public void testSaveUserExt() throws Exception {
        UserExtDto userExtDto = new UserExtDto();
        userExtDto.setUserId("UC0000000647");
        userExtDto.setAddress("asda");
        userExtDto.setUserName("aa");
        userExtDto.setIdCardNo("22");
        userExtDto.setAreaId(44064L);
        userExtService.saveUserExt(userExtDto);
    }

    @Test
    public void testGetUserInfoByTobaccoNo() throws Exception {
        userService.getUserInfoByTobaccoNo("330404105868");
    }

    @Test
    public void testSaveFromUserCenter() throws Exception {
        CenterUserDto centerUserDto = new CenterUserDto();
        centerUserDto.setUuid("123");
        centerUserDto.setUsername("测试");
        centerUserDto.setMobile("13805844900");
        centerUserDto.setGender(0);
        centerUserDto.setBrithday("2015-07-07");
        centerUserDto.setRealName("ceshi");
        centerUserDto.setIdCardNumber("123123344");
        centerUserDto.setIdCardStart("2015-07-07");
        centerUserDto.setIdCardEnd("2015-07-07");
        centerUserDto.setProvince("省1");
        centerUserDto.setCity("市1");
        centerUserDto.setArea("区1");
        centerUserDto.setStreet("街道1");
        centerUserDto.setProvinceId(11L);
        centerUserDto.setCityId(21L);
        centerUserDto.setAreaId(31L);
        centerUserDto.setStreetId(41L);
        centerUserDto.setHomeAddress("家庭地址1");

        System.out.println(userService.saveFromUserCenter(centerUserDto));
    }

} 
