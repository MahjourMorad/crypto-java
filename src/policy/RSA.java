package policy;

import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

public class RSA {

    private static String publicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQChIz+s76cUIll+Iu1j7UUQVWUqE0NBSpq+09zMms0JpgxmCjmosE+KbHouVGMy3UNYcpK5JsixouaEAxptsS3X4iuyhbSOiOt3onek0gg4x8uXJfN0rTHmHvaNB/QWsGp1xsa9senJIMOGTw1VpkKYD42B/3K1JkX1gcWTpqMOhwIDAQAB";
    private static String privateKey = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAKEjP6zvpxQiWX4i7WPtRRBVZSoTQ0FKmr7T3MyazQmmDGYKOaiwT4psei5UYzLdQ1hykrkmyLGi5oQDGm2xLdfiK7KFtI6I63eid6TSCDjHy5cl83StMeYe9o0H9BawanXGxr2x6ckgw4ZPDVWmQpgPjYH/crUmRfWBxZOmow6HAgMBAAECgYAJGOhbZxxTsqzCxk4IujiP94JCodvGL+QUqflpQ8QXq1w/1ovyziFvezharUD8eil3MOC/u0TRYpUJFvPDBaHE23ztYdFDl8WzQ024TqcE6+usZJH93iFPlEMKR9rZ3ay9zryVqELzx/PLSGV7OHLiz/1aDIdwJir2XK7axgVnIQJBAMxdu/Gz0M1Uu9hXz8DIWbnBVpj811e/aSc3DVWa8+P7aIi80DR11pe2qfvsHbj7utmk1x4zC/GSZT7NSjIQlwUCQQDJ2Yba8kCRJ9IjLAlZQLsmWn1rlmlLinKec33V+LSc7ZC855UlZu/X9I8YrmQTC/dNstXsfik7rouUzkHsHm0bAkEAk2W3vGhY3P+UTEFpUj4umM9dShJyrpMboHEQaQHNjk0x5A1qjavADsRsbYqrFVRaO8esb9eZca6I33bpYJqekQJBAIQ+QO0HVCgFt9YVTtar4zFgjdVECR5VyXxoXCQoumQp/O7Wps+HXlHVRz87WhNkfpToOuGjUm5oj8hhTa1dzcsCQF7RdEiw97Xvw5E1x0ThwzOOWacyo5HeB4xCv1Kf88Rqf44Ro0Kx9HjWX0G/CcxQBzlHh7ylj4iGf8j0M39qsT0=";

    public static PublicKey getPublicKey(String base64PublicKey){
        PublicKey publicKey = null;
        try{
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(Base64.getDecoder().decode(base64PublicKey.getBytes()));
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            publicKey = keyFactory.generatePublic(keySpec);
            return publicKey;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        }
        return publicKey;
    }

    public static PrivateKey getPrivateKey(String base64PrivateKey){
        PrivateKey privateKey = null;
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(Base64.getDecoder().decode(base64PrivateKey.getBytes()));
        KeyFactory keyFactory = null;
        try {
            keyFactory = KeyFactory.getInstance("RSA");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        try {
            privateKey = keyFactory.generatePrivate(keySpec);
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        }
        return privateKey;
    }

    public static byte[] encrypt(String data, String publicKey) throws BadPaddingException, IllegalBlockSizeException, InvalidKeyException, NoSuchPaddingException, NoSuchAlgorithmException {
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        cipher.init(Cipher.ENCRYPT_MODE, getPublicKey(publicKey));
        return cipher.doFinal(data.getBytes());
    }

    public static String decrypt(byte[] data, PrivateKey privateKey) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        return new String(cipher.doFinal(data));
    }

    public static String decrypt(String data, String base64PrivateKey) throws IllegalBlockSizeException, InvalidKeyException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException {
        return decrypt(Base64.getDecoder().decode(data.getBytes()), getPrivateKey(base64PrivateKey));
    }

    public static void main(String[] args) throws IllegalBlockSizeException, InvalidKeyException, NoSuchPaddingException, BadPaddingException {
        try {
            String encryptedString = Base64.getEncoder().encodeToString(encrypt("MAHJOUR MORAD", publicKey));
          
            System.out.println("la chaine encrypte est :");
            System.out.println(encryptedString);
            
            System.out.println("la chaine decripte est :");
            String decryptedString = RSA.decrypt(encryptedString, privateKey);
            System.out.println(decryptedString);
        } catch (NoSuchAlgorithmException e) {
            System.err.println(e.getMessage());
        }

    }
}