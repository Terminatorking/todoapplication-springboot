package ghaimoradi.soheil.todoapp.utils.encryption;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

public class AES {
    private static final String KEY = "L0dMFwwsJeta5Sy8";
    private static final String IV = "H0TeO6beV6OAlF2T";
    private static final String CIPHER_TRANSFORMATION = "AES/CBC/PKCS5Padding";
    private static final String SECRET_KEY_ALGORITHM = "AES";

    public static String encryptAES(String value) {
        try {
            Cipher cipher = Cipher.getInstance(CIPHER_TRANSFORMATION);
            SecretKey key = new SecretKeySpec(KEY.getBytes(), SECRET_KEY_ALGORITHM);
            IvParameterSpec iv = new IvParameterSpec(IV.getBytes());
            cipher.init(Cipher.ENCRYPT_MODE, key, iv);
            byte[] cipherText = cipher.doFinal(value.getBytes());
            return Base64.getEncoder().encodeToString(cipherText);
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return null;
        }
    }

    public static String decryptAES(String value) {
        try {
            Cipher cipher = Cipher.getInstance(CIPHER_TRANSFORMATION);
            SecretKey key = new SecretKeySpec(KEY.getBytes(), SECRET_KEY_ALGORITHM);
            IvParameterSpec iv = new IvParameterSpec(IV.getBytes());
            cipher.init(Cipher.DECRYPT_MODE, key, iv);
            byte[] decode = Base64.getDecoder().decode(value);
            byte[] decrypted = cipher.doFinal(decode);
            return new String(decrypted);
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return null;
        }
    }
}