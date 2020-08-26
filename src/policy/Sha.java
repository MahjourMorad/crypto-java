package policy;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.Properties;
 
/**
 * The HashService class 
 * NOTE : Remember to always precise the keyword final if class is not supposed to be inheritable
 */
public final class Sha {
	
 
    private static final String PROP_FILE = "/hashservice.properties";
 
    // Set default algo and provider :
    private String defaultProvider="SUN";
    private String defaultAlgorithm="SHA-512";    
    // Set algo and provider for SHA-1
    private String sha1Provider="SUN";
    private String sha1Algorithm="SHA-1";
  
    // For singleton pattern
    private static Sha instance = new Sha();
 
    /**
     * Public access so it is easy to know the default algorithm
     */
    public String getDefaultAlgorithm() {
	return this.defaultAlgorithm;
    }
 
    /**
     * Constructor (initialise properties)
     */
    private Sha() {
	try {
	    final Properties prop = new Properties();
	    prop.load(Sha.class.getResourceAsStream(PROP_FILE));
	    this.defaultProvider = prop.getProperty("defaultProvider");
	    this.defaultAlgorithm = prop.getProperty("defaultAlgorithm");
	    this.sha1Provider = prop.getProperty("sha1Provider");
	    this.sha1Algorithm = prop.getProperty("sha1Algorithm");
	} catch (Exception e) {
	    e.printStackTrace();
	}
    }
 
    /**
     * Return the HashService Instance
     * @return
     */
    public static synchronized Sha getInstance() {
	return instance;
    }
 
    /**
     * We do not want our singleton to have clones
     */
    protected Object clone() throws CloneNotSupportedException {
	throw new CloneNotSupportedException("Clone is not allowed.");
    }
 
    /**
     * Return the Hash String corresponding to the digest byte[] 
     * NOTE : This method could be exported to another service
     * @param hexBytes
     * @return
     */
    private String hex2String(final byte[] hexBytes) {
	final StringBuffer hexString = new StringBuffer();
	for (int i = 0; i < hexBytes.length; i++) {
	    hexString.append(Integer.toHexString(0xFF & hexBytes[i]));
	}
	return hexString.toString();
    }
 
    /**
     * The method used to hash a message 
     * @param message
     * @return
     */
    public String digest(final String message) {
	byte[] hashMessageBytes;
	String hashMessage = null;
	try {
	    // Get messageDigest instance
	    final MessageDigest messageDigest = MessageDigest.getInstance(this.defaultAlgorithm, this.defaultProvider);
	    // Hash the message
	    hashMessageBytes = messageDigest.digest(message.getBytes());
	    // Convert the hash bytes to String
	    hashMessage = hex2String(hashMessageBytes);
	} catch (NoSuchAlgorithmException e) {
	    // You can add log feature here
	    e.printStackTrace();
	} catch (NoSuchProviderException e) {
	    // You can add log feature here
	    e.printStackTrace();
	}
	return hashMessage;
    }
 
 
    public String sha1Digest(final String message) {
	byte[] hashMessageBytes;
	String hashMessage = null;
	try {
	    // Get messageDigest instance
	    final MessageDigest messageDigest = MessageDigest.getInstance(this.sha1Algorithm, this.sha1Provider);
	    // Hash the message
	    hashMessageBytes = messageDigest.digest(message.getBytes());
	    // Convert the hash bytes to String
	    hashMessage = hex2String(hashMessageBytes);
	} catch (NoSuchAlgorithmException e) {
	    // You can add log feature here
	    e.printStackTrace();
	} catch (NoSuchProviderException e) {
	    // You can add log feature here
	    e.printStackTrace();
	}
	return hashMessage;
    }
    public static void main(String[] args) {
    	String message = "MAHJOUR MORAD";
    	try {
     
    	    System.out.println("SHA-512 for " + message + " : "+Sha.getInstance().digest(message));
    	    System.out.println("SHA-1 for "+ message + " : "+Sha.getInstance().sha1Digest(message));
     
    	} catch (Exception e) {
    	    e.printStackTrace();
    	}
        }
}