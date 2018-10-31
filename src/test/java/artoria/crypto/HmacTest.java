package artoria.crypto;

import artoria.codec.Hex;
import org.junit.Test;

public class HmacTest {
    private static Hmac hmd5 = new Hmac(Hmac.HMAC_MD5);
    private static Hmac hsha1 = new Hmac(Hmac.HMAC_SHA1);
    private static Hmac hsha256 = new Hmac(Hmac.HMAC_SHA256);
    private static Hmac hsha384 = new Hmac(Hmac.HMAC_SHA384);
    private static Hmac hsha512 = new Hmac(Hmac.HMAC_SHA512);
    private static Hex hex = Hex.getInstance(true);
    private static String key = "123456";

    static {
        hmd5.setKey(key);
        hsha1.setKey(key);
        hsha256.setKey(key);
        hsha384.setKey(key);
        hsha512.setKey(key);
    }

    @Test
    public void hmacString() throws Exception {
        String data = "12345";
        System.out.println(hex.encodeToString(hmd5.calc(data)));
        System.out.println(hex.encodeToString(hsha1.calc(data)));
        System.out.println(hex.encodeToString(hsha256.calc(data)));
        System.out.println(hex.encodeToString(hsha384.calc(data)));
        System.out.println(hex.encodeToString(hsha512.calc(data)));
    }

}
