package artoria.crypto;

import artoria.codec.Base64Utils;
import artoria.exception.ExceptionUtils;
import artoria.logging.Logger;
import artoria.logging.LoggerFactory;
import org.junit.Test;

import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import java.security.GeneralSecurityException;

import static artoria.common.Constants.DESEDE;

/**
 * DESede/ECB/NoPadding
 * DESede/ECB/PKCS5Padding
 * DESede/CBC/NoPadding
 * DESede/CBC/PKCS5Padding
 */
public class DESedeTest extends BouncyCastleSupport {
    private static Logger log = LoggerFactory.getLogger(DESedeTest.class);
    private static SymmetricCrypto symmetricCrypto = new DefaultSymmetricCrypto();
    private static IvParameterSpec ivParameterSpec;
    private byte[] data = "Hello, Java!".getBytes();

    static {
        try {
            // Wrong keySize: must be equal to 112 or 168
            String algorithm = DESEDE; int keySize = 168;
            SecretKey secretKey = KeyUtils.generateKey(algorithm, keySize);
            log.info("Algorithm = {}, KeySize = {}", algorithm, keySize);
            log.info("Secret key: {}", KeyUtils.toBase64String(secretKey));
            // Wrong IV length: must be 8 bytes long
            ivParameterSpec = new IvParameterSpec("TeTestIv".getBytes());
            symmetricCrypto.setSecretKey(secretKey);
            symmetricCrypto.setAlgorithm(algorithm);
            symmetricCrypto.setMode(Mode.ECB);
            symmetricCrypto.setPadding(Padding.NO_PADDING);
        }
        catch (GeneralSecurityException e) {
            throw ExceptionUtils.wrap(e);
        }
    }

    private void testEncryptAndDecrypt(Mode mode, Padding padding) throws Exception {
        log.info("Start test DESede/{}/{}", mode, padding);
        symmetricCrypto.setMode(mode);
        symmetricCrypto.setPadding(padding);
        byte[] bytes = symmetricCrypto.encrypt(data);
        log.info("Encrypt: {}", Base64Utils.encodeToString(bytes));
        byte[] bytes1 = symmetricCrypto.decrypt(bytes);
        log.info("Decrypt: {}", new String(bytes1));
        log.info("End test DESede/{}/{}", mode, padding);
    }

    @Test
    public void testEcbNoPadding() throws Exception {

        this.testEncryptAndDecrypt(Mode.ECB, Padding.NO_PADDING);
    }

    @Test
    public void testEcbPKCS5Padding() throws Exception {

        this.testEncryptAndDecrypt(Mode.ECB, Padding.PKCS5_PADDING);
    }

    @Test
    public void testCbcNoPadding() throws Exception {
        symmetricCrypto.setAlgorithmParameterSpec(ivParameterSpec);
        this.testEncryptAndDecrypt(Mode.CBC, Padding.NO_PADDING);
        symmetricCrypto.setAlgorithmParameterSpec(null);
    }

    @Test
    public void testCbcPKCS5Padding() throws Exception {
        symmetricCrypto.setAlgorithmParameterSpec(ivParameterSpec);
        this.testEncryptAndDecrypt(Mode.CBC, Padding.PKCS5_PADDING);
        symmetricCrypto.setAlgorithmParameterSpec(null);
    }

}