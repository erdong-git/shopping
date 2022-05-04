import org.java.auth.pojo.UserInfo;
import org.java.auth.utils.JwtUtils;
import org.java.auth.utils.RsaUtils;
import org.junit.Before;
import org.junit.Test;

import java.security.PrivateKey;
import java.security.PublicKey;

public class JwtTest {

    private static final String pubKeyPath = "C:\\tmp\\rsa\\rsa.pub";

    private static final String priKeyPath = "C:\\tmp\\rsa\\rsa.pri";

    private PublicKey publicKey;

    private PrivateKey privateKey;

    @Test
    public void testRsa() throws Exception {
        RsaUtils.generateKey(pubKeyPath, priKeyPath, "234");
    }

    /**
     * 生成私钥公钥时，需要将该方法的@Before注释去掉
     * @throws Exception
     */
    @Before
    public void testGetRsa() throws Exception {
        this.publicKey = RsaUtils.getPublicKey(pubKeyPath);
        this.privateKey = RsaUtils.getPrivateKey(priKeyPath);
    }

    @Test
    public void testGenerateToken() throws Exception {
        // 生成token
        String token = JwtUtils.generateToken(new UserInfo(1L, "zhangsan"), privateKey, 5);
        System.out.println("token = " + token);
    }

    @Test
    public void testParseToken() throws Exception {
        String token = "eyJhbGciOiJSUzI1NiJ9.eyJpZCI6MSwidXNlcm5hbWUiOiJ6aGFuZ3NhbiIsImV4cCI6MTY0NjMxNzk5NH0.JYHKHoXIDu0twuC1EC2pSeNiAgn5NV9x2bEwTfmM0NCWIXwXflN6exybbBLTqjG8r51pgZeOaAcVqjB2X7Cl9iywOpdq9yh3wxLhalRA1wKFeLZxxhi-Chw-zMzrz2NQxp_USUTD-tR1hwayPWGbXpqZ2VK8G_w8cVeC-nrN-bQ";

        // 解析token
        UserInfo user = JwtUtils.getInfoFromToken(token, publicKey);
        System.out.println("id: " + user.getId());
        System.out.println("userName: " + user.getUsername());
    }
    @Test
    public void lover (){
        Object lover = new Object();
        System.out.println(lover);
    }
}