
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

/**
 * This class stores details of all the sensors in a treeMap
 * @author ankur
 */
public class Sensor {
    //Stores the sensor detaisl with sensorId as key and sensorLocation as an arraylist values
    TreeMap<String, ArrayList<String>> tmap = null;

    /**
     * Constructor to initialize the tree map 
     */
    public Sensor(){
        tmap = new TreeMap<String, ArrayList<String>>();
    }
    
    /**
     * Adds a new entry for sensor in the treemap
     * @param sensorId String of sensor ID
     * @param longitude String of longitude value
     * @param latitude String of latitude value
     */
    public void addElement(String sensorId, String longitude, String latitude){
        ArrayList<String> val = new ArrayList<String>();
        val.add(longitude);
        val.add(latitude);
        tmap.put(sensorId,val);
    }
    
    /**
     * Checks if the sensor exists
     * @param sensorId String of sensor ID
     * @return returns true if the sensor already exists
     */
    public boolean ifExist(String sensorId){
        if(tmap.containsKey(sensorId)) return true;
        return false;
    }
    
    /**
     * Returns the treemap containing sensor details
     * @return
     */
    public TreeMap<String, ArrayList<String>> getTree(){
        return this.tmap;
    }
    
    /**
     * This method creates the kml file with all the sensor details
     * @return
     */
    public String toKML(){
                //basic kml layout
		StringBuffer kml = new StringBuffer();
		kml.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		kml.append("<kml xmlns=\"http://earth.google.com/kml/2.2\">\n");
		kml.append("<Document>\n");
		kml.append("<Style id=\"style1\">\n<IconStyle>\n<Icon>\n");
		kml.append("<href>http://icons.iconarchive.com/icons/pelfusion/long-shadow-media/128/Microphone-icon.png</href>\n");
		kml.append("</Icon>\n</IconStyle>\n");
		kml.append("</Style>\n");
		//while there are sensors keep adding sensor details and coordinates in different placemark
                for(Map.Entry<String,ArrayList<String>> entry : tmap.entrySet()){
			kml.append("<Placemark>\n");
			kml.append("<name>Sensor" + entry.getKey() + "</name>\n");
			kml.append("<description>audio</description>\n");
			kml.append("<styleUrl>#style1</styleUrl>\n");
			kml.append("<Point>\n");
			kml.append("<coordinates>\n");
			kml.append(entry.getValue().get(0) + "," + entry.getValue().get(1) + ",0.000000\n");
			kml.append("</coordinates>\n");
			kml.append("</Point>\n");
			kml.append("</Placemark>\n");
		}
		kml.append("</Document>\n");
		kml.append("</kml>\n");
                //Create a kml file with all the sensor details
                try{
                    String deskTopLocation = System.getProperty("user.home") + "/Desktop/Sensors.kml";
                    BufferedWriter out = new BufferedWriter(new FileWriter(new File(deskTopLocation)));
                    out.write(kml.toString());  //Replace with the string 
                    out.flush();
                    out.close();     
                } catch (IOException e){
                    System.out.println("Throw error");
                    }
		return kml.toString();
    }
}
