import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;


public class Crypto {

    private static final String ALGO = "AES";
    private static final byte[] keyValue =
            new byte[]{'A', 'n', 'd', 'c', 'h', 'e', 'r',
                    'r', 'i', 'c', 'k', 'n', 'e', 'l', 'e', 'e'};
    static Key k;

    static {
        try
        {
            k = generateKey();
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    public static byte[] encrypt(byte[] Data) {
        try {
            Cipher cipher = Cipher.getInstance(ALGO);
            cipher.init(Cipher.ENCRYPT_MODE, k);
            byte[] encryptVal = cipher.doFinal(Data);
            return encryptVal;
        }
        catch (Exception ex){
            throw new RuntimeException(ex);
        }
    }

    public static byte[] decrypt(byte[] encryptedData) {
        try
        {
            Cipher cipher = Cipher.getInstance(ALGO);
            cipher.init(Cipher.DECRYPT_MODE, k);
            byte[] decryptValue = cipher.doFinal(encryptedData);
            return decryptValue;
        }
        catch (Exception ex){
            throw new RuntimeException(ex);
        }

    }

    private static Key generateKey() {
        try {
            Key k = new SecretKeySpec(keyValue, ALGO);
            return k;
        }
        catch (Exception ex){
            throw new RuntimeException(ex);
        }
    }
}
