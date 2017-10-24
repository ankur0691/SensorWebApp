
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.Random;
import java.util.Scanner;

/**
 * This class was run once to generate a Salt and Hash value for each user
 * @author ankur
 */
public class PasswordHash {
    //random variable
    private static final Random RANDOM = new SecureRandom(); 

    /**
     * generates a random salt string
     * @return a randomly generated string
     */
    public static String getNextSalt() {
        //sticking to generating only alpha numeric random string
        String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < 18) { // length of the random string.
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }
        String saltStr = salt.toString();
        return saltStr;
    }
    
    /**
     * this method generates a hash value out of the passed text
     * @param text byte represented message to be hashed
     * @return a base64 encoded string of the hashed value generated
     * @throws NoSuchAlgorithmException
     */
    public static String generateHash(byte[] text) throws NoSuchAlgorithmException{
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hash = digest.digest(text);
        return Base64.getEncoder().encodeToString(hash);
    }
    
    /**
     * this method tests the user credentials based on the passed password, salt and hash value
     * @param testPassword Password string that needs to be checked for validity
     * @param salt A randomly generated String to be appended to password
     * @param hash A hash string generated for this password
     * @return true if the password is correct else false
     * @throws NoSuchAlgorithmException
     */
    public static boolean testCredentials(String testPassword, String salt, String hash) throws NoSuchAlgorithmException{
        StringBuffer combination = new StringBuffer();
        //append the password to be tested with salt value
        combination.append(salt).append(testPassword);
        //generate a hash for this salt+password string and compare it with the stored hash value 
        String hash2 = generateHash(combination.toString().getBytes());
        if(hash2.equals(hash)) {
            return true;
        }
        return false;
    }

    /**
     *
     * @param args
     * @throws NoSuchAlgorithmException
     */
    public static void main(String args[]) throws NoSuchAlgorithmException{
        //take user input
        Scanner reader = new Scanner(System.in);
        System.out.println("#Enter user ID");
        String userId = reader.nextLine();
        System.out.println("#Enter password");
        String password = reader.nextLine();
        //generate a random salt String for this user
        String salt = getNextSalt();
        StringBuffer combination = new StringBuffer();
        //append pssword to salt
        combination.append(salt).append(password);
        //generate has value for this salt+password string
        String hash = generateHash(combination.toString().getBytes());
        
        //display to user the salt and hash values to be sotred for future use
        System.out.println("#The following could be stored on a file:");
        System.out.println("#User ID = " + userId);
        System.out.println("#Salt = " + salt);
        System.out.println("#Hash of salt + password = " + hash);
        
        //check for user authentication
        System.out.println("#Enter user ID for authenticaion test");
        String testUserId = reader.nextLine();
        System.out.println("#Enter password for authenticaion test");
        String testPassword = reader.nextLine();
        
        if(testUserId.equals(userId)){
        if(testCredentials(testPassword, salt, hash)){
            System.out.println("Validated user id and password pair");
        }
        } else System.out.println("Not able to validate this user id, password pair");
    }

}
