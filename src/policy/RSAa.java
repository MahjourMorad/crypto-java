package policy;

import javax.crypto.BadPaddingException; 

import javax.crypto.Cipher; 

import javax.crypto.IllegalBlockSizeException; 

import javax.crypto.NoSuchPaddingException; 

import java.security.*; 

import java.security.spec.InvalidKeySpecException; 

import java.security.spec.PKCS8EncodedKeySpec; 

import java.security.spec.X509EncodedKeySpec; 

import java.util.Arrays; 

import java.util.Base64; 

import java.io.*; 

public class RSAa { 

  

    public static PublicKey getPublicKey(byte[] publicKeybytes){ 

        PublicKey publicKey = null; 

        try{ 

            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(publicKeybytes); 

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

  

    public static PrivateKey getPrivateKey(byte[] privateKeybytes){ 

        PrivateKey privateKey = null; 

        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(privateKeybytes); 

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

  

    public static byte[] encrypt(String data, byte[] publicKey) throws BadPaddingException, IllegalBlockSizeException, InvalidKeyException, NoSuchPaddingException, NoSuchAlgorithmException { 

        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding"); 

        cipher.init(Cipher.ENCRYPT_MODE, getPublicKey(publicKey)); 

        return cipher.doFinal(data.getBytes()); 

    } 

  

    public static byte[] decrypt(byte[] data, byte[] privateKey) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException { 

        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding"); 

        cipher.init(Cipher.DECRYPT_MODE, getPrivateKey(privateKey)); 

        return  cipher.doFinal(data); 

    } 

     

    private static void getKeys(int keySize, String output,String algorithm) throws Exception { 

         KeyPairGenerator kg = KeyPairGenerator.getInstance("RSA"); 

         byte[] ky; 

         kg.initialize(keySize); 

         System.out.println(); 

         System.out.println("KeyPairGenerator Object Info: "); 

         System.out.println("Algorithm = "+kg.getAlgorithm()); 

         System.out.println("Provider = "+kg.getProvider()); 

         System.out.println("Key Size = "+keySize); 

         System.out.println("toString = "+kg.toString()); 

         KeyPair pair = kg.generateKeyPair(); 

         PrivateKey priKey = pair.getPrivate(); 

         PublicKey pubKey = pair.getPublic(); 

         String fl = output+".pr"; 

         FileOutputStream out = new FileOutputStream(fl); 

         ky = priKey.getEncoded(); 

         out.write(ky); 

         out.close(); 

         System.out.println(); 

         System.out.println("Private Key Info: "); 

         System.out.println("Algorithm = "+priKey.getAlgorithm()); 

         System.out.println("Saved File = "+fl); 

         System.out.println("Size = "+ky.length); 

         System.out.println("Format = "+priKey.getFormat()); 

         System.out.println("private key = "+Base64.getEncoder().encodeToString(ky)); 

         fl = output+".pb"; 

         out = new FileOutputStream(fl); 

         ky = pubKey.getEncoded(); 

         out.write(ky); 

         out.close(); 

         System.out.println(); 

         System.out.println("Public Key Info: "); 

         System.out.println("Algorithm = "+pubKey.getAlgorithm()); 

         System.out.println("Saved File = "+fl); 

         System.out.println("Size = "+ky.length); 

         System.out.println("Format = "+pubKey.getFormat()); 

         System.out.println("public key = "+Base64.getEncoder().encodeToString(ky)); 

      } 

    private static pairekey readkeys(String chemin) 

    {    int i=0; 

        pairekey pk =new pairekey(); 

        FileInputStream in; 

        try { 

        String chemin_pub=chemin+".pb"; 

        String chemin_pri=chemin+".pr"; 

        in = new FileInputStream(chemin_pub); 

        ByteArrayOutputStream bo1=new ByteArrayOutputStream(); 

        while((i=in.read())!=-1)     

        bo1.write(i);  

        in.close(); 

        pk.publicKeybytes=bo1.toByteArray(); 

        in = new FileInputStream(chemin_pri); 

        ByteArrayOutputStream bo2=new ByteArrayOutputStream(); 

        while((i=in.read())!=-1)     

        bo2.write(i);  

        in.close(); 

        pk.privateKeybytes=bo2.toByteArray(); 

         

        } catch (Exception e) { 

            System.err.println(e.getMessage()); 

        }     

        return pk; 

    } 

    public static void main(String[] args) throws Exception { 

            getKeys(1024,"security","RSA"); 

            pairekey pk =readkeys("security"); 

             

             

            String message_en_clair="Mahjour morad"; 

            System.out.println("Le message en clair : "+message_en_clair); 

            byte [] message_chiffre_byte =encrypt(message_en_clair, pk.publicKeybytes); 

            String encryptedString = Base64.getEncoder().encodeToString(message_chiffre_byte); 

            System.out.println("Le message chiffré : "+encryptedString); 

            byte [] message_dechiffre_byte=RSAa.decrypt(message_chiffre_byte, pk.privateKeybytes); 

            System.out.println("Le message déchiffré : "+new String(message_dechiffre_byte)); 

            

         

    } 

} 

  

class pairekey 

{ 

     public static byte [] publicKeybytes; 

     public static byte [] privateKeybytes; 

} 