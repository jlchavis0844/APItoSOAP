package testing;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

/**
 * A class that loads and fetches codes for the county Names
 * @author jchavis
 *
 */
public final class CountyCodes {

	private static Map<String, Integer> tMap;
	private static Reader in;
	private static Iterable<CSVRecord> records;

	/**
	 * Default constructor. Loads data from CountyCodes.csv into map of County Name key, code value<br>
	 * Key: String of County Name<br>
	 * Value: Integer of County Code<br>
	 */
	static {
		try {
			tMap = new HashMap<String, Integer>();
			in = new InputStreamReader(new FileInputStream("CountyCodes.csv"), "UTF-8");
			records = CSVFormat.DEFAULT.withHeader().parse(in);

			for(CSVRecord rec: records){
				String key = rec.get(0).replaceAll("\u00A0", "");
				Integer value = Integer.valueOf(rec.get(1).replaceAll("\u00A0", ""));

				tMap.put(key, value);
			}//end for loop
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}//end catch	
	} // end constructor

	/**
	 * Fetches county code of the given county name
	 * @param countyName Name of the county
	 * @return integer of county code, 999 if county name is not found
	 */
	public static int getCountyCode(String countyName){
		if(!tMap.containsKey(countyName)){//if not found
			System.err.println("County code not found for " + countyName + "\n");
			return 999;//return error code
		} else {//return found code
			return (int)tMap.get(countyName);
		}
	}//end getCountyCode

	/**
	 * Returns County Name for given County Code
	 * @param countyCode County code to search for
	 * @return Name of the county
	 */
	public static String getCountyName(int countyCode){
		if(!tMap.containsValue(countyCode)){//if not found
			System.err.println("County Name not found for code " + countyCode + "\n");
		} else {//if value exists
			for(Entry<String, Integer> entry: tMap.entrySet()){
				if(entry.getValue() == Integer.valueOf(countyCode)){
					return entry.getKey();
				}
			}
		}
		return "Not Found"; //if not found, return this
	}

}//end class
