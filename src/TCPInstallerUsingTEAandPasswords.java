
import java.net.*;
import java.io.*;
import java.util.Scanner;
import org.json.JSONObject;
import org.json.JSONArray;

/**
 * This class is the client code which sends request to server
 * @author ankur
 */
public class TCPInstallerUsingTEAandPasswords {

    /**
     * This method builds a json object and out of the input values
     * @param userId user ID string
     * @param password password string
     * @param sensorId sensor ID string
     * @param longitude longitude of the sensor location
     * @param latitude latitude of the sensor location
     * @return the JSONObject created
     */
    public JSONObject buildJson(String userId,String password,String sensorId, String longitude,String latitude){
            JSONObject obj = new JSONObject();
            obj.put("ID", userId);
            obj.put("passwd", password);
            obj.put("Sensor ID", sensorId);
            obj.put("Longitude", longitude);
            obj.put("Latitude", latitude);
            return obj;
        }
        
    /**
     * Main method
     * @param args pass localhost
     */
    public static void main (String args[]) {
		// arguments supply message and hostname
		Socket s = null;
		try{
                        TCPInstallerUsingTEAandPasswords tiutp = new TCPInstallerUsingTEAandPasswords();
			int serverPort = 7898;
			s = new Socket(args[0], serverPort);    
			DataInputStream in = new DataInputStream( s.getInputStream());
			DataOutputStream out =new DataOutputStream( s.getOutputStream());
                        Scanner reader = new Scanner(System.in);
                        //input the symmetric key. Ask the user to re-enter key if it's less that 16 digit
                        String key;
                        do{
                            System.out.println("Enter symmetric key as a 16-digit integer.");
                            key = reader.nextLine();
                        }while(key.length() < 16);
                        
                        //set the key in a TEA object
                        TEA tea = new TEA(key.getBytes());
                        
                        //Get input from the user
                        System.out.println("Enter your ID: ");
                        String userId = reader.nextLine();
                        
                        System.out.println("Enter your password: ");
                        String password = reader.nextLine();
                        
                        System.out.println("Enter sensor ID: ");
                        String sensorId = reader.nextLine();
                        
                        System.out.println("Enter new Sensor location: ");
                        String location = reader.nextLine();
                        String locValue[];
                        locValue = location.split(",");
                        
                        //crate a JSON object, encrypt it and send it to the server
                        JSONObject obj = tiutp.buildJson(userId,password,sensorId,locValue[0],locValue[1]);
                        byte [] encryptedText =  tea.encrypt(obj.toString().getBytes());
                        out.writeInt(encryptedText.length); 
                        out.write(encryptedText);
                        
                        //read the response from the server and decrypt it
                        int len = in.readInt();                    
                        byte[] message = new byte[len];
                        in.read(message,0,len);
                        byte[] decryptedMessage = tea.decrypt(message);
                        
                        String output = new String(decryptedMessage);
                        System.out.println(output);
                        
		}catch (UnknownHostException e){
                    System.out.println("Socket:"+e.getMessage());
		}catch (EOFException e){
                    System.out.println("EOF:"+e.getMessage());
		}catch (IOException e){
                    System.out.println("readline:"+e.getMessage());
		}finally {
                    if(s!=null) try {
                        s.close();
                    }catch (IOException e){
                        System.out.println("close:"+e.getMessage());}}
     }
        
}
