
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * This class stores the installer details in a hashmap.
 * @author ankur
 */
public class Installer {
    //stores the intaller details with key as userId and salt,hash and title as arraylist
    HashMap<String, ArrayList<String>> map;
    
   
    /**
     * Installer constructor to initialize a hashmap
     */
    public Installer(){
        map = new HashMap<String, ArrayList<String>>();
    }

    /**
     * Adds an entry for a user in the hashmap
     * @param userId String of userId used as key for hashmap
     * @param salt String of salt value used for this user
     * @param hash String of hash value for this user's salt+password
     * @param title the title of this user
     */
    public void addElement(String userId, String salt, String hash, String title){
        ArrayList<String> val = new ArrayList<String>();
        val.add(salt);
        val.add(hash);
        val.add(title);
        map.put(userId,val);
    }
    
    /**
     * returns the details of a installer
     * @param userId Arraylist of installer information
     * @return
     */
    public ArrayList getValue(String userId){
        if(map.containsKey(userId))
        return map.get(userId);
        else return null;
    }
    
    /**
     * This method validates whether the user credentials are correct(authentication) 
     * @param userId
     * @param password
     * @return
     * @throws NoSuchAlgorithmException
     */
    public boolean validate(String userId, String password) throws NoSuchAlgorithmException{
        //calls the PasswordHash static method to authenticate the installer 
        if(map.containsKey(userId)) {
            if(PasswordHash.testCredentials(password, map.get(userId).get(0), map.get(userId).get(1))) return true;
        } 
        return false;
    }
    
    /**
     * This method checks whether the installer is a chief installer
     * @param userId String userId of the installer
     * @return true if the installer is a chief installer
     */
    public boolean checkChief(String userId){
        if(map.get(userId).get(2).equals("Chief Sensor Installer"))
            return true;
        return false;
    }
}
