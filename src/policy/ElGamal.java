package policy;
import java.security.Key;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.SecureRandom;
import java.security.Security;

import javax.crypto.Cipher;

public class ElGamal {
  public static void main(String[] args) throws Exception {
    Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());

    byte[] input = "MAHJOUR MORAD".getBytes();
    Cipher cipher = Cipher.getInstance("ElGamal/None/NoPadding", "BC");
    KeyPairGenerator generator = KeyPairGenerator.getInstance("ElGamal", "BC");
    SecureRandom random = new SecureRandom();

    generator.initialize(1024, random);

    KeyPair pair = generator.generateKeyPair();
    Key pubKey = pair.getPublic();
    Key privKey = pair.getPrivate();
    
    cipher.init(Cipher.ENCRYPT_MODE, pubKey, random);
    byte[] cipherText = cipher.doFinal(input);
    System.out.println("Message crypte : " + new String(cipherText));

    cipher.init(Cipher.DECRYPT_MODE, privKey);
    byte[] plainText = cipher.doFinal(cipherText);
    System.out.println("Message Decrypte : " + new String(plainText));
  }
}